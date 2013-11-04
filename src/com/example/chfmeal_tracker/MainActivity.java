package com.example.chfmeal_tracker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

    private Button addFoodButton; 
    private Activity act;
    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        
        DatabaseHandler dh = DatabaseHandler.getInstance(this);
        addFoodButton = (Button)findViewById(R.id.add_food_button);
        
        //testMatches(dh);
        //addFoodFromFile(this,dh);
        //testRemoveFood(dh);        
        
        addFoodButton.setOnClickListener(new View.OnClickListener() {
		
		
			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent(MainActivity.this,SelectMealTypeActivity.class);
				startActivity(intent);
			}
		});
    }


	private void testRemoveFood(DatabaseHandler dh) {
		int rows = dh.getNumRows();
        Log.d("check","num rows: "+rows);
        
        int affected = dh.removeFood("bar");
        Log.d("check","affected: "+affected);
        Food food1 = dh.getFood("bar");
        if( food1 != null ){
        	Log.d("check",food1.toString());
        }else{
        	Log.d("check","null");
        }
	}


	private void testMatches(DatabaseHandler dh) {
		
		List<Food> matches = new ArrayList<Food>(); 
		dh.getFoodMatches("KRAFT",matches);
        Log.d("check","num matches: "+matches.size());
        
        Log.d("check",matches.get(0).toString());
        Log.d("check",matches.get(1).toString());
	}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    private void addFoodFromFile(Context context, DatabaseHandler dh){
    	
    	Scanner scan = null;
    	
    	try {
			scan = new Scanner(context.getAssets().open("USDAfood.tsv"));
		} catch (IOException e) {e.printStackTrace();}
    	
    	scan.nextLine();
    	
    	while( scan.hasNext() ){
    		
    		String[]row = scan.nextLine().split("\t");
    		Food food = new Food(row);
    		dh.addFood(food);
    	}
    	
    }
}
