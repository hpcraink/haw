package haw.common.persistence.service;

import haw.common.action.annotation.Action;
import haw.common.action.service.EntityEventService;
import haw.common.action.service.NotificationEventService;
import haw.common.error.handler.HawErrorHandler;
import haw.common.exception.HawAuthorizationException;
import haw.common.exception.HawResourceConflictException;
import haw.common.exception.HawResourceNotExistsException;
import haw.common.exception.HawServerException;
import haw.common.exception.HawValidationException;
import haw.common.resource.entity.helper.ReflectionHelper;
import haw.common.persistence.annotation.PersistenceService;
import haw.common.persistence.handler.ActionHandler;
import haw.common.persistence.handler.ResultConvertor;
import haw.common.resource.base.HawEntity;
import haw.common.resource.base.HawWrapper;
import haw.common.resource.base.change.ChangeSet;
import haw.common.resource.query.LimitQuery;
import haw.common.security.UserContext;
import haw.common.validator.HawValidator;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;

/**
 * This is the common persistence class for the HAW entities.
 *
 * @param <E> The entity
 * @param <W> The wrapper of the entity
 * @author Uwe Eisele
 */
@Dependent
@PersistenceService
@SuppressWarnings("rawtypes") //Injection does not work if HawWrapper has a generic type
public class HawPersistenceService<E extends HawEntity, W extends HawWrapper> {

    /**
     * The entity manager.
     */
    @Inject
    private EntityManager entityManager;

    /**
     * The entity event service for firing actions.
     */
    @Inject
    private EntityEventService entityEventService;

    /**
     * The notification event service.
     */
    @Inject
    private NotificationEventService notificationEventService;

    /**
     * The error handler.
     */
    @Inject
    private HawErrorHandler errorHandler;

    /**
     * The validator.
     */
    @Inject
    private HawValidator validator;

    /**
     * The user context.
     */
    @Inject
    private UserContext userContext;

    /**
     * The logger.
     */
    @Inject
    private Logger logger;

    /**
     * The class of the entity.
     */
    private final Class<E> ENTITY_CLASS;

    /**
     * The class of the wrapper.
     */
    private final Class<W> WRAPPER_CLASS;

    /**
     * The name of the entity.
     */
    private final String ENTITY_NAME;

    /**
     * Indicates if actions are fired by this service.
     */
    private final boolean FIRE_ACTIONS;

    /**
     * Instantiates a new JakPersistenceService. The fields which are annotated
     * with @Injected are not instantiated.
     *
     * @param entityClass the class of the entity
     * @param wrapperClass the class of the wrapper
     * @param fireActions indicates if actions should be fired
     */
    protected HawPersistenceService(Class<E> entityClass,
            Class<W> wrapperClass, boolean fireActions) {
        this.ENTITY_CLASS = entityClass;
        this.WRAPPER_CLASS = wrapperClass;
        this.ENTITY_NAME = ENTITY_CLASS.getSimpleName();
        this.FIRE_ACTIONS = fireActions;
    }

    /**
     * Instantiates a new JakPersistenceService based on the InjectionPoint.
     *
     * @param ip the injection point
     */
    @Inject
    @SuppressWarnings("unchecked")
    public HawPersistenceService(InjectionPoint ip) {
        ParameterizedType type = (ParameterizedType) ip.getType();
        Type[] typeArgs = type.getActualTypeArguments();
        this.ENTITY_CLASS = (Class<E>) typeArgs[0];
        this.WRAPPER_CLASS = (Class<W>) typeArgs[1];
        this.ENTITY_NAME = ENTITY_CLASS.getSimpleName();
        PersistenceService annotation
                = ip.getAnnotated().getAnnotation(PersistenceService.class);
        this.FIRE_ACTIONS = annotation.fireActions();
    }

