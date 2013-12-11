package com.example.chfmeal_tracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;


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



/*
public class HistoryLogActivity extends Activity {
 
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
	DatabaseHandler dh;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_log);
 
        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);
 
        // preparing list data
        prepareListData();
 
        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
 
        // setting list adapter
        expListView.setAdapter(listAdapter);
    }
 
    
     // Preparing the list data
     
    
    //get data here
    
    //get all distinct dates from the table where... [selection clause]
    //for each day, retrieve data where date == each day AND [selection clause]
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        
        
		dh = DatabaseHandler.getInstance();
		final ArrayList<Meal> history = new ArrayList<Meal>();
		
		//HistoryListAdapter adapter = new HistoryListAdapter(this,history,dh);
		
		dh.getMeals(history);
		//lv = (ListView)findViewById(R.id.historyListView);
		final ArrayList<String> mealDates = new ArrayList<String>();
		//get arraylist of mealDates from history table
		
		String latestDate = (history.get(0)).get_Date();
		
		//get an arraylist of all distinct dates
		mealDates.add(latestDate);
		for(int i = 0; i < history.size(); i++) {
			if(!latestDate.equals((history.get(0)).get_Date())) {
				mealDates.add(latestDate);
			}
		}
		
		//the header are all the dates
		listDataHeader = mealDates;
		
		//HistoryListAdapter adapter = new HistoryListAdapter(this,history,dh);
		//lv.setAdapter(adapter);
		
		//create an array of List <meal>
		
		ArrayList<List<String>> allMeals = new ArrayList<List<String>>(listDataHeader.size());
		mealDates.add(latestDate);
		
		
		for(int i = 0; i < listDataHeader.size(); i++) {
			
			//generate child list for each date
			String currentDate = listDataHeader.get(i);
			Log.d("mydebug", "Searching in history for date records matching " + currentDate);
			
			for(int j = 0; j < history.size(); j++) 
				//means current record has the same date
				if(currentDate.equals((history.get(j)).get_Date())) {
					//add child data to list
					allMeals.get(i).add(history.get(j).getFood_name());
			}
			
			listDataChild.put(listDataHeader.get(i),allMeals.get(i) ); // Header, Child data		
			
		}
		
    }
}

*/
/*
 * 
 *


//Arraylist of meal objects fetched.
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
	
		
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.history_log, menu);
		return true;
	}

}
*/