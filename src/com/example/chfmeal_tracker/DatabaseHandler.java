package com.example.chfmeal_tracker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {
	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "CHFMealTrackerDB";

	// Table name
	private static final String TABLE_USDAfood = "USDAfood";

	// Table Columns names
	private static final String KEY_ID = "NDB_No";
	private static final String KEY_NAME = "food_name";
	private static final String KEY_WATER = "water_g";
	private static final String KEY_CALORIE = "calorie";
	private static final String KEY_PROTEIN = "protein_g";
	private static final String KEY_CARBOHYDRATE = "carbohydrate_g";
	private static final String KEY_FIBER = "fiber_g";
	private static final String KEY_SUGAR = "sugar_g";
	private static final String KEY_CALCIUM = "calcium_mg";
	private static final String KEY_SODIUM = "sodium_mg";
	private static final String KEY_CHOLESTEROL = "cholesterol_mg";
	private static final String KEY_GMWT1 = "gmwt1";
	private static final String KEY_GMWT1_DESC = "gmwt1_desc";
	private static final String KEY_GMWT2 = "gmwt2";
	private static final String KEY_GMWT2_DESC = "gmwt2_desc";
	private static DatabaseHandler instance;
	private static Context mainActivityContext;
	
	public static DatabaseHandler getInstance(Context context){
		
		if( instance == null ){
	
			mainActivityContext= context;
			instance = new DatabaseHandler(mainActivityContext);
		}
		return instance;
	}
	public static DatabaseHandler getInstance(){
		
		if( instance == null ){
			
			instance = new DatabaseHandler(mainActivityContext);
		}
		return instance;
	}
	private DatabaseHandler(Context context) {
		
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		
	}

	/*
	 * There is where we need to write create table statements.This is called
	 * when database is created.
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		
		
		StringBuilder sb = new StringBuilder();
		sb.append("CREATE TABLE " + TABLE_USDAfood + "(");
		sb.append(KEY_ID + " TEXT PRIMARY KEY,");
		sb.append(KEY_NAME + " TEXT,");
		sb.append(KEY_WATER + " REAL,");
		sb.append(KEY_CALORIE + " REAL,");
		sb.append(KEY_PROTEIN + " REAL,");
		sb.append(KEY_CARBOHYDRATE + " REAL,");
		sb.append(KEY_FIBER + " REAL,");
		sb.append(KEY_SUGAR + " REAL,");
		sb.append(KEY_CALCIUM + " REAL,");
		sb.append(KEY_SODIUM + " REAL,");
		sb.append(KEY_CHOLESTEROL + " REAL,");
		sb.append(KEY_GMWT1 + " REAL,");
		sb.append(KEY_GMWT1_DESC + " TEXT,");
		sb.append(KEY_GMWT2 + " REAL,");
		sb.append(KEY_GMWT2_DESC + " TEXT");
		sb.append(")");
		String CREATE_USDA_FOOD_TABLE = sb.toString();
		Log.d("mydebug", CREATE_USDA_FOOD_TABLE);
		
		db.execSQL(CREATE_USDA_FOOD_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USDAfood);

		// Create tables again
		onCreate(db);
	}

	// Adding new contact
	public void addFood(Food food) {
		
		
		
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();

		values.put(KEY_ID, food.get_NDB_No());
		values.put(KEY_NAME, food.get_food_name());
		values.put(KEY_WATER, food.get_water_g());
		values.put(KEY_CALORIE, food.get_calorie());
		values.put(KEY_PROTEIN, food.get_protein_g());
		values.put(KEY_CARBOHYDRATE, food.get_carbohydrate_g());
		values.put(KEY_FIBER, food.get_fiber_g());
		values.put(KEY_SUGAR, food.get_sugar_g());
		values.put(KEY_CALCIUM, food.get_calcium_mg());
		values.put(KEY_SODIUM, food.get_sodium_mg());
		values.put(KEY_CHOLESTEROL, food.get_cholesterol_mg());
		values.put(KEY_GMWT1, food.get_gmwt1());
		values.put(KEY_GMWT1_DESC, food.get_gmwt1_desc());
		values.put(KEY_GMWT2, food.get_gmwt2());
		values.put(KEY_GMWT2_DESC, food.get_gmwt2_desc());
		// Inserting Row
		db.insert(TABLE_USDAfood, null, values);
		Log.d("mydebug", "insert " + food.get_NDB_No());
		db.close(); // Closing database connection
	}
	// Getting single contact
	public Food getFood(String food_name) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_USDAfood, new String[] { KEY_ID,
				KEY_NAME, KEY_CALORIE, KEY_SODIUM, KEY_GMWT1, KEY_GMWT1_DESC,
				KEY_GMWT2, KEY_GMWT2_DESC }, KEY_NAME + "=?",
				new String[] { food_name }, null, null, null, null);
		
		Food food = null;
		
		if (cursor != null && cursor.getCount()!=0){
			cursor.moveToFirst();
			food = createFoodItem(cursor);
		}
		// return contact
		return food;
	}

	public int removeFood(String foodName){
		
		SQLiteDatabase db = this.getWritableDatabase();
		
		int rows = db.delete(TABLE_USDAfood, KEY_NAME+"=?", new String[]{foodName});
		return rows;
	}
	public void getFoodMatches(String food_name, List<Food> matches){
		
		matches.clear();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_USDAfood, new String[] { KEY_ID,
				KEY_NAME, KEY_CALORIE, KEY_SODIUM, KEY_GMWT1, KEY_GMWT1_DESC,
				KEY_GMWT2, KEY_GMWT2_DESC }, KEY_NAME + " LIKE ?",
				new String[] { "%"+food_name+"%" }, null, null, null, null);
		
		
		if( cursor!= null && cursor.getCount()!=0 ){
			int rows = cursor.getCount();
			
			cursor.moveToFirst();
			Food food = createFoodItem(cursor);
			matches.add(food);
			
			for(int i=1;i<rows;i++){
				
				cursor.moveToNext();
				food = createFoodItem(cursor);
				matches.add(food);
			}
		}
	}

	private Food createFoodItem(Cursor cursor) {
		Food food = new Food(cursor.getString(0), cursor.getString(1),
				cursor.getDouble(2), cursor.getDouble(3), cursor.getDouble(4),
				cursor.getString(5), cursor.getDouble(6), cursor.getString(7));
		return food;
	}

	public int getNumRows() {
		
		SQLiteDatabase db = this.getReadableDatabase();
		
		Cursor mCount= db.rawQuery("select count(*) from "+TABLE_USDAfood, null);
		mCount.moveToFirst();
		int count= mCount.getInt(0);
		mCount.close();
		
		return count;
	}
}
