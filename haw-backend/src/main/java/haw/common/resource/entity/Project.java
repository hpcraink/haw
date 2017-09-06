package haw.common.resource.entity;

import haw.common.exception.HawAuthorizationException;
import haw.common.helper.HTMLHelper;
import static haw.common.helper.HTMLHelper.convForHTMLRow;
import static haw.common.helper.StringHelper.isNotEmpty;
import haw.common.resource.base.HawEntity;
import haw.common.resource.base.HawNotifyable;
import haw.common.resource.base.change.ChangeSet;
import static haw.common.resource.entity.helper.MergeHelper.areCollectionsEqual;
import static haw.common.resource.entity.helper.MergeHelper.areObjectsEqual;
import static haw.common.resource.entity.helper.MergeHelper.areStringsEqual;
import static haw.common.resource.entity.helper.MergeHelper.norm;
import haw.common.security.UserPrincipal;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The persistent class for the project database table.
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Project.findAll", query = "SELECT p FROM Project p"),
    @NamedQuery(name = "Project.findAllCount", query = "SELECT COUNT(p.id) FROM Project p")
})
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@Data
@EqualsAndHashCode(callSuper = false)
public class Project extends HawEntity implements HawNotifyable {

    private static final long serialVersionUID = 1L;

    /**
     * The name of the project.
     */
    @Column(length = 60)
    @NotNull
    private String name;

    /**
     * The starting date of the project.
     */
    @Temporal(TemporalType.DATE)
    private Date startDate;

    /**
     * The ending date of the project. This of course has to be after startDate.
     */
    @Temporal(TemporalType.DATE)
    private Date endDate;

    /**
     * The leading person of the project.
     */
    //bi-directional many-to-one association to Person
    @ManyToOne
    @JoinColumn(name = "leaderPersonID")
    @NotNull
    private Person leaderPerson;

    //bi-directional many-to-many association to Person
    @ManyToMany
    @JoinTable(name = "projectparticipants",
            joinColumns = @JoinColumn(name = "projectID"),
            inverseJoinColumns = @JoinColumn(name = "personID")
    )
    private List<Person> persons;

    // XXX/TODO
    // addPerson
    // removePerson
    @Override
    public boolean isIndependentEntity() {
        return true;
    }

    @Override
    protected void mergeAction(ChangeSet changeSet, HawEntity hawEntity, UserPrincipal principal) throws HawAuthorizationException {
        Project newEntity = (Project) hawEntity;

        // Merge name
        if (!areStringsEqual(name, newEntity.name)) {
            changeSet.setValueForEntityAttribute(norm(name), "name");
            this.setName(newEntity.name);
        }

        // Merge startDate
        if (!areObjectsEqual(startDate, newEntity.startDate)) {
            changeSet.setValueForEntityAttribute(norm(startDate), "startDate");
            this.setStartDate(newEntity.startDate);
        }

        // Merge endDate
        if (!areObjectsEqual(endDate, newEntity.endDate)) {
            changeSet.setValueForEntityAttribute(norm(endDate), "endDate");
            this.setEndDate(newEntity.endDate);
        }

        // Merge leaderPerson
        if (!areObjectsEqual(leaderPerson, newEntity.leaderPerson)) {
            changeSet.setValueForEntityAttribute(leaderPerson, "leaderPerson");
            this.setLeaderPerson(newEntity.leaderPerson);
        }

        // Merge persons
        if (!areCollectionsEqual(persons, newEntity.persons)){
            changeSet.setValueForEntityAttribute(norm(persons), "persons");
            this.setPersons(newEntity.persons);
        }
    }

    @Override
    protected void createAction(ChangeSet changeSet, UserPrincipal principal
    ) {
        // This entity needs no specific actions after the creation
    }

    @Override
    protected void removeAction(ChangeSet changeSet, UserPrincipal principal
    ) {
        // This entity needs no specific actions after the creation
    }

    @Override
    public String toStringForNotification() {
        StringBuilder builder = new StringBuilder();
        builder.append(HTMLHelper.TABLE_START);

        if (isNotEmpty(name)) {
            builder.append(convForHTMLRow("Name", name));
        }
        if (null != startDate) {
            builder.append(convForHTMLRow("Startdatum", startDate.toString()));
        }

        if (null != endDate) {
            builder.append(convForHTMLRow("Enddatum", endDate.toString()));
        }

        if (null != leaderPerson && !leaderPerson.isEntityEmpty()) {
            builder.append(convForHTMLRow("Projektverantwortlicher", leaderPerson.getConvenientIdentifier()));
        }

        if (null != persons) {
            int num = 0;
            for (Person p : persons) {
                builder.append(convForHTMLRow("Projektbeteiligter " + num++,
                        p.getConvenientIdentifier()));
            }
        }

        builder.append(HTMLHelper.TABLE_END);
        return builder.toString();
    }

    @Override
    public void setConvenientIdentifier(String convenientIdentifier
    ) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
