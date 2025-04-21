package com.example.database_example;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HomeActivity extends AppCompatActivity {

    Button btnLogout;
    TextView uName, uEmail;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnLogout = findViewById(R.id.btnLogout);
        uName = findViewById(R.id.uName);
        uEmail = findViewById(R.id.uEmail);

        String uname = getIntent().getStringExtra("uname");
        String email = getIntent().getStringExtra("email");

        if (uname == null || email == null) {
            SharedPreferences pref = getSharedPreferences("login", MODE_PRIVATE);
            uname = pref.getString("uname", "Guest");
            email = pref.getString("email", "guest@example.com");
        }

        uName.setText("Welcome, " + uname + "!");
        uEmail.setText("Your email: " + email);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences pref = getSharedPreferences("login",MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putBoolean("flag",false);
                editor.clear();
                editor.apply();

                Intent iHome = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(iHome);
                finish();
            }
        });
    }
}