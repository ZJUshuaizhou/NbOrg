/**
 * SimpleAuthServiceCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.3  Built on : Jun 27, 2015 (11:17:49 BST)
 */
package com.nb.org.sync.util;


/**
 *  SimpleAuthServiceCallbackHandler Callback class, Users can extend this class and implement
 *  their own receiveResult and receiveError methods.
 */
public abstract class SimpleAuthServiceCallbackHandler {
    protected Object clientData;

    /**
     * User can pass in any object that needs to be accessed once the NonBlocking
     * Web service call is finished and appropriate method of this CallBack is called.
     * @param clientData Object mechanism by which the user can pass in user data
     * that will be avilable at the time this callback is called.
     */
    public SimpleAuthServiceCallbackHandler(Object clientData) {
        this.clientData = clientData;
    }

    /**
     * Please use this constructor if you don't want to set any clientData
     */
    public SimpleAuthServiceCallbackHandler() {
        this.clientData = null;
    }

    /**
     * Get the client data
     */
    public Object getClientData() {
        return clientData;
    }

    /**
     * auto generated Axis2 call back method for getUserRole method
     * override this method for handling normal response from getUserRole operation
     */
    public void receiveResultgetUserRole(
        com.nb.org.sync.util.SimpleAuthServiceStub.GetUserRoleResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from getUserRole operation
     */
    public void receiveErrorgetUserRole(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for logout method
     * override this method for handling normal response from logout operation
     */
    public void receiveResultlogout(
        com.nb.org.sync.util.SimpleAuthServiceStub.LogoutResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from logout operation
     */
    public void receiveErrorlogout(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for ticketValidation method
     * override this method for handling normal response from ticketValidation operation
     */
    public void receiveResultticketValidation(
        com.nb.org.sync.util.SimpleAuthServiceStub.TicketValidationResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from ticketValidation operation
     */
    public void receiveErrorticketValidation(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for getUserSys method
     * override this method for handling normal response from getUserSys operation
     */
    public void receiveResultgetUserSys(
        com.nb.org.sync.util.SimpleAuthServiceStub.GetUserSysResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from getUserSys operation
     */
    public void receiveErrorgetUserSys(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for getUserInfo method
     * override this method for handling normal response from getUserInfo operation
     */
    public void receiveResultgetUserInfo(
        com.nb.org.sync.util.SimpleAuthServiceStub.GetUserInfoResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from getUserInfo operation
     */
    public void receiveErrorgetUserInfo(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for idValidation method
     * override this method for handling normal response from idValidation operation
     */
    public void receiveResultidValidation(
        com.nb.org.sync.util.SimpleAuthServiceStub.IdValidationResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from idValidation operation
     */
    public void receiveErroridValidation(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for generateST method
     * override this method for handling normal response from generateST operation
     */
    public void receiveResultgenerateST(
        com.nb.org.sync.util.SimpleAuthServiceStub.GenerateSTResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from generateST operation
     */
    public void receiveErrorgenerateST(java.lang.Exception e) {
    }
}
