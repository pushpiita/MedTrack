package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SellProductActivity extends AppCompatActivity {

    private TextView tvProductName, tvSellingPrice, tvCurrentQuantity;
    private EditText etQuantityToSell;
    private Button btnConfirmSell;
    private DatabaseHelper databaseHelper;
    private int productId;
    private int currentQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_product);

        databaseHelper = new DatabaseHelper(this);

        // Initialize views
        tvProductName = findViewById(R.id.tvProductName);
        tvSellingPrice = findViewById(R.id.tvSellingPrice);
        tvCurrentQuantity = findViewById(R.id.tvCurrentQuantity);
        etQuantityToSell = findViewById(R.id.etQuantityToSell);
        btnConfirmSell = findViewById(R.id.btnConfirmSell);

        // Get data from intent
        Intent intent = getIntent();
        productId = intent.getIntExtra("PRODUCT_ID", -1);
        String productName = intent.getStringExtra("PRODUCT_NAME");
        String sellingPrice = intent.getStringExtra("SELLING_PRICE");
        currentQuantity = Integer.parseInt(intent.getStringExtra("QUANTITY"));

        // Set data to views
        tvProductName.setText(productName);
        tvSellingPrice.setText(sellingPrice);
        tvCurrentQuantity.setText(String.valueOf(currentQuantity));

        btnConfirmSell.setOnClickListener(v -> sellProduct());
    }

    private void sellProduct() {
        String quantityToSellStr = etQuantityToSell.getText().toString().trim();
        if (quantityToSellStr.isEmpty()) {
            Toast.makeText(this, "Please enter a quantity to sell.", Toast.LENGTH_SHORT).show();
            return;
        }

        int quantityToSell = Integer.parseInt(quantityToSellStr);
        if (quantityToSell > currentQuantity) {
            Toast.makeText(this, "Insufficient stock to sell.", Toast.LENGTH_SHORT).show();
            return;
        }

        int newQuantity = currentQuantity - quantityToSell;
        double sellingPrice = Double.parseDouble(tvSellingPrice.getText().toString());

        // Update the product quantity in the database
        databaseHelper.updateProductQuantity(productId, newQuantity);

        // Update the sales data
        double totalSale = sellingPrice * quantityToSell;
        databaseHelper.insertDailySales(java.time.LocalDate.now().toString(), totalSale);

        Toast.makeText(this, "Product sold successfully.", Toast.LENGTH_SHORT).show();

        // Pass back the updated quantity to the calling activity
        Intent resultIntent = new Intent();
        resultIntent.putExtra("UPDATED_QUANTITY", newQuantity);
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}
