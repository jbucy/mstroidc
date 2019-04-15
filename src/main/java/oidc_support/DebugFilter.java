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


public class DebugFilter implements Filter {

	@Override
	public void doFilter(final ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// get current user from pac4j post-authentication
		
		final WebContext context = new J2EContext((HttpServletRequest) request, (HttpServletResponse) response);
		
		//java.lang.System.out.println("======================================="); 				
		java.lang.System.out.println("41.Executing DebugCallbackFilter");
		java.lang.System.out.println("42.request");
		java.lang.System.out.println(request.toString());
		java.lang.System.out.println("43.response");
		java.lang.System.out.println(response.toString());
		
		java.lang.System.out.println("44.chain");
		java.lang.System.out.println(chain.toString());

		java.lang.System.out.println("45.Context_getSessionStore");
		java.lang.System.out.println(context.getSessionStore().toString());
		java.lang.System.out.println(context.getRequestCookies());
		java.lang.System.out.println("46.Context_getRequestParameters");
		java.lang.System.out.println(context.getRequestParameters().toString());
		java.lang.System.out.println(context.getRequestParameters());
		java.lang.System.out.println("46.Context_getRequestContent");
		java.lang.System.out.println(context.getRequestContent());
		java.lang.System.out.println("47.Context_getFullRequestURL");
		java.lang.System.out.println(context.getFullRequestURL().toString());
		java.lang.System.out.println("48.Context_getRequestCookies");
		java.lang.System.out.println(context.getRequestCookies());
		
		
    	//final String computedCallbackUrl = client.computeFinalCallbackUrl(context);
		//java.lang.System.out.println("======================================="); 
		
		
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		java.lang.System.out.println("40.Executing DebugCallbackFilter Init");
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
}