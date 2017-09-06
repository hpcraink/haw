package haw.common.resource.entity;

import haw.application.configuration.ApplicationConfiguration;
import haw.common.exception.HawAuthorizationException;
import static haw.common.helper.StringHelper.isNotEmpty;
import haw.common.resource.base.HawEntity;
import haw.common.resource.base.change.ChangeSet;
import haw.common.security.UserPrincipal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The persistent class for the country database table. Countries are managed
 * independently outside of the application.
 */
@Entity
@Table(name = "country",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = {"iso2"}),
            @UniqueConstraint(columnNames = {"iso3"})
        }
)
@NamedQueries({
    @NamedQuery(name = "Country.findAll", query = "SELECT c FROM Country c"),
    @NamedQuery(name = "Country.findAllCount", query = "SELECT COUNT(c.id) FROM Country c"),
    @NamedQuery(name = "Country.findAllOnlyIDAndName", query = "SELECT c.id, c.namede, c.nameen FROM Country c ORDER BY c.namede ASC"),
    @NamedQuery(name = "Country.findAllOnlyIDAndNameDe", query = "SELECT c.id, c.namede FROM Country c ORDER BY c.namede ASC"),
    @NamedQuery(name = "Country.findAllOnlyIDAndNameEn", query = "SELECT c.id, c.nameen FROM Country c ORDER BY c.nameen ASC")
})
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@Data
@EqualsAndHashCode(callSuper = false)
public class Country extends HawEntity {

    private static final long serialVersionUID = 1L;

    /**
     * The unique 2-letter code as defined by ISO 3166-1.
     */
    @Column(length = 2)
    @NotNull
    private String iso2;

    /**
     * The unique 3-letter code as defined by ISO 3166-1.
     */
    @Column(length = 3)
    @NotNull
    private String iso3;

    /**
     * The countries official German name.
     */
    @Column(length = 64)
    @NotNull
    private String namede;

    /**
     * The countries official English name.
     */
    @NotNull
    @Column(length = 64)
    private String nameen;

    /**
     * Transient field not stored in the DB -- just for returning the queried
     * name (aka either DE, EN or FR)...
     */
    @Transient
    private String name;

    public String getName() {
        switch (ApplicationConfiguration.getInstance().getLanguage()) {
            case "DE":
                name = this.namede;
                break;
            case "EN":
                name = this.nameen;
                break;
            default:
                name = "n/a";
        }
        return name;
    }

    public void setName(String name) {
        switch (ApplicationConfiguration.getInstance().getLanguage()) {
            case "DE":
                this.namede = name;
            case "EN":
                this.nameen = name;
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (isNotEmpty(iso2)) {
            builder.append("ISO2 = ");
            builder.append(iso2);
            builder.append(", ");
        }
        if (isNotEmpty(iso3)) {
            builder.append("ISO3 = ");
            builder.append(iso3);
            builder.append(", ");
        }
        if (nameen != null) {
            builder.append("nameen = ");
            builder.append(nameen);
            builder.append(", ");
        }
        if (namede != null) {
            builder.append("namede = ");
            builder.append(namede);
            builder.append(", ");
        }
        super.adjustToStringWithBraces(builder);
        return builder.toString();
    }

    @Override
    public boolean isIndependentEntity() {
        return true;
    }

    @Override
    protected void mergeAction(ChangeSet changeSet, HawEntity hawEntity, UserPrincipal principal) throws HawAuthorizationException {
        /**
         * Currently mergeAction is not supported -- and required. Since the
         * country's id (and names...) are defined by ISO 3166-1: if they need
         * to be changed / adapted, we directly change them in the database
         * anyhow, making sure, that e.g. addresses of split countries wind up
         * in the right country, afterwards.
         */
    }

    @Override
    protected void createAction(ChangeSet changeSet, UserPrincipal principal) {
        // See above
        throw new UnsupportedOperationException("Not supported yet.");

    }

    @Override
    protected void removeAction(ChangeSet changeSet, UserPrincipal principal) {
        // See above
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
