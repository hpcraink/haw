package haw.common.resource.entity;

import haw.application.configuration.ApplicationConfiguration;
import haw.common.exception.HawAuthorizationException;
import haw.common.resource.base.HawEntity;
import haw.common.resource.base.change.ChangeSet;
import haw.common.security.UserPrincipal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The persistent class for the currency database table.
 *
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Currency.findAll", query = "SELECT c FROM Currency c"),
    @NamedQuery(name = "Currency.findAllCount", query = "SELECT COUNT(c.id) FROM Currency c")
})
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@Data
@EqualsAndHashCode(callSuper = false)
public class Currency extends HawEntity {

    private static final long serialVersionUID = 1L;

    /**
     * The official name in German.
     */
    @Column(length = 64)
    private String namede;

    /**
     * The official name in English.
     */
    @Column(length = 64)
    private String nameen;

    /**
     * The international abbreviation. This might be "EUR" for the Euro, or
     * "USD" for US-Dollars.
     */
    @Column(length = 8)
    private String abbreviation;

    /**
     * The symbol used for this currency. E.g. "$" for the USD.
     */
    @Column(length = 1)
    private String symbol;

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
    public boolean isIndependentEntity() {
        return true;
    }

    @Override
    protected void mergeAction(ChangeSet changeSet, HawEntity hawEntity, UserPrincipal principal) throws HawAuthorizationException {
        /**
         * Currently mergeAction is not supported -- and required.
         * Just like Country, we preset two currencies and currently do not
         * foresee having to create/merge/remove any.
         */
    }

    @Override
    protected void createAction(ChangeSet changeSet, UserPrincipal principal) {
        // See comment in mergeAction
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected void removeAction(ChangeSet changeSet, UserPrincipal principal) {
        // See comment in mergeAction
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