    /**
     * Returns all elements of the managed entity matching the given limitation
     * query.
     *
     * This query gets all elements including the given limitation query of
     * number of entries per page of a specific page number. Passing
     * {@code false} in {@code onlyIdAndName} will result in the named queries
     * {@code '<EntityName>.findAll'} for the elements and the corresponding
     * query {@code '<EntityName>.findAllCount'}) for the count of elements
     * returned. Passing {@code true} in {@code onlyIdAndName} will return only
     * the (mandatory) id of the element plus a proper name -- both of which
     * need be converted from the JPA named query.
     *
     * @param query Limitation for results to be returned.
     * @param onlyIdAndName if true only the id and the name of the entity will
     * be returned.
     * @param convertor A converter for converting the result. Is required if
     * onlyIdAndName is true.
     * @return A wrapper containing the result list
     * @throws HawServerException Thrown if problem on database occurs.
     */
    public W getAll(LimitQuery query, boolean onlyIdAndName, ResultConvertor<E> convertor)
            throws HawServerException {
        if (!onlyIdAndName) {
            return getAll(query);
        } else {
            return getAllOnlyIDAndName(query, convertor);
        }
    }

    /**
     * Returns all elements of the managed entity matching the given limitation
     * query. (Expects named queries {@code '<EntityName>.findAll'} and
     * {@code '<EntityName>.findAllCount'})
     *
     * @param query Limitation for results to be returned.
     * @return A wrapper containing the result list
     * @throws HawServerException Thrown if problem on database occurs.
     */
    public W getAll(LimitQuery query) throws HawServerException {
        return getByNamedQuery("findAll", "findAllCount", query);
    }

    /**
     * Returns all elements of the managed entity matching the given limitation
     * query. (Expects named queries {@code '<EntityName>.findAllOnlyIDAndName'}
     * and {@code '<EntityName>.findAllCount'})
     *
     * @param query Limitation for results to be returned.
     * @param convertor A converter for converting the result.
     * @return A wrapper containing the result list
     * @throws HawServerException Thrown if problem on database occurs.
     */
    public W getAllOnlyIDAndName(LimitQuery query, ResultConvertor<E> convertor) throws HawServerException {
        return getByNamedQuery("findAllOnlyIDAndName", "findAllCount", query, null, convertor);
    }

    /**
     * Returns the elements of the managed entity matching the given named query
     * and the given limitation query.
     *
     * @param queryName The name of the query (Expects query with name
     * {@code '<EntityName>.<QueryName>'})
     * @param countQueryName The name of the count query (Expects query with
     * name {@code '<EntityName>.<CountQueryName>'})
     * @param query Limitation for results to be returned.
     * @return A wrapper containing the result list
     * @throws HawServerException Thrown if problem on database occurs.
     */
    public W getByNamedQuery(String queryName, String countQueryName, LimitQuery query)
            throws HawServerException {
        return getByNamedQuery(queryName, countQueryName, query, null, null);
    }

    /**
     * Returns the elements of the managed entity matching the given named query
     * and the given limitation query.
     *
     * @param queryName The name of the query (Expects query with name
     * {@code '<EntityName>.<QueryName>'})
     * @param countQueryName The name of the count query (Expects query with
     * name {@code '<EntityName>.<CountQueryName>'})
     * @param query Limitation for results to be returned.
     * @param parameters The query parameters
     * @return A wrapper containing the result list
     * @throws HawServerException Thrown if problem on database occurs.
     */
    public W getByNamedQuery(String queryName, String countQueryName, LimitQuery query, Map<String, Object> parameters)
            throws HawServerException {
        return getByNamedQuery(queryName, countQueryName, query, parameters, null);
    }

    /**
     * Returns the elements of the managed entity matching the given named query
     * and the given limitation query.
     *
     * @param queryName The name of the query (Expects query with name
     * {@code '<EntityName>.<QueryName>'})
     * @param countQueryName The name of the count query (Expects query with
     * name {@code '<EntityName>.<CountQueryName>'})
     * @param query Limitation for results to be returned.
     * @param parameters The query parameters
     * @param convertor A converter for converting the result.
     * @return A wrapper containing the result list
     * @throws HawServerException Thrown if problem on database occurs.
     */
    @SuppressWarnings("unchecked")
    public W getByNamedQuery(String queryName, String countQueryName, LimitQuery query, Map<String, Object> parameters, ResultConvertor<E> convertor)
            throws HawServerException {
        List<E> result = null;
        int pages = 0;
        long rowcount = 0;
        try {
            TypedQuery<E> jpaQuery = entityManager.createNamedQuery(
                    ENTITY_NAME + "." + queryName, ENTITY_CLASS);
            setParameter(jpaQuery, parameters);
            if (query.hasLimit()) {
                jpaQuery.setMaxResults(query.getLimit())
                        .setFirstResult(query.getFrom());
                result = jpaQuery.getResultList();
                TypedQuery<Long> countQuery = entityManager
                        .createNamedQuery(ENTITY_NAME + "." + countQueryName, Long.class);
                setParameter(countQuery, parameters);
                rowcount = countQuery.getSingleResult();
                pages = query.calculateNoOfPages(rowcount);
            } else {
                result = jpaQuery.getResultList();
                rowcount = result.size();
                pages = 1;
            }
        } catch (Throwable e) {
            errorHandler.handleTechnicalResourceError(e, ENTITY_NAME);
        }
        if (convertor != null) {
            result = convertResult(result, convertor);
        }
        return (W) ReflectionHelper.createHawWrapper(WRAPPER_CLASS, result, query.getPage(), pages, (int) rowcount);
    }

