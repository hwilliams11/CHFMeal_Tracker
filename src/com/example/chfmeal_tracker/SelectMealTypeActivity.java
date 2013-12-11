package com.example.chfmeal_tracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TableRow;

import com.example.chfmeal_tracker.Meal.MealType;

public class SelectMealTypeActivity extends Activity {

	ImageButton breakfast;
	ImageButton lunch;
	ImageButton dinner;
	ImageButton snack;
	
	TableRow breakfastRow;
	TableRow lunchRow;
	TableRow dinnerRow;
	TableRow snackRow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_meal_type);
		breakfastRow = (TableRow) findViewById(R.id.breakfastTableRow);
		lunchRow = (TableRow) findViewById(R.id.lunchTableRow);
		dinnerRow = (TableRow) findViewById(R.id.dinnerTableRow);
		snackRow = (TableRow) findViewById(R.id.snackTableRow);

		breakfastRow.setOnClickListener(new AddFoodListener(MealType.BREAKFAST));
		lunchRow.setOnClickListener(new AddFoodListener(MealType.LUNCH));
		dinnerRow.setOnClickListener(new AddFoodListener(MealType.DINNER));
		snackRow.setOnClickListener(new AddFoodListener(MealType.SNACK));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.select_meal_type, menu);
		return true;
	}

	class AddFoodListener implements View.OnClickListener {

		int type;

		public AddFoodListener(MealType mealType) {
			this.type = mealType.type;
		}

		@Override
		public void onClick(View v) {

			Intent intent = new Intent(SelectMealTypeActivity.this,
					SearchFoodActivity.class);
			intent.putExtra("mealType", type);
			Log.d("mydebug", "type: " + type);
			startActivity(intent);

		}

	}
}
