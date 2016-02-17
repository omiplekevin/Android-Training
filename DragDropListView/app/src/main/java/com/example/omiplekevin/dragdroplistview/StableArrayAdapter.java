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

package com.example.omiplekevin.dragdroplistview;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class StableArrayAdapter extends ArrayAdapter<DataModel> {

    final int INVALID_ID = -1;

    HashMap<String, Integer> mIdMap = new HashMap<>();
    Context context;
    List<DataModel> objects;

    public StableArrayAdapter(Context context, List<DataModel> objects) {
        super(context, R.layout.text_view, objects);
        this.context = context;
        this.objects = objects;
        for (int i = 0; i < objects.size(); ++i) {
            //in here, we can use the exercise id to track the ids of the items
            mIdMap.put(objects.get(i).name, i);
        }
    }

    @Override
    public long getItemId(int position) {
        if (position < 0 || position >= mIdMap.size()) {
            return INVALID_ID;
        }
        String item = getItem(position).name;
        return mIdMap.get(item);
    }

    @Override
    public boolean hasStableIds() {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.text_view, parent, false);

            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.age = (TextView) convertView.findViewById(R.id.age);
            holder.state = (RadioButton) convertView.findViewById(R.id.activeState);
            holder.state.setFocusable(false);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.name.setText(objects.get(position).name);
        holder.age.setText(objects.get(position).age + "");
        holder.state.setChecked(objects.get(position).state);
        return convertView;
    }

    class ViewHolder{
        TextView name;
        TextView age;
        RadioButton state;
    }
}
