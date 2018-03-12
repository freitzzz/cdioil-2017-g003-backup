/**
 *
 */
package eapli.demoapplication;

import eapli.demoapplication.domain.authz.ActionRight;

/**
 * A "global" static class with the application registry of well known objects
 *
 * @author Paulo Gandra Sousa
 */
public class Application {

	private static final AppSession SESSION = new AppSession();
	private static final AppSettings SETTINGS = new AppSettings();


	private Application() {
		// private visibility to ensure singleton & utility
	}

	public static AppSession session() {
		return SESSION;
	}

	public static AppSettings settings() {
		return SETTINGS;
	}

	/**
	 * helper method to check the permission of the currently logged in user
	 */
	public static void ensurePermissionOfLoggedInUser(ActionRight action) {
		session().ensurePermissionOfLoggedInUser(action);
	}
}
