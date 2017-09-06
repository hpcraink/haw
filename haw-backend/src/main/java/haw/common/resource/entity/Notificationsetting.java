package haw.common.resource.entity;

import haw.common.exception.HawAuthorizationException;
import haw.common.resource.base.HawEntity;
import haw.common.resource.base.change.ChangeSet;
import haw.common.security.UserPrincipal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The persistent class for the notificationsettings database table.
 *
 */
@Entity
@Table(name = "notificationsettings")
@NamedQueries({
    @NamedQuery(name = "Notificationsetting.findAll", query = "SELECT n FROM Notificationsetting n"),
    @NamedQuery(name = "Notificationsetting.findAllCount", query = "SELECT COUNT(n.id) FROM Notificationsetting n")
})
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@Data
@EqualsAndHashCode(callSuper = false)
public class Notificationsetting extends HawEntity {

    private static final long serialVersionUID = 1L;

    /**
     * The hostserver of the email account.
     */
    @Column(length = 60)
    @NotNull
    private String emailServer;

    /**
     * Port of SMTP server which the notifications will be sent to.
     */
    @Min(0)
    @Max(65535)
    private int port;

    /**
     * Port of SMTP server for using SSL/TLS.
     */
    @Min(0)
    @Max(65535)
    private int sslPort;

    /**
     * Use SSL/TLS to connect to SMTP server.
     */
    @NotNull
    private Boolean useSsl;

    /**
     * EmailAddress from which the notifications will be sent.
     */
    @Column(length = 60)
    @NotNull
    @Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
    private String email;

    /**
     * Name from whom the notifications will be sent.
     */
    @Column(length = 60)
    private String nameFrom;

    /**
     * Username for the email account stored in email attribute.
     */
    @Column(length = 60)
    private String user;

    /**
     * Password for the email account stored in email attribute.
     */
    @Column(length = 60)
    private String password;

    @Override
    public boolean isIndependentEntity() {
        return true;
    }

    @Override
    protected void mergeAction(ChangeSet changeSet, HawEntity hawEntity, UserPrincipal principal) throws HawAuthorizationException {
        // Nobody should need to edit or create this...
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void createAction(ChangeSet changeSet, UserPrincipal principal) {
        // Nobody should need to edit or create this...
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void removeAction(ChangeSet changeSet, UserPrincipal principal) {
        // Nobody should need to edit or create this...
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
