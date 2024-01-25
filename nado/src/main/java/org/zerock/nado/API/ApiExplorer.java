package org.zerock.nado.API;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ApiExplorer {
// 외교부_국가·지역별 재외공관 정보--------------------------------------------------------------------------------------------------------------------------------------------------------------
    public static List<EmbassyInfo> getEmbassyList(String countryName) throws IOException {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1262000/EmbassyService2/getEmbassyList2"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=zpDSuAzWoD6LB7nk6hYHS%2FIK%2B0sPm6XquAtZZlTgXOV0C69VWL7Ln5qLzzFzvqOxvUslQZzR6b12Dd%2FwZ7SofQ%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("returnType", "UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*XML 또는 JSON*/
        urlBuilder.append("&" + URLEncoder.encode("cond[country_nm::EQ]", "UTF-8") + "=" + URLEncoder.encode(countryName, "UTF-8")); /*한글 국가명*/
        String apiResponse = sendGetRequest(urlBuilder.toString());

        // JSON 응답을 EmbassyInfo 리스트로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(apiResponse);
        JsonNode dataNode = rootNode.get("data");

        List<EmbassyInfo> embassyList = objectMapper.convertValue(
                dataNode,
                new TypeReference<List<EmbassyInfo>>() {}
        );

        return embassyList;
    }

    private static String sendGetRequest(String apiUrl) throws IOException {
        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

        StringBuilder response = new StringBuilder();
        try (BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
            }
        }

        return response.toString();
    }


    // 외교부_국가·지역별 치안환경-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    public static String getSecurityEnvironment(String countryName) throws IOException {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1262000/SecurityEnvironmentService/getSecurityEnvironmentList"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=zpDSuAzWoD6LB7nk6hYHS%2FIK%2B0sPm6XquAtZZlTgXOV0C69VWL7Ln5qLzzFzvqOxvUslQZzR6b12Dd%2FwZ7SofQ%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("returnType", "UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*XML 또는 JSON*/
        urlBuilder.append("&" + URLEncoder.encode("cond[country_nm::EQ]", "UTF-8") + "=" + URLEncoder.encode(countryName, "UTF-8")); /*한글 국가명*/

        String apiResponse = sendGetRequest2(urlBuilder.toString());

        // JSON 응답을 문자열로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(apiResponse);
        JsonNode dataNode = rootNode.get("data");

        // 필요한 데이터 추출 및 문자열로 변환
        List<SecurityEnvironment> securityEnvironmentList = objectMapper.convertValue(
                dataNode,
                new TypeReference<List<SecurityEnvironment>>() {}
        );

        StringBuilder result = new StringBuilder();

        // securityEnvironmentList에 있는 데이터를 문자열로 결합
        for (SecurityEnvironment securityEnvironment : securityEnvironmentList) {
            result.append(securityEnvironment.toString()); // 혹은 원하는 형식으로 데이터를 문자열로 만들기
            result.append("\n"); // 각 데이터를 줄바꿈으로 구분
        }

        return result.toString();
    }

    private static String sendGetRequest2(String apiUrl) throws IOException {
        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

        StringBuilder response = new StringBuilder();
        try (BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
            }
        }

        return response.toString();
    }

    // 외교부_국가∙지역별 특별여행주의보
    public static List<SptravelWarningList> getSptravelWarningMap(String countryName) throws IOException {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1262000/SptravelWarningService2/getSptravelWarningList2"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=zpDSuAzWoD6LB7nk6hYHS%2FIK%2B0sPm6XquAtZZlTgXOV0C69VWL7Ln5qLzzFzvqOxvUslQZzR6b12Dd%2FwZ7SofQ%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("returnType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*XML 또는 JSON*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("cond[country_nm::EQ]","UTF-8") + "=" + URLEncoder.encode(countryName, "UTF-8")); /*한글 국가명*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지 번호*/

        String apiResponse = sendGetRequest3(urlBuilder.toString());

        // JSON 응답을 문자열로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(apiResponse);
        JsonNode dataNode = rootNode.get("data");

        // 필요한 데이터 추출 및 문자열로 변환
        List<SptravelWarningList> sptravelWarningList = new ArrayList<>();

        for (JsonNode itemNode : dataNode) {
            SptravelWarningList sptravelWarning = new SptravelWarningList();
            sptravelWarning.setDangMapDownloadUrl(Collections.singletonList(itemNode.path("dang_map_download_url").asText()));
            // 다른 필요한 데이터도 동일하게 추가

            sptravelWarningList.add(sptravelWarning);
        }

        return sptravelWarningList;
    }

    private static String sendGetRequest3(String apiUrl) throws IOException {
        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

        StringBuilder response = new StringBuilder();
        try (BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
            }
        }

        return response.toString();
    }
}