    /**
     * Converts the given result list with the given result convertor.
     *
     * @param result The result to be converted
     * @param convertor A convertor for converting the result.
     * @return Returns the converted result list
     */
    private List<E> convertResult(List<E> result, ResultConvertor<E> convertor) {
        List<E> convertedResult = new LinkedList<>();
        for (Object entity : result) {
            E convertedEntity = convertor.convertEntity(entity);
            convertedResult.add(convertedEntity);
        }
        return convertedResult;
    }

    /**
     * Returns the result entity matching the given named query.
     *
     * @param queryName The named query
     * @return The result entity of the query
     * @throws HawServerException Thrown if problem on database occurs or more
     * then one result retrieved.
     * @throws HawResourceNotExistsException Thrown if resource with the given
     * id does not exist
     */
    public E getByNamedQuerySingleResult(String queryName)
            throws HawServerException, HawResourceNotExistsException {
        return getByNamedQuerySingleResult(queryName, null);
    }

    /**
     * Returns the result entity matching the given named query.
     *
     * @param queryName The named query
     * @param parameters The query parameters
     * @return The result entity of the query
     * @throws HawServerException Thrown if problem on database occurs or more
     * then one result retrieved.
     * @throws HawResourceNotExistsException Thrown if resource with the given
     * id does not exist
     */
    public E getByNamedQuerySingleResult(String queryName, Map<String, Object> parameters)
            throws HawServerException, HawResourceNotExistsException {
        return getByNamedQuerySingleResult(queryName, parameters, null);
    }

    /**
     * Returns the result entity matching the given named query.
     *
     * @param queryName The named query
     * @param parameters The query parameters
     * @param convertor A convertor for converting the result.
     * @return The result entity of the query
     * @throws HawServerException Thrown if problem on database occurs or more
     * then one result retrieved.
     * @throws HawResourceNotExistsException Thrown if resource with the given
     * id does not exist
     */
    public E getByNamedQuerySingleResult(String queryName, Map<String, Object> parameters, ResultConvertor<E> convertor)
            throws HawServerException, HawResourceNotExistsException {
        E result = null;
        try {
            TypedQuery<E> jpaQuery = entityManager.createNamedQuery(
                    ENTITY_NAME + "." + queryName, ENTITY_CLASS);
            setParameter(jpaQuery, parameters);
            result = jpaQuery.getSingleResult();
            if (convertor != null) {
                result = convertor.convertEntity(result);
            }
        } catch (NoResultException e) {
            errorHandler.handleResourceNotFoundError(e, ENTITY_NAME, queryName, parameters);
        } catch (NonUniqueResultException e) {
            errorHandler.handleTechnicalResourceNotUniqueError(e, ENTITY_NAME, queryName, parameters);
        } catch (Throwable e) {
            errorHandler.handleTechnicalResourceError(e, ENTITY_NAME);
        }
        return result;
    }

    /**
     * Sets the give parameters to the given query.
     *
     * @param {@code <T>} the generic type
     * @param jpaQuery The typed query
     * @param parameters The parameters to set
     */
    private <T> void setParameter(TypedQuery<T> jpaQuery, Map<String, Object> parameters) {
        if (parameters != null) {
            for (Entry<String, Object> param : parameters.entrySet()) {
                jpaQuery.setParameter(param.getKey(), param.getValue());
            }
        }
    }

