package haw.common.resource.entity;

import haw.common.exception.HawAuthorizationException;
import haw.common.resource.base.HawEntity;
import haw.common.resource.base.change.ChangeSet;
import haw.common.security.UserPrincipal;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;

/**
 * The persistent class for the resourceaccounted database table.
 *
 */
@Entity
@NamedQuery(name = "Resourceaccounted.findAll", query = "SELECT r FROM Resourceaccounted r")
public class Resourceaccounted extends HawEntity {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private ResourceaccountedPK id;

    private double costPerUnit;

    private int numberOf;

    //bi-directional many-to-one association to Resource
    @ManyToOne
    @JoinColumn(name = "containedResourceID")
    private Resource resource1;

    //bi-directional many-to-one association to Currency
    @ManyToOne
    @JoinColumn(name = "currencyID")
    private Currency currency;

    //bi-directional many-to-one association to Resource
    @ManyToOne
    @JoinColumn(name = "resourceID")
    private Resource resource2;

    //bi-directional many-to-one association to Unit
    @ManyToOne
    @JoinColumn(name = "unitID")
    private Unit unit;

    @Override
    public String toString() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

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
