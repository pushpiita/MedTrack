package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        Button btnInsert = findViewById(R.id.btn_insert);
        Button btnViewUpdateDelete = findViewById(R.id.btn_view_update_delete);

        btnInsert.setOnClickListener(v -> {
            Intent intent = new Intent(ProductActivity.this, InsertActivity.class);
            startActivity(intent);
        });

        btnViewUpdateDelete.setOnClickListener(v -> {
            Intent intent = new Intent(ProductActivity.this, ViewDeleteUpdateActivity.class);
            startActivity(intent);
        });
    }
}
