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

public class SearchFoodActivity extends Activity {

	Button searchButton;
	EditText searchText;
	DatabaseHandler dh;
	ListView lv;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_food);
		
		searchText = (EditText)findViewById(R.id.searchFoodEditText);
		searchButton = (Button)findViewById(R.id.searchFoodButton);
		dh = DatabaseHandler.getInstance();
		lv = (ListView)findViewById(R.id.matchesListView);
		final List<Food> matches = new ArrayList<Food>();
		
		lv.setAdapter(new MatchListAdapter(this,matches));
		
		searchButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				String search = searchText.getText().toString();
				Log.d("check","search: "+search);
				dh.getFoodMatches(search,matches);
			}
		});
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			
		    public void onItemClick(AdapterView parent, View v, int position, long id){
		       
		    	Food food = matches.get(position);
		    	Intent intent = new Intent(SearchFoodActivity.this,SetServingSizeActivity.class);
				intent.putExtra("foodId", food.get_NDB_No());
				startActivity(intent);
		    }
		});
		
		//food_id = getIntent().getExtras().getInt("food_id");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_food, menu);
		return true;
	}

}