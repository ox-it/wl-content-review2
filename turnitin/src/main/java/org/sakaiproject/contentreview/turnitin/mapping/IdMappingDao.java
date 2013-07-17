package org.sakaiproject.contentreview.turnitin.mapping;

public interface IdMappingDao {
    IdMapping getIdMappingBySakaiId(IdMapping.Type type, String sakaiId);
    IdMapping getIdMappingByTurnitinId(IdMapping.Type type, String tiiId);
    void createMappingId(IdMapping idMapping);
}
