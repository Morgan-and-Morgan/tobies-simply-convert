package com.simplyconvert;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.salesforce.functions.jvm.sdk.Context;
import com.salesforce.functions.jvm.sdk.InvocationEvent;
import com.salesforce.functions.jvm.sdk.SalesforceFunction;

/**
 * Post Salesforce Intakes and associated docs to the Simply Convert API
 */
public class SimplyconvertFunction implements SalesforceFunction<FunctionInput, FunctionOutput> {
  private static final Logger LOGGER = LoggerFactory.getLogger(SimplyconvertFunction.class);

  @Override
  public FunctionOutput apply(InvocationEvent<FunctionInput> event, Context context)
    throws Exception {

        Map<String, String> payloads = event.getData().getPayloads();
        Map<String, List<Attachment>> docs = event.getData().getDocs();
        Settings scSettings = event.getData().getScSettings();

      try {

        // get the session Id from Salesforce
        String sessionId = SessionIdHelper.getSessionId(scSettings.getSessionIdURL(), 
          scSettings.getSessionIdUserName(), scSettings.getSessionIdPassword(), scSettings.getSessionIdSecurityToken());

        for (String intakeId : payloads.keySet()) {

          // post the case to and get back the uuid for the documents
          String uuid = SimplyConvertAPI.postCase(payloads.get(intakeId), scSettings.getSimplyConvertURL(), scSettings.getSimplyConvertAPIKey());

          for (Attachment attch : docs.get(intakeId)) {

            System.out.println("DOC " + attch);

            // get the doc from SF
            String base64Doc = SFDocHelper.getDocBase64( attch.getId(), sessionId, scSettings.getDocumentURL());

            System.out.println("PASSED");

            // post the doc to simply convert case
            SimplyConvertAPI.postDoc(base64Doc, uuid, attch.getName(), scSettings.getSimplyConvertURL(), scSettings.getSimplyConvertAPIKey());
          }
        }

      } catch (Exception e) {
        LOGGER.info("ERROR:::: " + e + " - "+ e.getMessage());
        return new FunctionOutput(false, e.getMessage(), payloads.keySet().toString());
      }
    return new FunctionOutput(true, "", payloads.keySet().toString());
  }
  
}
