package org.zerock.nado.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class SptravelWarningListDTO {
    @JsonProperty("dang_map_download_url")
    private List <String> DangMapDownloadUrl;

    @Override
    public String toString() {
        if (DangMapDownloadUrl != null) {
            System.out.println("DTO URL : " + DangMapDownloadUrl.get(0));
            return DangMapDownloadUrl.get(0);
        } else {
            System.out.println("지역별 위험표시 지도 다운로드 URL이 없습니다.");
            return "지역별 위험표시 지도 다운로드 URL이 없습니다.";
        }
    }

    public String getUrl() {
            return DangMapDownloadUrl.get(0);
        }
}
