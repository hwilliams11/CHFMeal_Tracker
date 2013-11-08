package com.example.chfmeal_tracker;

import java.util.ArrayList;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ListView;

public class HistoryLogActivity extends Activity {

	DatabaseHandler dh;
	ListView lv;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_history_log);

		dh = DatabaseHandler.getInstance();
		lv = (ListView)findViewById(R.id.historyListView);
		final ArrayList<Meal> history = new ArrayList<Meal>();
		
		HistoryListAdapter adapter = new HistoryListAdapter(this,history,dh);
		lv.setAdapter(adapter);
		dh.getMeals(history);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.history_log, menu);
		return true;
	}

}
