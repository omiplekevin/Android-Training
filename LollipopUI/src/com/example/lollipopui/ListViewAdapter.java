package com.example.lollipopui;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListViewAdapter extends BaseAdapter{
	
	ArrayList<ContactModel> contacts;
	Context context;
	
	public ListViewAdapter(Context context, ArrayList<ContactModel> contacts) {
		this.contacts = contacts;
		this.context = context;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return contacts.size();
	}

	@Override
	public ContactModel getItem(int position) {
		// TODO Auto-generated method stub
		return contacts.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if(convertView == null) {
			holder = new ViewHolder();
			
			convertView = View.inflate(context, R.layout.listitem_view, null);
			holder.contactName = (TextView)convertView.findViewById(R.id.contactName);
			holder.contactNumber = (TextView)convertView.findViewById(R.id.contactNumber);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder)convertView.getTag();
		}
		
		holder.contactName.setText(contacts.get(position).contactName);
		holder.contactNumber.setText(contacts.get(position).contactNumber);
		
		return convertView;
	}
	
	static class ViewHolder{
		TextView contactName;
		TextView contactNumber;
	}

}
