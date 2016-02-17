package com.example.omiplekevin.ndudemoapp;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AddItemDialogFragment.NewItemAddedListener{

    ListView itemListView;
    ItemListAdapter adapter;
    List<String> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * we bind itemListView instance of ListView
         */
        itemListView = (ListView) findViewById(R.id.itemsListView);

        /**
         * lets instantiate an array of String that will be added to the ListView
         */
        items = new ArrayList<>();

        /**
         * LISTVIEW ADAPTER helps you populate your ListView with data that you provided.
         * It also allows you to customize the look and behavior of your List Item particularly.
         *
         * lets instantiate the adapter and pass the current application context and the ArrayList (items) as a reference for items to be listed
         */
        adapter = new ItemListAdapter(this, items);

        /**
         * now, we set the adapter to the ListView instance that made earlier
         */
        itemListView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFinishedAdding(String newItem) {
        Log.e("ADDING ITEM", newItem);
        /**
         * add the new item to the ArrayList
         */
        items.add(newItem);

        /**
         * lets notify the adapter that its current reference of ArrayList is being updated, hence we call adapter.notifyDataSetChanged();
         */
        adapter.notifyDataSetChanged();
    }

    /**
     * this is the function that has been referenced by the Button in the layout during onClick
     * @param v the widget reference
     */
    public void addItem(View v) {
        /**
         * we acquire current FragmentManager since we're using DialogFragment
         */
        FragmentManager fragmentManager = getFragmentManager();
        /**
         * we instantiate the Custom DialogFragment
         */
        AddItemDialogFragment addItemDialogFragment = new AddItemDialogFragment();
        /**
         * now, we'll make the DialogFragment visible to the user, passing the FragmentManager instance and the dialog's Title
         */
        addItemDialogFragment.show(fragmentManager, "Add New Item");
    }
}
