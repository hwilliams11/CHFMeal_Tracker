package com.example.chfmeal_tracker;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class HistoryListAdapter extends BaseAdapter {

	private List<Meal> history;
	private Context context;
	private ViewHolder mHolder = null;
	private LayoutInflater mInflater = null;
	private static DatabaseHandler dh;
	
	private final class ViewHolder{
		TextView textView;
	}

	public HistoryListAdapter(Context context,List<Meal> history,DatabaseHandler dh){
		
		this.context = context;
		this.history = history;
		this.dh = dh;
		mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
	}
	public int getCount() {
		
		return history.size();
	}
	public Object getItem(int index) {
		
		return history.get(index);
	}
	public long getItemId(int index) {
		
		return index;
	}
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if( convertView==null ){
			mHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.history_list_item, null);
			convertView.setTag(mHolder);
		}else{
			mHolder = (ViewHolder)convertView.getTag();
		}
	
		Meal meal = history.get(position);
		Food food = dh.getFood(meal.get_NDB_No());
		String name = food.get_food_name();
		String date = meal.get_Date();
		mHolder.textView = (TextView)convertView.findViewById(R.id.historyLogTextView);
		mHolder.textView.setText(name+" "+date);

		
		return convertView;
	}

}
