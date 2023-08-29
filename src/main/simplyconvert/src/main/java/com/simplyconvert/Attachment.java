package com.simplyconvert;

public class Attachment {
    private final String Id;
    private final String Name;
    private final String ContentType;

    public Attachment(String Id, String Name, String ContentType) {
        this.Id = Id;
        this.Name = Name;
        this.ContentType = ContentType;
    }

    public String getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public String getContentType() {
        return ContentType;
    }
}