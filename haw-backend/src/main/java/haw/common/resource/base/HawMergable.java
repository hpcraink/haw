/**
 *
 */
package haw.common.resource.base;

import haw.common.exception.HawAuthorizationException;
import haw.common.resource.base.change.ChangeSet;
import haw.common.security.UserPrincipal;

/**
 * This interface should be implemented by each entity. It provides methods for
 * a role specific merge.
 *
 * @author Katharina Knaus
 * @author Uwe Eisele
 */
public interface HawMergable {

    /**
     * Compares this with the given newEntity, merges the changed values to this
     * (role specific merge) and returns an Object with only changed attributes
     * containing old values.
     *
     * @param newEntity entity which contains the new values
     * @param principal the principal of the current user
     * @return ChangeSet which contains all changes
     * @throws HawAuthorizationException Throw if this user tries to set a new
     * value to an attribute which is not allowed for his role
     */
    public ChangeSet merge(HawEntity newEntity, UserPrincipal principal)
            throws HawAuthorizationException;

    /**
     * Creates the entity. Changes must affect this entity.
     *
     * @param principal the user principal
     * @return ChangSet with the new entity
     */
    public ChangeSet create(UserPrincipal principal);

    /**
     * Removes the entity.
     *
     * @param principal the user principal
     * @return ChangSet with the removed entity
     */
    public ChangeSet remove(UserPrincipal principal);
}
