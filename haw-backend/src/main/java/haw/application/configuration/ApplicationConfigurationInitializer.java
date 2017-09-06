package haw.application.configuration;

import haw.common.helper.StringHelper;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Enumeration;
import javax.servlet.ServletContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * The class initializes the application parameters. All initialization
 * parameters of the web.xml are loaded. In addition the image root directory
 * path is set based on the image context root.
 *
 * @author Uwe Eisele, Rainer Keller
 */
@WebListener
public class ApplicationConfigurationInitializer implements
        ServletContextListener {

        /*
	 * (non-Javadoc)
	 * @see
	 * javax.servlet.ServletContextListener#contextInitialized(javax.servlet
	 * .ServletContextEvent)
         */
        @Override
        public void contextInitialized(ServletContextEvent sce) {
                ApplicationConfiguration ac = ApplicationConfiguration
                        .getInstance();
                ServletContext sc = sce.getServletContext();
                // Add all init parameters to application properties
                Enumeration<String> parameters = sc.getInitParameterNames();
                while (parameters.hasMoreElements()) {
                        String parameter = parameters.nextElement();
                        String value = sc.getInitParameter(parameter);
                        ac.putProperty(parameter, value);
                }

                // Set hostname to system hostname if not defined
                String hostname = sc.getInitParameter(
                        ApplicationConfiguration.PARAMETER_COMMON_HOSTNAME);
                if (StringHelper.isEmpty(hostname)) {
                        try {
                                hostname = InetAddress.getLocalHost().getCanonicalHostName();
                                ac.setHostname(hostname);
                        } catch (UnknownHostException e) {
                                //Do nothing
                        }
                }

                // Prior to init of JavaMail, set values to allow proper SMTP HELO/EHLO commands
                sc.setInitParameter("mail.smtp.localhost", hostname);
                System.setProperty("mail.smtp.localhost", hostname);

                // Set port to app server listener port if not defined
                String port = sc.getInitParameter(
                        ApplicationConfiguration.PARAMETER_COMMON_PORT);
                if (StringHelper.isEmpty(port)) {
                        port = System.getProperty("HTTP_LISTENER_PORT", "8080");
                        ac.setPort(port);
                }
        }

        /*
	 * (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.
	 * ServletContextEvent)
         */
        @Override
        public void contextDestroyed(ServletContextEvent sce) {
                // do nothing
        }

}
