package haw.module.person.service;

import haw.common.action.annotation.Action;
import haw.common.action.service.NotificationEventService;
import haw.common.error.handler.HawErrorHandler;
import haw.common.exception.HawAuthenticationException;
import haw.common.exception.HawAuthorizationException;
import haw.common.exception.HawResourceConflictException;
import haw.common.exception.HawResourceNotExistsException;
import haw.common.exception.HawServerException;
import haw.common.exception.HawValidationException;
import haw.common.logging.Track;
import haw.common.persistence.annotation.PersistenceService;
import haw.common.persistence.handler.ActionHandler;
import haw.common.persistence.handler.ResultConvertor;
import haw.common.persistence.service.HawPersistenceService;
import haw.common.resource.base.change.ChangeSet;
import haw.common.resource.base.list.MergableList;
import haw.common.resource.entity.Login;
import haw.common.resource.entity.Person;
import haw.common.resource.query.LimitQuery;
import haw.common.resource.type.Role;
import haw.common.resource.wrapper.Persons;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;
import lombok.NonNull;

/**
 * The Class PersonPersistanceService.
 */
@Track
@Stateless
public class PersonPersistanceService implements PersonService {

    /**
     * The persistence service.
     */
    @Inject
    @PersistenceService(fireActions = true)
    private HawPersistenceService<Person, Persons> persistenceService;

    /**
     * The person business.
     */
    @Inject
    private PersonBusinessService personBusiness;

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
     * The Constant RESULT_CONVERTOR.
     */
    private static final ResultConvertor<Person> RESULT_CONVERTOR = new ResultConvertor<Person>() {

        /*
         * (non-Javadoc)
         * @see
         * haw.common.persistence.handler.ResultConvertor#convertEntity(java
         * .lang.Object)
         */
        @Override
        public Person convertEntity(Object entity) {
            Person newEntity = new Person();
            Object[] neuE = (Object[]) entity;
            // Please check, that order is correct in Person.java
            newEntity.setId((Integer) neuE[0]);
            newEntity.setFirstName((String) neuE[1]);
            newEntity.setLastName((String) neuE[2]);
            newEntity.setEmail((String) neuE[3]);
            return newEntity;
        }
    };

    /*
     * (non-Javadoc)
     * @see
     * haw.module.person.service.PersonService#getAllPersons(haw.common.resource
     * .query.LimitQuery, boolean)
     */
    @Override
    public Persons getAllPersons(LimitQuery query, boolean onlyIdAndName)
            throws HawServerException {
        return persistenceService.getAll(query, onlyIdAndName, RESULT_CONVERTOR);
    }

    /*
     * (non-Javadoc)
     * @see
     * haw.module.person.service.PersonService#getPersonById(java.lang.Integer)
     */
    @Override
    public Person getPersonById(int id) throws HawServerException,
            HawResourceNotExistsException {
        return persistenceService.getById(id);
    }

    /*
     * (non-Javadoc)
     * @see
     * haw.module.person.service.PersonService#getPersonsByRole(haw.common.resource
     * .query.LimitQuery, java.util.List)
     */
    @Override
    public Persons getPersonsByRoles(LimitQuery query, Collection<Role> roles)
            throws HawServerException {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("role", new MergableList<>(roles));
        return persistenceService.getByNamedQuery("findByRole", "findByRoleCount", query, parameters);
    }

    /*
     * (non-Javadoc)
     * @see
     * haw.module.person.service.PersonService#getAllDeletedPersons(haw.common
     * .resource.query.LimitQuery)
     */
    @Override
    public Persons getAllDeletedPersons(LimitQuery query)
            throws HawServerException {
        return persistenceService.getByNamedQuery("findAllDeleted", "findAllDeletedCount", query);
    }

    /*
     * (non-Javadoc)
     * @see
     * haw.module.person.service.PersonService#getPersonByEmail(java.lang.String)
     */
    @Override
    public Person getPersonByEmail(String email)
            throws HawServerException, HawResourceNotExistsException {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("email", email);
        return persistenceService.getByNamedQuerySingleResult("findByEmail", parameters);
    }

