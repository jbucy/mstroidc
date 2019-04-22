package oidc_support;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author hfaheem
 * @Date 04/17/2019
 * 
 * This propertiesFileReader class use for read properties files value under envConfig folder.
 * All property values will be set to the class variable.
 * All property value mapped as KEY/VALUE pair.
 * 
 *  */

public class PropertiesFileReader {
	
	/* 
	 * logger variable use for print logs on to file or console 
	 * logger methods 
	 * logger.info -> for display information, 
	 * logger.error -> for display error
	 * logger.debug -> use for debugging 
	 * */
	private static final Logger logger = LoggerFactory.getLogger(ProfileFilter.class);

	/* Configuration to connect properties for reading*/
	private InputStream inputStream;
	private String propertiesBasePath = "envConfig/";
	private String currentEnv = "";

	/* OIDC Clients  configuration setting */
	private String clientID = "LBMT";
	private String secret = "restrict_cater_tray";
	private String discoryURL = "";

	/* OIDC clients and callback configuration */
	private String callBackURL = "";
	private String clientsURL = "";

	/* This constracture use the default path for reading the properties file */
	public PropertiesFileReader() {
		try {
			init(propertiesBasePath);
			initializeProperties(propertiesBasePath + getCurrentEnv() + "/config.properties");
		} catch (IOException ex) {
			logger.error("Error to open properties file " + ex.toString());
		}
	}

	/* 
	 * This constructor use custom path for reading properties file 
	 * */
	public PropertiesFileReader(String propertiesBasePath) {
		try {
			this.propertiesBasePath = propertiesBasePath;
			init(propertiesBasePath);
			initializeProperties(propertiesBasePath + getCurrentEnv() + "/config.properties");
		} catch (IOException ex) {
			logger.error("Error to open properties file " + ex.toString());
		}
	}

	/* 
	 * Initialize environment variable for reading the property file accordingly 
	 * */
	public void init(String propertiesBasePath) throws IOException {
		try {
			Properties prop = new Properties();
			inputStream = getClass().getClassLoader().getResourceAsStream(propertiesBasePath + "envConfig.properties");

			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propertiesBasePath + "envConfig.properties"
						+ "' not found in the classpath");
			}
		
			if (prop.getProperty("env") != null) {
				setCurrentEnv(prop.getProperty("env"));	
			}
			logger.info("Current environment set to " + getCurrentEnv());

		} catch (Exception e) {
			logger.error("Exception: " + e);
		} finally {
			inputStream.close();
		}

	}

	/* 
	 * Read properties value and initialize to its corresponding configuration variable 
	 * */
	private void initializeProperties(String currentEnv) throws IOException {
		try {
			Properties prop = new Properties();
			inputStream = getClass().getClassLoader().getResourceAsStream(currentEnv);

			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + currentEnv + "' not found in the classpath");
			}

			if (prop.getProperty("clientId") != null) {
				setClientID(prop.getProperty("clientId"));
			}

			if (prop.getProperty("secret") != null) {
				setSecret(prop.getProperty("secret"));
			}

			if (prop.getProperty("discoveryURL") != null) {
				setDiscoryURL(prop.getProperty("discoveryURL"));
			}

			if (prop.getProperty("callBackURL") != null) {
				setCallBackURL(prop.getProperty("callBackURL"));
			}
			if (prop.getProperty("clientsURL") != null) {
				setClientsURL(prop.getProperty("clientsURL"));
			}

			logger.info("All properties values are set to " + getCurrentEnv() + "environment");

		} catch (Exception ex) {
			logger.error("Exception: " + ex);
		} finally {
			inputStream.close();
		}

	}

	/*
	 * Setter and getter for all private variable to 
	 * access these variable from the outside 
	 * */
	public String getCurrentEnv() {
		return this.currentEnv;
	}

	public void setCurrentEnv(String env) {
		this.currentEnv = env;
	}

	public String getPropertiesBasePath() {
		return propertiesBasePath;
	}

	public void setPropertiesBasePath(String propertiesBasePath) {
		this.propertiesBasePath = propertiesBasePath;
	}

	public String getClientID() {
		return clientID;
	}

	public void setClientID(String clientID) {
		this.clientID = clientID;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getDiscoryURL() {
		return discoryURL;
	}

	public void setDiscoryURL(String discoryURL) {
		this.discoryURL = discoryURL;
	}

	public String getCallBackURL() {
		return callBackURL;
	}

	public void setCallBackURL(String callBackURL) {
		this.callBackURL = callBackURL;
	}

	public String getClientsURL() {
		return clientsURL;
	}

	public void setClientsURL(String clientsURL) {
		this.clientsURL = clientsURL;
	}

}
