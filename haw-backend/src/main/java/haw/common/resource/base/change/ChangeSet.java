package haw.common.resource.base.change;

import static haw.common.helper.StringHelper.isNotEmpty;
import haw.common.action.annotation.Action;
import haw.common.resource.entity.helper.ReflectionHelper;
import haw.common.resource.base.HawEntity;
import haw.common.resource.base.HawNotifyable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The Class ChangeSet. This class saves all changes for an entity, attribute
 * and action.
 */
public class ChangeSet {

    /**
     * The parent.
     */
    private ChangeSet parent;

    /**
     * The attribute which was changed.
     */
    private String attribute;

    /**
     * The attribute for notification.
     */
    private String attributeNotification;

    /**
     * The convenient identifier for the attribute entity.
     */
    private String convenientIdentifierEntity;

    /**
     * The name of the entity.
     */
    private final String name;

    /**
     * The action.
     */
    private final Action action;

    /**
     * The entity which was changed.
     */
    private final Object entity;

    /**
     * All changes of the main entity.
     */
    private final Set<String> entityChanges;

    /**
     * All changes of sub entities.
     */
    private final Map<String, List<ChangeSet>> subChanges;

    /**
     * All changes of sub entities which must sent notifications.
     */
    private final Map<String, List<ChangeSet>> subChangesForNotification;

    /**
     * Instantiates a new change set.
     *
     * @param <T> the generic type
     * @param action the {@link Action}
     * @param entityId the entity id of the changed entity
     * @param name the name of the entity
     * @param entityClass the changed entity class
     */
    public <T extends HawEntity> ChangeSet(Action action, int entityId,
            String name, Class<T> entityClass) {
        HawEntity jakEntity
                = ReflectionHelper.createHawEntity(entityId, entityClass);
        this.action = action;
        this.entity = jakEntity;
        this.name = name;
        this.entityChanges
                = Collections.synchronizedSet(new HashSet<String>());
        this.subChanges
                = Collections.synchronizedMap(new HashMap<String, List<ChangeSet>>());
        this.subChangesForNotification = Collections
                .synchronizedMap(new HashMap<String, List<ChangeSet>>());
    }

    /**
     * Instantiates a new change set.
     *
     * @param action the {@link Action}
     * @param entity the entity which was changed.
     */
    public ChangeSet(Action action, Object entity) {
        this.action = action;
        this.entity = entity;
        if (entity instanceof HawEntity) {
            this.name = ((HawEntity) entity).getConvenientIdentifier();
        } else {
            this.name = "";
        }
        this.entityChanges
                = Collections.synchronizedSet(new HashSet<String>());
        this.subChanges
                = Collections.synchronizedMap(new HashMap<String, List<ChangeSet>>());
        this.subChangesForNotification = Collections
                .synchronizedMap(new HashMap<String, List<ChangeSet>>());
    }

    /**
     * Gets the parent {@link ChangeSet}.
     *
     * @return the parent
     */
    public ChangeSet getParent() {
        return parent;
    }

    /**
     * Sets the parent {@link ChangeSet}.
     *
     * @param parent the new parent
     */
    public void setParent(ChangeSet parent) {
        this.parent = parent;
    }

    /**
     * Gets the action.
     *
     * @return the action
     */
    public Action getAction() {
        return action;
    }

    /**
     * Gets the name. If the changes were made in an {@link JakEntity} this
     * method will return the {@link JakEntity#getConvenientIdentifier()},
     * otherwise an empty String.
     *
     * @return the name of the entity
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the fieldname of the changed attribute.
     *
     * @return the attribute
     */
    public String getAttribute() {
        return attribute;
    }

    /**
     * Sets the fieldname of the changed attribute.
     *
     * @param attribute the new attribute
     */
    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    /**
     * Check this for changes. It returns true if
     * <ul>
     * <li>hasEntityChanges returns true</li>
     * <li>hasSubChanges returns true</li>
     * </ul>
     *
     * @return true, if successful
     */
    public boolean hasChanges() {
        return hasEntityChanges() || hasSubChanges();
    }

    /**
     * Checks if the main entity has been changed. It returns true if
     * <ul>
     * <li>has entity changes</li>
     * <li>the action is {@link Action#CREATE}</li>
     * <li>the action is {@link Action#DELETE}</li>
     * </ul>
     *
     * @return true if the main entity has been changed.
     */
    public boolean hasEntityChanges() {
        return !entityChanges.isEmpty()
                || Action.CREATE.equals(getAction())
                || Action.DELETE.equals(getAction());
    }

