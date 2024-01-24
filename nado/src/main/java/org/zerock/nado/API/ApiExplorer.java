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
import java.util.List;

public class ApiExplorer {
    public static void main(String[] args) throws IOException {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1262000/EmbassyService2/getEmbassyList2"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=zpDSuAzWoD6LB7nk6hYHS%2FIK%2B0sPm6XquAtZZlTgXOV0C69VWL7Ln5qLzzFzvqOxvUslQZzR6b12Dd%2FwZ7SofQ%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("returnType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*XML 또는 JSON*/
        urlBuilder.append("&" + URLEncoder.encode("cond[country_nm::EQ]","UTF-8") + "=" + URLEncoder.encode("일본", "UTF-8")); /*한글 국가명*/
        //urlBuilder.append("&" + URLEncoder.encode("cond[country_iso_alp2::EQ]","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*ISO 2자리코드*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        System.out.println(sb.toString());
    }

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

    public static String parseApiResponse(String apiResponse) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(apiResponse);

        // 필요한 정보 추출
        JsonNode dataNode = rootNode.get("data").get(0); // 첫 번째 국가 정보
        String embassyName = dataNode.get("embassy_kor_nm").asText();
        String embassyAddress = dataNode.get("emblgbd_addr").asText();
        String embassyPhoneNumber = dataNode.get("tel_no").asText();
        String embassyUrgencyNumber = dataNode.get("urgency_tel_no").asText();
        String embassyFreeNumber = dataNode.get("free_tel_no").asText();
        String embassyLat = dataNode.get("embassy_lat").asText();
        String embassyLng = dataNode.get("embassy_lng").asText();

        // 추출된 정보 출력 또는 필요한 처리 수행
        System.out.println("대사관 이름: " + embassyName);
        System.out.println("대사관 주소: " + embassyAddress);
        System.out.println("대사관 전화번호: " + embassyPhoneNumber);
        System.out.println("대사관 무료 전화번호: " + embassyFreeNumber);
        System.out.println("대사관 긴급 전화번호: " + embassyUrgencyNumber);
        System.out.println("대사관 위도: " + embassyLat);
        System.out.println("대사관 경도: " + embassyLng);

        // 필요에 따라 추출된 정보 반환
        return embassyName + "/ " + embassyAddress + "/ " + embassyPhoneNumber + "/ " + embassyUrgencyNumber + "/ " + embassyFreeNumber + "/ " + embassyLat + "/ " + embassyLng;
    }
}
