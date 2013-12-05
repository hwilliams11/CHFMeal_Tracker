package com.example.chfmeal_tracker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

public class SyncMealItems extends AsyncTask<String, String, String> {

	private static final String TAG_SUCCESS = "success";
	private static final String TAG_NDB_No = "_NDB_No";

	private DatabaseHandler dh;
	private static String url_sync_meals = "http://10.0.2.2/chf_meal_tracker/sync_meals.php";

	/**
	 * Before starting background thread Show Progress Dialog
	 * */
	@Override
	protected void onPreExecute() {
		/*
		 * super.onPreExecute(); pDialog = new
		 * ProgressDialog(EditProductActivity.this);
		 * pDialog.setMessage("Saving product ...");
		 * pDialog.setIndeterminate(false); pDialog.setCancelable(true);
		 * pDialog.show();
		 */
		dh = DatabaseHandler.getInstance();
	}

	/**
	 * Saving product
	 * */
	protected String doInBackground(String... args) {

		// get meal items from database that have not been sent
		List<Meal> meals = dh.getNewMeals();
		if (meals.size() > 0) {
			// send list to server for updating
			Gson gson = new Gson();
			String out = gson.toJson(meals);
			Log.d("mydebug", "out\n" + out);

			test2(out);

			Log.d("mydebug", "made request");
		}
		return null;

	}

	/**
	 * After completing background task Dismiss the progress dialog
	 * **/
	protected void onPostExecute(String file_url) {
		// dismiss the dialog once product updated
		// pDialog.dismiss();
	}

	public static HttpResponse makeRequest(String uri, String json) {
		try {
			HttpPost httpPost = new HttpPost(uri);

			StringEntity se = new StringEntity("message=" + json);
			httpPost.setEntity(se);
			return new DefaultHttpClient().execute(httpPost);

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void postData() {
		// Create a new HttpClient and Post Header
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(url_sync_meals);

		try {
			// Add your data
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("id", "12345"));
			nameValuePairs.add(new BasicNameValuePair("stringdata",
					"AndDev is Cool!"));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			// Execute HTTP Post Request
			HttpResponse response = httpclient.execute(httppost);

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
	}

	public void test() {
		String json = "{\"message\":\"This is a message\"}";

		HttpClient httpClient = new DefaultHttpClient();

		try {
			HttpPost request = new HttpPost(url_sync_meals);
			StringEntity params = new StringEntity("message=" + json);
			request.addHeader("content-type",
					"application/x-www-form-urlencoded");
			request.setEntity(params);
			HttpResponse response = httpClient.execute(request);

			// handle response here...

			System.out.println(org.apache.http.util.EntityUtils
					.toString(response.getEntity()));

		} catch (Exception ex) {
			// handle exception here
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
	}

	public void test2(String json) {
		// String json = "{\"message\":\"This is a message\"}";

		HttpClient httpClient = new DefaultHttpClient();

		try {
			HttpPost request = new HttpPost(url_sync_meals);
			StringEntity params = new StringEntity("message=" + json);
			request.addHeader("content-type",
					"application/x-www-form-urlencoded");
			request.setEntity(params);
			HttpResponse response = httpClient.execute(request);
			JSONArray jsonArray = getJSON(response);
			Log.d("mydebug", "JSONArray\n" + jsonArray.toString());

			updateSyncedMeals(jsonArray);

			Log.d("mydebug", "response: \n" + response.toString());
			// handle response here...

			System.out.println(org.apache.http.util.EntityUtils
					.toString(response.getEntity()));

		} catch (Exception ex) {
			// handle exception here
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
	}

	private void updateSyncedMeals(JSONArray array) {

		for (int i = 0; i < array.length(); i++) {

			JSONObject obj = null;

			try {
				obj = array.getJSONObject(i);
			} catch (JSONException e) {
				e.printStackTrace();
			}

			int success = 0;
			try {
				success = obj.getInt("success");
			} catch (JSONException e) {
				e.printStackTrace();
			}

			if (success == 1) {
				try {
					int _NDB_No = obj.getInt("_NDB_No");
					String _Date = obj.getString("_Date");
					int _Type = obj.getInt("_Type");
					dh.updateMeal(_NDB_No + "", _Date, _Type + "");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

	}

	private JSONArray getJSON(HttpResponse response) {

		InputStream is = null;
		try {
			is = response.getEntity().getContent();
		} catch (IllegalStateException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		String json = null;
		JSONArray jarr = null;

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			json = sb.toString();
			Log.d("mydebug", "response json:" + json);
		} catch (Exception e) {
			Log.e("Buffer Error", "Error converting result " + e.toString());
		}

		// try parse the string to a JSON object
		try {
			jarr = new JSONArray(json);
		} catch (JSONException e) {
			Log.e("JSON Parser", "Error parsing data " + e.toString());
		}
		return jarr;
	}
}