    /**
     * Checks if a sub entity has been changed.
     *
     * @return true if a sub entity has been changed.
     */
    public boolean hasSubChanges() {
        return !subChanges.isEmpty();
    }

    /**
     * Checks if a sub entity has been changed and must sent notifications.
     *
     * @return true if a sub entity has been changed and must sent
     * notifications.
     */
    public boolean hasSubChangesForNotification() {
        return !subChangesForNotification.isEmpty();
    }

    /**
     * Gets the entity.
     *
     * @return the entity
     */
    public Object getEntity() {
        return entity;
    }

    /**
     * Gets the entity for the given entityType.
     *
     * @param <T> the generic type
     * @param entityType the entity type
     * @return the entity for the given entityType.
     */
    @SuppressWarnings("unchecked")
    public <T> T getEntity(Class<T> entityType) {
        return (T) getEntity();
    }

    /**
     * Checks if the stored entity is of given entityType.
     *
     * @param <T> the generic type
     * @param entityType the entity type to check.
     * @return true, if the stored entity is of the given type
     */
    public <T> boolean isEntityOfType(Class<T> entityType) {
        return entityType.isAssignableFrom(getEntity().getClass());
    }

    /**
     * Sets the value for the given attribute.
     *
     * @param value the value to set
     * @param attribute the attribute to which the value should be set
     */
    public void setValueForEntityAttribute(Object value, String attribute) {
        if (!entityChanges.contains(attribute)) {
            entityChanges.add(attribute);
        }
        if (value != null) {
            Field field = getFieldOfAttribute(entity.getClass(), attribute);
            setValueForField(field, entity, value);
        }
    }

    /**
     * Returns the attributes of the main entity which has been changed.
     *
     * @return Set of changed attributes
     */
    public Set<String> getEntityChanges() {
        return new HashSet<>(entityChanges);
    }

    /**
     * Gets all changes of sub entities.
     *
     * @return the List of changes in sub entities.
     */
    public List<ChangeSet> getSubChanges() {
        List<ChangeSet> allChanges = new ArrayList<>();
        for (List<ChangeSet> attributeChanges : subChanges.values()) {
            allChanges.addAll(attributeChanges);
        }
        return allChanges;
    }

    /**
     * Gets all sub changes which must sent notifications .
     *
     * @return the List of changes which must sent notifications.
     */
    public List<ChangeSet> getSubChangesForNotification() {
        List<ChangeSet> allChanges = new ArrayList<>();
        for (List<ChangeSet> attributeNotifChanges : subChangesForNotification
                .values()) {
            allChanges.addAll(attributeNotifChanges);
        }
        return allChanges;
    }

    /**
     * Gets the List of changeSets for the given class.
     *
     * @param entityClass the entity class
     * @return the changes
     */
    public List<ChangeSet> getSubChanges(Class<?> entityClass) {
        List<ChangeSet> allChanges = new ArrayList<>();
        for (List<ChangeSet> attributeChanges : subChanges.values()) {
            for (ChangeSet oneChange : attributeChanges) {
                if (entityClass.isAssignableFrom(oneChange.entity.getClass())) {
                    allChanges.add(oneChange);
                }
            }
        }
        return allChanges;
    }

    /**
     * Gets the List of changeSets for Notification for the given class.
     *
     * @param entityClass the entity class
     * @return the changes which must sent notifications
     */
    public List<ChangeSet> getSubChangesForNotification(Class<?> entityClass) {
        List<ChangeSet> allChanges = new ArrayList<>();
        for (List<ChangeSet> attributeChanges : subChangesForNotification
                .values()) {
            for (ChangeSet oneChange : attributeChanges) {
                if (entityClass.isAssignableFrom(oneChange.entity.getClass())) {
                    allChanges.add(oneChange);
                }
            }
        }
        return allChanges;
    }

    /**
     * Gets the List of changes for the given attribute. If there are no changes
     * it will return an empty List.
     *
     * @param attribute the attribute
     * @return the changes for attribute
     */
    public List<ChangeSet> getSubChangesForAttribute(String attribute) {
        List<ChangeSet> changesForAttribute = subChanges.get(attribute);
        if (changesForAttribute == null) {
            changesForAttribute = new ArrayList<>();
        }
        return changesForAttribute;
    }

