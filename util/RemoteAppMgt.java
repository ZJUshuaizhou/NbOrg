package com.nb.org.util;

import org.wso2.carbon.identity.application.common.model.xsd.ServiceProvider;

import com.nb.org.application.client.ApplicationMgtClient;

public class RemoteAppMgt {
	public int registerApp(String name,String description) throws Exception{
		ApplicationMgtClient client = new ApplicationMgtClient();
		AppDefaultData data = new AppDefaultData();
		ServiceProvider sp = data.getDefaultServiceProvider();
		sp.setApplicationName(name);
		sp.setDescription(description);
		return client.createApplication(sp);
	}
	
}
