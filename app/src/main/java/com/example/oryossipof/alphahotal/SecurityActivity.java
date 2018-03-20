package com.example.oryossipof.alphahotal;

import android.app.Activity;
import android.app.DownloadManager;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class SecurityActivity extends Activity {
    private String department = "Security";
    private ListView mListView ;
    private Context context;
    private BroadcastReceiver receiver;
    private String roomNum;
   // private String telNumber ="035433333";
    private ProgressDialog progress ;
    private int [] drawable = {R.drawable.safe,R.drawable.bellboy,R.drawable.opendor,R.drawable.firstaid,R.drawable.calll};
    String security[];
    String securityDesc[] = {"Open Safe", "BellBoy", "Open Door", "First Aid", "Call"};

    private AASync AASync;
    private BroadcastReceiver serviceReceiver;
    ArrayList<HotelService> securityResult = new ArrayList<>();
    ArrayList<HotelService> newService =new ArrayList<>();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_security);

        security = new String[] {getResources().getString(R.string.open_safe_str),getResources().getString(R.string.bellboy_str),getResources().getString(R.string.open_door_str),getResources().getString(R.string.first_aid_str),getResources().getString(R.string.call_str)};


        mListView = (ListView) findViewById(R.id.lv_housekeeping);
        final CutomAdapter2 adapter = new CutomAdapter2();
        mListView.setAdapter(adapter);
        context=this;
        roomNum = getIntent().getStringExtra("roomNum");
        progress= new ProgressDialog(SecurityActivity.this);

    }

    class CutomAdapter2 extends BaseAdapter
    {


        @Override
        public int getCount() {
            return drawable.length;
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
            iv.setImageResource(drawable[i]);
            textview.setText(security[i]);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent;
                    switch(index)
                    {
                        case 4:
                            String type = "getService";
                            String CurrentLang = Locale.getDefault().getLanguage();
                            // startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("035433333")));
                            AASync = new AASync(SecurityActivity.this);
                            AASync.execute(type,CurrentLang);
                            registerReceiver(serviceReceiver =new BroadcastReceiver() {
                                @Override
                                public void onReceive(Context context, Intent intent) {
                                    securityResult = (ArrayList<HotelService>)intent.getSerializableExtra("result");
                                    Log.e("result",securityResult.size()+"");
                                    newService = new ArrayList<HotelService>();
                                    for(int i=0;i <securityResult.size();i++)
                                    {
                                        newService.add(securityResult.get(i));
                                    }
                                    //unregisterReceiver(receiver);
                                    CallService.callPhoneNumber(SecurityActivity.this,newService.get(0).securityNumber);

                                }


                            }, new IntentFilter("serviceIntent"));





                            break;

                        default:
                            BackgroundWorker bg = new BackgroundWorker(SecurityActivity.this);
                            bg.execute("insertNewRequest", roomNum, department, securityDesc[index], "");
                            progress.setMessage(getResources().getString(R.string.Delivring_request_str));
                            progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                            progress.setIndeterminate(false);
                            progress.setCancelable(false);
                            progress.setCanceledOnTouchOutside(false);
                            progress.setProgress(0);
                            progress.show();

                            registerReceiver(receiver = new BroadcastReceiver() {
                                @Override
                                public void onReceive(Context context, Intent intent) {
                                    String result = (String) intent.getExtras().getString("result");
                                    progress.setProgress(100);
                                    progress.dismiss();
                                    //alertDialog.show();
                                    if(result.equals("New requests accepted successfully")) {
                                        Toast.makeText(SecurityActivity.this, getResources().getString(R.string.New_request_accepted_successfully_str), Toast.LENGTH_SHORT).show();

                                        unregisterReceiver(receiver);

                                    }
                                    else if (result.equals("no one in the room"))
                                    {
                                        Toast.makeText(SecurityActivity.this, getResources().getString(R.string.not_occupied_str), Toast.LENGTH_SHORT).show();
                                        unregisterReceiver(receiver);
                                    }
                                    else if (result.equals("same request"))
                                    {
                                        Toast.makeText(SecurityActivity.this,getResources().getString(R.string.tooManyRequests_str), Toast.LENGTH_SHORT).show();
                                        unregisterReceiver(receiver);

                                    }
                                    else
                                    {
                                        Toast.makeText(SecurityActivity.this, getResources().getString(R.string.Connection_error_try_again_later_str), Toast.LENGTH_SHORT).show();
                                        unregisterReceiver(receiver);
                                    }


                                }

                            }, new IntentFilter("resultIntent4"));


                            break;

                    }
                }
            });

            return view;
        }



    }

}
