package com.example.oryossipof.alphahotal;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Locale;

public class TripRateActivity extends Activity {

    private GenricAASync<Trip> genricAASync;
    private BroadcastReceiver receiver;
    private String roomNum;
    ArrayList<Trip> result = new ArrayList<>();
    ArrayList<Trip> newUsers =new ArrayList<>();
    TripAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_trip_rate);

        roomNum = getIntent().getStringExtra("roomNum");

        ArrayList<Trip> trips = new ArrayList<Trip>();
        // Create the adapter to convert the array to views
        adapter = new TripAdapter(this, trips);
        // Attach the adapter to a ListView
        final ListView listView = (ListView) findViewById(R.id.tripListView);

        String type = "getTripData";
        // Add item to adapter
        listView.setAdapter(adapter);

        genricAASync = new GenricAASync<Trip>(TripRateActivity.this);
        genricAASync.execute(type, Locale.getDefault().getLanguage());
        registerReceiver(receiver =new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                result = (ArrayList<Trip>)intent.getSerializableExtra("result");
                Log.e("result",result.size()+"");
                newUsers = new ArrayList<Trip>();
                for(int i=0;i <result.size();i++)
                {
                    newUsers.add(result.get(i));
                }

                adapter.addAll(newUsers);
                adapter.newDish = newUsers;
                adapter.context =TripRateActivity.this;
                adapter.roomNum = roomNum;
                listView.setAdapter(adapter);
                unregisterReceiver(receiver);

            }
        }, new IntentFilter("TripIntent"));

    }


}



