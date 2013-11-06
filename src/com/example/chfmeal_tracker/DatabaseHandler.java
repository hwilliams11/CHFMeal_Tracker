package com.example.chfmeal_tracker;

public class DatabaseHandler extends SQLiteOpenHelper {
	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "CHFMealTrackerDB";

	// Table name
	private static final String TABLE_USDAfood = "USDAfood";
	private static final String TABLE_MEAL = "meals";
	private static final String TABLE_SCORE = "scores";
	// USDAfood Table Columns names
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
	// Meals Table Columns names
	private static final String KEY_DATE = "creation_date";
	private static final String KEY_MEAL_TYPE = "meal_type";
	private static final String KEY_SERVING_SIZE = "serving_size";
	// Scores Table Columns names
	private static final String KEY_IDEAL_CALORIE = "ideal_calorie";
	private static final String KEY_IDEAL_SODIUM = "ideal_sodium";
	private static final String KEY_ACTUAL_CALORIE = "actual_calorie";
	private static final String KEY_ACTUAL_SODIUM = "actual_sodium";

	private static Context mainActivityContext;

	private static DatabaseHandler instance;

	public static DatabaseHandler getInstance(Context context) {

		if (instance == null) {

			mainActivityContext = context;
			instance = new DatabaseHandler(mainActivityContext);
		}
		return instance;
	}

	public static DatabaseHandler getInstance() {

		if (instance == null) {

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
	public void createTables() {

		SQLiteDatabase db = getWritableDatabase();
		createScoreTable(db);
		// createMealsTable(db);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		// create food table
		createUSDATable(db);
		// create scores table
		createScoreTable(db);
		// create meals table
		createMealsTable(db);

	}

	private void createMealsTable(SQLiteDatabase db) {

		StringBuilder sb = new StringBuilder();

		sb = new StringBuilder();
		sb.append("CREATE TABLE " + TABLE_MEAL + "(");
		sb.append(KEY_ID + " INTEGER,");
		sb.append(KEY_DATE + " DATE,");
		sb.append(KEY_SERVING_SIZE + " REAL,");
		sb.append(KEY_MEAL_TYPE + " INTEGER,");

		sb.append("FOREIGN KEY(");
		sb.append(KEY_ID);
		sb.append(")REFERENCES ");
		sb.append(TABLE_USDAfood + "(");
		sb.append(KEY_ID);
		sb.append(")");
		;
		String CREATE_MEAL_TABLE = sb.toString();
		Log.d("mydebug", CREATE_MEAL_TABLE);

		db.execSQL(CREATE_MEAL_TABLE);
	}

	private void createScoreTable(SQLiteDatabase db) {

		StringBuilder sb = new StringBuilder();
		sb.append("CREATE TABLE " + TABLE_SCORE + "(");
		sb.append(KEY_DATE + " DATE,");
		sb.append(KEY_MEAL_TYPE + " INTEGER,");
		sb.append(KEY_IDEAL_CALORIE + " REAL,");
		sb.append(KEY_IDEAL_SODIUM + " REAL,");
		sb.append(KEY_ACTUAL_CALORIE + " REAL,");
		sb.append(KEY_ACTUAL_SODIUM + " REAL");
		sb.append(")");
		String CREATE_SCORE_TABLE = sb.toString();
		Log.d("mydebug", CREATE_SCORE_TABLE);

		db.execSQL(CREATE_SCORE_TABLE);
	}

	private void createUSDATable(SQLiteDatabase db) {
		StringBuilder sb = new StringBuilder();
		sb.append("CREATE TABLE " + TABLE_MEAL + "(");
		sb.append(KEY_ID + " INTEGER,");
		sb.append(KEY_DATE + " DATE,");
		sb.append(KEY_SERVING_SIZE + " REAL,");
		sb.append(KEY_MEAL_TYPE + " INTEGER, ");

		sb.append("FOREIGN KEY(");
		sb.append(KEY_ID);
		sb.append(") REFERENCES ");
		sb.append(TABLE_USDAfood + "(");
		sb.append(KEY_ID);

		sb.append("))");
		;
		String CREATE_MEAL_TABLE = sb.toString();
		Log.d("mydebug", CREATE_MEAL_TABLE);

		db.execSQL(CREATE_MEAL_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCORE);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEAL);
		// Create tables again
		onCreate(db);
	}

	// Adding new meal

	// Adding new food
	public void addFood(Food food) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();

		Log.d("mydebug", "insert " + food.get_NDB_No());

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

	// Getting single food
	public Food getFood(String food_name) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_USDAfood, new String[] { KEY_ID,
				KEY_NAME, KEY_CALORIE, KEY_PROTEIN, KEY_CARBOHYDRATE,
				KEY_SODIUM, KEY_CHOLESTEROL, KEY_GMWT1, KEY_GMWT1_DESC,
				KEY_GMWT2, KEY_GMWT2_DESC }, KEY_NAME + "=?",
				new String[] { food_name }, null, null, null, null);

		Food food = null;

