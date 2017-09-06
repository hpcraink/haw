package haw.common.resource.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the resourceaccounted database table.
 * 
 */
@Embeddable
public class ResourceaccountedPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(insertable=false, updatable=false)
	private int resourceID;

	@Column(insertable=false, updatable=false)
	private int containedResourceID;

	public ResourceaccountedPK() {
	}
	public int getResourceID() {
		return this.resourceID;
	}
	public void setResourceID(int resourceID) {
		this.resourceID = resourceID;
	}
	public int getContainedResourceID() {
		return this.containedResourceID;
	}
	public void setContainedResourceID(int containedResourceID) {
		this.containedResourceID = containedResourceID;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ResourceaccountedPK)) {
			return false;
		}
		ResourceaccountedPK castOther = (ResourceaccountedPK)other;
		return 
			(this.resourceID == castOther.resourceID)
			&& (this.containedResourceID == castOther.containedResourceID);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.resourceID;
		hash = hash * prime + this.containedResourceID;
		
		return hash;
	}
}