package oidc_support;

import org.pac4j.core.client.Clients;
import org.pac4j.core.client.finder.DefaultCallbackClientFinder;
import org.pac4j.core.config.Config;
import org.pac4j.core.config.ConfigFactory;
import org.pac4j.core.engine.CallbackLogic;
import org.pac4j.core.http.callback.NoParameterCallbackUrlResolver;
import org.pac4j.oidc.client.OidcClient;
import org.pac4j.oidc.config.OidcConfiguration;

public class OidcConfigurationFactory implements ConfigFactory {
	@SuppressWarnings("deprecation")
	@Override	
	public org.pac4j.core.config.Config build(Object... arg0) {

		//java.lang.System.out.println("Test print line");
		java.lang.System.out.println("11.Executing OidcConfigurationFactory");		
		
		final OidcConfiguration oidcConfig = new OidcConfiguration();
		oidcConfig.setClientId("LBMT");
		oidcConfig.setSecret("restrict_cater_tray");
		oidcConfig.setDiscoveryURI("https://dev-authenticationservice.devcloud.thirtyonegifts.com/.well-known/openid-configuration");
		//oidcConfig.setWithState(true);
		//oidcConfig.setStateData("TOGSession");
		//oidcConfig.setExpireSessionWithToken(false);
		//oidcConfig.setConnectTimeout(5000);
		//oidcConfig.setResponseType("code id_token");
		
		System.out.println("12.OidcConfigurationFactory_oidcConfig=");
		System.out.println(oidcConfig.toString());	
		System.out.println(oidcConfig.STATE.toString());
		System.out.println(oidcConfig.STATE_SESSION_ATTRIBUTE);		
		System.out.println("12b.OidcConfigurationFactory_oidcConfig.getStateData=");
		System.out.println(oidcConfig.getStateData());
	
		final OidcClient client = new OidcClient(oidcConfig);

		
		client.setCallbackUrl("https://localhost:8443/customweb/callback");
		client.setCallbackUrlResolver(new NoParameterCallbackUrlResolver());
		
		System.out.println("13.OidcConfigurationFactory_client=");
		System.out.println(client.toString());
		
		//Clients clients = new Clients("https://dc1wdedwrpt04.31gifts.corp:8443/customweb", client);
        Clients clients = new Clients("https://dc1wdedwrpt04.31gifts.corp:8443/customweb/callback", client);
        
        //clients.setDefaultSecurityClients("OidcClient");
        Config config = new Config(clients);     
    
 		System.out.println("14.OidcConfigurationFactory_config=");    		
        System.out.println(config.toString());    
        
        return config;   
	}
}