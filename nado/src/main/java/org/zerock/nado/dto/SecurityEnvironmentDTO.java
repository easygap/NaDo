package org.zerock.nado.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class SecurityEnvironmentDTO {

    @JsonProperty("current_travel_alarm")
    private String currentTravelAlarm;

    @Override
    public String toString() {
        return "여행 경보 " + currentTravelAlarm;
    }
}
