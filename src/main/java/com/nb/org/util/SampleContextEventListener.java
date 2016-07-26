package com.nb.org.util;
import org.wso2.carbon.identity.sso.agent.exception.SSOAgentException;
import org.wso2.carbon.identity.sso.agent.util.SSOAgentConfigs;


import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.SecureRandom;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SampleContextEventListener implements ServletContextListener {

    private static Logger LOGGER = Logger.getLogger("InfoLogging");

    public void contextInitialized(ServletContextEvent servletContextEvent) {
    	
    	/* 忽略证书 */
		try {
			SSLContext ctx = SSLContext.getInstance("TLS");
			ctx.init(new KeyManager[0], new TrustManager[] { new DefaultTrustManager() }, new SecureRandom());
			SSLContext.setDefault(ctx);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
        Properties properties = new Properties();
        try {
            properties.load(servletContextEvent.getServletContext().getClassLoader().getResourceAsStream("SamlSSOSample.properties"));
            SSOAgentConfigs.initConfig(properties);
        } catch (IOException e){
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        } catch (SSOAgentException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
        SSOAgentConfigs.setKeyStoreStream(servletContextEvent.getServletContext().getClassLoader().getResourceAsStream("wso2carbon.jks"));
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
    
    /* 忽略证书工具类 */
	private static class DefaultTrustManager implements X509TrustManager {
		public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType)
				throws java.security.cert.CertificateException {}

		public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType)
				throws java.security.cert.CertificateException {}

		public java.security.cert.X509Certificate[] getAcceptedIssuers() {
			return null;
		}
	}
}
