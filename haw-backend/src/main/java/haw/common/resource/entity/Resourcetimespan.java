package haw.common.resource.entity;

import haw.common.exception.HawAuthorizationException;
import haw.common.resource.base.HawEntity;
import haw.common.resource.base.change.ChangeSet;
import static haw.common.resource.entity.helper.MergeHelper.areObjectsEqual;
import static haw.common.resource.entity.helper.MergeHelper.areStringsEqual;
import static haw.common.resource.entity.helper.MergeHelper.norm;
import haw.common.resource.type.Scheduler;
import haw.common.security.UserPrincipal;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The persistent class for the resourcetimespan database table.
 *
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Resourcetimespan.findAll", query = "SELECT r FROM Resourcetimespan r"),
    @NamedQuery(name = "Resourcetimespan.findAllCount", query = "SELECT COUNT(r.id) FROM Resourcetimespan r"),
    @NamedQuery(name = "Resourcetimespan.findByResource", query = "SELECT r FROM Resourcetimespan r WHERE r.resource=:resource"),
    @NamedQuery(name = "Resourcetimespan.findByResourceCount", query = "SELECT COUNT(r.id) FROM Resourcetimespan r WHERE r.resource=:resource"),})
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@Data
@EqualsAndHashCode(callSuper = false)
public class Resourcetimespan extends HawEntity {

    private static final long serialVersionUID = 1L;

    /**
     * The name of the timespan. E.g. "Erstinstallation Phase1" or Ausbau2017.
     */
    @Column(length = 60)
    private String name;

    /**
     * The timespan number.
     *
     * The timespan number is UNIQUE for a given resourceID, so there are 1 to n
     * timespans, with n being the number of timespans for this resource.
     * Timespans MUST not overlap for a given resource.
     */
    @Min(0)
    private int timespan;

    /**
     * The start date of this time-span.
     *
     * The start date demarks the installation, when first accounting data was
     * gathered, or when the university's share have changed.
     */
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    @NotNull
    private Date startDate;

    /**
     * The end date of this time-span.
     *
     * When the time-span ended.
     */
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    @NotNull
    private Date endDate;

    /**
     * The scheduler used for this system (MOAB, et al).
     * 
     * This is bound to resourcetimespan, as it may change over the course of an
     * installation. E.g. in the phase1 MOAB, then Torque/Maui.
     */
    @NotNull
    private Scheduler scheduler;

    //bi-directional many-to-one association to Resource
    @ManyToOne
    @JoinColumn(name = "resourceID")
    private Resource resource;

    //bi-directional many-to-one association to Resourceuniversityshare
    @OneToMany(mappedBy = "resourcetimespan")
    private List<Resourceuniversityshare> resourceuniversityshares;

    @Override
    public boolean isIndependentEntity() {
        return false; // This is dependent on th resource.
    }

    @Override
    protected void mergeAction(ChangeSet changeSet, HawEntity hawEntity, UserPrincipal principal) throws HawAuthorizationException {
        Resourcetimespan newEntity = (Resourcetimespan) hawEntity;
        /*
        resourceID INT NOT NULL COMMENT 'The unique ID of the resource',
    name VARCHAR(60) COMMENT 'The name of the timespan (e.g. Erstinstallation Phase1 or Ausbau2017)',
    -- Time-spans may NOT overlap, they are actually cumbersome, but the only way, since time-periods are not available for Primary Keys!
    timespan INT NOT NULL DEFAULT 1 COMMENT 'The unique time-span (e.g. 1.1.2017 - 31.12.2018 is timespan1, 1.1.2019-31.12.2020 is timespan2)',
    startDate DATE NOT NULL COMMENT 'Beginning of time-span for this share',
    endDate DATE NOT NULL COMMENT 'End of time-span for this share',
    scheduler SET('UNSPECIFIED', 'MOAB') NOT NULL COMMENT 'The scheduler for this resource, for this timespan.',
         */
        // Merge name
        if (!areStringsEqual(name, newEntity.name)) {
            changeSet.setValueForEntityAttribute(norm(name), "name");
            this.setName(newEntity.name);
        }
        // Merge resource
        if (!areObjectsEqual(resource, newEntity.resource)) {
            changeSet.setValueForEntityAttribute(resource, "resource");
            this.setResource(newEntity.resource);
        }
        // Merge timespan
        if (!areObjectsEqual(timespan, newEntity.timespan)) {
            changeSet.setValueForEntityAttribute(timespan, "timespan");
            this.setTimespan(newEntity.timespan);
        }
        // Merge startDate
        if (!areObjectsEqual(startDate, newEntity.startDate)) {
            changeSet.setValueForEntityAttribute(startDate, "startDate");
            this.setStartDate(newEntity.startDate);
        }
        // Merge endDate
        if (!areObjectsEqual(endDate, newEntity.endDate)) {
            changeSet.setValueForEntityAttribute(startDate, "endDate");
            this.setEndDate(newEntity.endDate);
        }
        // Merge scheduler
        if (!areObjectsEqual(scheduler, newEntity.scheduler)) {
            changeSet.setValueForEntityAttribute(scheduler, "scheduler");
            this.setScheduler(newEntity.scheduler);
        }

        // Make sure that endDate is > startDate
        assert (endDate.compareTo(startDate) <= 0);
        // XXX/TODO/FIXME: assert that timespan number is still from 0 to count for this resourceId.
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
