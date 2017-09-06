package haw.common.resource.base;

/**
 * This interface must be implemented by each entity, which can be sent via
 * NotificationService.
 *
 * @author Katharina
 */
public interface HawNotifyable {

    /**
     * This toString provides a more readable layout, which is needed for
     * notifications.
     *
     * @return all necessary information about this entity in a more readable
     * layout than the toString method.
     */
    public String toStringForNotification();

    /**
     * Returns the name of this entity in lower case letters.
     *
     * @return Name of this entity.
     */
    public String getNormalizedEntityName();

    /**
     * Returns the name of this entity.
     *
     * @return Name of this entity.
     */
    public String getEntityName();

    /**
     * Convenient identifier is a string which allows humans to identify an
     * entity. It is not necessary that this string is unique. (e.g. the name of
     * a event) This method must be implemented in the same way, if the method
     * {@link JakEntity#getConvenientIdentifier()} is implemented.
     *
     * @param convenientIdentifier The convenient identifier
     */
    public void setConvenientIdentifier(String convenientIdentifier);

}
