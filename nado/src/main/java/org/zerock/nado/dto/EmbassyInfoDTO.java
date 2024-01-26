package org.zerock.nado.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmbassyInfoDTO {
    @JsonProperty("embassy_kor_nm")
    private String embassyName;

    @JsonProperty("emblgbd_addr")
    private String embassyAddress;

    @JsonProperty("tel_no")
    private String embassyPhoneNumber;

    @JsonProperty("free_tel_no")
    private String embassyFreeNumber;

    @JsonProperty("urgency_tel_no")
    private String embassyUrgencyNumber;

    @JsonProperty("embassy_lat")
    private String embassyLat;

    @JsonProperty("embassy_lng")
    private String embassyLng;

    public String getName() {
        return embassyName;
    }

    public String getAddress() {
        return embassyAddress;
    }

    public String getPhoneNumber() {
        return embassyPhoneNumber;
    }

    public String getUrgencyNumber() {
        return embassyUrgencyNumber;
    }

    public String getLat() {
        return embassyLat;
    }

    public String getLng() {
        return embassyLng;
    }
}
