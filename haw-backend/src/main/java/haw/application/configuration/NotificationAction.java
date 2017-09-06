package haw.application.configuration;

import haw.common.resource.base.HawNotifyable;

import java.util.HashMap;
import java.util.Map;

/**
 * This class provides all necessary information to send a notification.
 *
 * @author Katharina
 */
public class NotificationAction {

    /**
     * The recipient. <EMailAddress, Name>
     */
    private Map<String, String> receipient = new HashMap<>();

    /**
     * The entity.
     */
    private HawNotifyable entity;

    /**
     * The subject.
     */
    private String subject;

    /**
     * The body written with HTML tags.
     */
    private String body;

    /**
     * The affected attribute value.
     */
    private Object affectedAttributeValue;

    /**
     * The affected attribute name.
     */
    private String affectedAttributeName;

    /**
     * Instantiates a new notification.
     */
    public NotificationAction() {
        super();
    }

    /**
     * Gets the entity.
     *
     * @return the entity
     */
    public HawNotifyable getEntity() {
        return entity;
    }

    /**
     * Sets the entity.
     *
     * @param entity the entity to set
     */
    public void setEntity(HawNotifyable entity) {
        this.entity = entity;
    }

    /**
     * Adds the recipient if this eMailAddress is not already in the Map.
     * Otherwise do nothing.
     *
     * @param eMailAddress the e mail address
     * @param name the name
     * @return true, if successful
     */
    public boolean addReceipient(String eMailAddress, String name) {
        if (receipient.containsKey(eMailAddress)) {
            return false;
        }
        receipient.put(eMailAddress, name);
        return true;
    }

    /**
     * Deletes the recipient if this eMailAddress is in the Map. Otherwise do
     * nothing.
     *
     * @param eMailAddress the e mail address
     */
    public void deleteReceipient(String eMailAddress) {
        if (receipient.containsKey(eMailAddress)) {
            receipient.remove(eMailAddress);
        }
    }

    /**
     * Gets the recipient.
     *
     * @return the receipient
     */
    public Map<String, String> getReceipient() {
        return receipient;
    }

    /**
     * Sets the recipient.
     *
     * @param receipient the recipient to set
     */
    public void setReceipient(Map<String, String> receipient) {
        this.receipient = receipient;
    }

    /**
     * Converts an {@link HawNotifyable} Object to a {@link NotificationAction}
     * Object.
     *
     * @param entity the entity
     * @return NotificationAction Element with information
     */
    public static NotificationAction mapEntityToAction(HawNotifyable entity) {
        NotificationAction action = new NotificationAction();
        if (entity != null) {
            action.setEntity(entity);
        }
        return action;
    }

    /**
     * Converts an entity Object to a {@link NotificationAction} Object.
     * Parameters must not be null.
     *
     * @param affectedEntity The affected entity (may contain only the changes)
     * @param affectedAttribute The name of the attribute which is affected.
     * @param affectedAttributeValue2 the affected attribute value
     * @return {@link NotificationAction} object with information
     */
    public static NotificationAction mapEntityToAction(
            HawNotifyable affectedEntity, String affectedAttribute,
            Object affectedAttributeValue2) {
        NotificationAction action = new NotificationAction();
        if (affectedEntity != null) {
            action.setEntity(affectedEntity);
            action.setAffectedAttributeName(affectedAttribute.toLowerCase());
            action.setAffectedAttributeValue(affectedAttributeValue2);
        }
        return action;
    }

    /**
     * Gets the subject.
     *
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Sets the subject.
     *
     * @param subject the subject to set
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * Gets the body.
     *
     * @return the body
     */
    public String getBody() {
        return body;
    }

    /**
     * Sets the body.
     *
     * @param body the body to set
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * Gets the affected attribute value.
     *
     * @return the affected attribute value
     */
    public Object getAffectedAttributeValue() {
        return affectedAttributeValue;
    }

    /**
     * Sets the affected attribute value.
     *
     * @param affectedAttributeValue2 the affected attribute value to set
     */
    public void setAffectedAttributeValue(Object affectedAttributeValue2) {
        this.affectedAttributeValue = affectedAttributeValue2;
    }

    /**
     * Gets the affected attribute name.
     *
     * @return the affectedAttributeName
     */
    public String getAffectedAttributeName() {
        return affectedAttributeName;
    }

    /**
     * Sets the affected attribute name.
     *
     * @param affectedAttributeName the affected attribute name to set
     */
    public void setAffectedAttributeName(String affectedAttributeName) {
        this.affectedAttributeName = affectedAttributeName;
    }

}
