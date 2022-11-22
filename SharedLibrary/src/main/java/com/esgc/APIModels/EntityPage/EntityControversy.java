package com.esgc.APIModels.EntityPage;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class EntityControversy {

    @JsonProperty(value = "controversy_description")
    String controversyDescription;

    @JsonProperty(value = "controversy_location")
    String controversyLocation;

    @JsonProperty(value = "controversy_ref")
    String controversyRef;

    @JsonProperty(value = "controversy_updates")
    String controversyUpdates;

    @JsonProperty(value = "orbis_id")
    String orbisId;

    @JsonProperty(value = "severity")
    String severity;

    @JsonProperty(value = "title")
    String title;

    @JsonProperty(value = "related_sustainability_driver_ref")
    String driverReferences;

    @JsonProperty(value = "controversy_events")
    String controversyEvents;

    @JsonProperty(value = "managed_type")
    String managed_type;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityControversy that = (EntityControversy) o;
        return Objects.equals(controversyDescription, that.controversyDescription) &&
                Objects.equals(controversyLocation, that.controversyLocation) &&
                Objects.equals(controversyRef, that.controversyRef) &&
                Objects.equals(controversyUpdates, that.controversyUpdates) &&
                Objects.equals(orbisId, that.orbisId) &&
                Objects.equals(severity, that.severity) &&
                Objects.equals(title, that.title) &&
                Objects.equals(controversyEvents, that.controversyEvents) &&
                Objects.equals(driverReferences, that.driverReferences);
    }

    @Override
    public int hashCode() {
        return Objects.hash(controversyDescription, controversyLocation, controversyRef, controversyUpdates, orbisId, severity, title, controversyEvents, driverReferences);
    }
}
