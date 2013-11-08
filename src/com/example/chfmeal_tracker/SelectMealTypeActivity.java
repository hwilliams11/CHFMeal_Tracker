package com.example.chfmeal_tracker;

import com.example.chfmeal_tracker.Meal.MealType;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class SelectMealTypeActivity extends Activity {

	TextView breakfast;
	TextView lunch;
	TextView dinner;
	TextView snack;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_meal_type);
		breakfast = (TextView)findViewById(R.id.breakfastTextView);
		lunch = (TextView)findViewById(R.id.lunchTextView);
		dinner = (TextView)findViewById(R.id.dinnerTextView);
		snack = (TextView)findViewById(R.id.snackTextView);
		
		
		breakfast.setOnClickListener(new AddFoodListener(MealType.BREAKFAST));
		lunch.setOnClickListener(new AddFoodListener(MealType.LUNCH));
		dinner.setOnClickListener(new AddFoodListener(MealType.DINNER));
		snack.setOnClickListener(new AddFoodListener(MealType.SNACK));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.select_meal_type, menu);
		return true;
	}
	
	class AddFoodListener implements View.OnClickListener{

		int type;
		public AddFoodListener(MealType mealType){
			this.type = mealType.type;
		}
		@Override
		public void onClick(View v) {
			
			Intent intent = new Intent(SelectMealTypeActivity.this,SearchFoodActivity.class);
			intent.putExtra("mealType", type);
			Log.d("mydebug","type: "+type);
			startActivity(intent);
			
		}
		
	}
}
