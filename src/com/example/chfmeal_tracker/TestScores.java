package com.example.chfmeal_tracker;



import android.util.Log;

import com.google.gson.Gson;

public class TestScores {

	static class Data{
		String patientID;
		String date;
		Data(String patientID,String date){
			this.patientID=patientID;
			this.date=date;
		}
	}
	public static String test(){
		
		Data [] data = new Data[]{ 	new Data("0","2013-12-12"),
									new Data("0","2013-12-03"),
									new Data("0","2013-12-04"),
									new Data("0","2013-12-05"),};
		
		Gson gson = new Gson();
		String out = gson.toJson(data);
		Log.d("mydebug","date test:\n" + out);
		return out;
	}

}
