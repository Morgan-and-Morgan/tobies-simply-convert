package com.simplyconvert;

import java.io.IOException;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimplyConvertAPI {

    public static class SimplyConvertAPIException extends Exception {
        public SimplyConvertAPIException(String message) {
          super(message);
        }
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(SimplyconvertFunction.class);

    public static String postCase(String body, String url, String apiKey) 
        throws ClientProtocolException, IOException, JSONException, SimplyConvertAPIException { 

        String uuid = "";

        HttpResponse response = null;
        HttpClient httpclient = HttpClientBuilder.create().build();
    
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("API-Key", apiKey);
        httpPost.addHeader("Content-Type", "application/json");
        httpPost.setEntity(new StringEntity(body));
    
        response = httpclient.execute(httpPost);

        int statusCode = response.getStatusLine().getStatusCode();

        if (statusCode != 200) {
            String ErrorResult = EntityUtils.toString(response.getEntity());
            LOGGER.info("Doc error: " + statusCode + "-"+ ErrorResult);
            throw new SimplyConvertAPIException("Doc error: " + statusCode + "-" + ErrorResult); 
        } 

        HttpEntity entity = response.getEntity();
        String responseString = EntityUtils.toString(entity, "UTF-8");

        LOGGER.info("Simply Convert Case Response:::: " + responseString);

        JSONObject obj = new JSONObject(responseString);
        
        JSONObject data = obj.getJSONObject("data");
        uuid = data.getString("uuid");

        LOGGER.info("uuid ID:::: " + uuid);

        return uuid;
    }

    public static void postDoc(String base64Doc, String uuid, String filename, String url, String apiKey) 
        throws ParseException, IOException, SimplyConvertAPIException {

        String type = "retainer";

        HttpClient httpclient = HttpClientBuilder.create().build();
        HttpPost uploadFile = new HttpPost(url + "/" + uuid + "/documents");

        uploadFile.addHeader("API-Key", apiKey);
        
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();

        // requested updates for specific docs
        if (filename.contains("Authority_File_Morgan&Morgan")) {
            filename = "BLGATF " + filename;
            type = "secondary";
            //builder.addTextBody("type", "secondary", ContentType.TEXT_PLAIN);
        }
        else if (filename.contains("HIPAA_Morgan&Morgan")) {
           // builder.addTextBody("type", "hipaa", ContentType.TEXT_PLAIN);
            type = "hipaa";
        }

        builder.addTextBody("filename", filename, ContentType.TEXT_PLAIN);

        // This attaches the file to the POST:
        byte[] data = Base64.decodeBase64(base64Doc);

        builder.addBinaryBody(
            "file",
            data,
            ContentType.APPLICATION_OCTET_STREAM,
            filename
        );

        builder.addTextBody("file_meta", "{ \"filename\":\"" + filename + "\", \"type\":\"" + type + "\"}", ContentType.TEXT_PLAIN);

        HttpEntity multipart = builder.build();

        // ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        // multipart.writeTo(bytes);
        // String content = bytes.toString();
        // LOGGER.info("Body:::: " +  content);
        
        uploadFile.setEntity(multipart);
        HttpResponse response = httpclient.execute(uploadFile);
        int statusCode = response.getStatusLine().getStatusCode();

        if (statusCode != 200) {
            String ErrorResult = EntityUtils.toString(response.getEntity());
            LOGGER.info("Doc error: " + statusCode + "-"+ ErrorResult);
            throw new SimplyConvertAPIException("Doc error: " + statusCode + "-"+ ErrorResult); 
        } 

        HttpEntity responseEntity = response.getEntity();

        String responseString = EntityUtils.toString(responseEntity, "UTF-8");
    
        LOGGER.info("Simply Convert Doc Response:::: " + responseString);
    }
    
}