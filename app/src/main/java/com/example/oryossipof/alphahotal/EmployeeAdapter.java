package com.example.oryossipof.alphahotal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.example.oryossipof.alphahotal.InformationUtils.context;

public class EmployeeAdapter  extends ArrayAdapter<Employee> {

    public EmployeeAdapter(Context context, ArrayList<Employee> activity) {
        super(context, 0, activity);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Employee emp = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.employee_listview, parent, false);
        }
        // Lookup view for data population
        TextView firstName = (TextView) convertView.findViewById(R.id.employeenNametextview);
        TextView lastName = (TextView) convertView.findViewById(R.id.employeenLastNametextview);
        TextView department = (TextView) convertView.findViewById(R.id.employeeDepartmentextview);
        ImageView ImageStr = (ImageView) convertView.findViewById(R.id.employeeimageviewlayout);
        RatingBar ratingBar = (RatingBar) convertView.findViewById(R.id.ratingBar) ;


        // Populate the data into the template view using the data object

        firstName.setText(emp.firtstname);
        lastName.setText(emp.lastname);
        department.setText(emp.deppartment);

        if(emp.imageStr != "" )
        Picasso.with(context).load("http://servemeapp.000webhostapp.com/"+emp.imageStr).into(ImageStr);


        /// Return the completed view to render on screen
        return convertView;
    }
}