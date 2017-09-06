package haw.common.resource.type;

import haw.common.resource.entity.Resourcetimespan;

/**
 * The Enum Scheduler. It contains all possible schedulers for a
 * {@link Resourcetimespan#getScheduler()}
 */
public enum Scheduler {
	/** The MOAB scheduler/accounting system. */
	MOAB,
	/** The unspecified scheduler/accounting system. */
	UNSPECIFIED;
}
