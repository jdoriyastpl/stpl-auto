package com.sarvika.automation.enumerations;

public enum HttpMediaType {

    XML("xml"), JSON("json"), ATOM_XML("atom+xml");
    
    private final String mediaType;
    
    
    HttpMediaType(final String mediaType) {
        this.mediaType = mediaType;
    }
    
    public String getMediaType() {
        return mediaType;
    }
}
