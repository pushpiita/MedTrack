package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class InsertActivity extends AppCompatActivity {

    private EditText edtProductName, edtBuyingPrice, edtSellingPrice, edtQuantity, edtCompanyName, edtExpiredDate, edtBuyingDate;
    private Button btnInsert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        edtProductName = findViewById(R.id.edt_product_name);
        edtBuyingPrice = findViewById(R.id.edt_buying_price);
        edtSellingPrice = findViewById(R.id.edt_selling_price);
        edtQuantity = findViewById(R.id.edt_quantity);
        edtCompanyName = findViewById(R.id.edt_company_name);
        edtExpiredDate = findViewById(R.id.edt_expired_date);
        edtBuyingDate = findViewById(R.id.edt_buying_date);
        btnInsert = findViewById(R.id.btn_insert);

        btnInsert.setOnClickListener(v -> {
            String productName = edtProductName.getText().toString().trim();
            String buyingPriceStr = edtBuyingPrice.getText().toString().trim();
            String sellingPriceStr = edtSellingPrice.getText().toString().trim();
            String quantityStr = edtQuantity.getText().toString().trim();
            String companyName = edtCompanyName.getText().toString().trim();
            String expiredDate = edtExpiredDate.getText().toString().trim();
            String buyingDate = edtBuyingDate.getText().toString().trim();

            if (productName.isEmpty() || buyingPriceStr.isEmpty() || sellingPriceStr.isEmpty() || quantityStr.isEmpty() || companyName.isEmpty() || expiredDate.isEmpty() || buyingDate.isEmpty()) {
                Toast.makeText(InsertActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    double buyingPrice = Double.parseDouble(buyingPriceStr);
                    double sellingPrice = Double.parseDouble(sellingPriceStr);
                    int quantity = Integer.parseInt(quantityStr);

                    DatabaseHelper dbHelper = new DatabaseHelper(InsertActivity.this);
                    dbHelper.insertProduct(productName, buyingPrice, sellingPrice, quantity, companyName, expiredDate, buyingDate);

                    Toast.makeText(InsertActivity.this, "Product inserted successfully", Toast.LENGTH_SHORT).show();

                    // Clear the fields after insertion
                    edtProductName.setText("");
                    edtBuyingPrice.setText("");
                    edtSellingPrice.setText("");
                    edtQuantity.setText("");
                    edtCompanyName.setText("");
                    edtExpiredDate.setText("");
                    edtBuyingDate.setText("");
                } catch (NumberFormatException e) {
                    Toast.makeText(InsertActivity.this, "Invalid number format", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
