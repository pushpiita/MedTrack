package com.example.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ViewDeleteUpdateActivity extends AppCompatActivity {

    private EditText etSearch;
    private TextView tvProductName, tvBuyingPrice, tvSellingPrice, tvQuantity, tvCompanyName, tvExpiredDate, tvBuyingDate;
    private Button btnSearch, btnSell;
    private DatabaseHelper databaseHelper;
    private int currentProductId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_delete_update);

        databaseHelper = new DatabaseHelper(this);

        // Initialize views
        etSearch = findViewById(R.id.etSearch);
        tvProductName = findViewById(R.id.tvProductName);
        tvBuyingPrice = findViewById(R.id.tvBuyingPrice);
        tvSellingPrice = findViewById(R.id.tvSellingPrice);
        tvQuantity = findViewById(R.id.tvQuantity);
        tvCompanyName = findViewById(R.id.tvCompanyName);
        tvExpiredDate = findViewById(R.id.tvExpiredDate);
        tvBuyingDate = findViewById(R.id.tvBuyingDate);
        btnSearch = findViewById(R.id.btnSearch);
        btnSell = findViewById(R.id.btnSell);

        btnSearch.setOnClickListener(v -> searchProduct());
        btnSell.setOnClickListener(v -> sellProduct());
    }

    private void searchProduct() {
        String searchQuery = etSearch.getText().toString().trim();
        if (searchQuery.isEmpty()) {
            Toast.makeText(this, "Please enter a product name to search.", Toast.LENGTH_SHORT).show();
            return;
        }

        Cursor cursor = databaseHelper.getProductByName(searchQuery);
        if (cursor.moveToFirst()) {
            currentProductId = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_ID));
            tvProductName.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_PRODUCT_NAME)));
            tvBuyingPrice.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_BUYING_PRICE)));
            tvSellingPrice.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_SELLING_PRICE)));
            tvQuantity.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_PRODUCT_QUANTITY)));
            tvCompanyName.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_COMPANY_NAME)));
            tvExpiredDate.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_EXPIRED_DATE)));
            tvBuyingDate.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_BUYING_DATE)));
        } else {
            Toast.makeText(this, "Product not found.", Toast.LENGTH_SHORT).show();
            clearFields();
        }
        cursor.close();
    }

    private void sellProduct() {
        if (currentProductId != -1) {
            Intent intent = new Intent(this, SellProductActivity.class);
            intent.putExtra("PRODUCT_ID", currentProductId);
            intent.putExtra("PRODUCT_NAME", tvProductName.getText().toString());
            intent.putExtra("SELLING_PRICE", tvSellingPrice.getText().toString());
            intent.putExtra("QUANTITY", tvQuantity.getText().toString());
            startActivityForResult(intent, 1); // Use a request code to identify the result
        } else {
            Toast.makeText(this, "Please search for a product first.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            if (data != null) {
                int updatedQuantity = data.getIntExtra("UPDATED_QUANTITY", -1);
                if (updatedQuantity != -1) {
                    tvQuantity.setText(String.valueOf(updatedQuantity));
                    Toast.makeText(this, "Product quantity updated.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void clearFields() {
        currentProductId = -1;
        tvProductName.setText("N/A");
        tvBuyingPrice.setText("N/A");
        tvSellingPrice.setText("N/A");
        tvQuantity.setText("N/A");
        tvCompanyName.setText("N/A");
        tvExpiredDate.setText("N/A");
        tvBuyingDate.setText("N/A");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseHelper.close();
    }
}
