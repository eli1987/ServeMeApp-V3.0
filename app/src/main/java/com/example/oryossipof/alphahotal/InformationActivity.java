package com.example.oryossipof.alphahotal;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

public class InformationActivity extends Activity {

    public  String WEATHER_ADDRESS = "";
    public  String SHABBAT_ADDRESS = "";
   public  String FLIGHTS_ADDRESS = "";
    public  String MAPS_ADDRESS = "";
    private ListView mListView ;
    private Context context;
    private int [] drawableName= {R.drawable.weather,R.drawable.maps,R.drawable.flight,R.drawable.shabb2,R.drawable.din,R.drawable.pre,R.drawable.pool,R.drawable.recommendedicon,R.drawable.restaurants2,R.drawable.trip};
    private String infoDesc[] = {"Weather","Maps","Flight Times","Shabbat Hours ","Dinning Hours","Activities","Pool hours","Recommended dish for you","Recommended restaurants for you","Recommended trips"};
    private String info[];
    ArrayList<Dish> result = new ArrayList<>();
    ArrayList<Trip> Tripresult = new ArrayList<>();
    ArrayList<Dish> newUsers =new ArrayList<>();
    ArrayList<Trip> trips =new ArrayList<>();
    private GenricAASync<Dish> genricAASync;
    private GenricAASync<Trip> genricAASync2;
    private BroadcastReceiver receiver;
    private String Name;
    private String roomNum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_information);


        info = new String[]{getResources().getString(R.string.Weather_str),getResources().getString(R.string.Maps_str),getResources().getString(R.string.Flight_Times_str),getResources().getString(R.string.Shabbat_Hours_str),getResources().getString(R.string.Dinning_Hours_str),getResources().getString(R.string.Activities_str),getResources().getString(R.string.Pool_hours_str),getResources().getString(R.string.RecommandeDish_str),getResources().getString(R.string.RecommendedRestaurents),getResources().getString(R.string.Recommend_tours_str)};
        mListView = (ListView) findViewById(R.id.lv_housekeeping);
        final CutomAdapter2 adapter = new CutomAdapter2();
        mListView.setAdapter(adapter);
        context=this;
        roomNum = getIntent().getStringExtra("roomNum");



        WEATHER_ADDRESS = InformationUtils.WEATHER_ADDRESS;
        MAPS_ADDRESS = InformationUtils.MAPS_ADDRESS;
        FLIGHTS_ADDRESS = InformationUtils.FLIGHTS_ADDRESS;
        SHABBAT_ADDRESS = InformationUtils.SHABBAT_ADDRESS;

    }

    class CutomAdapter2 extends BaseAdapter
    {


        @Override
        public int getCount() {
            return drawableName.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
        final int index = i;
            view = getLayoutInflater().inflate(R.layout.listview_layout,null);
            ImageView iv= (ImageView) view.findViewById(R.id.imageviewlayout);
            TextView textview = (TextView) view.findViewById(R.id.textviewLayout);
            iv.setImageResource(drawableName[i]);
            textview.setText(info[i]);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent;
                    switch(index)
                    {
                        case 0:     //weather

                            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(WEATHER_ADDRESS));
                            startActivity(intent);
                            break;

                        case 1:   //MAPS
                            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(MAPS_ADDRESS));
                            startActivity(intent);
                            break;

                        case 2:     //flights
                            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(FLIGHTS_ADDRESS));
                            startActivity(intent);
                            break;

                        case 3:   //shabbat
                            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(SHABBAT_ADDRESS));
                            startActivity(intent);
                            break;

                        case 4:  //Activities
                            intent = new Intent(InformationActivity.this, DiningHoursActivity.class);
                            startActivity(intent);
                            break;

                        case 5:
                            intent = new Intent(InformationActivity.this, ActivitiesActivity.class);
                            startActivity(intent);
                            break;

                        case 6:
                            intent = new Intent(InformationActivity.this, PoolTimeActivity.class);
                            startActivity(intent);

                            break;

                        case 7:
                            intent = new Intent(InformationActivity.this, RecommendedForYouActivity.class);
                            intent.putExtra("roomNum",roomNum);
                            startActivity(intent);
                            break;

                        case 8:
                            String type = "getRestaurants";
                            genricAASync = new GenricAASync<Dish>(InformationActivity.this);
                            genricAASync.execute(type,roomNum);
                            registerReceiver(receiver =new BroadcastReceiver() {
                                @Override
                                public void onReceive(Context context, Intent intent) {
                                    result = (ArrayList<Dish>)intent.getSerializableExtra("result");

                                    if(result == null)
                                    {
                                        Toast.makeText(context, context.getResources().getString(R.string.Sorry_str), Toast.LENGTH_SHORT).show();

                                    }
                                    else
                                    {
                                            newUsers = new ArrayList<Dish>();
                                            for(int i=0;i <result.size();i++)
                                            {
                                                newUsers.add(result.get(i));
                                            }

                                                 Name = newUsers.get(0).dishName;
                                               intent = new Intent(Intent.ACTION_VIEW, Uri.parse(InformationUtils.restaurntsWebPage+Name));
                                                startActivity(intent);
                                    }

                                    unregisterReceiver(receiver);

                                }
                            }, new IntentFilter("getRestaurants"));

                            break;

                        case 9:
                            intent = new Intent(InformationActivity.this, RecommendedTrip.class);
                            intent.putExtra("roomNum",roomNum);
                            startActivity(intent);
                            break;

                    }

                }

            });
            return view;
        }
    }
}
