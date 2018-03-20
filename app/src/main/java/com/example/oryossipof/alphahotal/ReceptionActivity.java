package com.example.oryossipof.alphahotal;

        import android.app.Activity;
import android.app.ProgressDialog;
        import android.content.BroadcastReceiver;
        import android.content.Context;
import android.content.Intent;
        import android.content.IntentFilter;
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

        import java.util.ArrayList;
        import java.util.Locale;

public class ReceptionActivity extends Activity {
    private String telNumber ="035433333";
    private ProgressDialog progress ;
    private String roomNum;
    private int [] drawableName = {R.drawable.call3,R.drawable.trip,R.drawable.tour,R.drawable.food,R.drawable.taxi,R.drawable.weakup};
    String receptionDesc[] = {"Call","Book a Trip","Recommend tours","Recommend restaurants","Get Taxi","Wake up call"};
    String reception[];
    private ListView mListView ;
    private Context context;
    private AASync AASync;
    private BroadcastReceiver serviceReceiver;
    ArrayList<HotelService> receService = new ArrayList<>();
    ArrayList<HotelService> newService =new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_reception);


        reception = new String[] {getResources().getString(R.string.call_str), getResources().getString(R.string.book_trip_str), getResources().getString(R.string.Recommend_tours_str), getResources().getString(R.string.Recommend_restaurants_str),getResources().getString(R.string.Get_Taxi_str),getResources().getString(R.string.Wake_up_call_str)};
        mListView = (ListView) findViewById(R.id.lv_housekeeping);
        final CutomAdapter2 adapter = new CutomAdapter2();
        mListView.setAdapter(adapter);
        context=this;
        roomNum = getIntent().getStringExtra("roomNum");
        progress= new ProgressDialog(ReceptionActivity.this);
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
            textview.setText(reception[i]);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent;
                    switch(index)
                    {
                        case 0:
                            // startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("035433333")));


                            String type = "getService";
                            String CurrentLang = Locale.getDefault().getLanguage();
                            // startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("035433333")));
                            AASync = new AASync(ReceptionActivity.this);
                            AASync.execute(type,CurrentLang);
                            registerReceiver(serviceReceiver =new BroadcastReceiver() {
                                @Override
                                public void onReceive(Context context, Intent intent) {
                                    receService = (ArrayList<HotelService>)intent.getSerializableExtra("result");
                                    Log.e("result",receService.size()+"");
                                    newService = new ArrayList<HotelService>();
                                    for(int i=0;i <receService.size();i++)
                                    {
                                        newService.add(receService.get(i));
                                    }
                                    //unregisterReceiver(receiver);
                                    CallService.callPhoneNumber(ReceptionActivity.this,newService.get(0).recNumber);

                                }


                            }, new IntentFilter("serviceIntent"));


                           // CallService.callPhoneNumber(ReceptionActivity.this,telNumber);

                            break;

                        case 1:  //book a Trip
                            intent = new Intent(ReceptionActivity.this, BookTripActivity.class);
                            intent.putExtra("roomNum",roomNum);
                            startActivity(intent);
                            break;

                        case 4:  //Get Taxi
                            intent = new Intent(ReceptionActivity.this, BookTaxiActivity.class);
                            intent.putExtra("roomNum",roomNum);
                            startActivity(intent);
                            break;

                        case 5:  //wake up call
                            intent = new Intent(ReceptionActivity.this, WakeUpCall.class);
                            intent.putExtra("roomNum",roomNum);
                            startActivity(intent);
                            break;


                        default:



                            break;

                    }
                }
            });

            return view;
        }
    }
}