    /**
     * Returns the entity with the given id.
     *
     * @param id The id of the entity to return (the primary key)
     * @return Entity with the given id
     * @throws HawServerException Thrown if problem on database occurs.
     * @throws HawResourceNotExistsException Thrown if resource with the given
     * id does not exist
     */
    public E getById(Integer id) throws HawServerException, HawResourceNotExistsException {
        E entity = getByIdIfExists(id);
        if (entity == null) {
            errorHandler.handleResourceNotFoundError(ENTITY_NAME, id);
        }
        return entity;
    }

    /**
     * Returns the entity with the given id or null if not exists.
     *
     * @param id The id of the entity to return (the primary key)
     * @return Entity with the given id or null if entity does not exists.
     * @throws HawServerException Thrown if problem on database occurs.
     */
    public E getByIdIfExists(Integer id) throws HawServerException {
        E entity = null;
        try {
            entity = entityManager.find(ENTITY_CLASS, id);
        } catch (Throwable e) {
            errorHandler.handleTechnicalResourceError(e, ENTITY_NAME);
        }
        return entity;
    }

    /**
     * Adds a new entity. Id of entity must not be set. If 'fireActions' is true
     * an {@code <EntityEvent>} with annotation @ActionCreate is fired after the
     * entity has been created.
     *
     * @param entity Entity to create
     * @return Created entity with new unique id.
     * @throws HawServerException Thrown if problem on database occurs.
     * @throws HawValidationException Thrown if passed entity is not valid.
     */
    public E add(E entity) throws HawServerException, HawValidationException {
        return add(entity, null);
    }

    /**
     * Adds a new entity. Id of entity must not be set. If 'fireActions' is true
     * an {@code <EntityEvent>} with annotation @ActionCreate is fired after the
     * entity has been created.
     *
     * @param entity Entity to create
     * @param handler The handler is executed before the {@code <EntityEvent>}
     * is fired.
     * @return Created entity with new unique id.
     * @throws HawServerException Thrown if problem on database occurs.
     * @throws HawValidationException Thrown if passed entity is not valid.
     */
    public E add(E entity, ActionHandler<E> handler)
            throws HawServerException, HawValidationException {
        ChangeSet changeSet = entity.create(userContext.getPrincipal());
        validator.validate(entity);
        try {
            entityManager.persist(entity);
            entityManager.flush();
            entityManager.refresh(entity);
            if (handler != null) {
                handler.afterAction(Action.CREATE, entity, changeSet);
            }
            if (FIRE_ACTIONS) {
                entityEventService.fireAction(changeSet);
                if (changeSet.hasSubChangesForNotification()) {
                    notificationEventService.fireAction(changeSet);
                }
            }
        } catch (HawServerException e) {
            throw e;
        } catch (ConstraintViolationException e) {
            Set violations = e.getConstraintViolations();
            if (violations.isEmpty()) {
                throw new HawValidationException("Unkown validation error");
            } else {
                throw new HawValidationException(violations.toString());
            }
        } catch (Throwable e) {
            errorHandler.handleTechnicalResourceError(e, ENTITY_NAME);
        }
        return entity;
    }

    /**
     * Updates an existing entity. Id of entity must be set. If 'fireActions' is
     * true an {@code <EntityEvent>} with annotation @ActionUpdate is fired
     * after the entity has been updated.
     *
     * @param newEntity Entity to update. All attributes are set to the existing
     * entity which has the same id.
     * @return The updated entity
     * @throws HawServerException Thrown if problem on database occurs.
     * @throws HawAuthorizationException Thrown if the logged in user has no
     * permission to update a changed attribute.
     * @throws HawValidationException Thrown if passed entity is not valid.
     * @throws HawResourceNotExistsException Thrown if passed entity does not
     * exist.
     * @throws HawResourceConflictException Thrown if update conflicts with
     * another update request.
     */
    public E update(E newEntity)
            throws HawServerException, HawAuthorizationException,
            HawValidationException, HawResourceNotExistsException, HawResourceConflictException {
        return update(newEntity, (ActionHandler<E>) null);
    }

