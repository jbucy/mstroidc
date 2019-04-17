package oidc_support;

import org.pac4j.core.client.Clients;
import org.pac4j.core.config.Config;
import org.pac4j.core.config.ConfigFactory;
import org.pac4j.core.http.callback.NoParameterCallbackUrlResolver;
import org.pac4j.oidc.client.OidcClient;
import org.pac4j.oidc.config.OidcConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
 * @author hfaheem
 * @DATE 04/17/2019
 * 
 * This class use pac4j and setting all configuration.
 * 
 * */
public class OidcConfigurationFactory implements ConfigFactory {

	private static final Logger logger = LoggerFactory.getLogger(OidcConfigurationFactory.class);
	private PropertiesFileReader propertiesFileReader = new PropertiesFileReader();

	@SuppressWarnings("deprecation")
	@Override
	public org.pac4j.core.config.Config build(Object... arg0) {

		logger.info("***** Successfully Initializing OIDC Configuration Factory********");
		
		final OidcConfiguration oidcConfig = new OidcConfiguration();
		oidcConfig.setClientId(propertiesFileReader.getClientID());
		oidcConfig.setSecret(propertiesFileReader.getSecret());
		oidcConfig.setDiscoveryURI(propertiesFileReader.getDiscoryURL());
		/*
		 * oidcConfig.setWithState(true); oidcConfig.setStateData("TOGSession");
		 * oidcConfig.setExpireSessionWithToken(false);
		 * oidcConfig.setConnectTimeout(5000);
		 * oidcConfig.setResponseType("code id_token");
		 */

		logger.info("Successfully configure OIDC with Client ID and URI ");
		final OidcClient oidcClient = new OidcClient(oidcConfig);
		
		oidcClient.setCallbackUrl(propertiesFileReader.getCallBackURL()+"customweb/callback");
		oidcClient.setCallbackUrlResolver(new NoParameterCallbackUrlResolver());
		logger.info("Successfully configure OIDC Client with current environment URL " + oidcClient.toString());

		/*
		 * Clients clients = new
		 * Clients("https://dc1wdedwrpt04.31gifts.corp:8443/customweb", client);
		 */

		Clients clients = new Clients(propertiesFileReader.getClientsURL()+"customweb/callback", oidcClient);
		// clients.setDefaultSecurityClients("OidcClient");
		Config config = new Config(clients);
		logger.info("Successfully Configuration completed with clients : " + config.toString());

		return config;

	}
}