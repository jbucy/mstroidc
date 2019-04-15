package oidc_support;

import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.pac4j.core.context.J2EContext;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.profile.ProfileManager;
import org.pac4j.oidc.profile.OidcProfile;

//import com.microstrategy.auth.SSOUserInfo;
//import com.microstrategy.web.objects.AuthUtils;


public class ProfileFilter implements Filter {

	@Override
	public void doFilter(final ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// get current user from pac4j post-authentication
		
		java.lang.System.out.println("21.Executing ProfileFilter");
		
		final WebContext context = new J2EContext((HttpServletRequest) request, (HttpServletResponse) response);
		java.lang.System.out.println("22.ProfileFilter=assigned context");
		final ProfileManager<OidcProfile> pm = new ProfileManager<OidcProfile>(context);
		
		java.lang.System.out.println("23.ProfileFilter=assigned pm");
		final OidcProfile userProfile = pm.get(true).get();
		java.lang.System.out.println("24.ProfileFilter=assigned userProfie");
		final String oidcUsername = userProfile.getUsername();

		java.lang.System.out.println("25.ProfileFilter_pm=");
		java.lang.System.out.println(pm.toString());	
		java.lang.System.out.println("26.ProfileFilter_oidcUsername=");
		java.lang.System.out.println(oidcUsername.toString());		
		
		
		/*final HttpServletRequest httpRequest = (HttpServletRequest) request;
	    HttpServletRequestWrapper wrapper = new HttpServletRequestWrapper(httpRequest); 
	    //String newheaders = request.getRemoteAddr();
	    ((HeaderMapRequestWrapper) wrapper).addHeader("SM_User",oidcUsername);
	    */
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HeaderMapRequestWrapper requestWrapper =  new HeaderMapRequestWrapper(httpRequest);
		requestWrapper.addHeader("SM_User", oidcUsername);
		chain.doFilter(requestWrapper, response);
		
	    //{
	    //    @Override
	    //    public String getHeader(String name) {
	    //        final String value = request.getParameter(name);
	    //        if (value != null) {
	    //            return value;
	    //        }
	    //        return super.getHeader(name);
	    //    }
	       
	    }


	    
		//HttpSession session = ((HttpServletRequest) request).getSession();
		//SSOUserInfo obj = ((SSOUserInfo)session.getAttribute("SSOUserInfo"));

		//if (obj == null || obj.getUserId() != oidcUsername) {
			// need to create SSOUserInfo object
		//	obj = new OidcSSOUserInfo(userProfile);
		//	session.setAttribute("SSOUserInfo", obj);
		//}	
		//chain.doFilter(request, response);		



@Override
public void init(FilterConfig filterConfig) throws ServletException {

	java.lang.System.out.println("20.Executing ProfileFilter Init");
	
}


// https://stackoverflow.com/questions/2811769/adding-an-http-header-to-the-request-in-a-servlet-filter
// http://sandeepmore.com/blog/2010/06/12/modifying-http-headers-using-java/
// http://bijubnair.blogspot.de/2008/12/adding-header-information-to-existing.html
/**
 * allow adding additional header entries to a request
 * 
 * @author wf
 * @return 
 * 
 */

public class HeaderMapRequestWrapper extends HttpServletRequestWrapper {
    /**
     * construct a wrapper for this request
     * 
     * @param request
     */
    public HeaderMapRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    private Map<String, String> headerMap = new HashMap<String, String>();

    /**
     * add a header with given name and value
     * 
     * @param name
     * @param value
     */
    public void addHeader(String name, String value) {
        headerMap.put(name, value);
    }

    @Override
    public String getHeader(String name) {
        String headerValue = super.getHeader(name);
        if (headerMap.containsKey(name)) {
            headerValue = headerMap.get(name);
        }
        return headerValue;
    }

    /**
     * get the Header names
     */
    @Override
    public Enumeration<String> getHeaderNames() {
        List<String> names = Collections.list(super.getHeaderNames());
        for (String name : headerMap.keySet()) {
            names.add(name);
        }
        return Collections.enumeration(names);
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        List<String> values = Collections.list(super.getHeaders(name));
        if (headerMap.containsKey(name)) {
            values.add(headerMap.get(name));
        }
        return Collections.enumeration(values);
    }

   
}


public void destroy() {
	// TODO Auto-generated method stub
	
}
}
