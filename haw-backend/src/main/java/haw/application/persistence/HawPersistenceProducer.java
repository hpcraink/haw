package haw.application.persistence;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * This is a convenience class to enable injection of the EntityManager with @Inject
 * annotation.
 * 
 * @author Uwe Eisele
 */
@RequestScoped
public class HawPersistenceProducer {

	/** The entity manager. */
	@PersistenceContext(unitName = "HawPU")
	private EntityManager entityManager;

	/**
	 * Gets the entity manager.
	 * 
	 * @return the entity manager
	 */
	@Produces
	@RequestScoped
	protected EntityManager getEntityManager() {
		return entityManager;
	}
}
