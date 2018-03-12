/**
 *
 */
package eapli.demoapplication.persistence;

import eapli.demoapplication.Application;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * provides easy access to the persistence layer. works as a factory of
 * factories
 *
 * @author Paulo Gandra Sousa
 */
public class PersistenceContext {

	private PersistenceContext() {
	}

	public static RepositoryFactory repositories() {
		// return new InMemoryRepositoryFactory();
		// return new JpaRepositoryFactory();

		final String factoryClassName = Application.settings().getRepositoryFactory();
		try {
			return (RepositoryFactory) Class.forName(factoryClassName).newInstance();
		} catch (ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
			// FIXME handle exception properly
			Logger.getLogger(PersistenceContext.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}
}
