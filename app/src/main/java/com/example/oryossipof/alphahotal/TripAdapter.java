package com.example.oryossipof.alphahotal;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
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

public class TripAdapter extends ArrayAdapter<Trip> {
    public ArrayList<Trip> newDish =new ArrayList<>();
    private int index;
    private ProgressDialog progress ;
    private BroadcastReceiver receiver2;
    private String rate;
    public String roomNum;
    private int tripId;
    public Context context;
    Trip trip;
    private RatingBar ratingBar;
    public TripAdapter(Context context, ArrayList<Trip> activity) {
        super(context, 0, activity);
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        index = position;
        trip = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.trip_listview, parent, false);
        }
        // Lookup view for data population
        TextView tripName = (TextView) convertView.findViewById(R.id.tripNametextview);
        ImageView ImageStr = (ImageView) convertView.findViewById(R.id.tripimageviewlayout);
        Button tripRateBt = (Button) convertView.findViewById(R.id.button) ;
        tripRateBt.setTag(position);

        // Populate the data into the template view using the data object

        tripName.setText(trip.tripName);
       tripId = trip.tripId;
        Log.e("Dish",tripId+"");



        if(trip.tripImg != "" )
            Picasso.with(context).load("http://servemeapp.000webhostapp.com/"+trip.tripImg).fit().into(ImageStr);

        final Object btTag = tripRateBt.getTag();


        tripRateBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.e("Dish",tripId+"");

                BackgroundWorker bg = new BackgroundWorker(context);
                int index = Integer.parseInt(btTag.toString());
                bg.execute("rateTrip", roomNum,newDish.get(index).tripId+"");
                progress= new ProgressDialog(context);
                progress.setMessage(context.getResources().getString(R.string.Delivring_request_str));
                progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progress.setIndeterminate(false);
                progress.setCancelable(false);
                progress.setCanceledOnTouchOutside(false);
                progress.setProgress(0);
                progress.show();

                context.registerReceiver(receiver2 = new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        String result = (String) intent.getExtras().getString("result");
                        progress.setProgress(100);
                        progress.dismiss();
                        if(result.equals("Record updated successfully")) {
                            Toast.makeText(context, context.getResources().getString(R.string.New_Rate_accepted_successfully_str), Toast.LENGTH_SHORT).show();
                            context.unregisterReceiver(receiver2);

                        }

                        else if(result.equals("already rated!")) {
                            Toast.makeText(context,context.getResources().getString(R.string.already_rated_str), Toast.LENGTH_SHORT).show();
                            context.unregisterReceiver(receiver2);

                        }
                        else
                        {
                            Toast.makeText(context, context.getResources().getString(R.string.Connection_error_try_again_later_str), Toast.LENGTH_SHORT).show();
                            context.unregisterReceiver(receiver2);
                        }
                    }

                }, new IntentFilter("rateTrip"));



            }
        });


        /// Return the completed view to render on screen
        return convertView;
    }

}
