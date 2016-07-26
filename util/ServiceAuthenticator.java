package com.nb.org.util;

import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.transport.http.HttpTransportProperties;

import com.nb.org.exception.AuthenticationException;

/**
 * Setting the trasnport authenticator for carbon.
 * 
 * @author huang xin
 * 
 */
public class ServiceAuthenticator {

	private static ServiceAuthenticator instance = null;

	private ServiceAuthenticator() {
	}

	public static ServiceAuthenticator getInstance() {

		if (instance != null) {
			return instance;
		} else {
			instance = new ServiceAuthenticator();
			return instance;
		}
	}

	public void authenticate(ServiceClient client) throws AuthenticationException {

		if (GlobalConfig.user != null && GlobalConfig.password != null) {
			Options option = client.getOptions();
			HttpTransportProperties.Authenticator auth = new HttpTransportProperties.Authenticator();
			auth.setUsername(GlobalConfig.user);
			auth.setPassword(GlobalConfig.password);
			auth.setPreemptiveAuthentication(true);
			option.setProperty(org.apache.axis2.transport.http.HTTPConstants.AUTHENTICATE, auth);
			option.setManageSession(true);
		} else {
			throw new AuthenticationException("Authentication username or password not set");
		}
	}

}
