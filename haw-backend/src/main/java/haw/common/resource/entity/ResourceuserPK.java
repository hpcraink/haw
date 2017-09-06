package haw.common.resource.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the resourceusers database table.
 * 
 */
@Embeddable
public class ResourceuserPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(insertable=false, updatable=false)
	private int resourceID;

	@Column(insertable=false, updatable=false)
	private int personID;

	public ResourceuserPK() {
	}
	public int getResourceID() {
		return this.resourceID;
	}
	public void setResourceID(int resourceID) {
		this.resourceID = resourceID;
	}
	public int getPersonID() {
		return this.personID;
	}
	public void setPersonID(int personID) {
		this.personID = personID;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ResourceuserPK)) {
			return false;
		}
		ResourceuserPK castOther = (ResourceuserPK)other;
		return 
			(this.resourceID == castOther.resourceID)
			&& (this.personID == castOther.personID);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.resourceID;
		hash = hash * prime + this.personID;
		
		return hash;
	}
}