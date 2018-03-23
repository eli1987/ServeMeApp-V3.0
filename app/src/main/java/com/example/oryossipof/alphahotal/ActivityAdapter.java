package com.example.oryossipof.alphahotal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ActivityAdapter extends ArrayAdapter<MyActivity> {

    public ActivityAdapter(Context context, ArrayList<MyActivity> activity) {
        super(context, 0, activity);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        MyActivity activity = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_list, parent, false);
        }
        // Lookup view for data population
        TextView title = (TextView) convertView.findViewById(R.id.activity_titleTv);
        TextView info = (TextView) convertView.findViewById(R.id.Activity_InfoTv);

        // Populate the data into the template view using the data object

        title.setText(activity.activityName);
        info.setText(activity.info);

        return convertView;
    }
}