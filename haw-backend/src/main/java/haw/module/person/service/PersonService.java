package haw.module.person.service;

import haw.common.exception.HawAuthenticationException;
import haw.common.exception.HawAuthorizationException;
import haw.common.exception.HawResourceConflictException;
import haw.common.exception.HawResourceNotExistsException;
import haw.common.exception.HawServerException;
import haw.common.exception.HawValidationException;
import haw.common.resource.entity.Person;
import haw.common.resource.query.LimitQuery;
import haw.common.resource.type.Role;
import haw.common.resource.wrapper.Persons;

import java.util.Collection;

/**
 * Interface for member service. Service which implements this interface is
 * responsible for managing members.
 *
 * @author Alla Shapira
 * @author Katharina Knaus
 */
public interface PersonService {

    /**
     * Returns all not deleted persons.
     *
     * @param query Limitation for results to be returned.
     * @param onlyIdAndName if true only the id and the name of the member will
     * be returned.
     * @return Members which matches the passed limitation query.
     * @throws HawServerException Thrown if problem on database occurs.
     */
    public Persons getAllPersons(LimitQuery query, boolean onlyIdAndName)
            throws HawServerException;

    /**
     * Get a person with the given id.
     *
     * @param id of the person
     * @return the person
     * @throws HawServerException Thrown if problem on database occurs.
     * @throws HawResourceNotExistsException Thrown if passed person ID does not
     * exist.
     */
    public Person getPersonById(int id) throws HawServerException,
            HawResourceNotExistsException;

    /**
     * Returns all members which have the given roles.
     *
     * @param query Limitation for results to be returned.
     * @param roles the roles which should be searched for.
     * @return Members which matches the passed limitation query and have the
     * given roles.
     * @throws HawServerException Thrown if problem on database occurs.
     */
    public Persons getPersonsByRoles(LimitQuery query, Collection<Role> roles)
            throws HawServerException;

    /**
     * Returns all deleted persons.
     *
     * @param query Limitation for results to be returned
     * @return Members which matches the passed limitation query.
     * @throws HawServerException Thrown if problem on database occurs.
     */
    public Persons getAllDeletedPersons(LimitQuery query)
            throws HawServerException;

    /**
     * Returns all not deleted Project Leaders, but only name, first name and
     * the id of them.
     *
     * @param query Limitations for the results to be returned.
     * @return Volunteers which matches the passed limitation query.
     * @throws HawServerException Thrown if problem on database occurs .
     */
    /* XXX / TODO
    public Persons getAllProjectLeadersOnlyIDAndName(LimitQuery query) throws HawServerException;
    */

    /**
     * Gets the person by email.
     *
     * @param email the email address
     * @return the person by username
     * @throws HawServerException Thrown if problem on database occurs
     * @throws HawResourceNotExistsException resource does not exists exception
     */
    public Person getPersonByEmail(String email)
            throws HawServerException, HawResourceNotExistsException;

    /**
     * Gets the person by their University.
     *
     * @param university the University
     * @return the person by university
     * @throws HawServerException Thrown if problem on database occurs
     * @throws HawResourceNotExistsException resource does not exists exception
     */
    public Person getPersonByUniversity(String university)
            throws HawServerException, HawResourceNotExistsException;

    /**
     * Adds a new person.
     *
     * @param person Person to create
     * @return Created person with new unique id.
     * @throws HawServerException Thrown if problem on database occurs.
     * @throws HawValidationException Thrown if passed member is not valid.
     */
    public Person addPerson(Person person) throws HawServerException,
            HawValidationException;

    /**
     * Reactivates a deleted member if this member was deleted. If this member
     * has been not deleted, the method will do nothing.
     *
     * @param personId the id of the person
     * @throws HawServerException Thrown if problem on database occurs
     * @throws HawResourceNotExistsException the jak resource not exists
     * exception
     */
    public void reactivatePersonById(int personId)
            throws HawServerException, HawResourceNotExistsException;

    /**
     * Updates an existing person. Id of member must be set.
     *
     * @param person Person to update. All attributes are set to the existing
     * member which has the same id.
     * @return The updated person (equal to the passed person).
     * @throws HawServerException Thrown if problem on database occurs.
     * @throws HawValidationException Thrown if passed member is not valid.
     * @throws HawResourceNotExistsException Thrown if passed member does not
     * exist.
     * @throws HawAuthorizationException Thrown if the logged in user has no
     * permission to update a changed attribute
     * @throws HawResourceConflictException Thrown if update conflicts with
     * another update request.
     */
    public Person updatePerson(Person person) throws HawServerException,
            HawValidationException, HawResourceNotExistsException,
            HawAuthorizationException, HawResourceConflictException;

    /**
     * Changes the password.
     *
     * @param personID the personID of the member which password should be
     * changed
     * @param oldPW old password of the member, if admin=false; otherwise null
     * @param newPW new password of the member, if admin=false; otherwise null
     * @param admin true, if this method is called by an admin
     * @return Person, which password was changed.
     * @throws HawServerException Thrown if problem on database occurs .
     * @throws HawValidationException Thrown if passed member is not valid .
     * @throws HawResourceNotExistsException Thrown if member with the given id
     * does not exist.
     * @throws HawAuthenticationException if the oldPassword is not correkt
     */
    public Person changePW(Integer personID, String oldPW, String newPW,
            boolean admin) throws HawServerException, HawValidationException,
            HawResourceNotExistsException, HawAuthenticationException;

    /**
     * Removes the person with the specified id.
     *
     * @param id the id
     * @throws HawServerException Thrown if problem on database occurs.
     */
    public void removePersonById(Integer id) throws HawServerException;

    /**
     * Removes the person.
     *
     * @param person the person to remove
     * @throws HawServerException Thrown if problem on database occurs.
     */
    public void removePerson(Person person) throws HawServerException;

}
