package haw.application.configuration;

import static haw.common.helper.StringHelper.*;
import haw.common.helper.BeanHelper;
import haw.common.helper.StringHelper;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.naming.NamingException;

import org.slf4j.LoggerFactory;

/**
 * This class holds the application configuration. The parameters can be
 * configured over system properties or the web.xml file.
 *
 * @author Uwe Eisele, Rainer Keller
 */
@ApplicationScoped
public class ApplicationConfiguration {

    // Global configuration constants which can not be changed during runtime
    /* First all Regular Expressions -- please check webclient's config.js
    /* At least the RegExp for data provided through public web-interface must be included */
 /* Regular Expressio for empty String (if allowed) */
    public static final String REGEXPEMPTYSTRING = "^$";

    /**
     * Regular Expression for Strings with ASCII and digits
     */
    public static final String REGEXPSTRINGWITHDIGITS = "^[A-Za-z0-9]+$";

    /**
     * Regular Expression for Strings with accents / Umlaute
     */
    public static final String REGEXPNAMEWITHACCENTS = "^[A-Za-z\\s\\.\\:\\-\\/\\&\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF]+$";

    /**
     * Regular Expression for Strings with digits, accents / Umlaute
     */
    public static final String REGEXPNAMEWITHDIGITSACCENTS = "^[A-Za-z0-9\\s\\.\\:\\-\\/\\&\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF]+$";

    /**
     * Regular Expression for Strings with digits, accents / Umlaute and
     * punctuation
     */
    public static final String REGEXPNAMEWITHDIGITSACCENTSPUNCTUATION = "^[A-Za-z0-9\\s\\.\\:\\-\\/\\&\\(\\)\\',;!\\?\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF]+$";

    /**
     * Regular Expression for Strings with international telephone numbers
     */
    public static final String REGEXPINTLTELNUMBER = "^(\\+?[0-9]{1,3})?[0-9]+((\\s?\\-\\s?|\\s|\\s?\\/\\s?)?[0-9]+)+$";

    /**
     * Regular Expression for Email (stolen from Backbone-validation) so it
     * matches the web-frontends requirements
     */
    // Converted from backbone-validation.js using http://www.regexplanet.com/advanced/java/index.html
    // ATTENTION: Needed to allow capital case as well, e.g. instead of [a-z] changed to [a-zA-Z] 
    public static final String REGEXPEMAIL = "^((([a-zA-Z]|\\d|[!#\\$%&'\\*\\+\\-\\/=\\?\\^_`{\\|}~]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])+(\\.([a-zA-Z]|\\d|[!#\\$%&'\\*\\+\\-\\/=\\?\\^_`{\\|}~]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])+)*)|((\\x22)((((\\x20|\\x09)*(\\x0d\\x0a))?(\\x20|\\x09)+)?(([\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x7f]|\\x21|[\\x23-\\x5b]|[\\x5d-\\x7e]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(\\\\([\\x01-\\x09\\x0b\\x0c\\x0d-\\x7f]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF]))))*(((\\x20|\\x09)*(\\x0d\\x0a))?(\\x20|\\x09)+)?(\\x22)))@((([a-zA-Z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(([a-zA-Z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])([a-zA-Z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])*([a-zA-Z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])))\\.)+(([a-zA-Z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(([a-zA-Z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])([a-zA-Z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])*([a-zA-Z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])))$";

    /**
     * Regular Expression for jak-server Gender
     */
    public static final String REGEXPGENDER = "^(MALE|FEMALE|UNSPECIFIED)$";

    /**
     * The root for all REST services.
     */
    public static final String RESOURCEROOT = "resources";

    /**
     * The name of the authorization cookie.
     */
    public static final String AUTHORIZATION_COOKIENAME = "authorization";

    // Configuration parameters
    /**
     * Parameter: The protocol (default is http).
     */
    public static final String PARAMETER_COMMON_PROTOCOL = "haw.application.configuration.common.protocol";

    /**
     * Parameter: The hostname or domain (default is localhost).
     */
    public static final String PARAMETER_COMMON_HOSTNAME = "haw.application.configuration.common.hostname";

