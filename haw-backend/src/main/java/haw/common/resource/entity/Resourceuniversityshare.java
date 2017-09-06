package haw.common.resource.entity;

import haw.common.exception.HawAuthorizationException;
import haw.common.resource.base.HawEntity;
import haw.common.resource.base.change.ChangeSet;
import static haw.common.resource.entity.helper.MergeHelper.areDoublesEqual;
import static haw.common.resource.entity.helper.MergeHelper.norm;
import haw.common.security.UserPrincipal;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The persistent class for the resourceuniversityshare database table.
 *
 */
@Entity
@Table(name = "resourceuniversityshare", 
        uniqueConstraints = {
            @UniqueConstraint(columnNames = "[resourcetimespanID, universityID]")
        })
@NamedQueries({
    @NamedQuery(name = "Resourceuniversityshare.findAll", query = "SELECT r FROM Resourceuniversityshare r"),
    @NamedQuery(name = "Resourceuniversityshare.findAllCount", query = "SELECT COUNT(r.id) FROM Resourceuniversityshare r")
})
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@Data
@EqualsAndHashCode(callSuper = false)
public class Resourceuniversityshare extends HawEntity {

    private static final long serialVersionUID = 1L;

    /*
    @EmbeddedId
    private ResourceuniversitysharePK id;
    */

    /**
     * This University's shared of this resource for this timespan.
     */
    private double universityShare;

    // XXX/TODO
    //bi-directional many-to-one association to Resourcetimespan
    @ManyToOne
    @JoinColumn(name = "resourcetimespanID", referencedColumnName = "id")
    private Resourcetimespan resourcetimespan;

    @Override
    public boolean isIndependentEntity() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void mergeAction(ChangeSet changeSet, HawEntity hawEntity, UserPrincipal principal) throws HawAuthorizationException {
        Resourceuniversityshare newEntity = (Resourceuniversityshare) hawEntity;
        if (!areDoublesEqual(universityShare, newEntity.universityShare)) {
            changeSet.setValueForEntityAttribute(norm(universityShare), "universityShare");
            this.setUniversityShare(newEntity.universityShare);
        }
    }

    @Override
    protected void createAction(ChangeSet changeSet, UserPrincipal principal) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void removeAction(ChangeSet changeSet, UserPrincipal principal) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
