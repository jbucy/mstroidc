package oidc_support;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * 
 * @author hfaheem
 * @DATE 04/17/2019
 * 
 * This class use to add/fetch custom header parameters from the HTTP header.
 * getheader() method also return custom HTTP header parameter. Return null if parameter not exist.
 * addHeader() method use to add custom parameters into the HTTP header.
 * 
 * Note: Parameters are store in the HTTP header as a KEY/VALUE pair 
 * and Keys are case sensitive.
 * 
 * 
 * */
public class HeaderMapRequestWrapper extends HttpServletRequestWrapper {

	public HeaderMapRequestWrapper(HttpServletRequest request) {
		super(request);
	}

	private Map<String, String> headerMap = new HashMap<String, String>();

	/**
	 * This method use for adding custom value in to the HTTP header.
	 * HTTP header map the custom values as KEY/VALUE pair
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
