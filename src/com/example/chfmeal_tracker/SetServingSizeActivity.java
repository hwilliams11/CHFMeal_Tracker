package com.example.chfmeal_tracker;

import java.text.DecimalFormat;
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
		Log.d("mydebug",food.toString());
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
	            String date = dateFormat.format(new Date());
				Meal m = new Meal(food.get_NDB_No()+"",date,numServings,mealType);
				Log.d("mydebug",m.toString());
				
				double scale = numServings*(food.get_gmwt1()+food.get_gmwt2())/100.0;
				double calories = food.get_calories()*scale;
				double sodium = food.get_sodium_mg()*scale;
				
				dh.addMeal(m,calories,sodium,numServings);
				
				new SyncMealItems().execute();
				
				Intent intent = new Intent(SetServingSizeActivity.this,HistoryLogActivity.class);
				startActivity(intent);
				finish();
				
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
			Log.d("mydebug","gmwt1: "+food.get_gmwt1());
			Log.d("mydebug","gmwt2: "+food.get_gmwt2());
			double scale = numServings*(food.get_gmwt1()+food.get_gmwt2())/100.0;
			Log.d("mydebug","scale: "+scale);
			
			double numCalories = (food.get_calories()*scale);
			double cholest = (food.get_cholesterol_mg()*scale);
			double sodium = (food.get_sodium_mg()*scale);
			double carb = (food.get_carbohydrate_g()*scale);
			double protein = (food.get_protein_g()*scale);
			double sugar = (food.get_sugar_g()*scale);
			double fiber = (food.get_fiber_g()*scale);
			
			DecimalFormat fmt = new DecimalFormat("#.00");
			
			String nutritionFacts = "Energy (kcal): "+fmt.format(numCalories)+"\n"+
					"Carbohydrates (g): "+fmt.format(carb)+"\n"+
					"Protein (g): "+fmt.format(protein)+"\n"+
					"Fiber (g): "+fmt.format(fiber)+"\n"+
					"Sugars (g): "+fmt.format(sugar)+"\n"+
					"Sodium (mg): "+fmt.format(sodium)+"\n"+
					"Cholesterol (mg):"+fmt.format(cholest)+"\n";
					
			
			nutritionTextView.setText(nutritionFacts);
		}
				
	}

}