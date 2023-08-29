package com.simplyconvert;

public class Settings {
 
    private final String SessionIdURL__c;	 
    private final String SessionIdUserName__c; 
    private final String SessionIdPassword__c;
    private final String SessionIdSecurityToken__c; 	 
    private final String DocumentURL__c; 	 
    private final String SimplyConvertURL__c;	 
    private final String SimplyConvertAPIKey__c;

    public Settings(String SessionIdURL__c, String SessionIdUserName__c, String SessionIdPassword__c, 
        String SessionIdSecurityToken__c, String DocumentURL__c, String SimplyConvertURL__c, String SimplyConvertAPIKey__c) {

        this.SessionIdURL__c = SessionIdURL__c;
        this.SessionIdUserName__c = SessionIdUserName__c;
        this.SessionIdPassword__c = SessionIdPassword__c;
        this.SessionIdSecurityToken__c = SessionIdSecurityToken__c;
        this.DocumentURL__c = DocumentURL__c;
        this.SimplyConvertURL__c = SimplyConvertURL__c;
        this.SimplyConvertAPIKey__c = SimplyConvertAPIKey__c;
    }

    public String getSessionIdURL() {
        return this.SessionIdURL__c;
    }

    public String getSessionIdUserName() {
        return this.SessionIdUserName__c;
    }

    public String getSessionIdPassword() {
        return this.SessionIdPassword__c;
    }

    public String getSessionIdSecurityToken() {
        return this.SessionIdSecurityToken__c;
    }

    public String getDocumentURL() {
        return this.DocumentURL__c;
    }

    public String getSimplyConvertURL() {
        return this.SimplyConvertURL__c;
    }

    public String getSimplyConvertAPIKey() {
        return this.SimplyConvertAPIKey__c;
    }
    
}
