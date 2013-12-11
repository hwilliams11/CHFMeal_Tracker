package com.example.chfmeal_tracker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.DialogFragment;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.GraphViewSeries.GraphViewSeriesStyle;
import com.jjoe64.graphview.LineGraphView;

public class ShowScoreVisualization extends Activity {

	
	private static final int n = 1000;
	private static final int VIEW_PORT_WIDTH=20;
	private static int start;
	private DatabaseHandler dh;
	private static String dates[];
	static List<HashMap<String, String>> scores=null;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_score_visualization);
		
		getScoreData();
		
	}

	private void getScoreData() {
		start = 0;
		dh = DatabaseHandler.getInstance();
		scores = dh.getAllScores();
		Log.d("mydebug","scores: "+scores);
		
		
		/*GraphViewData[]actualCalories = new GraphViewData[scores.size()];
		GraphViewData[]idealCalories = new GraphViewData[scores.size()];
		GraphViewData[]actualSodium = new GraphViewData[scores.size()];
		GraphViewData[]idealSodium = new GraphViewData[scores.size()];
		dates[] = new String[scores.size()]*/
		
		GraphViewData[]actualCalories = new GraphViewData[n];
		GraphViewData[]idealCalories = new GraphViewData[n];
		GraphViewData[]actualSodium = new GraphViewData[n];
		GraphViewData[]idealSodium = new GraphViewData[n];
		
		
		if( scores != null && scores.size()>0 ){
		
			//for(int i=0;i<scores.size();i++){
			for(int i=0;i<n;i++){	
				
				//HashMap<String,String> map = scores.get(i);
			/*	
				actualCalories[i] = new GraphViewData( i, Double.parseDouble( map.get(DatabaseHandler.ACTUAL_CALORIES) ) );
				idealCalories[i] = new GraphViewData( i, Double.parseDouble( map.get(DatabaseHandler.IDEAL_CALORIES) ) );
				actualSodium[i] = new GraphViewData( i, Double.parseDouble( map.get(DatabaseHandler.ACTUAL_SODIUM) ) );
				idealSodium[i] = new GraphViewData( i, Double.parseDouble( map.get(DatabaseHandler.IDEAL_CALORIES) ) );
				dates[i]=map.get(DatabaseHandler.KEY_CREATION_DATE);
				*/
				actualCalories[i] = new GraphViewData( i,20*Math.random() );
				idealCalories[i] = new GraphViewData( i,20*Math.random() );
				actualSodium[i] = new GraphViewData( i,20*Math.random() );
				idealSodium[i] = new GraphViewData( i,20*Math.random() );
				
			}
			//sort the dates;
			/*
			Arrays.sort(dates,new Comparator<String>(){

				@Override
				public int compare(String arg0, String arg1) {
					Date date1=null,date2=null;
					try {
						date1 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(arg0);
						date2 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(arg1);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					if( date1!=null && date2!=null )
						return date1.compareTo(date2);
					else
						return 0;
				}
				
			});
			*/
			GraphViewSeries actualCaloriesSeries = new GraphViewSeries("Actual", new GraphViewSeriesStyle(Color.GREEN, 3), actualCalories);
        	GraphViewSeries idealCaloriesSeries =new GraphViewSeries("Ideal", new GraphViewSeriesStyle(Color.RED, 3), idealCalories);
        	GraphViewSeries actualSodiumSeries = new GraphViewSeries("Actual", new GraphViewSeriesStyle(Color.GREEN, 3), actualCalories);
        	GraphViewSeries idealSodiumSeries =new GraphViewSeries("Ideal", new GraphViewSeriesStyle(Color.RED, 3), idealCalories);
        
        	GraphView graphView1 = new LineGraphView(  
                    this // context  
                    , "Calories History" // heading  
            );  
            graphView1.addSeries(actualCaloriesSeries); // data
            graphView1.addSeries(idealCaloriesSeries); // data
            
            GraphView graphView2 = new LineGraphView(  
                    this // context  
                    , "Sodium History" // heading  
            );  
            graphView2.addSeries(actualSodiumSeries); // data
            graphView2.addSeries(idealSodiumSeries); // data
            
            graphView1.setViewPort(start, VIEW_PORT_WIDTH);  
            graphView1.setScrollable(true);    
            graphView1.setScalable(true);
            graphView2.setViewPort(start, VIEW_PORT_WIDTH);  
            graphView2.setScrollable(true);    
            graphView2.setScalable(true);
            
        	LinearLayout layout = (LinearLayout) findViewById(R.id.graph1);  
            layout.addView(graphView1);
            
            layout = (LinearLayout) findViewById(R.id.graph2);  
            layout.addView(graphView2); 
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_score_visualization, menu);
		return true;
	}

	public void showDatePickerDialog(View v) {
	    DialogFragment newFragment = new DatePickerFragment();
	    newFragment.show(getFragmentManager(), "datePicker");
	}

	public static void updateDate(String date) {

		Log.d("mydebug","Date: "+date);
		if( dates!=null && dates.length!= 0 ){

			Log.d("mydebug","Date check 1");
			int index = Arrays.binarySearch(dates, date);
			//-index-1
			if( index<0 ){
			
				Log.d("mydebug","index="+index+" would be here: "+(-1*(index+1)));
				index = -1*(index+1);
			}
			if( index < dates.length )
				start = index;
			
		}
		else{
			if( scores!= null && scores.size()!= 0 ){
			
				Log.d("mydebug","Date check 2");
				for(int i=0;i<scores.size();i++){
					
					Log.d("mydebug","Date1: "+scores.get(i).get(DatabaseHandler.KEY_CREATION_DATE)+" Date2: "+date);
					if( scores.get(i).get("creation_date").equals(date) ){
						Log.d("mydebug","match: i="+i+" creation_date: "+date);
						start = i;
					}
				}
			}
		}
		
	}
}
