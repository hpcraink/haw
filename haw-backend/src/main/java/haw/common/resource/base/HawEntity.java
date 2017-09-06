/**
 *
 */
package haw.common.resource.base;

import haw.common.action.annotation.Action;
import haw.common.exception.HawAuthorizationException;
import haw.common.resource.base.change.ChangeSet;
import haw.common.security.UserPrincipal;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Map;
import javax.persistence.Access;
import static javax.persistence.AccessType.FIELD;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlAttribute;
import lombok.Data;

/**
 * This interface should be implemented by each entity. It summarizes all
 * necessary Methods for Logging.
 *
 * @author Katharina Knaus
 * @author Uwe Eisele
 */
@MappedSuperclass
@Access(FIELD)
@Data
public abstract class HawEntity implements HawMergable, Serializable {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 4769824624989374151L;

    /**
     * The unique ID of this entity.
     *
     * XXX Starting with GlassFish-4.1, the ID in JSON changed from "@id"
     * (GF-4.0) to the tag "id" (GF-4.1) -- so we either provide XmlAttribute
     * setting the name to "@id" hard-coded, or change the Frontends to accept
     * id as the idAttribute.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlAttribute
    private Integer id;

    /**
     * The version of this row, necessary for optimistic logging.
     */
    @Version
    private Integer version;

    /**
     * Tests if two objects are equal. Two objects are equal if they both are
     * assignable from class {@link HawEntity} and they have the same identity.
     * Because this is a independent entity , the identity is the primary key.
     *
     * @param obj the Object to compare with
     * @return true, if successful
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        HawEntity other = (HawEntity) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }

    /**
     * Convenient identifier is a string which allows humans to identify an
     * entity. It is not necessary that this string is unique. (e.g. the name of
     * a event)
     *
     * @return The convenient identifier
     */
    public String getConvenientIdentifier() {
        return "";
    }

    /**
     * This method must be overwritten because compareOld Method checks with
     * help of toString if an entity has values in attributes or not. It is
     * important to take care, that toString provides a not empty String in the
     * case when at least one loggable attribute has a value. Otherwise it
     * should provide null or an empty String.
     *
     * @return the string
     */
    @Override
    public abstract String toString();

    /**
     * Returns the name of this entity.
     *
     * @return Name of this entity.
     */
    public final String getEntityName() {
        return this.getClass().getSimpleName();
    }

    /**
     * Returns the name of this entity in lower case letters.
     *
     * @return Name of this entity.
     */
    public final String getNormalizedEntityName() {
        return getEntityName().toLowerCase();
    }

    /**
     * Returns if this object has some values in its attributes.
     *
     * @return true, if this object has no values in its attributes
     */
    public boolean isEntityEmpty() {
        // Reflection is used to check if an entity is empty, because empty
        // entities are ignored in merge or create.
        boolean isEmpty = isEntityEmpty(this, this.getClass());
        if (isEmpty
                && HawEntity.class.isAssignableFrom(this.getClass()
                        .getSuperclass())
                && !HawEntity.class.equals(this.getClass().getSuperclass())) {
            isEmpty = isEntityEmpty(this, this.getClass().getSuperclass());
        }
        return isEmpty;
    }

    /**
     * Checks if is entity empty.
     *
     * <pre>
     * Example HawEntity.isEntityEmpty(new Member,  Member.class)
     * </pre>
     *
     * @param obj the object of the given class to check
     * @param clazz the class to check
     * @return true, if is entity empty
     */
    private static boolean isEntityEmpty(Object obj, Class<?> clazz) {
        boolean isEmpty = true;
        for (Field field : clazz.getDeclaredFields()) {
            if (!field.isAnnotationPresent(Transient.class)
                    && !Modifier.isTransient(field.getModifiers())
                    && !Modifier.isStatic(field.getModifiers())) {
                boolean isAccessible = field.isAccessible();
                try {
                    field.setAccessible(true);
                    Object value = field.get(obj);
                    if (value != null) {
                        if (HawEntity.class.isAssignableFrom(field.getType())) {
                            HawEntity entityValue = (HawEntity) value;
                            if (!entityValue.isEntityEmpty()) {
                                isEmpty = false;
                                break;
                            }
                        } else if (Collection.class.isAssignableFrom(field
                                .getType())) {
                            Collection<?> collectionValue = (Collection<?>) value;
                            if (!collectionValue.isEmpty()) {
                                isEmpty = false;
                                break;
                            }
                        } else if (Map.class.isAssignableFrom(field.getType())) {
                            Map<?, ?> mapValue = (Map<?, ?>) value;
                            if (!mapValue.isEmpty()) {
                                isEmpty = false;
                                break;
                            }
                        } else if (String.class.isAssignableFrom(field
                                .getType())) {
                            String stringValue = (String) value;
                            if (!stringValue.trim().isEmpty()) {
                                isEmpty = false;
                                break;
                            }
                        } else {
                            isEmpty = false;
                            break;
                        }
                    }
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                } finally {
                    field.setAccessible(isAccessible);
                }
            }
        }
        return isEmpty;
    }

