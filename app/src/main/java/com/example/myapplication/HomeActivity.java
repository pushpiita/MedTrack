package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;



public class HomeActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button btnProduct = findViewById(R.id.btn_product);
        Button btnAccount = findViewById(R.id.btn_account);
        Button btnLogout = findViewById(R.id.btn_logout);

        btnProduct.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ProductActivity.class);
            startActivity(intent);
        });

        btnAccount.setOnClickListener(v -> {
            String storedPassword = sharedPreferences.getString("account_password", "");
            Intent intent;
            if (storedPassword.isEmpty()) {
                intent = new Intent(HomeActivity.this, SetpasswordActivity.class);
            } else {
                intent = new Intent(HomeActivity.this, EnterPasswordActtivity.class);
            }
            startActivity(intent);
        });

        btnLogout.setOnClickListener(v -> {

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();


            Intent intent = new Intent(HomeActivity.this, LogoutActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }
}
