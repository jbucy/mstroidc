package oidc_support;

import com.nimbusds.jwt.JWT;
import com.nimbusds.oauth2.sdk.AuthorizationCode;
import com.nimbusds.oauth2.sdk.ParseException;
import com.nimbusds.oauth2.sdk.id.State;
import com.nimbusds.oauth2.sdk.token.AccessToken;
import com.nimbusds.openid.connect.sdk.*;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.credentials.extractor.CredentialsExtractor;
import org.pac4j.core.exception.TechnicalException;
import org.pac4j.core.util.CommonHelper;
import org.pac4j.oidc.client.OidcClient;
import org.pac4j.oidc.config.OidcConfiguration;
import org.pac4j.oidc.credentials.OidcCredentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

public class OidcExtractor implements CredentialsExtractor<OidcCredentials> {

	private static final Logger logger = LoggerFactory.getLogger(OidcExtractor.class);
    protected OidcConfiguration configuration;
    protected OidcClient client;
    
    
    public OidcExtractor(final OidcConfiguration configuration, final OidcClient client) {
    	
    	java.lang.System.out.println("31. Executing OidcExtractor");
    	
    	CommonHelper.assertNotNull("configuration", configuration);
        CommonHelper.assertNotNull("client", client);
        this.configuration = configuration;
        this.client = client;
    }

    @Override
    public OidcCredentials extract(final WebContext context) {
    	
    	java.lang.System.out.println("32.Executing OidcCredentials");
    	java.lang.System.out.println("33.OidcCredentials_context=");
    	java.lang.System.out.println(context.toString());
    	
    	final String computedCallbackUrl = client.computeFinalCallbackUrl(context);

    	java.lang.System.out.println("34.OidcCredentials_computedCallbackUrl=");
    	java.lang.System.out.println(computedCallbackUrl.toString()); 
    	
    	final Map<String, List<String>> parameters = retrieveParameters(context);
         
    	java.lang.System.out.println("35.OidcCredentials_parameters=");
    	java.lang.System.out.println(parameters.toString());        
        
    	AuthenticationResponse response;
    	
        try {
           
			response = AuthenticationResponseParser.parse(new URI(computedCallbackUrl), parameters);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				throw new TechnicalException(e);
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				throw new TechnicalException(e);
			}
        //} catch (final URISyntaxException | ParseException e) {
        //    throw new TechnicalException(e);
        //

    	java.lang.System.out.println("36.OidcCredentials_response=");
    	java.lang.System.out.println(response.toString());
    	
        
        if (response instanceof AuthenticationErrorResponse) {
            logger.error("Bad authentication response, error={}",
                    ((AuthenticationErrorResponse) response).getErrorObject());
            //return Optional.empty();
            return null;
        }

        logger.debug("Authentication response successful");
    	java.lang.System.out.println("logger.debug Message=");
        java.lang.System.out.println("Authentication response successful");
                
        AuthenticationSuccessResponse successResponse = (AuthenticationSuccessResponse) response;
    	java.lang.System.out.println("37.OidcCredentials_successResponse=");
        java.lang.System.out.println(successResponse.toString());
                
        final State state = successResponse.getState();
        if (state == null) {
            throw new TechnicalException("Missing state parameter");
        }
    	java.lang.System.out.println("38.OidcCredentials_state=");
        java.lang.System.out.println(state.toString());

        
        //if (!state.equals(context.getSessionStore().get(context, OidcConfiguration.STATE_SESSION_ATTRIBUTE).orElse(null))) {
        //    throw new TechnicalException("State parameter is different from the one sent in authentication request. "
        //            + "Session expired or possible threat of cross-site request forgery");
        //}
        if (!state.equals(context.getSessionStore().get(context, OidcConfiguration.STATE_SESSION_ATTRIBUTE))) {
            throw new TechnicalException("State parameter is different from the one sent in authentication request. "
                    + "Session expired or possible threat of cross-site request forgery");
        }

        final OidcCredentials credentials = new OidcCredentials();
        // get authorization code

        final AuthorizationCode code = successResponse.getAuthorizationCode();
        if (code != null) {
            credentials.setCode(code);
        }

        // get ID token
        final JWT idToken = successResponse.getIDToken();
        if (idToken != null) {
            credentials.setIdToken(idToken);
        }

        // get access token
        final AccessToken accessToken = successResponse.getAccessToken();
        if (accessToken != null) {
            credentials.setAccessToken(accessToken);
        }

        //return Optional.of(credentials);
        return credentials;
    }

    protected Map<String, List<String>> retrieveParameters(final WebContext context) {
        final Map<String, String[]> requestParameters = context.getRequestParameters();
        final Map<String, List<String>> map = new HashMap<String, List<String>>();
        for (final Map.Entry<String, String[]> entry : requestParameters.entrySet()) {
            map.put(entry.getKey(), Arrays.asList(entry.getValue()));
        }
    	java.lang.System.out.println("39.OidcCredentials_map=");
        java.lang.System.out.println(map.toString());
        
        return map;
    }
}