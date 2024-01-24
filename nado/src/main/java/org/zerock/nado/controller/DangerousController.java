package org.zerock.nado.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.zerock.nado.service.NadoService;

import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.io.BufferedReader;
import java.io.IOException;

@Controller
@Log4j2
@RequiredArgsConstructor
public class DangerousController {
    private final NadoService service;

    @GetMapping("/dangerous")
    public String dangerousList() {
        log.info("dangerous.......");

        StringBuilder sb = new StringBuilder();
        String line = null;
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1262000/TravelBanService/getTravelBanList"); /*URL*/

        try {
            urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=USNHlgHpTgebXZ8V2RFHHTeV%2Bbx%2BWZf%2BsellHXxewTwGz6f4BnGcJyG3UJSJ2C85q9BJK9psxKg79fVO23qQyg%3D%3D"); /*Service Key*/
            urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
            urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지 번호*/
            urlBuilder.append("&" + URLEncoder.encode("isoCode1", "UTF-8") + "=" + URLEncoder.encode("GHA", "UTF-8")); /*ISO 국가코드*/
            urlBuilder.append("&" + URLEncoder.encode("isoCode2", "UTF-8") + "=" + URLEncoder.encode("GAB", "UTF-8")); /*ISO 국가코드*/
            urlBuilder.append("&" + URLEncoder.encode("isoCode3", "UTF-8") + "=" + URLEncoder.encode("GUY", "UTF-8")); /*ISO 국가코드*/
            urlBuilder.append("&" + URLEncoder.encode("isoCode4", "UTF-8") + "=" + URLEncoder.encode("GMB", "UTF-8")); /*ISO 국가코드*/
            urlBuilder.append("&" + URLEncoder.encode("isoCode5", "UTF-8") + "=" + URLEncoder.encode("GGY", "UTF-8")); /*ISO 국가코드*/
            urlBuilder.append("&" + URLEncoder.encode("isoCode6", "UTF-8") + "=" + URLEncoder.encode("GLP", "UTF-8")); /*ISO 국가코드*/
            urlBuilder.append("&" + URLEncoder.encode("isoCode7", "UTF-8") + "=" + URLEncoder.encode("GTM", "UTF-8")); /*ISO 국가코드*/
            urlBuilder.append("&" + URLEncoder.encode("isoCode8", "UTF-8") + "=" + URLEncoder.encode("GUM", "UTF-8")); /*ISO 국가코드*/
            urlBuilder.append("&" + URLEncoder.encode("isoCode9", "UTF-8") + "=" + URLEncoder.encode("GRD", "UTF-8")); /*ISO 국가코드*/
            urlBuilder.append("&" + URLEncoder.encode("isoCode10", "UTF-8") + "=" + URLEncoder.encode("GRC", "UTF-8")); /*ISO 국가코드*/
            URL url = new URL(urlBuilder.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");
            System.out.println("Response code: " + conn.getResponseCode());
            BufferedReader rd;
            if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }

            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            rd.close();
            conn.disconnect();
            System.out.println(sb.toString());
        } catch (ProtocolException ex) {
            throw new RuntimeException(ex);
        } catch (MalformedURLException ex) {
            throw new RuntimeException(ex);
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return line;
    }
}
