package haw.common.action.service;

import haw.common.action.annotation.Action;
import haw.common.action.annotation.AffectedEntityLiteral;
import haw.common.action.model.EntityAction;
import haw.common.resource.base.HawEntity;
import haw.common.resource.base.change.ChangeSet;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;

/**
 * Service for firing entity events. This class encapsulates the
 * javax.enterprise.event.Event and is therefore a convenience class for
 * handling events.
 *
 * @author Uwe Eisele
 */
@ApplicationScoped
public class EntityEventService {

    /**
     * The event for any EntityAction. Specification is done in the fireAction
     * Methods regarding the provided parameters.
     */
    @Inject
    private Event<EntityAction> event;

    /**
     * Fire actions for the given changeSet.
     *
     * @param changeSet the change set
     */
    public void fireAction(ChangeSet changeSet) {
        fireActionSingleChangeSet(changeSet);
        for (ChangeSet subChange : changeSet.getSubChanges(HawEntity.class)) {
            fireAction(subChange);
        }
    }

    /**
     * Fire action single change set.
     *
     * @param changeSet the change set
     */
    private void fireActionSingleChangeSet(ChangeSet changeSet) {
        if (changeSet.hasEntityChanges()) {
            HawEntity affectedEntity = changeSet.getEntity(HawEntity.class);
            String normalizedAffectedEntityName = affectedEntity
                    .getNormalizedEntityName();
            EntityAction entityAction = mapEntityToAction(
                    changeSet.getAction(), changeSet.getPath(), affectedEntity);
            event.select(changeSet.getAction().getAnnotation(),
                    new AffectedEntityLiteral(normalizedAffectedEntityName)).fire(
                            entityAction);
        }
    }

    /**
     * Fires the given action for the given affected entity. Depending on action
     * a event with annotation @ActionCreate, @ActionUpdate or @ActionDelete is
     * fired. To identify the entity in addition the event is also annotated
     * with @AffectedEntity("{name of the entity}"). Observers can catch a event
     * with at least one matching annotation.
     *
     * @param action the action
     * @param affectedEntity the affected entity
     */
    public void fireAction(Action action, HawEntity affectedEntity) {
        String normalizedAffectedEntityName = affectedEntity
                .getNormalizedEntityName();
        EntityAction entityAction = mapEntityToAction(action, "",
                affectedEntity);
        event.select(action.getAnnotation(),
                new AffectedEntityLiteral(normalizedAffectedEntityName)).fire(
                        entityAction);
    }

    /**
     * Fires the given action for the given affected entity. Depending on action
     * a event with annotation @ActionCreate, @ActionUpdate or @ActionDelete is
     * fired. To identify the entity in addition the event is also annotated
     * with @AffectedEntity("{name of the entity}"). Observers can catch a event
     * with at least one matching annotation.
     *
     * @param action the action
     * @param entityName the name of the affected entity
     * @param entityId the id of the affected entity
     */
    public void fireAction(Action action, String entityName, int entityId) {
        String normalizedAffectedEntityName = entityName.toLowerCase();
        EntityAction entityAction = new EntityAction(entityId, entityName);
        entityAction.setAction(action);
        event.select(action.getAnnotation(),
                new AffectedEntityLiteral(normalizedAffectedEntityName)).fire(
                        entityAction);
    }

    /**
     * Converts an entity Object to a {@link EntityAction} Object.
     *
     * @param action the action
     * @param path the path
     * @param entity The object to convert
     * @return EntityAction Element with information
     */
    private EntityAction mapEntityToAction(Action action, String path,
            HawEntity entity) {
        EntityAction entityAction = new EntityAction();
        entityAction.setAction(action);
        if (entity != null) {
            entityAction.setEntityClass(entity.getEntityName());
            entityAction.setEntityID(entity.getId());
            entityAction.setEntityChanges(path + entity.toString());
        }
        return entityAction;
    }

}