    /**
     * Checks if this is an independent entity. Independent entity means that
     * the lifecycle of this entity is not dependent of an other entity. For
     * example an address is a dependent entity, it can be only created/deleted,
     * if a location gets created/deleted.
     *
     * @return true, if is independent entity
     */
    public abstract boolean isIndependentEntity();

    /*
     * (non-Javadoc)
     * @see
     * haw.common.resource.base.HawMergable#merge(jak.common.resource.base.HawEntity
     * , haw.common.security.UserPrincipal)
     */
    @Override
    public final ChangeSet merge(HawEntity newEntity, UserPrincipal principal)
            throws HawAuthorizationException {
        ChangeSet changeSet = new ChangeSet(Action.UPDATE, getId(),
                getConvenientIdentifier(), this.getClass());
        if (newEntity != null
                && this.getClass().isAssignableFrom(newEntity.getClass())) {
            mergeAction(changeSet, newEntity, principal);
            if (changeSet.hasChanges() && isIndependentEntity()) {
                this.setVersion(newEntity.getVersion());
            }
        }
        return changeSet;
    }

    /**
     * Merges the passed entity to this entity and collects the changes in the
     * changeSet, if the current user is allowed to make changes.
     *
     * @param changeSet the set with all changes
     * @param hawEntity the {@link HawEntity} to merge
     * @param principal the user principal
     * @throws HawAuthorizationException Thrown if the logged in user has no
     * permission to update a changed attribute
     */
    protected abstract void mergeAction(ChangeSet changeSet,
            HawEntity hawEntity, UserPrincipal principal)
            throws HawAuthorizationException;

    /*
     * (non-Javadoc)
     * @see
     * haw.common.resource.base.HawMergable#create(jak.common.security.UserPrincipal
     */
    @Override
    public final ChangeSet create(UserPrincipal principal) {
        setId(null);
        setVersion(null);
        ChangeSet changeSet = new ChangeSet(Action.CREATE, this);
        createAction(changeSet, principal);
        return changeSet;
    }

    /**
     * Handles specific create actions for this entity.
     *
     * @param changeSet the set with all changes
     * @param principal the user principal
     */
    protected abstract void createAction(ChangeSet changeSet, UserPrincipal principal);

    /*
     * (non-Javadoc)
     * @see
     * haw.common.resource.base.HawMergable#remove(haw.common.security.UserPrincipal
     * )
     */
    @Override
    public final ChangeSet remove(UserPrincipal principal) {
        ChangeSet changeSet = new ChangeSet(Action.DELETE, this);
        removeAction(changeSet, principal);
        return changeSet;
    }

    /**
     * Handles specific remove actions for this entity.
     *
     * @param changeSet the set with all changes
     * @param principal the user principal
     */
    protected abstract void removeAction(ChangeSet changeSet, UserPrincipal principal);

    /**
     * Deletes the last comma in the String. Return true, if the builder.length
     * is !=0
     *
     * @param builder the builder to modify
     * @return true, if the builder.length is !=0
     */
    protected boolean adjustToString(StringBuilder builder) {
        int length = builder.length();
        if (length != 0) {
            if (builder.toString().endsWith(", ")) {
                builder.replace(length - 2, length, " ");
            }
            return true;
        }
        return false;
    }

    /**
     * Deletes the last comma and adds braces [] at the beginning and the end of
     * the String.
     *
     * @param builder the builder to modify
     */
    protected void adjustToStringWithBraces(StringBuilder builder) {
        if (adjustToString(builder)) {
            builder.insert(0, "[");
            builder.append("]");
        }
    }

}
