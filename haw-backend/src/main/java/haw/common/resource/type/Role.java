package haw.common.resource.type;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Type which specifies the security roles. The roles are priorized by the
 * ordinal of the enum constants. The lowest role is ANONYMOUS which has
 * priority 0 and the highest role is ADMIN which has priority 7.
 */
public enum Role {

    /**
     * The anonymous user. This user is just visiting the site and is not yet
     * logged in.
     */
    ANONYMOUS,
    /**
     * The normal user, which may log in. This user may see his computed jobs.
     */
    USER,
    /**
     * The project leader. The project leader sees the computed jobs by all of
     * the people involved in this project.
     */
    PROJECTLEADER,
    /**
     * The university responsible person. Persons in this role may see all the
     * computed jobs from his/her University.
     */
    UNIRESPONSIBLE,
    /**
     * The admin. Users belonging to the Admin role may see and edit all the
     * entities
     */
    ADMIN;

    /**
     * The sorted roles.
     */
    private static SortedSet<Role> sortedRoles = Collections
            .unmodifiableSortedSet(new TreeSet<>(Arrays.asList(Role.values())));

    /**
     * Returns the priority of this role. This is the same then the ordinal of
     * the enum constant.
     *
     * @return the priority
     */
    public int getPriority() {
        return this.ordinal();
    }

    /**
     * Returns the lowest role of this enum.
     *
     * @return the lowest role
     */
    public static Role getLowestRole() {
        return sortedRoles.first();
    }

    /**
     * Returns the highest role of this enum.
     *
     * @return the highest role
     */
    public static Role getHighestRole() {
        return sortedRoles.last();
    }

    /**
     * Returns all roles.
     *
     * @return the all roles
     */
    public static SortedSet<Role> getAllRoles() {
        return sortedRoles;
    }

    /**
     * Returns a set of all roles which have a priority equal or lower then the
     * given role.
     *
     * @param role the role
     * @return the all roles until role
     */
    public static SortedSet<Role> getAllRolesUntilRole(Role role) {
        return Collections.unmodifiableSortedSet(new TreeSet<>(EnumSet.range(
                USER, role)));
    }

    /**
     * Returns a set of all roles which have a priority equal and higher then
     * the given role.
     *
     * @param role the role
     * @return the all roles from role
     */
    public static SortedSet<Role> getAllRolesFromRole(Role role) {
        return Collections.unmodifiableSortedSet(new TreeSet<>(EnumSet.range(
                role, ADMIN)));
    }

    /**
     * Returns a set of all roles which have a priority equal to or lower than
     * the highest role in the given collection.
     *
     * @param assignedRoles the assigned roles
     * @return the all roles until highest role of
     */
    public static SortedSet<Role> getAllRolesUntilHighestRoleOf(
            Collection<Role> assignedRoles) {
        return getRolesUntilHighestRoleOf(new TreeSet<>(assignedRoles));
    }

    /**
     * Returns a set of all roles which have a priority equal or lower then the
     * highest role in the given set.
     *
     * @param assignedRoles the assigned roles
     * @return the roles until highest role of
     */
    public static SortedSet<Role> getRolesUntilHighestRoleOf(
            SortedSet<Role> assignedRoles) {
        TreeSet<Role> roles;
        if (!assignedRoles.isEmpty()) {
            roles = new TreeSet<>(EnumSet.range(USER, assignedRoles.last()));
        } else {
            roles = new TreeSet<>(EnumSet.of(USER));
        }
        return Collections.unmodifiableSortedSet(roles);
    }

    /**
     * Indicates if the given name is a valid role.
     *
     * @param roleName the role name
     * @return true, if is valid role
     */
    public static boolean isValidRole(String roleName) {
        return getByName(roleName) != null;
    }

    /**
     * Returns the Role of the given name.
     *
     * @param roleName the role name
     * @return the by name
     */
    public static Role getByName(String roleName) {
        Role role = null;
        for (Role aRole : values()) {
            if (aRole.name().equalsIgnoreCase(roleName)) {
                role = aRole;
                break;
            }
        }
        return role;
    }
}
