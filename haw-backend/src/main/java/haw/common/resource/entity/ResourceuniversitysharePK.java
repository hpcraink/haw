package haw.common.resource.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Data;

/**
 * The primary key class for the resourceuniversityshare database table.
 */
@Embeddable
@Data
public class ResourceuniversitysharePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(insertable=false, updatable=false)
	private int resourcetimespanID;

	@Column(insertable=false, updatable=false)
	private int universityID;
}