    /*
     * (non-Javadoc)
     * @see
     * haw.module.person.service.PersonService#getPersonByUniversity(java.lang.String)
     */
    @Override
    public Person getPersonByUniversity(String university) throws HawServerException,
            HawResourceNotExistsException {
        Map<String, Object> parameters = new HashMap<>();
        // XXX/TODO Find the University ID, the check for that?
        parameters.put("university", university);
        return persistenceService.getByNamedQuerySingleResult("findByUniversity", parameters);
    }

    /*
     * (non-Javadoc)
     * @see
     * haw.module.person.service.PersonService#addPerson(haw.common.resource
     * .entity.Person)
     */
    @Override
    public Person addPerson(Person person) throws HawServerException,
            HawValidationException {
        return persistenceService.add(person, new ActionHandler<Person>() {
            @Override
            public void afterAction(Action action, Person entity, ChangeSet changeSet)
                    throws HawServerException {
                personBusiness.createOrUpdateLoginInformation(entity, false, null);
                persistenceService.getEntityManager().flush();
            }
        });
    }

    /*
     * (non-Javadoc)
     * @see
     * haw.module.person.service.PersonService#reactivatePersonById(java.lang
     * .Integer)
     */
    @Override
    public void reactivatePersonById(int personId)
            throws HawServerException, HawResourceNotExistsException {
        Person person = persistenceService.getById(personId);
        try {
            if (person.getDeleted()) {
                person.setDeleted(Boolean.FALSE);
                personBusiness.createOrUpdateLoginInformation(person, true, null);
                persistenceService.getEntityManager().flush();
                persistenceService.getEntityEventService().fireAction(Action.CREATE, person);
            }
        } catch (Exception e) {
            errorHandler.handleTechnicalResourceError(e, persistenceService.getEntityName());
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * haw.module.person.service.PersonService#updatePerson(haw.common.resource
     * .entity.Person)
     */
    @Override
    public Person updatePerson(Person newPerson) throws HawServerException,
            HawValidationException, HawResourceNotExistsException,
            HawResourceConflictException, HawAuthorizationException {
        return persistenceService.update(newPerson);
    }

    /*
     * (non-Javadoc)
     * @see haw.module.person.service.PersonService#changePW(java.lang.Integer,
     * java.lang.String, java.lang.String, boolean)
     */
    @Override
    public Person changePW(Integer personID, String oldPW, String newPW,
            boolean admin) throws HawServerException, HawValidationException,
            HawResourceNotExistsException, HawAuthenticationException {
        Person person = getPersonById(personID);
        Login logInOld = person.getLogin();
        if (!admin) {
            String salt = logInOld.getSalt();
            String saltedPassDB = logInOld.getPassword();
            String saltedPassInput = personBusiness.encryptPW(oldPW, salt);
            if (!saltedPassInput.equals(saltedPassDB)) {
                errorHandler.handleAuthenticationError();
            }
            personBusiness.createOrUpdateLoginInformation(person, true, newPW);
        } else {
            boolean update = (logInOld != null);
            personBusiness.createOrUpdateLoginInformation(person, update, null);
        }
        return person;
    }

    /*
     * (non-Javadoc)
     * @see
     * haw.module.person.service.PersonService#removePersonById(java.lang.Integer
     * )
     */
    @Override
    public void removePersonById(@NonNull Integer id) throws HawServerException {
        Person person = persistenceService.getByIdIfExists(id);
        if (person != null) {
            removePerson(person);
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * haw.module.person.service.PersonService#removePerson(haw.common.resource
     * .entity.Person)
     */
    @Override
    public void removePerson(Person person) throws HawServerException {
        try {
            if (!person.getDeleted()) {
                person.setDeleted(Boolean.TRUE);
                persistenceService.getEntityManager().flush();
            }
        } catch (Exception e) {
            errorHandler.handleTechnicalResourceError(e, persistenceService.getEntityName());
        }
        if (persistenceService.isFireActions()) {
            persistenceService.getEntityEventService().fireAction(Action.DELETE, person);
            notificationEventService.fireAction(Action.DELETE, person);
        }
    }

}
