package com.example.chfmeal_tracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class LoadAllProducts extends AsyncTask<String, String, String> {

	// Progress Dialog
	private ProgressDialog pDialog;

	// Creating JSON Parser object
	JSONParser jParser = new JSONParser();

	ArrayList<HashMap<String, String>> productsList;

	// url to get all products list
	private static String url_all_products = "http://10.0.2.2/android_connect/get_all_products.php";

	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_PRODUCTS = "products";
	private static final String TAG_PID = "pid";
	private static final String TAG_NAME = "name";

	// products JSONArray
	JSONArray products = null;


	/**
	 * Before starting background thread Show Progress Dialog
	 * */
	@Override
	protected void onPreExecute() {
		Log.d("mydebug","here1");
	}

	/**
	 * getting All products from url
	 * */
	protected String doInBackground(String... args) {
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		// getting JSON string from URL
		Log.d("mydebug","here");
		JSONObject json = jParser.makeHttpRequest(url_all_products, "GET", params);
		if( json==null ) Log.d("mydebug","NULL");
		// Check your log cat for JSON reponse
		Log.d("All Products: ", json.toString());

		try {
			// Checking for SUCCESS TAG
			int success = json.getInt(TAG_SUCCESS);

			if (success == 1) {
				// products found
				// Getting Array of Products
				products = json.getJSONArray(TAG_PRODUCTS);

				// looping through All Products
				for (int i = 0; i < products.length(); i++) {
					JSONObject c = products.getJSONObject(i);

					// Storing each json item in variable
					String id = c.getString(TAG_PID);
					String name = c.getString(TAG_NAME);

					// creating new HashMap
					HashMap<String, String> map = new HashMap<String, String>();

					// adding each child node to HashMap key => value
					map.put(TAG_PID, id);
					map.put(TAG_NAME, name);

					// adding HashList to ArrayList
					productsList.add(map);
				}
				Log.d("mydebug","here2");
				Log.d("mydebug",productsList.toString());
			} else {

			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * After completing background task Dismiss the progress dialog
	 * **/
	protected void onPostExecute(String file_url) {
		// dismiss the dialog after getting all products
		Log.d("mydebug","here3");
	}

	
}