    /**
     * Updates an existing entity. Id of entity must be set. If 'fireActions' is
     * true an {@code <EntityEvent>} with annotation @ActionUpdate is fired
     * after the entity has been updated.
     *
     * @param newEntity Entity to update. All attributes are set to the existing
     * entity which has the same id.
     * @param handler The handler is executed before the {@code <EntityEvent>}
     * is fired.
     * @return The updated entity
     * @throws HawServerException Thrown if problem on database occurs.
     * @throws HawValidationException Thrown if passed entity is not valid.
     * @throws HawResourceNotExistsException Thrown if passed entity does not
     * exist.
     * @throws HawResourceConflictException Thrown if update conflicts with
     * another update request.
     * @throws HawAuthorizationException Thrown if the logged in user has no
     * permission to update a changed attribute.
     */
    public E update(E newEntity, ActionHandler<E> handler) throws HawServerException, HawValidationException,
            HawResourceNotExistsException, HawResourceConflictException, HawAuthorizationException {
        E managedEntity = getById(newEntity.getId());
        return update(managedEntity, newEntity, handler);
    }

    /**
     * Updates the given managed entity. The given newEntity is merged in the
     * managed entity. The id of the new entity must be set. If 'fireActions' is
     * true an {@code <EntityEvent>} with annotation @ActionUpdate is fired
     * after the entity has been updated.
     *
     * @param managedEntity The managed entity to be updated
     * @param newEntity The entity with the new attributes
     * @return The updated entity
     * @throws HawServerException Thrown if problem on database occurs.
     * @throws HawValidationException Thrown if passed entity is not valid.
     * @throws HawResourceConflictException Thrown if update conflicts with
     * another update request.
     * @throws HawAuthorizationException Thrown if the logged in user has no
     * permission to update a changed attribute.
     */
    public E update(E managedEntity, E newEntity) throws HawServerException,
            HawValidationException, HawResourceConflictException, HawAuthorizationException {
        return update(managedEntity, newEntity, null);
    }

    /**
     * Updates the given managed entity. The given newEntity is merged in the
     * managed entity. The id of the new entity must be set. If 'fireActions' is
     * true an {@code <EntityEvent>} with annotation @ActionUpdate is fired
     * after the entity has been updated.
     *
     * @param managedEntity The managed entity to be updated
     * @param newEntity The entity with the new attributes
     * @param handler The handler is executed before the {@code <EntityEvent>}
     * is fired.
     * @return The updated entity
     * @throws HawServerException Thrown if problem on database occurs.
     * @throws HawValidationException Thrown if passed entity is not valid.
     * @throws HawResourceConflictException Thrown if update conflicts with
     * another update request.
     * @throws HawAuthorizationException Thrown if the logged in user has no
     * permission to update a changed attribute.
     */
    public E update(E managedEntity, E newEntity, ActionHandler<E> handler) throws HawServerException,
            HawValidationException, HawResourceConflictException, HawAuthorizationException {
        ChangeSet changeSet = managedEntity.merge(newEntity, userContext.getPrincipal());
        if (changeSet.hasChanges()) {
            validator.validate(managedEntity);
            try {
                managedEntity = entityManager.merge(managedEntity);
                entityManager.flush();
                if (handler != null) {
                    handler.afterAction(Action.UPDATE, managedEntity, changeSet);
                }
                if (FIRE_ACTIONS) {
                    entityEventService.fireAction(changeSet);
                    if (changeSet.hasSubChangesForNotification()) {
                        notificationEventService.fireAction(changeSet);
                    }
                }
            } catch (HawServerException e) {
                throw e;
            } catch (OptimisticLockException e) {
                errorHandler.handleResourceAlreadyUpdatedError(e, ENTITY_NAME, managedEntity.getId());
            } catch (PersistenceException e) {
                handlePersistenceException(e, managedEntity);
            } catch (Throwable e) {
                errorHandler.handleTechnicalResourceError(e, ENTITY_NAME);
            }
        }
        return managedEntity;
    }

    /**
     * Deletes the entity with the given id. If entity does not exist, nothing
     * happens. If 'fireActions' is true an {@code <EntityEvent>} with
     * annotation @ActionDeleted is fired after the entity has been removed.
     *
     * @param id The id of the entity to be deleted
     * @throws HawServerException Thrown if problem on database occurs.
     * @throws HawResourceConflictException Thrown if entity can't be removed
     * because of some conflicts
     */
    public void removeById(Integer id) throws HawServerException, HawResourceConflictException {
        removeById(id, null);
    }

