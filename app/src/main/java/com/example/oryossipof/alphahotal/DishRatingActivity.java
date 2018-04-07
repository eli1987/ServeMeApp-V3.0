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

public class DishRatingActivity extends Activity {


    //private DishAASync DishAASync;
    private GenricAASync<Dish> genricAASync;
    private BroadcastReceiver receiver;
    private String roomNum;
    ArrayList<Dish>  result = new ArrayList<>();
    ArrayList<Dish> newUsers =new ArrayList<>();
    DishAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dish_rating);

        roomNum = getIntent().getStringExtra("roomNum");

        ArrayList<Dish> dishes = new ArrayList<Dish>();
        // Create the adapter to convert the array to views
        adapter = new DishAdapter(this, dishes);
        // Attach the adapter to a ListView
        final ListView listView = (ListView) findViewById(R.id.dishListView);

        String type = "getDishData";
        // Add item to adapter
        listView.setAdapter(adapter);

        genricAASync = new GenricAASync<Dish>(DishRatingActivity.this);
        genricAASync.execute(type, Locale.getDefault().getLanguage());
        registerReceiver(receiver =new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                result = (ArrayList<Dish>)intent.getSerializableExtra("result");
                Log.e("result",result.size()+"");
                newUsers = new ArrayList<Dish>();
                for(int i=0;i <result.size();i++)
                {
                    newUsers.add(result.get(i));
                }

                adapter.addAll(newUsers);
                adapter.newDish = newUsers;
                adapter.context =DishRatingActivity.this;
                adapter.roomNum = roomNum;
                listView.setAdapter(adapter);
                unregisterReceiver(receiver);

            }
        }, new IntentFilter("dishIntent"));

    }


}

