package com.omiplekevin.android.customspinnerview;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by OMIPLEKEVIN on Feb 27, 2016.
 */
public class CustomSpinnerAdapter extends ArrayAdapter<MainActivity.PlayerModel> {

    Context context;
    List<MainActivity.PlayerModel> tallyList;
    int viewResourceLayout;

    public CustomSpinnerAdapter(Context context, int viewResourceId, ArrayList<MainActivity.PlayerModel> tallyList) {
        super(context, viewResourceId, tallyList);
        this.context = context;
        this.tallyList = tallyList;
        this.viewResourceLayout = viewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.e("DROPDOWN", position + "DROPDOWN");
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(this.context).inflate(viewResourceLayout, parent, false);
        TextView nameValue = (TextView) view.findViewById(R.id.nameValue);
        TextView topic1 = (TextView) view.findViewById(R.id.topic1);
        TextView topic2 = (TextView) view.findViewById(R.id.topic2);
        TextView topic3 = (TextView) view.findViewById(R.id.topic3);
        TextView topic4 = (TextView) view.findViewById(R.id.topic4);

        nameValue.setText(tallyList.get(position)._PLAYERNAME);
        topic1.setText(tallyList.get(position)._TOPIC1);
        topic2.setText(tallyList.get(position)._TOPIC2);
        topic3.setText(tallyList.get(position)._TOPIC3);
        topic4.setText(tallyList.get(position)._TOPIC4);

        return view;
    }

}
