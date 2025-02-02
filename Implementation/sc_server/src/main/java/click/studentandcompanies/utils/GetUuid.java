package click.studentandcompanies.utils;

import click.studentandcompanies.Config;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

public class GetUuid {

    private static final String AUTH_SERVER_URL = "http://sc-auth-service:8444/auth-api/get-uuid";

    public static String getUuid(String token) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, token);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(AUTH_SERVER_URL, HttpMethod.GET,
                    entity, new ParameterizedTypeReference<Map<String, Object>>() {
                    });
            String uuid = response.getBody().get("uuid").toString();
            if (uuid == null) {
                throw new Exception("UUID not found");
            }
            return uuid;
        } catch (HttpClientErrorException e) {
            // Handle specific HTTP errors
            Config.printStackTrace(e);
            System.err.println("HTTP error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
            return null;
        } catch (Exception e) {
            Config.printStackTrace(e);
            // Handle other errors, including UUID not found
            System.err.println("Error: " + e.getMessage());
            return null;
        }
    }
}
