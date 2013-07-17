package org.sakaiproject.contentreview.turnitin.mapping;

public class IdMapping {
    private final String sakaiId;
    private final Integer turnitinId;
    private final Type type;

    public IdMapping(Type type, String sakaiId, Integer turnitinId) {
        this.type = type;
        this.sakaiId = sakaiId;
        this.turnitinId = turnitinId;
    }

    public String getSakaiId() {
        return sakaiId;
    }

    public Integer getTurnitinId() {
        return turnitinId;
    }

    public IdMapping.Type getType() {
        return type;
    }

    public static enum Type{
        CLASS,
        USER,
        ASSIGNMENT
    }
}
