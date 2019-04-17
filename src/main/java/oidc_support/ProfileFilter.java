package oidc_support;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pac4j.core.context.J2EContext;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.profile.ProfileManager;
import org.pac4j.oidc.profile.OidcProfile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 

//import com.microstrategy.auth.SSOUserInfo;
//import com.microstrategy.web.objects.AuthUtils;

/**
 * 
 * @author hfaheem
 * @DATE 04/17/2019
 * 
 * ProfileFilter Class use for filtering the user.
 * This filter used for SSO authentication.
 * Logged inn user does not require to login again. 
 * This class used microstrategy 
 * 
 * */
public class ProfileFilter implements Filter {
	
	/* logger use to display log onto file or console. */
	private static final Logger logger = LoggerFactory.getLogger(ProfileFilter.class);
	
	@Override
	public void doFilter(final ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		// get current user from pac4j post-authentication
		logger.info("****Executing ProfileFilter*****");
		final WebContext webContext = new J2EContext((HttpServletRequest) request, (HttpServletResponse) response);
		logger.info("Assigned Web Context");
		
		final ProfileManager<OidcProfile> profileManager = new ProfileManager<OidcProfile>(webContext);
		final OidcProfile userProfile = profileManager.get(true).get();
		
		logger.info("Assigned Pofile Manager and User Profile");
		final String oidcUserName = userProfile.getUsername();
		logger.info("Profile Manager = " + profileManager.toString());
		
		/* Add custom parameter in the request header */
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HeaderMapRequestWrapper requestWrapper = new HeaderMapRequestWrapper(httpRequest);
		requestWrapper.addHeader("SM_USER", oidcUserName);
		logger.info("OIDC User Name added in the request header : " + oidcUserName);
		
		/* Go to Next Filter in the Chain with updated request */
		chain.doFilter(requestWrapper, response);

	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.info("*******Initializing and Configure Profile Filter*******");

	}

	@Override
	public void destroy() {
		logger.info("****Complete execution*****");
		
	}

}