		if (cursor != null && cursor.getCount() != 0) {
			cursor.moveToFirst();
			food = createFoodItem(cursor);
		}
		// return contact
		return food;
	}

	public Food getFood(int foodId) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_USDAfood, new String[] { KEY_ID,
				KEY_NAME, KEY_CALORIE, KEY_PROTEIN, KEY_CARBOHYDRATE,
				KEY_SODIUM, KEY_CHOLESTEROL, KEY_GMWT1, KEY_GMWT1_DESC,
				KEY_GMWT2, KEY_GMWT2_DESC }, KEY_ID + "=?",
				new String[] { foodId + "" }, null, null, null, null);

		Food food = null;

		Log.d("check", (cursor == null) + "");
		Log.d("check", cursor.getCount() + "");

		if (cursor != null && cursor.getCount() != 0) {
			cursor.moveToFirst();
			food = createFoodItem(cursor);
		}
		// return contact
		return food;
	}

	public int removeFood(String foodName) {

		SQLiteDatabase db = this.getWritableDatabase();

		int rows = db.delete(TABLE_USDAfood, KEY_NAME + "=?",
				new String[] { foodName });
		return rows;
	}

	public void getFoodMatches(String food_name, List<Food> matches) {

		matches.clear();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_USDAfood, new String[] { KEY_ID,
				KEY_NAME, KEY_CALORIE, KEY_PROTEIN, KEY_CARBOHYDRATE,
				KEY_SODIUM, KEY_CHOLESTEROL, KEY_GMWT1, KEY_GMWT1_DESC,
				KEY_GMWT2, KEY_GMWT2_DESC }, KEY_NAME + " LIKE ?",
				new String[] { "%" + food_name + "%" }, null, null, null, null);

		if (cursor != null && cursor.getCount() != 0) {
			int rows = cursor.getCount();

			cursor.moveToFirst();
			Food food = createFoodItem(cursor);
			matches.add(food);

			for (int i = 1; i < rows; i++) {

				cursor.moveToNext();
				food = createFoodItem(cursor);
				matches.add(food);
			}
		}
	}

	private Food createFoodItem(Cursor cursor) {
		Food food = new Food(cursor.getInt(0), cursor.getString(1),
				cursor.getDouble(2), cursor.getDouble(3), cursor.getDouble(4),
				cursor.getDouble(5), cursor.getDouble(6), cursor.getDouble(7),
				cursor.getString(8), cursor.getDouble(9), cursor.getString(10));

		return food;
	}

	public int getNumRows() {

		SQLiteDatabase db = this.getReadableDatabase();

		Cursor mCount = db.rawQuery("select count(*) from " + TABLE_USDAfood,
				null);
		mCount.moveToFirst();
		int count = mCount.getInt(0);
		mCount.close();

		return count;
	}

	// Adding new meal
	public void addMeal(Meal meal) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();

		values.put(KEY_ID, meal.get_NDB_No());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Log.d("mydebug", dateFormat.format(new Date()));
		values.put(KEY_DATE, dateFormat.format(new Date()));
		values.put(KEY_SERVING_SIZE, meal.get_Serving());
		values.put(KEY_MEAL_TYPE, meal.get_Type());
		// Inserting Row
		db.insert(TABLE_MEAL, null, values);
		db.close();
		Log.d("mydebug", "added meal");
	}

	// Adding new score
	public void addScore(Score score) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		values.put(KEY_DATE, dateFormat.format(new Date()));
		values.put(KEY_MEAL_TYPE, score.getMeal_type());
		values.put(KEY_IDEAL_CALORIE, score.getIdeal_calorie());
		values.put(KEY_IDEAL_SODIUM, score.getIdeal_sodium());
		values.put(KEY_ACTUAL_CALORIE, score.getActual_calorie());
		values.put(KEY_ACTUAL_SODIUM, score.getActual_sodium());
		// Inserting Row
		db.insert(TABLE_SCORE, null, values);
		db.close();
	}

	// get meal history
	public ArrayList<Meal> getMeals() {
		SQLiteDatabase db = this.getReadableDatabase();
		String query = "SELECT meals.NDB_No, food_name, serving_size, calorie, sodium_mg, creation_date, meal_type FROM USDAfood, meals where USDAfood.NDB_No=meals.NDB_No ORDER BY creation_date, meal_type";

		Cursor cursor = db.rawQuery(query, null);
		Log.d("mydebug", "finished query db for meals");
		if (cursor != null && cursor.getCount() != 0) {
			int rows = cursor.getCount();

			cursor.moveToFirst();
			ArrayList<Meal> result = new ArrayList<Meal>();

			Meal meal = new Meal(cursor.getString(0), cursor.getString(1),
					cursor.getDouble(2), cursor.getDouble(3),
					cursor.getDouble(4), cursor.getString(5), cursor.getInt(6));

			result.add(meal);
			for (int i = 1; i < rows; i++) {

				cursor.moveToNext();
				result.add(new Meal(cursor.getString(0), cursor.getString(1),
						cursor.getDouble(2), cursor.getDouble(3), cursor
								.getDouble(4), cursor.getString(5), cursor
								.getInt(6)));

			}
			Log.d("mydebug", result.toString());
			return result;
		}
		return null;

	}

	public ArrayList<Score> getScores() {
		SQLiteDatabase db = this.getReadableDatabase();
		String query = "SELECT * from " + TABLE_SCORE
				+ " order by creation_date, meal_type";

		Cursor cursor = db.rawQuery(query, null);
		if (cursor != null && cursor.getCount() != 0) {
			int rows = cursor.getCount();

			cursor.moveToFirst();
			ArrayList<Score> result = new ArrayList<Score>();

			result.add(new Score(cursor.getString(0), cursor.getInt(1), cursor
					.getDouble(2), cursor.getDouble(3), cursor.getDouble(4),
					cursor.getDouble(5)));
			for (int i = 1; i < rows; i++) {

				cursor.moveToNext();
				result.add(new Score(cursor.getString(0), cursor.getInt(1),
						cursor.getDouble(2), cursor.getDouble(3), cursor
								.getDouble(4), cursor.getDouble(5)));
			}
			return result;
		}
		return null;

	}
}