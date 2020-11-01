package ca.mcgill.ecse321.artgalleryapplication;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class ArtGalleryIntegrationTests {

    // METHODS FOR REST

    public JSONObject send(String type, String appURL, String path, String parameters) {
        try {
            URL URL = new URL(appURL + path + ((parameters == null) ? "" : ("?" + parameters)));
            System.out.println("Sending: " + URL.toString());
            HttpURLConnection c = (HttpURLConnection) URL.openConnection();
            c.setRequestMethod(type);
            c.setRequestProperty("Accept", "application/json");
            System.out.println(c.getContentType());
            if (c.getResponseCode() != 200) {
                throw new RuntimeException(URL.toString() + " Returned error: " + c.getResponseCode());
            }
            BufferedReader br = new BufferedReader(new InputStreamReader((c.getInputStream())));
            String response = br.readLine();
            if (response.equals("true") || response.equals("false")) {
                JSONObject json = new JSONObject();
                json.put("boolean", response);
                c.disconnect();
                return json;
            } else {
                JSONObject json = new JSONObject(response);
                c.disconnect();
                return json;
            }
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONArray sendArray(String type, String appURL, String path, String parameters) {
        try {
            URL url = new URL(appURL + path + ((parameters == null) ? "" : ("?" + parameters)));
            System.out.println("Sending: " + url.toString());
            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            c.setRequestMethod(type);
            c.setRequestProperty("Accept", "application/json");
            if (c.getResponseCode() != 200) {
                throw new RuntimeException(url.toString() + " Returned error:: " + c.getResponseCode());
            }
            BufferedReader br = new BufferedReader(new InputStreamReader((c.getInputStream())));
            String response = br.readLine();
            if (response != null) {
                JSONArray r = new JSONArray(response);
                c.disconnect();
                return r;
            }
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
