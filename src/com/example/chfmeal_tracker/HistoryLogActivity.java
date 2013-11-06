package com.example.chfmeal_tracker;

import android.app.Activity;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class HistoryLogActivity extends Activity{
    private Activity act;
	DatabaseHandler dh;
	ListView lv;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.history_log);
	
/*
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_food);
		
		searchText = (EditText)findViewById(R.id.search_food_edit_text);
		searchButton = (Button)findViewById(R.id.search_food_button);
		dh = DatabaseHandler.getInstance();
		lv = (ListView)findViewById(R.id.matchesListView);
		final List<Food> matches = new ArrayList<Food>();
		
		lv.setAdapter(new MatchListAdapter(this,matches));
		
		searchButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				String search = searchText.getText().toString();
				dh.getFoodMatches(search,matches);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_food, menu);
		return true;
	}

}

 */
}
