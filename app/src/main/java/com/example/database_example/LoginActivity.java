package com.example.database_example;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    EditText userName, userEmail, password, confirmPassword;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btnLogin);
        userName = findViewById(R.id.userName);
        userEmail = findViewById(R.id.userEmail);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmPassword);

        String username = userName.getText().toString();
        String email = userEmail.getText().toString();
        String pwd = password.getText().toString();
        String conPwd = confirmPassword.getText().toString();

        if(username.isEmpty() || email.isEmpty() || pwd.isEmpty() || conPwd.isEmpty()){
            Toast.makeText(this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!pwd.equals(conPwd)){
            Toast.makeText(this, "Password do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getSharedPreferences("login", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putBoolean("flag",true);
                editor.apply();

                Intent iHome = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(iHome);
            }
        });
    }
}