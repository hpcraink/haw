package haw.common.resource.entity;

import haw.common.exception.HawAuthorizationException;
import haw.common.resource.base.HawEntity;
import haw.common.resource.base.HawMergable;
import haw.common.resource.base.change.ChangeSet;
import haw.common.security.UserPrincipal;
import java.util.HashMap;

import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The persistent class for the resource database table.
 *
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Resource.findAll", query = "SELECT r FROM Resource r"),
    @NamedQuery(name = "Resource.findAllCount", query = "SELECT COUNT(r.id) FROM Resource r")
})
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@Data
@EqualsAndHashCode(callSuper = false)
public class Resource extends HawEntity {

    private static final long serialVersionUID = 1L;

    /**
     * The resources human-readable name.
     */
    @Column(length = 60)
    @NotNull
    private String name;

    /**
     * The accounting name for this scheduler. This might be "uc1" for
     * bwUniCluster.
     */
    @Column(length = 60)
    @NotNull
    private String accountingName;

    /**
     * The center/owner this resource belongs to.
     */
    //bi-directional one-to-many association to Center
    @ManyToOne
    @JoinTable(name = "centerresources",
            joinColumns = @JoinColumn(name = "resourceID"),
            inverseJoinColumns = @JoinColumn(name = "centerID"))
    private Center center;

    @ManyToOne
    @JoinTable(name = "resourceuniversityshare",
            joinColumns = { 
                    @JoinColumn(name = "resourceID", referencedColumnName = "id"),
                    @JoinColumn(name = "timespanID", referencedColumnName = "id"),
            },
            inverseJoinColumns = @JoinColumn(name = "timespanID"))
    // XXX/FIXME/TODO
    private Map <Resourcetimespan, Double> resourceuniversityshare;

    //bi-directional many-to-one association to Parsedfile
    @OneToMany(mappedBy = "resource")
    private List<Parsedfile> parsedfiles;

    //bi-directional many-to-one association to Resourceaccounted
    @OneToMany(mappedBy = "resource1")
    private List<Resourceaccounted> resourceaccounteds1;

    //bi-directional many-to-one association to Resourceaccounted
    @OneToMany(mappedBy = "resource2")
    private List<Resourceaccounted> resourceaccounteds2;

    //bi-directional many-to-one association to Resourcetimespan
    @OneToMany(mappedBy = "resource")
    private List<Resourcetimespan> resourcetimespans;

    //bi-directional many-to-one association to Resourceuser
    @OneToMany(mappedBy = "resource")
    private List<Resourceuser> resourceusers;

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

    @Override
    public String getConvenientIdentifier() {
        return name + " / " + accountingName;
    }

}