    /**
     * Gets the List of changes for notifications for the given attribute. If
     * there are no changes it will return an empty List.
     *
     * @param attributeForNotif the attribute
     * @return the changes for notification for attribute
     */
    public List<ChangeSet> getSubChangesForNotificationForAttribute(
            String attributeForNotif) {
        List<ChangeSet> changesForAttribute = subChangesForNotification
                .get(attributeForNotif);
        if (changesForAttribute == null) {
            changesForAttribute = new ArrayList<>();
        }
        return changesForAttribute;
    }

    /**
     * Adds the all changes for the given attribute.
     *
     * @param changeSets the change sets
     * @param attribute the attribute
     */
    public void addAllSubChanges(Collection<ChangeSet> changeSets, String attribute) {
        for (ChangeSet changeSet : changeSets) {
            addSubChange(changeSet, attribute);
        }
    }

    /**
     * Adds the all changes for notifications for the given attribute. The
     * ChangeSet Entity and the SubChanges.Entity must be an instance of
     * {@link JakNotifyable}, otherwise no Notifications will be not sent.
     *
     * @param changeSets the change sets
     * @param attribute the attribute
     * @param convenientIdentifier the convenientIdentifier for the parent
     * class.
     * @see JakEntity#getConvenientIdentifier()
     */
    public void addAllSubChangesForNotification(
            Collection<ChangeSet> changeSets, String attribute,
            String convenientIdentifier) {
        for (ChangeSet changeSet : changeSets) {
            addSubChangeForNotification(changeSet, attribute,
                    convenientIdentifier);
        }
    }

    /**
     * Adds a change for the given action and given attribute to each of the
     * entities.
     *
     * @param <T> the generic type
     * @param action the action
     * @param entities the entities
     * @param attribute the attribute
     */
    public <T> void addAllSubChanges(Action action, Collection<T> entities,
            String attribute) {
        for (T entity : entities) {
            addSubChange(action, entity, attribute);
        }
    }

    /**
     * Adds the ChangeSet for the given action and the given entity to all
     * changes for the given attribute.
     *
     * @param action the action
     * @param entity the entity which was changed
     * @param attribute the attribute which was changed
     */
    public void addSubChange(Action action, Object entity, String attribute) {
        ChangeSet change = new ChangeSet(action, entity);
        addSubChange(change, attribute);
    }

    /**
     * Adds the ChangeSet to the given attribute.
     *
     * @param change the change
     * @param attribute the attribute
     */
    public void addSubChange(ChangeSet change, String attribute) {
        change.setParent(this);
        change.setAttribute(attribute);
        List<ChangeSet> existingChanges = this.subChanges.get(attribute);
        if (existingChanges == null) {
            existingChanges = Collections.synchronizedList(new ArrayList<ChangeSet>());
            this.subChanges.put(attribute, existingChanges);
        }
        existingChanges.add(change);
    }

    /**
     * Adds the ChangeSet for notification to the given attribute.
     *
     * @param change the change
     * @param attributeNotification the attribute for Notification
     * @param convenientIdentifier the convenientIdentifier for the parent
     * class.
     * @see JakEntity#getConvenientIdentifier()
     */
    public void addSubChangeForNotification(ChangeSet change, String attributeNotification,
            String convenientIdentifier) {
        if (this.getParent() == null) {
            change.setParent(this);
        }
        change.setAttributeNotification(attributeNotification);
        change.setConvenientIdentifierEntity(convenientIdentifier);
        List<ChangeSet> existingChanges = this.subChangesForNotification
                .get(attributeNotification);
        if (existingChanges == null) {
            existingChanges = Collections
                    .synchronizedList(new ArrayList<ChangeSet>());
            this.subChangesForNotification.put(attributeNotification, existingChanges);
        }
        existingChanges.add(change);
    }

