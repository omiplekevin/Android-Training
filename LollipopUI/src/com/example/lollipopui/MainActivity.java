package com.example.lollipopui;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.PhoneLookup;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

public class MainActivity extends Activity {

	ListView listView;
	ArrayList<ContactModel> contactList;
	ListViewAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		String[] items = new String[100];
		for (int i = 0; i < 100; i++) {
			items[i] = "Items " + i;
		}
		contactList = new ArrayList<ContactModel>();

		getContacts();

		listView = (ListView) findViewById(R.id.listView);
		adapter = new ListViewAdapter(getApplicationContext(),
				contactList);
		listView.setAdapter(adapter);
		
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				ContactModel model = adapter.getItem(position);
				AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
				builder.setTitle(model.contactName);
				builder.setMessage(model.contactNumber);
				AlertDialog dialog = builder.create();
				dialog.show();
				return true;
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void getContacts() {

		Cursor phones = getContentResolver().query(
				ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null,
				null, null);
		ContactModel model = null;
		while (phones.moveToNext()) {
			model = new ContactModel();
			String name = phones
					.getString(phones
							.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
			model.contactName = name;
			String phoneNumber = phones
					.getString(phones
							.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
			model.contactNumber = phoneNumber;

			contactList.add(model);

		}
		phones.close();

	}

	/**
	ADDED COMMENT FOR THIS PARTICULAR LINE!
	*/
}
