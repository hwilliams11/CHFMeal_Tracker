package com.example.chfmeal_tracker;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
 
public class HistoryTabActivity extends TabActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_tab);
         
        TabHost tabHost = getTabHost();
        
        TabSpec tab1 = tabHost.newTabSpec("View Log");
        TabSpec tab2 = tabHost.newTabSpec("View Graphs");
        
        
         
     // Set the Tab name and Activity
        // that will be opened when particular Tab will be selected
         tab1.setIndicator("View Log");
        // tab1.setContent(new Intent(this,HistoryLogActivity.class));
         tab1.setContent(new Intent(this,HistoryLogActivity.class));
         
         tab2.setIndicator("View Graphs");
         tab2.setContent(new Intent(this,ShowScoreVisualization.class));

         //tab3.setIndicator("Score Graph");
         //tab3.setContent(new Intent(this,DayActivity.class));
         
         /** Add the tabs  to the TabHost to display. */
         tabHost.addTab(tab1);
         tabHost.addTab(tab2);
        // tabHost.addTab(tab3);
 	}
         
       
}