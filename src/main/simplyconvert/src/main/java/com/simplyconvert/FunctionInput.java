package com.simplyconvert;

import java.util.List;
import java.util.Map;

public class FunctionInput {

    private Map<String, String> payloads;
    private Map<String, List<Attachment>> docs;
    private Settings scSettings;

    public FunctionInput() {
    }

    public FunctionInput(Map<String, List<Attachment>> docs, Map<String, String> payloads, Settings scSettings) {

        this.docs = docs;
        this.payloads = payloads;
        this.scSettings = scSettings;
    }

    public Map<String, List<Attachment>> getDocs() {
        return this.docs;
    }

    public Map<String, String> getPayloads() {
        return this.payloads;
    }

    public Settings getScSettings() {
        return this.scSettings;
    }
}