    /**
     * Parameter: The domainname (default is localhost).
     */
    public static final String PARAMETER_COMMON_DOMAINNAME = "haw.application.configuration.common.domainname";

    /**
     * Parameter: The port for the above default protocol (default is 8080).
     */
    public static final String PARAMETER_COMMON_PORT = "haw.application.configuration.common.port";

    /**
     * Parameter: The application context root (default is /haw).
     */
    public static final String PARAMETER_COMMON_CONTEXTROOT = "haw.application.configuration.common.contextRoot";

    /**
     * Parameter: Whether the authorization cookie must only be set/read using
     * secure HTTPS (default is true).
     */
    public static final String PARAMETER_COMMON_AUTHORIZATION_COOKIE_SECURE = "haw.application.configuration.common.authorizationCookieSecure";

    /**
     * Parameter: The default language to use.
     */
    public static final String PARAMETER_COMMON_DEFAULT_LANGUAGE = "haw.application.configuration.common.language";

    /**
     * Parameter: The session timeout in minutes (default is 30).
     */
    public static final String PARAMETER_SESSION_TIMEOUT_MIN = "haw.application.configuration.session.timeoutMin";

    /**
     * *********************************************************************
     * Default values
     **********************************************************************
     */
    /**
     * The default value for the session timeout in minutes
     */
    public static final int DEFAULT_SESSION_TIMEOUT_MIN = 30;

    /**
     * The default language.
     */
    public static final String DEFAULT_LANGUAGE = "DE";

    /**
     * The default properties.
     */
    @SuppressWarnings("serial")
    private static final Map<String, String> DEFAULT_PROPERTIES = new HashMap<String, String>() {
        {
            put(PARAMETER_COMMON_PROTOCOL, "http");
            put(PARAMETER_COMMON_HOSTNAME, "localhost");
            put(PARAMETER_COMMON_PORT, "8080");
            put(PARAMETER_COMMON_CONTEXTROOT, "/haw");
            put(PARAMETER_COMMON_AUTHORIZATION_COOKIE_SECURE, "true");
            put(PARAMETER_COMMON_DEFAULT_LANGUAGE, "DE");
            put(PARAMETER_SESSION_TIMEOUT_MIN, String.valueOf(DEFAULT_SESSION_TIMEOUT_MIN));
        }
    };

    /**
     * The actual properties (always overridden by system properties).
     */
    private final Map<String, String> PROPERTIES
            = Collections.synchronizedMap(new HashMap<>(DEFAULT_PROPERTIES));

    /**
     * Gets the single instance of ApplicationConfiguration. Should only be used
     * if no CDI is available. To get an instance use injection (@Object
     * ApplicationConfiguration)
     *
     * @return single instance of ApplicationConfiguration
     */
    public static ApplicationConfiguration getInstance() {
        try {
            return BeanHelper.lookup(ApplicationConfiguration.class);
        } catch (NamingException e) {
            LoggerFactory.getLogger(ApplicationConfiguration.class).error(
                    e.getMessage());
            return null;
        }
    }

    /**
     * Returns the application property for the given parameter. System
     * properties have always precedence before application properties.
     *
     * @param parameter the parameter
     * @return The value for the given property (or null if not defined).
     */
    public String getProperty(String parameter) {
        String applicationValue = PROPERTIES.get(parameter);
        return System.getProperty(parameter, applicationValue);
    }

    /**
     * Sets a value for a given parameter to the application properties.
     *
     * @param parameter The parameter
     * @param value The value
     */
    public void putProperty(String parameter, String value) {
        PROPERTIES.put(parameter, value);
    }

    /**
     * Checks if is parameter defined.
     *
     * @param parameter the parameter
     * @return true, if is parameter defined
     */
    public boolean isParameterDefined(String parameter) {
        return getProperty(parameter) != null;
    }

    /**
     * Gets the protocol.
     *
     * @return the protocol
     */
    public String getProtocol() {
        return getProperty(PARAMETER_COMMON_PROTOCOL);
    }

    /**
     * Sets the protocol.
     *
     * @param protocol the new protocol
     */
    protected void setProtocol(String protocol) {
        putProperty(PARAMETER_COMMON_PROTOCOL, protocol);
    }

