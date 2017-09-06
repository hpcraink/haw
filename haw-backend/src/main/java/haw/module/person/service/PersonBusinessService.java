package haw.module.person.service;

import haw.common.action.annotation.Action;
import haw.common.action.service.NotificationEventService;
import haw.common.error.handler.HawErrorHandler;
import haw.common.exception.HawResourceNotExistsException;
import haw.common.exception.HawServerException;
import haw.common.helper.StringHelper;
import haw.common.logging.Track;
import haw.common.resource.entity.Login;
import haw.common.resource.entity.Person;
import haw.module.security.service.EncryptionService;
import java.sql.Timestamp;
import java.util.Date;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

/**
 * This service is responsible for managing business logic for the
 * PersonService.
 *
 * @author Katharina
 */
@Track
@Stateless
public class PersonBusinessService {

    /**
     * The em.
     */
    @Inject
    private EntityManager em;

    /**
     * The encryption service.
     */
    @Inject
    private EncryptionService encryptionService;

    /**
     * The notification event service.
     */
    @Inject
    private NotificationEventService notificationEventService;

    /**
     * The error handler
     */
    @Inject
    private HawErrorHandler errorHandler;

    /**
     * Creates or updates the LogInInformation and sets it to the given member.
     * If update = false the user will get a new userName
     *
     * @param person the person
     * @param update true, if the logInInformation must be updated, not created.
     * @param newPassword the new password. If its null or empty, a password
     * will be generated
     * @return the person with loginInformation
     * @throws HawServerException Thrown if problem on db occurs
     */
    public Person createOrUpdateLoginInformation(Person person, boolean update,
            String newPassword) throws HawServerException {
        Login login = new Login();
        Login oldLogIn = person.getLogin();
        if (update
                && oldLogIn != null
                && StringHelper.isNotEmpty(person.getEmail())) {
            login = oldLogIn;
        } else {
            // XXX Validate Email-Address: It should be Unique!
            login.setPerson(person);
            person.setLogin(login);
        }
        login.setRequestTime(new Timestamp(System.currentTimeMillis()));
        String salt = new Date().toString();
        login.setSalt(salt);
        String password = newPassword;
        if (!StringHelper.isNotEmpty(newPassword)) {
            password = StringHelper.generateString(10);
        }
        String saltedPassInput = encryptPW(password, salt);
        login.setPassword(saltedPassInput);
        if (update) {
            notificationEventService.fireAction(Action.UPDATE, person,
                    "password", password);
        } else {
            notificationEventService.fireAction(Action.CREATE, person,
                    "password", password);
        }
        return person;
    }

    /**
     * Encrypt the given password with the given salt and returns the encrypted
     * password.
     *
     * @param password the plain password
     * @param salt the salt to use for encryption
     * @return the encrypted password.
     * @throws HawServerException Thrown if problem with encryption occurs
     */
    public String encryptPW(String password, String salt) throws HawServerException {
        return encryptionService.getSaltedPassFromPlain(password, salt);
    }

    /**
     * Checks if the given userName already exists in the database. If true, a
     * number will be added to this name.
     *
     * @param userName username which should be checked
     * @param trial number of times this method was called for one userName
     * @return a valid userName
     * @throws HawServerException Thrown if problem on db occurs
     */
    private String validateUserName(String userName, int trial) throws HawServerException {
        String result = userName;
        int trialInternal = trial;
        if (trialInternal != 0) {
            result = userName + trialInternal;
        }
        Person alreadyExistingPerson = null;
        try {
            alreadyExistingPerson = searchInDB(result);
        } catch (HawResourceNotExistsException e) {
            return result;
        }
        if (alreadyExistingPerson != null) {
            result = validateUserName(result, ++trialInternal);
        }
        return result;
    }

    /**
     * Search in db for the given userName
     *
     * @param userName the user name
     * @return the person
     * @throws HawServerException Thrown if problem on db occurs
     * @throws HawResourceNotExistsException Thrown if username not found
     */
    private Person searchInDB(String userName)
            throws HawServerException, HawResourceNotExistsException {
        Person person = null;
        try {
            TypedQuery<Person> jpaQuery = em.createNamedQuery(
                    "Person.findByEmailAll", Person.class);
            jpaQuery.setParameter("email", userName);
            person = jpaQuery.getSingleResult();
        } catch (NoResultException e) {
            throw new HawResourceNotExistsException(e.getMessage(), e);
        } catch (Throwable e) {
            errorHandler.handleTechnicalResourceError(e, "member");
        }
        return person;
    }
}
