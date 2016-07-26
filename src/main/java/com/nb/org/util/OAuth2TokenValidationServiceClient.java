/*
 * Copyright (c) WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.nb.org.util;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.util.AXIOMUtil;
import org.apache.axis2.AxisFault;
import org.apache.axis2.Constants;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.transport.http.HttpTransportProperties;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.xml.stream.XMLStreamException;
import java.util.Iterator;

/**
 *
 */
public class OAuth2TokenValidationServiceClient {

    private String serverUrl;

    private String serverUserName;

    private String serverPassword;

    private ConfigurationContext configurationContext;

    private  HttpClient httpClient;

    private static Log log = LogFactory.getLog(OAuth2TokenValidationServiceClient.class);

    public OAuth2TokenValidationServiceClient(String serverUrl, String serverUserName, String serverPassword,
                                                                            ConfigurationContext configurationContext) {

        if(serverUrl != null){
            serverUrl = serverUrl.trim();
            if (!serverUrl.endsWith("/")) {
                serverUrl += "/";
            }
        }
        this.serverUrl = serverUrl;
        this.serverUserName = serverUserName;
        this.serverPassword = serverPassword;
        this.configurationContext = configurationContext;

        MultiThreadedHttpConnectionManager httpConnectionManager = new MultiThreadedHttpConnectionManager();
        httpConnectionManager.getParams().setDefaultMaxConnectionsPerHost(200);
        httpConnectionManager.getParams().setMaxTotalConnections(500);
        httpClient = new HttpClient(httpConnectionManager);
    }

    public ValidationResponse validate(String accessToken){

        String body = getBody(accessToken);
        String serverEndPoint = serverUrl + "OAuth2TokenValidationService";
        ServiceClient client = null;
        ValidationResponse response = new ValidationResponse(false, null,null);
        try{
            client = new ServiceClient(configurationContext, null);
            Options option = client.getOptions();
            option.setManageSession(true);
            HttpTransportProperties.Authenticator authenticator =
                    new HttpTransportProperties.Authenticator();
            authenticator.setUsername(serverUserName);
            authenticator.setPassword(serverPassword);
            authenticator.setPreemptiveAuthentication(true);
            option.setProperty(org.apache.axis2.transport.http.HTTPConstants.AUTHENTICATE, authenticator);
            option.setProperty(Constants.Configuration.TRANSPORT_URL, serverEndPoint);
            option.setAction("urn:validate");
            option.setProperty(HTTPConstants.REUSE_HTTP_CLIENT, Constants.VALUE_TRUE);
            option.setProperty(HTTPConstants.CACHED_HTTP_CLIENT, httpClient);
            OMElement omElement = client.sendReceive(AXIOMUtil.stringToOM(body));
            if(omElement != null && omElement.getFirstElement() != null ){
                omElement =   omElement.getFirstElement();
                if(omElement != null){
                    Iterator iterator = omElement.getChildElements();
                    while(iterator.hasNext()){
                        OMElement element = (OMElement) iterator.next();       
                        if("authorizedUser".equals(element.getLocalName())){
                           response.setAuthorizedUser(element.getText());
                        }
                        if("valid".equals(element.getLocalName())){
                            response.setValid(Boolean.parseBoolean(element.getText()));
                        }
                        if("authorizationContextToken".equals(element.getLocalName())){
                            Iterator iterator2 = element.getChildElements();
                            while(iterator2.hasNext()){
                                OMElement element2 = (OMElement) iterator2.next();
                                if("tokenString".equals(element2.getLocalName())){
                                    response.setToken(element2.getText());

                                }
                            }
                        }
                    }
                }
            }
        } catch (AxisFault axisFault) {
            log.error(axisFault.getMessage(), axisFault);
        } catch (XMLStreamException e) {
            log.error(e.getMessage(), e);
        } catch (Exception e){
            log.error(e.getMessage(), e);
        } finally {
            if(client != null){
                try{
                    client.cleanupTransport();
                    client.cleanup();
                } catch (AxisFault axisFault) {
                    log.error("Error while cleaning HTTP client", axisFault);
                }
            }
        }

        return response;
    }
    
    private String getBody(String accessToken){

        return "<xsd:validate xmlns:xsd=\"http://org.apache.axis2/xsd\" xmlns:xsd1=\"http://dto.oauth2.identity.carbon.wso2.org/xsd\">" +
                "            <xsd:validationReqDTO><xsd1:accessToken>\n" +
                "               <xsd1:identifier>" + accessToken + "</xsd1:identifier>\n" +
                "               <xsd1:tokenType>bearer</xsd1:tokenType>\n" +
                "            </xsd1:accessToken>\n" +
                "            <xsd1:requiredClaimURIs></xsd1:requiredClaimURIs>" +
                "</xsd:validationReqDTO></xsd:validate>";

    }
}
