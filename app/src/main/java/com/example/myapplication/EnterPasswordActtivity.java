package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EnterPasswordActtivity extends AppCompatActivity {

    private EditText etPassword;
    private Button btnEnter;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_password_acttivity);

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        etPassword = findViewById(R.id.etPassword);
        btnEnter = findViewById(R.id.btnEnter);

        btnEnter.setOnClickListener(v -> {
            String password = etPassword.getText().toString().trim();
            String storedPassword = sharedPreferences.getString("account_password", "");

            if (password.equals(storedPassword)) {
                Intent intent = new Intent(EnterPasswordActtivity.this, AccountActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Incorrect password", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
