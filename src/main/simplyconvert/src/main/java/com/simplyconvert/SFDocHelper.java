package com.simplyconvert;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SFDocHelper {

  public static class DocAPIException extends Exception {
    public DocAPIException(String message) {
      super(message);
    }
  }
  
  private static final Logger LOGGER = LoggerFactory.getLogger(SimplyconvertFunction.class);

  public static String getDocBase64(String docId, String sessionId, String docURLDomain) 
    throws ClientProtocolException, IOException, ParseException, DocAPIException {

      String result = "";
  
      HttpResponse response = null;
      HttpClient httpclient = HttpClientBuilder.create().build();

      // Create login request URL
      String docURL = docURLDomain + docId + "/VersionData";

      HttpGet httpGet = new HttpGet(docURL);
      httpGet.addHeader("Authorization", "Bearer " + sessionId);

      response = httpclient.execute(httpGet);

      int statusCode = response.getStatusLine().getStatusCode();

      if (statusCode != 200) {
        String ErrorResult = EntityUtils.toString(response.getEntity());
        LOGGER.info("Doc error: " + statusCode + "-"+ ErrorResult);
        throw new DocAPIException("Doc error: " + statusCode + "-"+ ErrorResult); 
      } 

      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

      response.getEntity().writeTo(byteArrayOutputStream);

      String body = Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());

      result = body;

      LOGGER.info("SF Doc Success!!");
  
      return result;
  
  }
    
}
