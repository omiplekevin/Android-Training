package com.omiplekevin.android.customspinnerview;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AutoCompleteTextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    AutoCompleteTextView autoCompleteTextView;
    ArrayList<PlayerModel> playerModelList;
    CustomSpinnerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.customSpinner);
        playerModelList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            PlayerModel q = new PlayerModel();
            q._PLAYERNAME = "PLAYER: " + i;
            q._TOPIC1 = "TOPIC1: " + i;
            q._TOPIC2 = "TOPIC2: " + i;
            q._TOPIC3 = "TOPIC3: " + i;
            q._TOPIC4 = "TOPIC4: " + i;
            playerModelList.add(q);
            Log.e("TALLY", q._PLAYERNAME + " " +
                    q._TOPIC1 + " " +
                    q._TOPIC2 + " " +
                    q._TOPIC3 + " " +
                    q._TOPIC4);
        }

        adapter = new CustomSpinnerAdapter(MainActivity.this, R.layout.dropdown_custom_view, playerModelList);
        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.setThreshold(256);
        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.e("TEXTCHANGE", s.toString());
                adapter = new CustomSpinnerAdapter(MainActivity.this, R.layout.dropdown_custom_view, filterName(s));
                autoCompleteTextView.setAdapter(adapter);
                autoCompleteTextView.showDropDown();
            }
        });
    }

    private ArrayList<PlayerModel> filterName(CharSequence charSequence) {
        ArrayList<PlayerModel> filtered = new ArrayList<>();
        for (PlayerModel player : playerModelList) {
            if (player._PLAYERNAME.toLowerCase().contains(charSequence.toString().toLowerCase())) {
                Log.e("FILTER", charSequence.toString() + " " + player._PLAYERNAME);
                filtered.add(player);
            }
        }

        return filtered;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class PlayerModel {
        public String _PLAYERNAME;
        public String _TOPIC1;
        public String _TOPIC2;
        public String _TOPIC3;
        public String _TOPIC4;
    }
}
