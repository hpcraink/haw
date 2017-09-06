package haw.common.resource.entity;

import haw.common.error.handler.HawErrorHandler;
import haw.common.exception.HawAuthorizationException;
import haw.common.resource.base.HawEntity;
import haw.common.resource.base.change.ChangeSet;
import static haw.common.resource.entity.helper.MergeHelper.areCollectionsEqual;
import static haw.common.resource.entity.helper.MergeHelper.areStringsEqual;
import static haw.common.resource.entity.helper.MergeHelper.norm;
import static haw.common.resource.entity.helper.UserHelper.isUserInRole;
import haw.common.resource.type.Role;
import haw.common.security.UserPrincipal;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
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
 * The persistent class for the center database table.
 *
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Center.findAll", query = "SELECT c FROM Center c"),
    @NamedQuery(name = "Center.findAllCount", query = "SELECT COUNT(c.id) FROM Center c")
})
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@Data
@EqualsAndHashCode(callSuper = false)
public class Center extends HawEntity {

    private static final long serialVersionUID = 1L;

    /**
     * The name of the compute Center (or owner of resources).
     */
    @Column(length = 64)
    @NotNull
    private String name;

    /**
     * The abbreviation of the compute center (or the owner).
     */
    @Column(length = 8)
    @NotNull
    private String abbreviation;

    /**
     * The resources that belong to this center/owner.
     */
    // bi-directional one-to-many association to Resource
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "center")
    @JoinTable(name = "centerresources",
            joinColumns = @JoinColumn(name = "centerID", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "resourceID", referencedColumnName = "id"))
    private List<Resource> resources;

    @Override
    public boolean isIndependentEntity() {
        return true;
    }

    @Override
    protected void mergeAction(ChangeSet changeSet, HawEntity hawEntity, UserPrincipal principal) throws HawAuthorizationException {
        Center newEntity = (Center) hawEntity;

        // Principal needs to have ADMIN role.
        if (!isUserInRole(principal, Role.ADMIN)) {
            HawErrorHandler.getInstance(
                    this.getClass()).handleAuthorizationErrorUpdateAttribute(
                    principal, getEntityName(), Role.ADMIN, "name");
        }

        // Merge name
        if (!areStringsEqual(name, newEntity.name)) {
            changeSet.setValueForEntityAttribute(name, "name");
            this.setName(newEntity.name);
        }
        // Merge abbreviation
        if (!areStringsEqual(abbreviation, newEntity.abbreviation)) {
            changeSet.setValueForEntityAttribute(abbreviation, "abbreviation");
            this.setName(newEntity.abbreviation);
        }
        // Merge resources
        if (!areCollectionsEqual(resources, newEntity.resources)) {
            changeSet.setValueForEntityAttribute(norm(resources), "resources");
            this.setResources(newEntity.resources);
        }
    }

    @Override
    protected void createAction(ChangeSet changeSet, UserPrincipal principal) {
        setResources(null);
        changeSet.addSubChangeForNotification(changeSet, "", this.getConvenientIdentifier());
    }

    @Override
    protected void removeAction(ChangeSet changeSet, UserPrincipal principal) {
        // This entity needs no specific actions for remove
    }

}
