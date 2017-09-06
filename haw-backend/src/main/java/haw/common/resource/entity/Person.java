package haw.common.resource.entity;

import haw.common.exception.HawAuthorizationException;
import haw.common.helper.HTMLHelper;
import static haw.common.helper.HTMLHelper.convForHTMLRow;
import static haw.common.helper.StringHelper.isNotEmpty;
import haw.common.resource.base.HawEntity;
import haw.common.resource.base.HawNotifyable;
import haw.common.resource.base.change.ChangeSet;
import haw.common.resource.base.list.MergableList;
import static haw.common.resource.entity.helper.MergeHelper.areObjectsEqual;
import static haw.common.resource.entity.helper.MergeHelper.areStringsEqual;
import static haw.common.resource.entity.helper.MergeHelper.norm;
import haw.common.resource.type.Gender;
import haw.common.resource.type.Role;
import haw.common.security.UserPrincipal;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

/**
 * The persistent class for the person database table. Equality of two Person
 * objects depend on their IDs.
 */
@Entity
@Table(name = "person")
@NamedQueries({
    @NamedQuery(name = "Person.findAll", query = "SELECT p FROM Person p WHERE p.deleted = FALSE"),
    @NamedQuery(name = "Person.findAllCount", query = "SELECT COUNT(p.id) FROM Person p WHERE p.deleted = FALSE"),
    // Please DO NOT change the order of the findAllOnlyIDAndName query.
    // The resultSet depends on it (hard coded numbers) :)
    @NamedQuery(name = "Member.findAllOnlyIDAndName", query = "SELECT p.id, p.firstName, p.lastName, p.email FROM Person p WHERE p.deleted = FALSE"),
    @NamedQuery(name = "Person.findAllDeleted", query = "SELECT p FROM Person p WHERE p.deleted = TRUE"),
    @NamedQuery(name = "Person.findAllDeletedCount", query = "SELECT COUNT(p.id) FROM Person p WHERE p.deleted = TRUE"),
    @NamedQuery(name = "Person.findByRole", query = "SELECT p FROM Person p WHERE p.role =:role AND p.deleted = FALSE"),
    @NamedQuery(name = "Person.findByRoleOnlyIDAndName", query = "SELECT p.id, p.firstName, p.lastName, p.email FROM Person p WHERE p.role =:role AND p.deleted = FALSE"),
    @NamedQuery(name = "Person.findByEmail", query = "SELECT p FROM Person p WHERE p.email=:email AND p.deleted = FALSE"),
    @NamedQuery(name = "Person.findByEmailCount", query = "SELECT COUNT(p.id) FROM Person p WHERE p.email=:email AND p.deleted = FALSE"),
    @NamedQuery(name = "Person.findByUniversity", query = "SELECT p FROM Person p WHERE p.university=:university AND p.deleted = FALSE"),
    @NamedQuery(name = "Person.findByUniversityCount", query = "SELECT COUNT(p.id) FROM Person p WHERE p.university=:university AND p.deleted = FALSE")
})
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@Data
@EqualsAndHashCode(callSuper = false)
public class Person extends HawEntity implements HawNotifyable {

    private static final long serialVersionUID = 1L;

    /**
     * The person's first (and middle) name. This MUST be provided.
     */
    @Column(length = 60)
    @NotNull
    private String firstName;

    /**
     * The person's first (and middle) name. This MUST be provided.
     */
    @Column(length = 60)
    @NotNull
    private String lastName;

    /**
     * The persons Email address. This MUST be a correct and working Email and
     * should hosted at this university.
     */
    @Column(length = 120, nullable = false, unique = true)
    @NotNull
    @Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
    private String email;

    /**
     * Whether this user has been deleted. This may be edited only by
     * UNIRESPONSIBLE and up.
     */
    @NotNull
    private Boolean deleted;

    /**
     * This person's gender. This may be left as UNSPECIFIED.
     */
    @Enumerated(EnumType.STRING)
    @Column(length = 15)
    // XXX/TODO: This is not ideal: what if we amend gender?
    @Pattern(regexp = "^UNSPECIFIED$|^MALE$|^FEMALE$")
    private Gender gender;

    /**
     * The additional information, about this person. This may be visible and
     * edited by the user, e.g. with comments about this person.
     */
    @Column(length = 512)
    private String additionalInformation;

