package haw.common.resource.entity;

import haw.common.exception.HawAuthorizationException;
import haw.common.exception.HawValidationException;
import haw.common.helper.DateFormatter;
import haw.common.helper.HTMLHelper;
import static haw.common.helper.HTMLHelper.convForHTMLRow;
import haw.common.resource.base.HawEntity;
import haw.common.resource.base.HawNotifyable;
import haw.common.resource.base.change.ChangeSet;
import haw.common.security.UserPrincipal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The persistent class for the login database table.
 *
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Login.findAll", query = "SELECT l FROM Login l"),
    @NamedQuery(name = "Login.findAllCount", query = "SELECT COUNT(l.id) FROM Login l")
})
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@Data
@EqualsAndHashCode(callSuper = false)
public class Login extends HawEntity implements HawNotifyable {

    private static final long serialVersionUID = 1L;

    /**
     * The Login information of this person.
     */
    // Uni-directional one-to-one association to Person
    @OneToOne
    @JoinColumn(name = "personID", nullable = false)
    private Person person;

    /**
     * The Password of this user login.
     */
    @Column(length = 1024, nullable = false)
    @NotNull
    private String password;

    /**
     * The salt for this password.
     */
    @Column(length = 1024, nullable = false)
    @NotNull
    private String salt;

    /**
     * The request time for a new password.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date requestTime;

    /**
     * ** Specific getters and setters ***
     */
    /**
     * Sets the time when the forgot password mail was requested.
     *
     * @param time the requestTime to set
     * @throws HawValidationException Thrown if passed time is not valid.
     */
    public void setRequestTime(String time) throws HawValidationException {
        setRequestTime(DateFormatter.TIMESTAMP.parse(time));
    }

    /**
     * Sets the time when the forgot password mail was requested.
     *
     * @param timestamp the new request time
     */
    public void setRequestTime(Date timestamp) {
        if (timestamp != null) {
            this.requestTime = new Date(timestamp.getTime());
        } else {
            this.requestTime = null;
        }
    }

    @Override
    public boolean isIndependentEntity() {
        return false; // Does not exist without a one-to-one mapping to Person
    }

    /**
     * A Login can not be changed with this method. The password will be changed
     * in
     * {@link PersonBusinessService#createOrUpdateLogin(Person, boolean, String)}.
     * .
     *
     * @throws haw.common.exception.HawAuthorizationException
     * @see
     * haw.common.resource.base.HawEntity#mergeAction(haw.common.resource.base.change.ChangeSet,
     * haw.common.resource.base.JakEntity, haw.common.security.UserPrincipal)
     */
    @Override
    protected void mergeAction(ChangeSet changeSet, HawEntity hawEntity, UserPrincipal principal) throws HawAuthorizationException {
        // Do nothing
    }

    @Override
    protected void createAction(ChangeSet changeSet, UserPrincipal principal) {
        // This entity needs no specific actions after the creation
    }

    @Override
    protected void removeAction(ChangeSet changeSet, UserPrincipal principal) {
        // This entity needs no specific actions after the creation
    }

    @Override
    public String toStringForNotification() {
        StringBuilder builder = new StringBuilder();
        builder.append(HTMLHelper.TABLE_START);
        if (null != person) {
            builder.append(convForHTMLRow("Email", person.getEmail()));
        } else {
            builder.append(convForHTMLRow("Email", "n/a"));
        }
        builder.append(HTMLHelper.TABLE_END);
        return builder.toString();
    }

    @Override
    public String getConvenientIdentifier() {
        if (null != person) {
            return person.getEmail();
        } else {
            return "n/a";
        }
    }

    @Override
    public void setConvenientIdentifier(String convenientIdentifier) {
        // Not required
    }

}
