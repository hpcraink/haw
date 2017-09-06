package haw.common.action.service;

import haw.common.action.annotation.Action;
import haw.common.action.annotation.AffectedAttributeLiteral;
import haw.common.action.annotation.AffectedEntityLiteral;
import haw.common.action.model.NotificationAction;
import haw.common.resource.base.HawNotifyable;
import haw.common.resource.base.change.ChangeSet;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;

/**
 * Service for firing notification events. This class encapsulates the
 * javax.enterprise.event.Event and is therefore a convenience class for
 * handling notification events.
 *
 * @author Katharina
 */
@ApplicationScoped
public class NotificationEventService {

    /**
     * The event for any EntityAction. Specification is done in the fireAction
     * Methods regarding the provided parameters.
     */
    @Inject
    private Event<NotificationAction> event;

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
    public void fireAction(Action action, HawNotifyable affectedEntity) {
        String normalizedAffectedEntityName = affectedEntity
                .getNormalizedEntityName();
        NotificationAction entityAction = NotificationAction
                .mapEntityToAction(affectedEntity);
        event.select(action.getAnnotation(),
                new AffectedEntityLiteral(normalizedAffectedEntityName)).fire(
                        entityAction);
    }

    /**
     * Fires the given action for the given affected entity. Depending on action
     * a event with annotation @ActionCreate, @ActionUpdate or @ActionDelete is
     * fired. To identify the entity in addition the event is also annotated
     * with @AffectedEntity("{name of the affected entity}"). This method also
     * enables it to identify a impacted entity. Therefore the fired event is
     * also annotated with @ImpactedEntity("{name of the impacted entity}").
     * Observers can catch a event with at least one matching annotation.
     *
     * @param action the action
     * @param affectedEntity the affected entity
     * @param affectedAttribute the name of the affected attribute
     * @param affectedAttributeValue the impacted entity value
     */
    public void fireAction(Action action, HawNotifyable affectedEntity,
            String affectedAttribute, Object affectedAttributeValue) {
        String normalizedAffectedEntityName = affectedEntity
                .getNormalizedEntityName();
        String normalizedAffectedAttribute = affectedAttribute.toLowerCase();
        NotificationAction entityAction = NotificationAction.mapEntityToAction(
                affectedEntity, normalizedAffectedAttribute,
                affectedAttributeValue);
        event.select(action.getAnnotation(),
                new AffectedEntityLiteral(normalizedAffectedEntityName),
                new AffectedAttributeLiteral(normalizedAffectedAttribute))
                .fire(entityAction);
    }

    /**
     * Fire notifications for the List of changeSetForNotification in the given
     * changeSet, if the Entities of the changeSet.getEntity and
     * changeSet.getSubChangesForNotification.getEntity are instances of
     * HawNotifyable.
     *
     * @param changeSet the change set with a list of changeSetsForNotification
     */
    public void fireAction(ChangeSet changeSet) {
        if (changeSet.isEntityOfType(HawNotifyable.class)) {
            HawNotifyable parent = changeSet.getEntity(HawNotifyable.class);
            for (ChangeSet subChange : changeSet.getSubChangesForNotification()) {
                if (subChange.isEntityOfType(HawNotifyable.class)) {
                    if (!subChange.getConvenientIdentifierEntity().isEmpty()) {
                        parent.setConvenientIdentifier(subChange
                                .getConvenientIdentifierEntity());
                    }
                    fireAction(subChange.getAction(), parent,
                            subChange.getAttributeNotification(),
                            subChange.getEntity(HawNotifyable.class));
                }
            }
        }
    }

}
