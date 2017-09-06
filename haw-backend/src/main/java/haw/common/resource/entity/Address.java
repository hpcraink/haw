package haw.common.resource.entity;

import haw.common.exception.HawAuthorizationException;
import haw.common.helper.HTMLHelper;
import static haw.common.helper.HTMLHelper.convForHTMLRow;
import static haw.common.helper.StringHelper.isNotEmpty;
import haw.common.resource.base.HawEntity;
import haw.common.resource.base.HawNotifyable;
import haw.common.resource.base.change.ChangeSet;
import static haw.common.resource.entity.helper.MergeHelper.areObjectsEqual;
import static haw.common.resource.entity.helper.MergeHelper.areStringsEqual;
import static haw.common.resource.entity.helper.MergeHelper.norm;
import haw.common.security.UserPrincipal;
import javax.persistence.Access;
import static javax.persistence.AccessType.FIELD;
import javax.persistence.Column;
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
 * The persistent class for the address database table.
 *
 */
@Entity
@Table(name = "address")
@NamedQueries({
    @NamedQuery(name = "Address.findAll", query = "SELECT a FROM Address a"),
    @NamedQuery(name = "Address.findAllCount", query = "SELECT COUNT(a.id) FROM Address a")
})
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@Data
@EqualsAndHashCode(callSuper = false)
public class Address extends HawEntity implements HawNotifyable {

    private static final long serialVersionUID = 1L;

    /**
     * The postal code (ZIP, PLZ, etc.).
     */
    @Column(length = 12)
    private String postcode;

    /**
     * The street and number.
     */
    @Column(length = 60)
    private String streetAndNr;

    /**
     * The town.
     */
    @Column(length = 60)
    private String town;

    //bi-directional many-to-one association to Country
    @ManyToOne(optional = false)
    @JoinColumn(name = "countryID", referencedColumnName = "id")
    private Country country;

    @Override
    public boolean isIndependentEntity() {
        return false;
    }

    @Override
    protected void mergeAction(ChangeSet changeSet, HawEntity hawEntity, UserPrincipal principal) throws HawAuthorizationException {
        Address newEntity = (Address) hawEntity;
        // Merge country (independently managed -> no cascade of merge)
        if (!areObjectsEqual(country, newEntity.country)) {
            changeSet.setValueForEntityAttribute(country, "country");
            this.setCountry(newEntity.country);
        }
        // Merge postcode
        if (!areStringsEqual(postcode, newEntity.postcode)) {
            changeSet.setValueForEntityAttribute(norm(postcode), "postcode");
            this.setPostcode(newEntity.postcode);
        }
        // Merge streetAndNr
        if (!areStringsEqual(streetAndNr, newEntity.streetAndNr)) {
            changeSet.setValueForEntityAttribute(norm(streetAndNr), "streetAndNr");
            this.setStreetAndNr(newEntity.streetAndNr);
        }
        // Merge town
        if (!areStringsEqual(town, newEntity.town)) {
            changeSet.setValueForEntityAttribute(norm(town), "town");
            this.setTown(newEntity.town);
        }
    }

    @Override
    protected void createAction(ChangeSet changeSet, UserPrincipal principal) {
        // this entity needs no specific actions after the creation
    }

    @Override
    protected void removeAction(ChangeSet changeSet, UserPrincipal principal) {
        // this entity needs no specific actions after the creation
    }

    @Override
    public String toStringForNotification() {
        StringBuilder builder = new StringBuilder();
        builder.append(HTMLHelper.TABLE_START);
        if (isNotEmpty(streetAndNr)) {
            builder.append(convForHTMLRow("Str. und Hausnr.", streetAndNr));
        }
        if (isNotEmpty(postcode)) {
            builder.append(convForHTMLRow("PLZ", postcode));
        }
        if (isNotEmpty(town)) {
            builder.append(convForHTMLRow("Stadt", town));
        }
        if (null != country) {
            builder.append(convForHTMLRow("Land", country.getNamede()));
        }
        builder.append(HTMLHelper.TABLE_END);
        return builder.toString();
    }

    /**
     * Sets the name
     *
     * @see
     * haw.common.resource.base.HawNotifyable#setConvenientIdentifier(java.lang.String)
     */
    @Override
    public void setConvenientIdentifier(String convenientIdentifier) {
        // not needed at the moment
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (isNotEmpty(streetAndNr)) {
            builder.append("Str. und Hausnr.:");
            builder.append(streetAndNr);
            builder.append(", ");
        }
        if (isNotEmpty(postcode)) {
            builder.append("PLZ: ");
            builder.append(postcode);
            builder.append(", ");
        }
        if (isNotEmpty(town)) {
            builder.append("Start: ");
            builder.append(town);
            builder.append(", ");
        }
        if (null != country) {
            builder.append("Land: ");
            builder.append(country.getNamede());
            builder.append(", ");
        }
        super.adjustToStringWithBraces(builder);
        return builder.toString();
    }

}
