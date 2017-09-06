/**
 *
 */
package haw.common.action.model;

import haw.common.action.annotation.Action;

import java.io.Serializable;

/**
 * An Event Object for data transport from all persistenceServices to the
 * EntityObserver.
 *
 * @author Katharina
 * @author Martin
 * @author Uwe Eisele
 */
public class EntityAction implements Serializable {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = -4903373798010920812L;

    /**
     * The action.
     */
    private Action action;

    /**
     * The entity id.
     */
    private Integer entityID;

    /**
     * The entity class.
     */
    private String entityClass;

    /**
     * The entity changes.
     */
    private String entityChanges;

    /**
     * Default constructor.
     */
    public EntityAction() {
    }

    /**
     * Constructor with all needed information.
     *
     * @param entityID The ID of the entity to log.
     * @param entityName The Class name of the entity to log.
     */
    public EntityAction(Integer entityID, String entityName) {
        super();
        this.entityID = entityID;
        this.entityClass = entityName;
    }

    /*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (action != null) {
            builder.append("action=");
            builder.append(action);
            builder.append(", ");
        }
        if (entityID != null) {
            builder.append("entityID=");
            builder.append(entityID);
            builder.append(", ");
        }
        if (entityClass != null) {
            builder.append("entityClass=");
            builder.append(entityClass);
            builder.append(", ");
        }
        if (entityChanges != null) {
            builder.append("entityChanges=");
            builder.append(entityChanges);
            builder.append(", ");
        }
        return builder.toString();
    }

    /*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((entityID == null) ? 0 : entityID.hashCode());
        result = prime * result
                + ((entityChanges == null) ? 0 : entityChanges.hashCode());
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
        if (getClass() != obj.getClass()) {
            return false;
        }
        EntityAction other = (EntityAction) obj;
        if (entityID == null) {
            if (other.entityID != null) {
                return false;
            }
        } else if (!entityID.equals(other.entityID)) {
            return false;
        }
        if (entityChanges == null) {
            if (other.entityChanges != null) {
                return false;
            }
        } else if (!entityChanges.equals(other.entityChanges)) {
            return false;
        }
        return true;
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
     * Sets the action.
     *
     * @param action the new action
     */
    public void setAction(Action action) {
        this.action = action;
    }

    /**
     * Gets the entity id.
     *
     * @return the entityID
     */
    public Integer getEntityID() {
        return entityID;
    }

    /**
     * Sets the entity id.
     *
     * @param entityID the entityID to set
     */
    public void setEntityID(Integer entityID) {
        this.entityID = entityID;
    }

    /**
     * Gets the entity changes.
     *
     * @return the entityName
     */
    public String getEntityChanges() {
        return entityChanges;
    }

    /**
     * Sets the entity changes.
     *
     * @param entityName the entityName to set
     */
    public void setEntityChanges(String entityName) {
        this.entityChanges = entityName;
    }

    /**
     * Gets the entity class.
     *
     * @return the entityClass
     */
    public String getEntityClass() {
        return entityClass;
    }

    /**
     * Sets the entity class.
     *
     * @param entityClass the entityClass to set
     */
    public void setEntityClass(String entityClass) {
        this.entityClass = entityClass;
    }

}
