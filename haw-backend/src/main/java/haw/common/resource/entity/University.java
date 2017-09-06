package haw.common.resource.entity;

import haw.common.exception.HawAuthorizationException;
import haw.common.resource.base.HawEntity;
import haw.common.resource.base.change.ChangeSet;
import haw.common.security.UserPrincipal;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "university")
@NamedQueries({
    @NamedQuery(name = "University.findAll", query = "SELECT u FROM University u"),
    @NamedQuery(name = "University.findAllCount", query = "SELECT COUNT(u.id) FROM University u"),
    @NamedQuery(name = "University.findAllHaw", query = "SELECT u FROM University u WHERE u.isHAW = TRUE"),
    @NamedQuery(name = "University.findAllHawCount", query = "SELECT COUNT(u.id) FROM University u WHERE u.isHAW = TRUE")
})
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@Data
@EqualsAndHashCode(callSuper=false)
public class University extends HawEntity {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The name of this University. The name MUST be set.
     */
    @NotNull
    @Column(length = 60)
    private String name;

    /**
     * The abbreviation of this University. The name of the university is
     * uniquely abbreviated in BWIDM / Shibboleth, e.g. st=Stuttgart, ul=Ulm, hs
     * = Hochschule fuer Technik Stuttgart, etc.
     *
     * The abbreviation MUST be set.
     */
    @NotNull
    @Column(length = 8, unique = true)
    private String abbreviation;

    /**
     * Whether this is a Hochschule fuer Angewandte Wissenschaften (HAW).
     *
     * This MUST be set.
     */
    @NotNull
    private Boolean isHAW;

    /**
     * The University's address.
     */
    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "addressID", referencedColumnName = "id")
    private Address address;

    /**
     * The persons belonging to this University, aka their members.
     */
    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinTable(name = "universitymembers",
            joinColumns = @JoinColumn(name = "universityID", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "personID", referencedColumnName = "id"))
    private List<Person> members;

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