    /**
     * Gets the path to the origin of this changes.
     *
     * @return the path
     */
    public String getPath() {
        StringBuilder buffer = new StringBuilder();
        if (getParent() != null && getParent() != this) {
            buffer.append(getParent().getPath());
            buffer.append(": ");
        }
        String entityName;
        Integer entityId;
        if (entity instanceof HawEntity) {
            entityName = ((HawEntity) entity).getEntityName();
            entityId = ((HawEntity) entity).getId();
        } else {
            entityName = entity.getClass().getSimpleName();
            entityId = entity.hashCode();
        }
        buffer.append(entityName);
        StringBuilder subBuffer = new StringBuilder();
        if (entityId != null) {
            subBuffer.append("id=");
            subBuffer.append(entityId);
        }
        if (getName() != null && !getName().isEmpty()) {
            if (subBuffer.length() > 0) {
                subBuffer.append(", ");
            }
            subBuffer.append("name=");
            subBuffer.append(getName());
        }
        if (getAttribute() != null && !getAttribute().isEmpty()) {
            if (subBuffer.length() > 0) {
                subBuffer.append(", ");
            }
            subBuffer.append("attribut=");
            subBuffer.append(getAttribute());
        }
        if (subBuffer.length() > 0) {
            buffer.append("(");
            buffer.append(subBuffer);
            buffer.append(") ");
        }
        return buffer.toString();
    }

    /*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder("[");
        if (isNotEmpty(attribute)) {
            buffer.append("attribute =");
            buffer.append(attribute);
            buffer.append(", ");
        }
        if (isNotEmpty(name)) {
            buffer.append("name =");
            buffer.append(name);
            buffer.append(", ");
        }
        if (action != null) {
            buffer.append("action =");
            buffer.append(action);
            buffer.append(", ");
        }
        buffer.append("hasEntityChanged =");
        buffer.append(hasEntityChanges());
        buffer.append(", ");
        buffer.append("hasSubChanges =");
        buffer.append(hasSubChanges());
        buffer.append("]");
        return buffer.toString();
    }

    /*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((entity == null) ? 0 : entity.hashCode());
        return result;
    }

    /*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        Object otherEntity;
        if (obj instanceof ChangeSet) {
            otherEntity = ((ChangeSet) obj).getEntity();
        } else {
            otherEntity = obj;
        }
        if (entity == null) {
            if (otherEntity != null) {
                return false;
            }
        } else if (!entity.equals(otherEntity)) {
            return false;
        }
        return true;
    }

    /**
     * Returns a Field object that reflects the given attribute of the given
     * class. The attribute parameter is a String that specifies the simple name
     * of the desired field. Note that this method will not reflect the length
     * field of an array class.
     *
     * @param clazz the Class of which the field should be
     * @param attribute the name of the field
     * @return the field for the given attribute
     */
    private Field getFieldOfAttribute(Class<?> clazz, String attribute) {
        Field field;
        try {
            field = clazz.getDeclaredField(attribute);
        } catch (NoSuchFieldException | SecurityException e) {
            if (clazz.getSuperclass() != null) {
                field = getFieldOfAttribute(clazz.getSuperclass(), attribute);
            } else {
                throw new RuntimeException(e.getMessage(), e);
            }
        }
        return field;
    }

    /**
     * Sets the value for the given field in the given object.
     *
     * @param field the field to change.
     * @param object the object to change.
     * @param value the new value of the field.
     */
    private void setValueForField(Field field, Object object, Object value) {
        boolean isAccessible = field.isAccessible();
        try {
            field.setAccessible(true);
            field.set(object, value);
        } catch (IllegalAccessException | IllegalArgumentException
                | SecurityException e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            field.setAccessible(isAccessible);
        }
    }

    /**
     * Gets the convenient identifier for the attribute entity.
     *
     * @return the convenientIdentifierEntity
     */
    public String getConvenientIdentifierEntity() {
        return convenientIdentifierEntity;
    }

    /**
     * Sets the convenient identifier for the attribute entity.
     *
     * @param convenientIdentifierEntity the convenientIdentifierEntity to set
     */
    public void setConvenientIdentifierEntity(String convenientIdentifierEntity) {
        this.convenientIdentifierEntity = convenientIdentifierEntity;
    }

    /**
     * Gets the attribute for notification.
     *
     * @return the attributeNotification
     */
    public String getAttributeNotification() {
        return attributeNotification;
    }

    /**
     * Sets the attribute for notification.
     *
     * @param attributeNotification the attributeNotification to set
     */
    public void setAttributeNotification(String attributeNotification) {
        this.attributeNotification = attributeNotification;
    }

}
