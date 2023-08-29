package com.simplyconvert;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionIdHelper {

    public static class SessionIdAPIException extends Exception {
        public SessionIdAPIException(String message) {
          super(message);
        }
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(SimplyconvertFunction.class);

    public static String getSessionId(String url, String username, String password, String securityToken) 
        throws ClientProtocolException, IOException, SessionIdAPIException {

        String sessionId = "";

        HttpResponse response = null;
        HttpClient httpclient = HttpClientBuilder.create().build();

        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Content-Type", "text/xml");
        httpPost.addHeader("SOAPAction", "\"\"");
        httpPost.setEntity(new StringEntity("" // also references the named credential
            + "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:partner.soap.sforce.com\">"
            + "   <soapenv:Header>"
            + "   </soapenv:Header>"
            + "   <soapenv:Body>"
            + "      <urn:login>"
            + "         <urn:username>" + username + "</urn:username>"
            + "         <urn:password>" + password + securityToken + "</urn:password>"
            + "      </urn:login>"
            + "   </soapenv:Body>"
            + "</soapenv:Envelope>"));

        // POST request to Login
        response = httpclient.execute(httpPost);

        int statusCode = response.getStatusLine().getStatusCode();

        if (statusCode != 200) {
            String ErrorResult = EntityUtils.toString(response.getEntity());
            LOGGER.info("Doc error: " + statusCode + "-"+ ErrorResult);
            throw new SessionIdAPIException("Doc error: " + statusCode + "-"+ ErrorResult); 
        } 

        HttpEntity entity = response.getEntity();
        String responseString = EntityUtils.toString(entity, "UTF-8");

        sessionId = getReturnedSessionId(responseString);

        LOGGER.info("FS Session ID:::: " + sessionId);

        return sessionId;
    }

    private static String getReturnedSessionId(String str) {

        String sID = "";
        Document doc = Jsoup.parse(str, "", Parser.xmlParser());
        sID = doc.select("sessionId").text();

        return sID;
    }
    
}
