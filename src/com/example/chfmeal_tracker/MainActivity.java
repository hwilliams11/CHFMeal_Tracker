package com.example.chfmeal_tracker;

import java.io.IOException;
import java.util.Scanner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends Activity {

	private ImageButton addFoodButton;
	private ImageButton viewHistoryButton;
	private Activity act;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// this line deletes database when uncommented
		// deleteDatabase("CHFMealTrackerDB");
		DatabaseHandler dh = DatabaseHandler.getInstance(this);
		addFoodButton = (ImageButton) findViewById(R.id.addFoodButtonMain);
		viewHistoryButton = (ImageButton) findViewById(R.id.viewHistoryButton);
		// this line reads and stores database to phone, uncomment to store.
		//addFoodFromFile(this, dh);

		new SyncMealItems().execute();
		new GetDesiredScores().execute();
		
		addFoodButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(MainActivity.this,
						SelectMealTypeActivity.class);
				startActivity(intent);
			}
		});
		viewHistoryButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent intent = new Intent(MainActivity.this,
						HistoryLogActivity.class);
				startActivity(intent);

			}
		});
	}

	private void testCount(DatabaseHandler dh) {
		int rows = dh.getNumRows();
		Log.d("mydebug", "num rows: " + rows);
	}

	private void testRemoveFood(DatabaseHandler dh) {
		int rows = dh.getNumRows();
		Log.d("mydebug", "num rows: " + rows);

		int affected = dh.removeFood("bar");
		Log.d("mydebug", "affected: " + affected);
		Food food1 = dh.getFood("bar");
		if (food1 != null) {
			Log.d("mydebug", food1.toString());
		} else {
			Log.d("mydebug", "null");
		}
	}

	private void testMatches(DatabaseHandler dh) {

		/*
		 * List<Food> matches = new ArrayList<Food>();
		 * dh.getFoodMatches("KRAFT",matches);
		 * Log.d("mydebug","num matches: "+matches.size());
		 * 
		 * Log.d("mydebug",matches.get(0).toString());
		 * Log.d("mydebug",matches.get(1).toString());
		 */
		Food food = dh.getFood(2);
		Log.d("mydebug", food.toString());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void addFoodFromFile(Context context, DatabaseHandler dh) {

		Scanner scan = null;
		Log.d("mydebug", "inserting from file");
		try {
			scan = new Scanner(context.getAssets().open("USDAfood.tsv"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		scan.nextLine();

		while (scan.hasNext()) {

			String[] row = scan.nextLine().split("\t");
			Food food = new Food(row);
			Log.d("mydebug", "new food: " + food);
			dh.addFood(food);
		}

	}
}
