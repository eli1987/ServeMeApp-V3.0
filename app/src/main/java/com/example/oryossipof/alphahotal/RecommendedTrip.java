package com.example.oryossipof.alphahotal;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

public class RecommendedTrip extends Activity {

    private GenricAASync<Trip> genricAASync;
    private BroadcastReceiver receiver;
    private String roomNum;
    ArrayList<Trip> result = new ArrayList<>();
    ArrayList<Trip> newTrips =new ArrayList<>();
    private ProgressDialog progress ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_recommended_trip);
        final TextView tripName = (TextView) findViewById(R.id.RecommendedAnsTv);
        final ImageView ImageStr = (ImageView) findViewById(R.id.Recommendeddishiv);
        roomNum = getIntent().getStringExtra("roomNum");
        progress= new ProgressDialog(RecommendedTrip.this);

        String type = "getRecommendedTrip";

        genricAASync = new GenricAASync<Trip>(RecommendedTrip.this);
        genricAASync.execute(type,roomNum , Locale.getDefault().getLanguage());
        progress.setMessage(getResources().getString(R.string.Delivring_request_str));
        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progress.setIndeterminate(false);
        progress.setCancelable(false);
        progress.setCanceledOnTouchOutside(false);
        progress.setProgress(0);
        progress.show();

        registerReceiver(receiver =new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                result = (ArrayList<Trip>)intent.getSerializableExtra("result");
                //Log.e("result",result.size()+"");
                if(result == null)
                {
                    tripName.setText(getResources().getString(R.string.Sorry_str));
                    progress.dismiss();
                }

                else
                {
                    newTrips = new ArrayList<Trip>();
                    for(int i=0;i <result.size();i++)
                    {
                        newTrips.add(result.get(i));
                    }
                    tripName.setText(newTrips.get(0).tripName);
                    Picasso.with(context).load("http://servemeapp.000webhostapp.com/"+newTrips.get(0).getTripName()).fit().into(ImageStr);
                    progress.dismiss();
                }

                unregisterReceiver(receiver);

            }
        }, new IntentFilter("getRecommendedTrip"));



    }
}
