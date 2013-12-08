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

public class GetDesiredScores extends AsyncTask<String, String, String> {

	private static final String TAG_SUCCESS = "success";
	private static final String TAG_NDB_No = "_NDB_No";

	private DatabaseHandler dh;
	private static String url_get_desired_scores = "http://10.0.2.2/chf_meal_tracker/get_desired_scores.php";

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

		String out = TestScores.test();
		test2(out);
		Log.d("mydebug", "made desired scores request");
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

	public void test2(String json) {
		// String json = "{\"message\":\"This is a message\"}";

		HttpClient httpClient = new DefaultHttpClient();

		try {
			HttpPost request = new HttpPost(url_get_desired_scores);
			StringEntity params = new StringEntity("message=" + json);
			request.addHeader("content-type",
					"application/x-www-form-urlencoded");
			request.setEntity(params);
			HttpResponse response = httpClient.execute(request);
			JSONArray jsonArray = getJSON(response);
			Log.d("mydebug","response:\n"+response);
			Log.d("mydebug", "JSONArray\n" + jsonArray);

			updateDesiredScores(jsonArray);


		} catch (Exception ex) {
			// handle exception here
			Log.d("mydebug","Exception thrown");
		} finally {
			httpClient.getConnectionManager().shutdown();
		
		}
	}

	private void updateDesiredScores(JSONArray array) {

		for (int i = 0; i < array.length(); i++) {

			JSONObject obj = null;

			try {
				obj = array.getJSONObject(i);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			try {
				String date = obj.getString("date");
				double calories = obj.getDouble("calories");
				double sodium = obj.getDouble("sodium");
				dh.updateDesiredScore(date,calories,sodium);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

		}

	}

	private JSONArray getJSON(HttpResponse response) {

		InputStream is = null;
		try {
			is = response.getEntity().getContent();
		} catch (IllegalStateException e1) {
			Log.d("mydebug","1");
			e1.printStackTrace();
		} catch (IOException e1) {
			Log.d("mydebug","2");
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
			Log.d("mydebug","3");
			Log.e("Buffer Error", "Error converting result " + e.toString());
		}

		// try parse the string to a JSON object
		Log.d("mydebug","json here:"+json);
		try {
			jarr = new JSONArray(json);
		} catch (JSONException e) {
			Log.d("mydebug","4");
			Log.e("JSON Parser", "Error parsing data " + e.toString());
		}
		return jarr;
	}
}