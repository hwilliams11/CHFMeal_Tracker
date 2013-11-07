package com.example.chfmeal_tracker;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class HistoryLogActivity extends Activity {
	DatabaseHandler dh;
	ListView lv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.history_log);
		
		dh = DatabaseHandler.getInstance();
		final List<Meal> mealList = new ArrayList<Meal>();
		
		lv.setAdapter(new MatchListAdapter(this,mealList));
		
		mealList = dh.getMeals();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.history_log, menu);
		return true;
	}
	
	//reference is SelectMealTypeActivity
	
	//==============================================================

	
	
	
		
	}

}










}
