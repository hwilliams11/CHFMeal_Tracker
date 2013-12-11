package com.example.chfmeal_tracker;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {

	private static final String url_get_desired_scores = "http://hit1.nimbus.cip.gatech.edu/chf_meal_tracker/get_desired_scores.php";
	//private static final String url_get_desired_scores = "http://10.0.2.2/chf_meal_tracker/get_desired_scores.php";
	private Button addFoodButton;
	private Button viewHistoryButton;
	private Activity act;
	private TextView calorie_budget = null;
	private TextView sodium_budget = null;
	private TextView actual_calories = null;
	private TextView actual_sodium = null;
	private ImageView calorie_img = null;
	private TextView calorie_togo = null;
	private TextView sodium_togo = null;
	private TextView calorie_desc = null;
	private TextView sodium_desc = null;
	private ImageView sodium_img = null;
	private final int default_patientID = 3;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// this line deletes database when uncommented
		// deleteDatabase("CHFMealTrackerDB");
		DatabaseHandler dh = DatabaseHandler.getInstance(this);
		addFoodButton = (Button) findViewById(R.id.addFood);
		viewHistoryButton = (Button) findViewById(R.id.ShowHistory);
		calorie_budget = (TextView) findViewById(R.id.desired_calories);
		sodium_budget = (TextView) findViewById(R.id.desired_sodium);
		calorie_togo = (TextView) findViewById(R.id.calorie_to_go);
		sodium_togo = (TextView) findViewById(R.id.sodium_to_go);
		calorie_desc = (TextView) findViewById(R.id.calorie_description);
		sodium_desc = (TextView) findViewById(R.id.sodium_description);
		actual_calories = (TextView) findViewById(R.id.actual_calories);
		actual_sodium = (TextView) findViewById(R.id.actual_sodium);
		calorie_img = (ImageView) findViewById(R.id.calorie_arrow);
		sodium_img = (ImageView) findViewById(R.id.sodium_arrow);
		// this line reads and stores database to phone, uncomment to store.
		//addFoodFromFile(this, dh);

		new SyncMealItems().execute();
		new GetDesiredScores()
				.execute(url_get_desired_scores);

		addFoodButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(MainActivity.this,
						SelectMealTypeActivity.class);
				startActivity(intent);
			}
		});
		viewHistoryButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent intent = new Intent(MainActivity.this,
						HistoryTabActivity.class);
				startActivity(intent);

			}
		});
	}
	@Override
	protected void onRestart() {
		
		super.onRestart();
		new GetDesiredScores()
		.execute(url_get_desired_scores);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void addFoodFromFile(Context context, DatabaseHandler dh) {

		Scanner scan = null;
		Log.d("mydebug", "inserting from file");
		try {
			scan = new Scanner(context.getAssets().open("USDAfood.tsv"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		scan.nextLine();

		while (scan.hasNext()) {

			String[] row = scan.nextLine().split("\t");
			Food food = new Food(row);
			Log.d("mydebug", "new food: " + food);
			dh.addFood(food);
		}

	}
	private class GetDesiredScores extends AsyncTask<String, Void, String> {
		
		boolean in_local=false;
		@Override
		protected String doInBackground(String... urls) {
			// try to get the desired score for that day from db
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String date = dateFormat.format(new Date());
			Score score = DatabaseHandler.getInstance().getDesiredScore(date);
			Log.d("mydebug","date: "+date);
			
			if (score == null) {
				return POST(urls[0]);
			} else {
				in_local=true;
				return "{" + "'calorie_budget':" + score.ideal_calorie
						+ ",'sodium_budget':" + score.ideal_sodium + "}";
			}

		}

		protected String POST(String url) {
			// Create the HTTP request
			HttpParams httpParameters = new BasicHttpParams();
			HttpClient httpclient = new DefaultHttpClient(httpParameters);
			HttpPost httppost = new HttpPost(url);
			ArrayList<NameValuePair> keyValuePairs = new ArrayList<NameValuePair>();
			keyValuePairs.add(new BasicNameValuePair("patientID",
					default_patientID + ""));
			try {
				httppost.setEntity(new UrlEncodedFormEntity(keyValuePairs));
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			HttpResponse response;
			try {
				response = httpclient.execute(httppost);
				HttpEntity entity = response.getEntity();
				String result = EntityUtils.toString(entity);

				return result;

			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}

		// onPostExecute displays the results of the AsyncTask.
		@Override
		protected void onPostExecute(String result) {
			// Create a JSON object from the request response
			Log.d("dbdebug", "result: "+result);
			JSONObject jsonObject;
			if (result != null) {
				try {
					jsonObject = new JSONObject(result);
					// Retrieve the data from the JSON object
					double calories = jsonObject.getDouble("calorie_budget");
					double sodium = jsonObject.getDouble("sodium_budget");
					
					calorie_budget.setText((int)calories + "");
					sodium_budget.setText((int)sodium + "");
					// get actual score
					SimpleDateFormat dateFormat = new SimpleDateFormat(
							"yyyy-MM-dd");
					HashMap<String, Double> actual_scores = DatabaseHandler
							.getInstance().getActualScores(
									dateFormat.format(new Date()));
					double act_calories = actual_scores.get("actual_calories");
					double act_sodium = actual_scores.get("actual_sodium");
					actual_calories.setText( (int)act_calories+ "/");
					actual_sodium.setText( (int)act_sodium+"/");
					
					if (act_calories > calories) {
						calorie_img.setImageResource(R.drawable.red_arrow);
						calorie_togo.setText((int)(act_calories - calories) + "");
						calorie_desc.setText("calories above budget");

					} else {
						calorie_img.setImageResource(R.drawable.green_arrow);
						calorie_togo.setText((int)(calories - act_calories) + "");
						calorie_desc.setText("calories under budget");
					}

					if (act_sodium > sodium) {
						sodium_img.setImageResource(R.drawable.red_arrow);
						sodium_togo.setText((int)(act_sodium - sodium) + "");
						sodium_desc.setText("mg sodium above budget");

					} else {
						sodium_img.setImageResource(R.drawable.green_arrow);
						sodium_togo.setText((int)(sodium - act_sodium) + "");
						sodium_desc.setText("mg sodium under budget");
					}

					if( !in_local ){
						DatabaseHandler.getInstance().updateDesiredScore(
							dateFormat.format(new Date()), calories, sodium);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
	}
}
