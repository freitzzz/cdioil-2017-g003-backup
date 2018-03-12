package eapli.demoapplication.frontoffice.consoleapp;

import eapli.demoapplication.bootstrap.DemoApplicationBootstrap;
import eapli.demoapplication.frontoffice.consoleapp.presentation.FrontMenu;

/**
 * FrontOffice App.
 */
public final class FrontOfficeApp {

	/**
	 * Empty constructor is private to avoid instantiation of this class.
	 */
	private FrontOfficeApp() {
	}

	public static void main(final String[] args) {

		// only needed because of the in memory persistence
		new DemoApplicationBootstrap().execute();

		new FrontMenu().show();
	}
}
