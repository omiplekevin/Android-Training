package com.jmcandroid.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jmcandroid.listviewactivity.MainActivity;
import com.jmcandroid.listviewactivity.R;

public class BasicListViewAdapter extends BaseAdapter{
	
	private String[] items;
	private Context context;
	
	public BasicListViewAdapter(Context context, String[] items) { //constructor
		this.items = items;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.items.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;
		if(convertView == null) {
			viewHolder = new ViewHolder();
			convertView = View.inflate(this.context, R.layout.listview_item, null);
			
			viewHolder.textView = (TextView)convertView.findViewById(R.id.textView1);
			viewHolder.button = (Button)convertView.findViewById(R.id.button1);
			
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder)convertView.getTag();
		}
		
		viewHolder.textView.setText(this.items[position]);
		viewHolder.button.setText(position + "");
		viewHolder.button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setTitle("Alert!");
				builder.setMessage("What do you want to do with this contact?");
				
				AlertDialog dialog = builder.create();
				dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel",
						new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});
				dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Share via G+",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								Toast.makeText(context, "Toast", Toast.LENGTH_LONG).show();
							}
						});
				dialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Send via SMS",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								Toast.makeText(context, "Hey!", Toast.LENGTH_LONG).show();
							}
						});
				dialog.show();
			}
		});
		
		return convertView;
	}
	
	static class ViewHolder{
		TextView textView;
		Button button;
	}

}
