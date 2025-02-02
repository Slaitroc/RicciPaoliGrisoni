package click.studentandcompanies;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Config {
    private static Config instance;
    private boolean debugMode;
    private double dynamicThresholdScaleFactor;
    private String welcomeMessage;

    private Config() {}

    public static void loadConfig() {
        ObjectMapper objectMapper = new ObjectMapper();
        try (InputStream inputStream = Config.class.getClassLoader().getResourceAsStream("config.json")) {
            if (inputStream == null) {
                throw new RuntimeException("Configuration file not found!");
            }
            instance = objectMapper.readValue(inputStream, Config.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config: " + e.getMessage(), e);
        }
    }

    public static boolean isDebugMode() {
        return instance.debugMode;
    }

    public static double getScaleFactor() {
        return instance.dynamicThresholdScaleFactor;
    }

    public static String welcomeMessage() {
        return instance.welcomeMessage;
    }

    public static void printStackTrace(Exception e) {
        if (instance.debugMode) {
            e.printStackTrace();
        }
    }
}