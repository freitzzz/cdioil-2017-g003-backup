/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template
 * in the editor.
 */
package eapli.demoapplication.backoffice.consoleapp;

import eapli.demoapplication.backoffice.consoleapp.presentation.MainMenu;
import eapli.demoapplication.bootstrap.DemoApplicationBootstrap;
import eapli.demoapplication.consoleapp.presentation.authz.LoginAction;

/**
 * @author Paulo Gandra Sousa
 */
public final class BackOfficeApp {

	/**
	 * Empty constructor is private to avoid instantiation of this class.
	 */
	private BackOfficeApp() {
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(final String[] args) {

		// only needed because of the in memory persistence
		new DemoApplicationBootstrap().execute();

		// login and go to main menu
		// TODO should provide three attempts
		if (new LoginAction().execute()) {
			final MainMenu menu = new MainMenu();
			menu.mainLoop();
		}
	}
}
