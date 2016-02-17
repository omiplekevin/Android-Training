/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.omiplekevin.dragdroplistview.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.omiplekevin.dragdroplistview.Cheeses;
import com.example.omiplekevin.dragdroplistview.DataModel;
import com.example.omiplekevin.dragdroplistview.DynamicListView;
import com.example.omiplekevin.dragdroplistview.R;
import com.example.omiplekevin.dragdroplistview.StableArrayAdapter;

import java.util.ArrayList;
import java.util.Random;

/**
 * This application creates a listview where the ordering of the data set
 * can be modified in response to user touch events.
 *
 * An item in the listview is selected via a long press event and is then
 * moved around by tracking and following the movement of the user's finger.
 * When the item is released, it animates to its new position within the listview.
 */
public class ListViewDraggingAnimation extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        ArrayList<DataModel>mCheeseList = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < Cheeses.sCheeseStrings.length; ++i) {
            DataModel d = new DataModel();
            d.name = Cheeses.sCheeseStrings[i];
            d.age = rand.nextInt(50) + i;
            if (d.age % 2 == 0) {
                d.state = true;
            } else {
                d.state = false;
            }
            mCheeseList.add(d);

            Log.e("LVDA", d.name + " " + d.age + " " + d.state);
        }

        StableArrayAdapter adapter = new StableArrayAdapter(this, mCheeseList);
        DynamicListView listView = (DynamicListView) findViewById(R.id.listview);

        listView.setCheeseList(mCheeseList);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }
}
