package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Test_DB";
    public static final int DATABASE_VERSION = 3;
    public static final String TABLE_REGISTER = "register";
    public static final String TABLE_PRODUCTS = "products";
    public static final String TABLE_DAILY_SALES = "daily_sales";
    public static final String COL_ID = "_id";
    public static final String COL_PHARMACYNAME = "pharmacyName";
    public static final String COL_EMAIL = "email";
    public static final String COL_PASSWORD = "password";
    public static final String COL_MOBILE = "mobile";
    public static final String COL_PRODUCT_NAME = "productName";
    public static final String COL_BUYING_PRICE = "buyingPrice";
    public static final String COL_SELLING_PRICE = "sellingPrice";
    public static final String COL_PRODUCT_QUANTITY = "productQuantity";
    public static final String COL_COMPANY_NAME = "companyName";
    public static final String COL_EXPIRED_DATE = "expiredDate";
    public static final String COL_BUYING_DATE = "buyingDate";
    public static final String COL_SALES_DATE = "salesDate";
    public static final String COL_SALES_AMOUNT = "salesAmount";

    private Context context;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REGISTER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DAILY_SALES);
        onCreate(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_REGISTER + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_PHARMACYNAME + " TEXT , " +
                COL_EMAIL + " TEXT , " +
                COL_PASSWORD + " TEXT, " +
                COL_MOBILE + " TEXT)");

        db.execSQL("CREATE TABLE " + TABLE_PRODUCTS + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_PRODUCT_NAME + " TEXT, " +
                COL_BUYING_PRICE + " REAL, " +
                COL_SELLING_PRICE + " REAL, " +
                COL_PRODUCT_QUANTITY + " INTEGER, " +
                COL_COMPANY_NAME + " TEXT, " +
                COL_EXPIRED_DATE + " TEXT, " +
                COL_BUYING_DATE + " TEXT)");

        db.execSQL("CREATE TABLE " + TABLE_DAILY_SALES + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_SALES_DATE + " TEXT, " +
                COL_SALES_AMOUNT + " REAL)");
    }

    public boolean insertUser(String pharmacyName, String email, String password, String mobile) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_PHARMACYNAME, pharmacyName);
        contentValues.put(COL_EMAIL, email);
        contentValues.put(COL_PASSWORD, password);
        contentValues.put(COL_MOBILE, mobile);

        long result = db.insert(TABLE_REGISTER, null, contentValues);
        db.close();

        if (result == -1) {
            Log.e("DatabaseHelper", "Failed to insert user");
            Toast.makeText(context, "Registration failed", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            Log.d("DatabaseHelper", "User inserted successfully");
            Toast.makeText(context, "Registration successful", Toast.LENGTH_SHORT).show();
            return true;
        }
    }
    public boolean checkUserByUsername(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COL_ID};
        String selection = COL_EMAIL + "=? and " + COL_PASSWORD + "=?";
        String[] selectionArgs = {username, password};
        Cursor cursor = db.query(TABLE_REGISTER, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count > 0;
    }


    public void insertProduct(String productName, double buyingPrice, double sellingPrice, int quantity, String companyName, String expiredDate, String buyingDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_PRODUCT_NAME, productName);
        values.put(COL_BUYING_PRICE, buyingPrice);
        values.put(COL_SELLING_PRICE, sellingPrice);
        values.put(COL_PRODUCT_QUANTITY, quantity);
        values.put(COL_COMPANY_NAME, companyName);
        values.put(COL_EXPIRED_DATE, expiredDate);
        values.put(COL_BUYING_DATE, buyingDate);
        db.insert(TABLE_PRODUCTS, null, values);
        db.close();
    }

    public Cursor getAllProducts() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_PRODUCTS, null);
    }

    public Cursor getProductByName(String productName) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_PRODUCTS + " WHERE " + COL_PRODUCT_NAME + " = ?", new String[]{productName});
    }
    public void updateProductQuantity(int productId, int newQuantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_PRODUCT_QUANTITY, newQuantity);

        db.update(TABLE_PRODUCTS, values, COL_ID + " = ?", new String[]{String.valueOf(productId)});
        db.close();
    }


    public void updateProduct(int productId, String productName, double buyingPrice, double sellingPrice, int quantity, String companyName, String expiredDate, String buyingDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_PRODUCT_NAME, productName);
        values.put(COL_BUYING_PRICE, buyingPrice);
        values.put(COL_SELLING_PRICE, sellingPrice);
        values.put(COL_PRODUCT_QUANTITY, quantity);
        values.put(COL_COMPANY_NAME, companyName);
        values.put(COL_EXPIRED_DATE, expiredDate);
        values.put(COL_BUYING_DATE, buyingDate);

        db.update(TABLE_PRODUCTS, values, COL_ID + " = ?", new String[]{String.valueOf(productId)});
        db.close();
    }

    public void insertDailySales(String salesDate, double salesAmount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_SALES_DATE, salesDate);
        values.put(COL_SALES_AMOUNT, salesAmount);
        long result = db.insert(TABLE_DAILY_SALES, null, values);
        Toast.makeText(context, "Daily sales data inserted successfully.", Toast.LENGTH_SHORT).show();

        db.close();
    }

    public double getTotalSales() {
        SQLiteDatabase db = this.getReadableDatabase();
        double totalSales = 0.0;

        Cursor cursor = db.rawQuery("SELECT SUM(" + COL_SALES_AMOUNT + ") FROM " + TABLE_DAILY_SALES, null);
        if (cursor.moveToFirst()) {
            totalSales = cursor.getDouble(0);
        }
        cursor.close();
        return totalSales;
    }
}
