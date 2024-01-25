package org.zerock.nado.API;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class ApiDanger {
    // Method to get the list of dangerous countries
    public static List<String> getDangerList() throws IOException {
        List<String> dangerList = new ArrayList<>();

        // Build the API URL
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1262000/TravelBanService/getTravelBanList");
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=USNHlgHpTgebXZ8V2RFHHTeV%2Bbx%2BWZf%2BsellHXxewTwGz6f4BnGcJyG3UJSJ2C85q9BJK9psxKg79fVO23qQyg%3D%3D");
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("10", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("countryName", "UTF-8") + "=" + URLEncoder.encode("가나", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("countryEnName", "UTF-8") + "=" + URLEncoder.encode("Ghana", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("isoCode1", "UTF-8") + "=" + URLEncoder.encode("GAB", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("isoCode2", "UTF-8") + "=" + URLEncoder.encode("GUY", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("isoCode3", "UTF-8") + "=" + URLEncoder.encode("GMB", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("isoCode4", "UTF-8") + "=" + URLEncoder.encode("GGY", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("isoCode5", "UTF-8") + "=" + URLEncoder.encode("GLP", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("isoCode6", "UTF-8") + "=" + URLEncoder.encode("GTM", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("isoCode7", "UTF-8") + "=" + URLEncoder.encode("GUM", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("isoCode8", "UTF-8") + "=" + URLEncoder.encode("GRD", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("isoCode9", "UTF-8") + "=" + URLEncoder.encode("GRC", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("isoCode10", "UTF-8") + "=" + URLEncoder.encode("GRL", "UTF-8"));

        // Send the GET request and parse the response
        String apiResponse = sendGetRequest(urlBuilder.toString());

        // Parse the XML response and extract values
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader(apiResponse)));

            NodeList nodeList = document.getElementsByTagName("item");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String country = element.getElementsByTagName("country").item(0).getTextContent();
                    String dangerLevel = element.getElementsByTagName("dangerLevel").item(0).getTextContent();

                    // 각 정보를 리스트에 추가
                    dangerList.add("Country: " + country + ", Danger Level: " + dangerLevel);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // You can add more logic to process the response if needed.

        return dangerList;
    }

    // Existing code...

    // Helper method to send a GET request and get the response
    private static String sendGetRequest(String apiUrl) throws IOException {
        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/xml"); // XML을 받는 API의 경우
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            System.out.println("버퍼드리더 당신은 누구십니까 : " + rd);
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
        System.out.println("내가 그렇게 그렇게 만만하니 HEY! : " + sb.toString());

        // 여기서 XML 파싱 또는 필요한 작업 수행

        return sb.toString();
    }
}