    /**
     * Deletes the entity with the given id. If entity does not exist, nothing
     * happens. If 'fireActions' is true an {@code <EntityEvent>} with
     * annotation @ActionDeleted is fired after the entity has been removed.
     *
     * @param id The id of the entity to be deleted
     * @param handler The handler is executed before the {@code <EntityEvent>}
     * is fired
     * @throws HawServerException Thrown if problem on database occurs.
     * @throws HawResourceConflictException Thrown if entity can't be removed
     * because of some conflicts
     */
    public void removeById(Integer id, ActionHandler<E> handler)
            throws HawServerException, HawResourceConflictException {
        E entity = null;
        if (id != null) {
            entity = getByIdIfExists(id);
        }
        if (entity != null) {
            remove(entity, handler);
        }
    }

    /**
     * Deletes the given entity (Id must be set). If entity does not exist,
     * nothing happens. If 'fireActions' is true an {@code <EntityEvent>} with
     * annotation @ActionDeleted is fired after the entity has been removed.
     *
     * @param entity Entity to be deleted
     * @throws HawServerException Thrown if problem on database occurs.
     * @throws HawResourceConflictException Thrown if entity can't be removed
     * because of some conflicts
     */
    public void remove(E entity) throws HawServerException, HawResourceConflictException {
        remove(entity, null);
    }

    /**
     * Deletes the given entity (Id must be set). If entity does not exist,
     * nothing happens. If 'fireActions' is true an {@code <EntityEvent>} with
     * annotation @ActionDeleted is fired after the entity has been removed.
     *
     * @param entity Entity to be deleted
     * @param handler The handler is executed before the {@code <EntityEvent>}
     * is fired
     * @throws HawServerException Thrown if problem on database occurs.
     * @throws HawResourceConflictException Thrown if entity can't be removed
     * because of some conflicts
     */
    public void remove(E entity, ActionHandler<E> handler)
            throws HawServerException, HawResourceConflictException {
        ChangeSet changeSet = entity.remove(userContext.getPrincipal());
        try {
            entityManager.remove(entity);
            // Flush is required for detect constraint violations immediately
            entityManager.flush();
            if (handler != null) {
                handler.afterAction(Action.DELETE, entity, changeSet);
            }
            if (FIRE_ACTIONS) {
                entityEventService.fireAction(changeSet);
            }
        } catch (HawServerException e) {
            throw e;
        } catch (PersistenceException e) {
            handlePersistenceException(e, entity);
        } catch (Throwable e) {
            errorHandler.handleTechnicalResourceError(e, ENTITY_NAME);
        }
    }

    /**
     * Refreshes the entity with the given id. If entity does not exists nothing
     * happens.
     *
     * @param id The id of the entity to be refreshed.
     */
    public void refresh(Integer id) {
        try {
            E entity = entityManager.find(ENTITY_CLASS, id);
            if (entity != null) {
                entityManager.refresh(entity);
            }
        } catch (Throwable e) {
            String message = String.format("Error during refresh of entity '%s' with id '%d': %s - %s",
                    ENTITY_NAME, id, e.getClass(), e.getMessage());
            logger.warn(message, e);
        }
    }

    /**
     * Returns the entity manager of this service.
     *
     * @return The entity manager
     */
    public EntityManager getEntityManager() {
        return entityManager;
    }

    /**
     * Returns the entity event service of this service.
     *
     * @return The entity event service
     */
    public EntityEventService getEntityEventService() {
        return entityEventService;
    }

    /**
     * Returns the name of the managed entity.
     *
     * @return The name of the managed entity.
     */
    public String getEntityName() {
        return ENTITY_NAME;
    }

    /**
     * Indicates whether this service fires actions or not.
     *
     * @return true if actions are fired, false if not.
     */
    public boolean isFireActions() {
        return FIRE_ACTIONS;
    }

    /**
     * Handles persistence exception.
     *
     * @param ex The exception
     * @param entity The entity
     * @throws HawServerException Thrown if cause is not a SQLException
     * @throws HawResourceConflictException Thrown if cause is a SQLException
     */
    private void handlePersistenceException(PersistenceException ex, E entity) throws HawServerException, HawResourceConflictException {
        if (ex.getCause() != null && ex.getCause().getCause() != null) {
            if (ex.getCause().getCause() instanceof SQLException) {
                SQLException sqlEx = (SQLException) ex.getCause().getCause();
                errorHandler.handleResourceConstraintViolationError(sqlEx, ENTITY_NAME, entity.getId());
            }
        }
        errorHandler.handleTechnicalResourceError(ex, ENTITY_NAME);
    }
}
