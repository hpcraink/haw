package haw.common.resource.entity;

import haw.common.exception.HawAuthorizationException;
import haw.common.resource.base.HawEntity;
import haw.common.resource.base.change.ChangeSet;
import haw.common.security.UserPrincipal;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The persistent class for the resourceusers database table.
 *
 */
@Entity
@Table(name = "resourceusers")
@NamedQueries({
    @NamedQuery(name = "Resourceuser.findAll", query = "SELECT r FROM Resourceuser r"),
    @NamedQuery(name = "Resourceuser.findAllCount", query = "SELECT COUNT(r.id) FROM Resourceuser r")
})
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@Data
@EqualsAndHashCode(callSuper = false)
public class Resourceuser extends HawEntity {

    private static final long serialVersionUID = 1L;

    /*
    @EmbeddedId
    private ResourceuserPK id;
    */

    private String username;

    //bi-directional many-to-one association to Person
    @ManyToOne
    @JoinColumn(name = "personID")
    private Person person;

    //bi-directional many-to-one association to Resource
    @ManyToOne
    @JoinColumn(name = "resourceID")
    private Resource resource;

    @Override
    public boolean isIndependentEntity() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void mergeAction(ChangeSet changeSet, HawEntity hawEntity, UserPrincipal principal) throws HawAuthorizationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
