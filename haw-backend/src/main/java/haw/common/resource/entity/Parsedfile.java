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
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The persistent class for the parsedfile database table.
 *
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Parsedfile.findAll", query = "SELECT p FROM Parsedfile p"),
    @NamedQuery(name = "Parsedfile.findAllCount", query = "SELECT COUNT(p.id) FROM Parsedfile p")
})
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@Data
@EqualsAndHashCode(callSuper = false)
public class Parsedfile extends HawEntity implements HawNotifyable {

    private static final long serialVersionUID = 1L;

    /**
     * The file name of the parsed scheduling file.
     */
    @Column(length = 60)
    private String fileName;

    /**
     * The file's date on the file-system. This must be in the past... No future
     * files allowed? Like, what if the resource in question is in a time-zone
     * ahead of us, this should be part of the date -- and still be in the past.
     */
    @Temporal(TemporalType.DATE)
    @Past
    private Date fileDate;

    /**
     * The hash computed (in some, application dependent) way. This hash might
     * be the file's MD5, or better the SHA512.
     */
    @Column(length = 120)
    private String fileHash;

    //bi-directional many-to-one association to Resource
    @ManyToOne
    @JoinColumn(name = "resourceID")
    @NotNull
    private Resource resource;

    @Override
    public boolean isIndependentEntity() {
        return false; // Depends on a Resource
    }

    @Override
    protected void mergeAction(ChangeSet changeSet, HawEntity hawEntity, UserPrincipal principal) throws HawAuthorizationException {
        Parsedfile newEntity = (Parsedfile) hawEntity;

        // Merge fileName;
        if (!areStringsEqual(fileName, newEntity.fileName)) {
            changeSet.setValueForEntityAttribute(norm(fileName), "fileName");
            this.fileName = newEntity.fileName;
        }

        // Merge fileDate;
        if (!areObjectsEqual(fileDate, newEntity.fileDate)) {
            changeSet.setValueForEntityAttribute(norm(fileDate), "fileDate");
            this.fileDate = newEntity.fileDate;
        }
        // Merge fileHash;
        if (!areStringsEqual(fileHash, newEntity.fileHash)) {
            changeSet.setValueForEntityAttribute(norm(fileHash), "fileHash");
            this.fileHash = newEntity.fileHash;
        }

        // Merge resource;
        if (!areObjectsEqual(resource, newEntity.resource)) {
            changeSet.setValueForEntityAttribute(resource, "resource");
            this.resource = newEntity.resource;
        }
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
    public String toStringForNotification() {
        StringBuilder builder = new StringBuilder();
        builder.append(HTMLHelper.TABLE_START);

        if (isNotEmpty(fileName)) {
            builder.append(convForHTMLRow("Dateiname", fileName));
        }
        if (fileDate != null) {
            builder.append(convForHTMLRow("Dateidatum",
                    getFileDate().toString()));
        }
        if (isNotEmpty(fileHash)) {
            builder.append(convForHTMLRow("Dateihash", fileHash));
        }
        if (null != resource) {
            builder.append(convForHTMLRow("Resource",
                    resource.getConvenientIdentifier()));
        }
        builder.append(HTMLHelper.TABLE_END);
        return builder.toString();
    }
    
    @Override
    public String getConvenientIdentifier() {
        return fileName + " (" + fileHash.substring(0, 10) + ")";
    }

    @Override
    public void setConvenientIdentifier(String convenientIdentifier) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
