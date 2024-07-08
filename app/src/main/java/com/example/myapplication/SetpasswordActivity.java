package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SetpasswordActivity extends AppCompatActivity {

    private EditText etPassword;
    private Button btnSetPassword;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setpassword);

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        etPassword = findViewById(R.id.etPassword);
        btnSetPassword = findViewById(R.id.btnSetPassword);

        btnSetPassword.setOnClickListener(v -> {
            String password = etPassword.getText().toString().trim();
            if (!password.isEmpty()) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("account_password", password);
                editor.apply();
                Toast.makeText(this, "Password set successfully", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
