package com.example.chfmeal_tracker;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.chfmeal_tracker.Meal.MealType;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SetServingSizeActivity extends Activity {

	private TextView nutritionTextView;
	private EditText numServingsEditText;
	private Button addFoodButton;
	private MealType mealType;
	private DatabaseHandler dh;
	
	Food food;
	Integer numServings;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_serving_size);
		
		nutritionTextView = (TextView)findViewById(R.id.nutritionFactsTextView);
		numServingsEditText = (EditText)findViewById(R.id.numServingsEditText);
		addFoodButton = (Button)findViewById(R.id.addFoodButton);
		
		int foodId = getIntent().getExtras().getInt("foodId");
		int num = getIntent().getExtras().getInt("mealType");
		Log.d("mydebug","meal type here: "+num);
		mealType = Meal.getMealType(num);
		Log.d("mydebug","meal type: "+mealType);
		dh = DatabaseHandler.getInstance();
		
		food = dh.getFood(foodId);
		numServings = Integer.parseInt(numServingsEditText.getText().toString());
		Log.d("check",food.toString());
		if( food!= null ){
		
			showNutritionFacts();
		}
		numServingsEditText.addTextChangedListener( new TextWatcher(){

			public void afterTextChanged(Editable arg0) {}
			public void beforeTextChanged(CharSequence arg0, int arg1,int arg2, int arg3) {}
			
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,int arg3) {
				
				String text = numServingsEditText.getText().toString();
				if( text!=null && text.length()>0){
					numServings = Integer.parseInt(text);
					if( numServings==null ){
						numServings = 1;
					}
				}else{
					
					numServings = 1;
					
				}
				showNutritionFacts();
				
			}
			
		});
		addFoodButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				
				
				
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	            Log.d("mydebug", dateFormat.format(new Date()));
	            String date = dateFormat.format(new Date());
				Meal m = new Meal(food.get_NDB_No()+"",date,numServings,mealType);
				Log.d("mydebug",m.toString());
				dh.addMeal(m);
				new SyncMealItems().execute();
				
				Intent intent = new Intent(SetServingSizeActivity.this,HistoryLogActivity.class);
				startActivity(intent);
				
				
			}
		});		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.set_serving_size, menu);
		return true;
	}
	private void showNutritionFacts(){
		
		if( food!= null ){
			double scale = numServings;
			
			int numCalories = (int)(food.get_calories()*scale);
			int cholest = (int)(food.get_cholesterol_mg()*scale);
			int sodium = (int)(food.get_sodium_mg()*scale);
			int carb = (int)(food.get_carbohydrate_g()*scale);
			int protein = (int)(food.get_protein_g()*scale);
			
			Log.d("check","carbs: "+food.get_carbohydrate_g());
			String nutritionFacts = "Calories: "+numCalories+"\n"+
					"Cholesterol: "+cholest+"\n"+
					"Sodium: "+sodium+"\n"+
					"Carb: "+carb+"\n"+
					"Protein: "+protein+"\n";
			
			nutritionTextView.setText(nutritionFacts);
		}
				
	}

}