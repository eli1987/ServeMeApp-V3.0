package com.example.oryossipof.alphahotal;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.example.oryossipof.alphahotal.InformationUtils.context;

public class EmployeeAdapter  extends ArrayAdapter<Employee> {

    private int index;
    private ProgressDialog progress ;
    private BroadcastReceiver receiver;
    private String rate;
    private String empId;
    Employee emp;
    public EmployeeAdapter(Context context, ArrayList<Employee> activity) {
        super(context, 0, activity);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        index = position;
        emp = getItem(position);
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
        Button empRateBt = (Button) convertView.findViewById(R.id.button) ;


        // Populate the data into the template view using the data object

        firstName.setText(emp.firtstname);
        lastName.setText(emp.lastname);
        department.setText(emp.deppartment);
        empId = emp.empId;
        progress= new ProgressDialog(context);


        if(emp.imageStr != "" )
        Picasso.with(context).load("http://servemeapp.000webhostapp.com/"+emp.imageStr).into(ImageStr);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {

                rate =String.valueOf(rating);

            }
        });

        rate = String.valueOf(ratingBar.getRating());

        empRateBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;

                        BackgroundWorker bg = new BackgroundWorker(context);
                        bg.execute("RateEmployee", empId ,rate);

                        progress.setMessage(context.getResources().getString(R.string.Delivring_request_str));
                        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                        progress.setIndeterminate(false);
                        progress.setCancelable(false);
                        progress.setCanceledOnTouchOutside(false);
                        progress.setProgress(0);
                        progress.show();


                        context.registerReceiver(receiver = new BroadcastReceiver() {
                            @Override
                            public void onReceive(Context context, Intent intent) {
                                String result = (String) intent.getExtras().getString("result");
                                progress.setProgress(100);
                                progress.dismiss();
                                //alertDialog.show();
                                if(result.equals("New requests accepted successfully")) {
                                    Toast.makeText(context, context.getResources().getString(R.string.New_Rate_accepted_successfully_str), Toast.LENGTH_SHORT).show();

                                    context.unregisterReceiver(receiver);

                                }
                                else
                                {
                                    Toast.makeText(context, context.getResources().getString(R.string.Connection_error_try_again_later_str), Toast.LENGTH_SHORT).show();
                                    context.unregisterReceiver(receiver);
                                }

                            }

                        }, new IntentFilter("RateEmployeeIntent"));





            }
        });


        /// Return the completed view to render on screen
        return convertView;
    }
}