    /**
     * The administrative (private) information of this person. This MUST not be
     * visible to the user/other users, only to admins.
     */
    @Column(length = 512)
    private String adminInformation;

    /**
     * The login for this member.
     */
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(referencedColumnName = "personID")
    private Login login;

    /**
     * The list of roles.
     */
    @OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "roles",
            joinColumns = @JoinColumn(name = "personID", referencedColumnName = "id"))
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private MergableList<Role> role;

    @ManyToOne
    private University university;

    public Set<Role> getRoles() {
        if (role == null) {
            return null;
        }
        return new HashSet<>(role);
    }

    public void setRoles(@NonNull MergableList<Role> roles) {
        this.role = roles;
    }

    public boolean addRole(Role newRole) {
        if (null == role) {
            role = new MergableList<Role>();
        }
        return role.add(newRole);
    }

    public boolean removeRole(Role oldRole) {
        return role.remove(oldRole);
    }

    @Override
    public boolean isIndependentEntity() {
        return true;
    }

    @Override
    protected void mergeAction(ChangeSet changeSet, HawEntity hawEntity, UserPrincipal principal) throws HawAuthorizationException {
        Person newEntity = (Person) hawEntity;
        // Merge firstName
        if (!areStringsEqual(firstName, newEntity.firstName)) {
            changeSet.setValueForEntityAttribute(norm(firstName), "firstName");
            this.setFirstName(newEntity.firstName);
        }
        // Merge lastName
        if (!areStringsEqual(lastName, newEntity.lastName)) {
            changeSet.setValueForEntityAttribute(norm(lastName), "lastName");
            this.setLastName(newEntity.lastName);
        }
        // Merge eMail
        if (!areStringsEqual(email, newEntity.email)) {
            changeSet.setValueForEntityAttribute(norm(email), "email");
            this.setEmail(newEntity.email);
        }
        // Merge gender
        if (!areObjectsEqual(gender, newEntity.gender)) {
            changeSet.setValueForEntityAttribute(norm(gender), "gender");
            this.setGender(newEntity.gender);
        }
        // Merge additionalInformation
        if (!areStringsEqual(additionalInformation, newEntity.additionalInformation)) {
            changeSet.setValueForEntityAttribute(norm(additionalInformation), "additionalInformation");
            this.setAdditionalInformation(newEntity.additionalInformation);
        }
        // Merge adminInformation
        if (!areStringsEqual(adminInformation, newEntity.adminInformation)) {
            changeSet.setValueForEntityAttribute(norm(adminInformation), "adminInformation");
            this.setAdminInformation(newEntity.adminInformation);
        }
        // Merge deleted
        if (!areObjectsEqual(deleted, newEntity.deleted)) {
            changeSet.setValueForEntityAttribute(norm(deleted), "deleted");
            this.setDeleted(newEntity.deleted);
        }
    }

    @Override
    protected void createAction(ChangeSet changeSet, UserPrincipal principal) {
        // Every person must be at leaste in Role USER
        this.addRole(Role.USER);
        this.deleted = false;
    }

    @Override
    protected void removeAction(ChangeSet changeSet, UserPrincipal principal) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String toStringForNotification() {
        StringBuilder builder = new StringBuilder();
        builder.append(HTMLHelper.TABLE_START);
        if (isNotEmpty(firstName)) {
            builder.append(convForHTMLRow("Vorname ", firstName));
        }
        if (isNotEmpty(lastName)) {
            builder.append(convForHTMLRow("Nachname ", lastName));
        }
        if (isNotEmpty(email)) {
            builder.append(convForHTMLRow("Email ", email));
        }
        if (null != gender) {
            builder.append(convForHTMLRow("Geschlecht ", gender.toString()));
        }
        if (isNotEmpty(additionalInformation)) {
            builder.append(convForHTMLRow("Zusatzinfo ", additionalInformation));
        }
        if (isNotEmpty(adminInformation)) {
            builder.append(convForHTMLRow("Admininfo ", adminInformation));
        }
        if (null != deleted) {
            builder.append(convForHTMLRow("Gelöscht ", deleted.toString()));
        }
        builder.append(HTMLHelper.TABLE_END);
        return builder.toString();
    }

    @Override
    public void setConvenientIdentifier(String convenientIdentifier) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
