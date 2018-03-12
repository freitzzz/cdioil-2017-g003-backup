package eapli.demoapplication;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * the application settings.
 *
 * @author Paulo Gandra Sousa
 */
public class AppSettings {

	private static final String PROPERTIES_RESOURCE = "demo-application.properties";
	private static final String REPOSITORY_FACTORY_KEY = "persistence.repositoryFactory";
	private static final String UI_MENU_LAYOUT_KEY = "ui.menu.layout";
	private static final String PERSISTENCE_UNIT_KEY = "persistence.persistenceUnit";
	private final Properties applicationProperties = new Properties();

	public AppSettings() {
		loadProperties();
	}

	private void loadProperties() {
		InputStream propertiesStream = null;
		try {
			propertiesStream = this.getClass().getClassLoader().getResourceAsStream(PROPERTIES_RESOURCE);
			if (propertiesStream != null) {
				this.applicationProperties.load(propertiesStream);
			} else {
				throw new FileNotFoundException(
						"property file '" + PROPERTIES_RESOURCE + "' not found in the classpath");
			}
		} catch (final IOException exio) {
			setDefaultProperties();

			Logger.getLogger(AppSettings.class.getName()).log(Level.SEVERE, null, exio);
		} finally {
			if (propertiesStream != null) {
				try {
					propertiesStream.close();
				} catch (final IOException ex) {
					Logger.getLogger(AppSettings.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}
	}

	private void setDefaultProperties() {
		this.applicationProperties.setProperty(REPOSITORY_FACTORY_KEY,
				"JpaRepositoryFactory");
		this.applicationProperties.setProperty(UI_MENU_LAYOUT_KEY, "horizontal");
		this.applicationProperties.setProperty(PERSISTENCE_UNIT_KEY, "eapli.demo-application-PU");
	}

	public Boolean isMenuLayoutHorizontal() {
		return "horizontal".equalsIgnoreCase(this.applicationProperties.getProperty(UI_MENU_LAYOUT_KEY));
	}

	public String getPersistenceUnitName() {
		return this.applicationProperties.getProperty(PERSISTENCE_UNIT_KEY);
	}

	public String getRepositoryFactory() {
		return this.applicationProperties.getProperty(REPOSITORY_FACTORY_KEY);
	}
}
