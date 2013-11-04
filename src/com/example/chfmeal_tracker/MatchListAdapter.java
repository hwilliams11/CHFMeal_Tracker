package com.example.chfmeal_tracker;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MatchListAdapter extends BaseAdapter {

	private List<Food> matches;
	private Context context;
	private ViewHolder mHolder = null;
	private LayoutInflater mInflater = null;
	
	private final class ViewHolder{
		TextView nameTextView;
	}

	public MatchListAdapter(Context context,List<Food> matches){
		
		this.context = context;
		this.matches = matches;
		mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
	}
	public int getCount() {
		
		return matches.size();
	}
	public Object getItem(int index) {
		
		return matches.get(index);
	}
	public long getItemId(int index) {
		
		return index;
	}
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if( convertView==null ){
			mHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.match_list_item, null);
			convertView.setTag(mHolder);
		}else{
			mHolder = (ViewHolder)convertView.getTag();
		}
		mHolder.nameTextView = (TextView)convertView.findViewById(R.id.food_name_text_view);
		mHolder.nameTextView.setText(matches.get(position).get_food_name());
		
		return convertView;
	}

}