    /**
     * Gets the hostname.
     *
     * @return the hostname
     */
    public String getHostname() {
        return getProperty(PARAMETER_COMMON_HOSTNAME);
    }

    /**
     * Sets the hostname.
     *
     * @param hostname the new hostname
     */
    protected void setHostname(String hostname) {
        putProperty(PARAMETER_COMMON_HOSTNAME, hostname);
    }

    /**
     * Gets the domainname.
     *
     * @return the domainname
     */
    public String getDomainname() {
        return getProperty(PARAMETER_COMMON_DOMAINNAME);
    }

    /**
     * Sets the domainname.
     *
     * @param domainname the new domainname
     */
    protected void setDomainname(String domainname) {
        putProperty(PARAMETER_COMMON_DOMAINNAME, domainname);
    }

    /**
     * Gets the port.
     *
     * @return the port
     */
    public String getPort() {
        return getProperty(PARAMETER_COMMON_PORT);
    }

    /**
     * Sets the port.
     *
     * @param port the new port
     */
    protected void setPort(String port) {
        putProperty(PARAMETER_COMMON_PORT, port);
    }

    /**
     * Gets the context root.
     *
     * @return the context root
     */
    public String getContextRoot() {
        return getProperty(PARAMETER_COMMON_CONTEXTROOT);
    }

    /**
     * Sets the context root.
     *
     * @param contextRoot the new context root
     */
    protected void setContextRoot(String contextRoot) {
        String tmpValue;
        if (StringHelper.isEmpty(contextRoot)) {
            tmpValue = "";
        } else {
            tmpValue = contextRoot.trim();
        }
        if (!tmpValue.startsWith("/")) {
            tmpValue = "/" + tmpValue;
        }
        if (tmpValue.endsWith("/")) {
            tmpValue = tmpValue.substring(0,
                    tmpValue.length() - 1);
        }
        putProperty(PARAMETER_COMMON_CONTEXTROOT, tmpValue);
    }

    /**
     * Returns whether the Authorization Cookie is set/read only via HTTPS.
     *
     * Whether the authorization cookie may only be set and read via a secure
     * HTTPS connection.
     *
     * @return the value of the secure setting.
     */
    public Boolean getAuthorizationCookieSecure() {
        String value = getProperty(PARAMETER_COMMON_AUTHORIZATION_COOKIE_SECURE);
        return "TRUE".equalsIgnoreCase(value);
    }

    /**
     * Sets the value of the secure setting for the Authorization Cookie.
     *
     * Allowing the authorization cookie only on secure HTTPS connections
     * increasing security in disallowing using the admin interface over
     * un-encrypted HTTP connections. The default is true (only using HTTPS
     * connections).
     *
     * @param secure the value of the setting
     */
    public void setAuthorizationCookieSecure(Boolean secure) {
        putProperty(PARAMETER_COMMON_AUTHORIZATION_COOKIE_SECURE, String.valueOf(secure));
    }

    /**
     * Returns the session timeout in minutes. Authorization cookies is valid
     * for this time.
     *
     * @return the session timeout in minutes.
     */
    public int getSessionTimeoutMin() {
        String value = getProperty(PARAMETER_SESSION_TIMEOUT_MIN);
        int intValue;
        if (isNumeric(value)) {
            intValue = Integer.parseInt(value);
        } else {
            intValue = DEFAULT_SESSION_TIMEOUT_MIN;
        }
        return intValue;
    }

    /**
     * Sets the session timeout in minutes. Authorization cookies is valid for
     * this time.
     *
     * @param value the session timeout in minutes
     */
    public void setSessionTimeoutMin(int value) {
        putProperty(PARAMETER_SESSION_TIMEOUT_MIN, String.valueOf(value));
    }

    /**
     * Gets the default language.
     *
     * @return the default language
     */
    public String getLanguage() {
        return getProperty(PARAMETER_COMMON_DEFAULT_LANGUAGE);
    }

    /**
     * Sets the language.
     *
     * @param language the new language
     */
    protected void setLanguage(String language) {
        putProperty(PARAMETER_COMMON_DEFAULT_LANGUAGE, language);
    }

}
