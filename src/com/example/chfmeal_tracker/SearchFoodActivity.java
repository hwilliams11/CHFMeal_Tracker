package com.example.chfmeal_tracker;

import java.util.ArrayList;
import java.util.List;

import com.example.chfmeal_tracker.Meal.MealType;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class SearchFoodActivity extends Activity {

	private Button searchButton;
	private EditText searchText;
	private DatabaseHandler dh;
	private ListView lv;
	private MatchListAdapter adapter;
	private TextView mealTypeTextView;
	private List<Food> matches;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_food);
		
		searchText = (EditText)findViewById(R.id.searchFoodEditText);
		searchButton = (Button)findViewById(R.id.searchFoodButton);
		mealTypeTextView = (TextView)findViewById(R.id.mealTypeText);
		
		dh = DatabaseHandler.getInstance();
		lv = (ListView)findViewById(R.id.matchesListView);
		matches = new ArrayList<Food>();
		
		int meal = getIntent().getExtras().getInt("mealType");
		String text="";
		
		switch( meal ){
		
			case 0:text = "Select breakfast item";break;
			case 1:text = "Select lunch item";break;
			case 2:text = "Select dinner item";break;
			case 3:text = "Select snack item";break;
			default:text = "Select meal item";meal=3;break;
		}
		mealTypeTextView.setText(text);
		adapter = new MatchListAdapter(this,matches);
		lv.setAdapter(adapter);
		
		
		searchButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				final String search = searchText.getText().toString();
				Log.d("check","search: "+search);
				
				new AsyncTask<String, Void, String>() {
					ProgressDialog pDialog;
					@Override
					protected void onPreExecute() {
						 
					}
					protected String doInBackground(String... params) {
						
						dh.getFoodMatches(search,matches);
						return null;
					}
					protected void onPostExecute(String result) {
						
						adapter.notifyDataSetChanged();
					};
			         
			    }.execute();
			}
		});
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			
		    public void onItemClick(AdapterView parent, View v, int position, long id){
		       
		    	Food food = matches.get(position);
		    	Intent intent = new Intent(SearchFoodActivity.this,SetServingSizeActivity.class);
		    	Log.d("mydebug","mealType: "+getIntent().getExtras().getInt("mealType"));
				intent.putExtra("foodId", food.get_NDB_No());
				intent.putExtra("mealType", getIntent().getExtras().getInt("mealType"));
				startActivity(intent);
				matches.clear();
				adapter.notifyDataSetChanged();
				searchText.setText("");
		    }
		});
		
		//food_id = getIntent().getExtras().getInt("food_id");
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		matches.clear();
		adapter.notifyDataSetChanged();
		searchText.setText("");
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_food, menu);
		return true;
	}

}