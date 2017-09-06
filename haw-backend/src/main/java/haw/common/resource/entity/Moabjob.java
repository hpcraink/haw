package haw.common.resource.entity;

import haw.common.exception.HawAuthorizationException;
import haw.common.resource.base.HawEntity;
import haw.common.resource.base.change.ChangeSet;
import haw.common.security.UserPrincipal;
import java.math.BigInteger;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The persistent class for the moabjob database table.
 *
 */
@Entity
@Table(name = "moabjob",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = "moabJobId")}
)
@NamedQueries({
    @NamedQuery(name = "Moabjob.findAll", query = "SELECT m FROM Moabjob m"),
    @NamedQuery(name = "Moabjob.findAllCount", query = "SELECT COUNT(m.id) FROM Moabjob m")
})
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@Data
@EqualsAndHashCode(callSuper = false)
public class Moabjob extends HawEntity {

    private static final long serialVersionUID = 1L;

    @Column(length = 60)
    private String moabAccount;

    @Column(length = 120)
    private String moabAname;

    private double moabAverageUtilizedProcs;

    private Boolean moabBackfill;

    private int moabBypassCount;

    @Column(length = 60)
    private String moabClass;

    @Column(length = 60)
    private String moabDistrm;

    private int moabDistrmjid;

    private double moabEffQueueTime;

    private BigInteger moabEndTime;

    @Column(length = 1024)
    private String moabExecutable;

    private Boolean moabGlobalqueue;

    @Column(length = 4)
    private String moabGroup;

    @Column(length = 1024)
    private String moabIWD;

    private int moabJobId;

    private int moabNodeCount;

    @Column(length = 60)
    private String moabNodeMatchPolicy;

    @Column(length = 120)
    private String moabPartition;

    private double moabPE;

    private int moabProcPerTask;

    private int moabReqWallTime;

    private int moabStartCount;

    private int moabStartPriority;

    private BigInteger moabStartTime;

    @Column(length = 1024)
    private String moabSubmitDir;

    private BigInteger moabSubmitTime;

    @Column(length = 60)
    private String moabSystemId;

    private int moabSystemJID;

    private int moabTaskCount;

    @Column(length = 60)
    private String moabTemplateSets;

    private int moabTimeQueuedTotal;

    private int moabTotalCost;

    private int moabTotalRequestedMemory;

    private int moabTotalRequestedNodes;

    private int moabTotalRequestedProcs;

    private int moabTotalRequestedTasks;

    @Column(length = 8)
    private String moabUMask;

    private byte moabUnicore;

    private int moabUsedWallTime;

    @Column(length = 60)
    private String moabUser;

    // XXX / TODO/FIXME: Does this need to stay here???
    // Shouldn't we have proper connection to the parsed file?
    private int parsedFileID;

    @Override
    public boolean isIndependentEntity() {
        return true;
    }

    @Override
    protected void mergeAction(ChangeSet changeSet, HawEntity hawEntity, UserPrincipal principal) throws HawAuthorizationException {
        // It is not foreseen, that Users may edit or change MoabJob entries.
    }

    @Override
    protected void createAction(ChangeSet changeSet, UserPrincipal principal) {
        // It is not foreseen, that Users may edit or change MoabJob entries.
    }

    @Override
    protected void removeAction(ChangeSet changeSet, UserPrincipal principal) {
        // It is not foreseen, that Users may edit or change MoabJob entries.
    }

}
