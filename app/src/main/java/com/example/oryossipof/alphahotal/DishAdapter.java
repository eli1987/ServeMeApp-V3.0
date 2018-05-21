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
import java.util.Locale;

public class DishAdapter extends ArrayAdapter<Dish> {
    public ArrayList<Dish> newDish =new ArrayList<>();
    private int index;
    private ProgressDialog progress ;
    private BroadcastReceiver receiver2;
    private String rate;
    public String roomNum;
    private int dishId;
    public Context context;
    Dish dish;
    private RatingBar ratingBar;
    public DishAdapter(Context context, ArrayList<Dish> activity) {
        super(context, 0, activity);
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        index = position;
        dish = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.dish_listview, parent, false);
        }
        // Lookup view for data population
        TextView dishtName = (TextView) convertView.findViewById(R.id.dishNametextview);
        ImageView ImageStr = (ImageView) convertView.findViewById(R.id.dishimageviewlayout);
        Button dishRateBt = (Button) convertView.findViewById(R.id.button) ;
        dishRateBt.setTag(position);

        // Populate the data into the template view using the data object

        dishtName.setText(dish.dishName);
        dishId = dish.dishId;
        Log.e("Dish",dishId+"");



        if(dish.dishImg != "" )
            Picasso.with(context).load("http://servemeapp.000webhostapp.com/"+dish.dishImg).fit().into(ImageStr);

        final Object btTag = dishRateBt.getTag();


        dishRateBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.e("Dish",dishId+"");

                BackgroundWorker bg = new BackgroundWorker(context);
                int index = Integer.parseInt(btTag.toString());
                bg.execute("rateDish", roomNum,newDish.get(index).dishId+"");
                progress= new ProgressDialog(context);
                progress.setMessage(context.getResources().getString(R.string.Delivring_request_str));
                progress.setProgressStyle(R.style.AppTheme_Dark_Dialog);
                progress.setIndeterminate(true);
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

                }, new IntentFilter("rateDish"));



            }
        });


        /// Return the completed view to render on screen
        return convertView;
    }
}