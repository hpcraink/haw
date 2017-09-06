package haw.application.rest.configuration;

import haw.application.configuration.ApplicationConfiguration;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * Defines the JAK JAX-RS application and supplies additional meta-data. With
 * the @ApplicationPath the root path of all rest services is specified. Without
 * this class the rest services wont work.
 *
 * @author Uwe Eisele
 */
@ApplicationPath(ApplicationConfiguration.RESOURCEROOT)
public class RestApplicationConfiguration extends Application {
    // do nothing
}
