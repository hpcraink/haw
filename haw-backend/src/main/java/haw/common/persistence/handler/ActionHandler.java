package haw.common.persistence.handler;

import haw.common.action.annotation.Action;
import haw.common.exception.HawServerException;
import haw.common.resource.base.change.ChangeSet;

/**
 * Handler for running tasks after a entity has been created, updated or
 * deleted.
 *
 * @author Uwe Eisele
 *
 * @param <E> The entity
 */
public interface ActionHandler<E> {

    /**
     * Handler method.
     *
     * @param action The action
     * @param entity The entity which has been affected
     * @param changeSet The changeset (can be null)
     * @throws HawServerException Thrown if a technical error occurs
     */
    public void afterAction(Action action, E entity, ChangeSet changeSet) throws HawServerException;
}
