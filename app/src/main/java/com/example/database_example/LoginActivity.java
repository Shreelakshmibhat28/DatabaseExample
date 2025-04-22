package com.example.database_example;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "My_Channel_ID";
    private static final int NOTIFICATION_ID = 100;
    private static final int PERMISSION_REQUEST_CODE = 101;
    private NotificationManager nm;
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



        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = userName.getText().toString();
                String email = userEmail.getText().toString();
                String pwd = password.getText().toString();
                String conPwd = confirmPassword.getText().toString();

                if(username.isEmpty() || email.isEmpty() || pwd.isEmpty() || conPwd.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!pwd.equals(conPwd)) {
                    Toast.makeText(LoginActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }


                SharedPreferences pref = getSharedPreferences("login", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putBoolean("flag",true);
                editor.putString("uname",username);
                editor.putString("email",email);
                editor.apply();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    if (ContextCompat.checkSelfPermission(LoginActivity.this, android.Manifest.permission.POST_NOTIFICATIONS)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(LoginActivity.this,
                                new String[]{Manifest.permission.POST_NOTIFICATIONS},
                                PERMISSION_REQUEST_CODE);
                    } else {
                        showNotification();
                    }
                } else {
                    showNotification();
                }

                Intent iHome = new Intent(LoginActivity.this, HomeActivity.class);
                iHome.putExtra("uname",username);
                iHome.putExtra("email",email);
                startActivity(iHome);
            }
        });
    }

    private void showNotification() {
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_notification, null);
        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
        Bitmap largeIcon = bitmapDrawable.getBitmap();

        nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        android.app.Notification notification;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "New Channel",
                    NotificationManager.IMPORTANCE_HIGH
            );
            nm.createNotificationChannel(channel);

            notification = new android.app.Notification.Builder(this, CHANNEL_ID)
                    .setLargeIcon(largeIcon)
                    .setSmallIcon(R.drawable.ic_notification)
                    .setContentTitle("Login Success")
                    .setContentText("You are logged in as "+ userName.getText().toString())
                   // .setSubText("SubText Example")
                    .build();
        } else {
            notification = new android.app.Notification.Builder(this)
                    .setLargeIcon(largeIcon)
                    .setSmallIcon(R.drawable.ic_notification)
                    .setContentTitle("Login Success")
                    .setContentText("You are logged in as "+ userName.getText().toString())
                    //.setSubText("SubText Example")
                    .build();
        }

        nm.notify(NOTIFICATION_ID, notification);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showNotification();
            }
        }
    }
}