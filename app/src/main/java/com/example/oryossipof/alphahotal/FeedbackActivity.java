package com.example.oryossipof.alphahotal;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.oryossipof.alphahotal.InformationUtils.context;

public class FeedbackActivity extends Activity {

    private  String roomNum;
    private  String s;
    private ProgressDialog progress ;
    private BroadcastReceiver receiver;
    private ArrayList<RatingBar> ratingList;
    private Button btnSubmit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_feedback);


        addListenerOnRatingBar();
        addListenerOnButton();
    }

    private void addListenerOnButton() {
        // TODO Auto-generated method stub
        roomNum = getIntent().getStringExtra("roomNum");
        btnSubmit = (Button) findViewById(R.id.feedback_page_button);




        //if click on me, then display the current rating value.
        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                BackgroundWorker bg = new BackgroundWorker(context);
                bg.execute("RateHotel", roomNum,String.valueOf(ratingList.get(0).getRating()),String.valueOf(ratingList.get(1).getRating()),String.valueOf(ratingList.get(2).getRating())
                        ,String.valueOf(ratingList.get(3).getRating()),String.valueOf(ratingList.get(4).getRating())
                        ,String.valueOf(ratingList.get(5).getRating()),String.valueOf(ratingList.get(6).getRating()),String.valueOf(ratingList.get(7).getRating()));

                    progress= new ProgressDialog(FeedbackActivity.this);

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

                        else if(result.equals("already rated!")) {
                            Toast.makeText(context,context.getResources().getString(R.string.already_rated_str), Toast.LENGTH_SHORT).show();

                            context.unregisterReceiver(receiver);

                        }


                        else
                        {
                            Toast.makeText(context, context.getResources().getString(R.string.Connection_error_try_again_later_str), Toast.LENGTH_SHORT).show();
                            context.unregisterReceiver(receiver);
                        }

                    }

                }, new IntentFilter("RateIntent"));

            }

        });


    }

    private void addListenerOnRatingBar() {
        // TODO Auto-generated method stub

        ratingList = new ArrayList<RatingBar>();
        ratingList.add((RatingBar) findViewById(R.id.Recep_ratingBar));
        ratingList.add((RatingBar) findViewById(R.id.check_in_ratingBar));
        ratingList.add((RatingBar) findViewById(R.id.check_out_ratingBar));
        ratingList.add((RatingBar) findViewById(R.id.clean_ratingBar));
        ratingList.add((RatingBar) findViewById(R.id.comfort_ratingBar));
        ratingList.add((RatingBar) findViewById(R.id.maintenance_ratingBar));
        ratingList.add((RatingBar) findViewById(R.id.quality_of_food_ratingBar));
        ratingList.add((RatingBar) findViewById(R.id.service_ratingBar));

    }

}