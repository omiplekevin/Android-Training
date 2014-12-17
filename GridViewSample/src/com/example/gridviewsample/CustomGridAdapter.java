package com.example.gridviewsample;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomGridAdapter extends BaseAdapter{

	Context context;
	int[] imageRes;
	
	public CustomGridAdapter(Context context, int[] imageRes) {
		this.context = context;
		this.imageRes = imageRes;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return imageRes.length;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int arg0, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		
		if(convertView == null) {
			holder = new ViewHolder();
			convertView = View.inflate(context, R.layout.custom_grid_content, null);
			
			holder.imageView = (ImageView)convertView.findViewById(R.id.imageView1);
			holder.textView = (TextView)convertView.findViewById(R.id.textView1);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder)convertView.getTag();
		}
		
		holder.textView.setText("Cell " + arg0);
		holder.imageView.setImageResource(this.imageRes[arg0]);
		
		return convertView;
	}
	
	static class ViewHolder{
		ImageView imageView;
		TextView textView;
	}

}
