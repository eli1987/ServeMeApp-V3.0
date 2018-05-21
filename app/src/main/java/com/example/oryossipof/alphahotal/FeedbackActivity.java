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
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
    private RadioGroup radioGroup1;
    private RadioButton didAppHelp;


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
        roomNum = getIntent().getStringExtra("roomNum");
        btnSubmit = (Button) findViewById(R.id.feedback_page_button);




        //if click on me, then display the current rating value.
        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String answer;
                didAppHelp = (RadioButton) findViewById(R.id.Yesbt);

                if (didAppHelp.isChecked())
                    answer = "1";
                else
                    answer = "0";

                if (!validate()) {
                    Toast.makeText(context, "Please fill out the last question!", Toast.LENGTH_SHORT).show();
                }
                else
                    {


                    BackgroundWorker bg = new BackgroundWorker(context);
                    bg.execute("RateHotel", roomNum, String.valueOf(ratingList.get(0).getRating()), String.valueOf(ratingList.get(1).getRating()), String.valueOf(ratingList.get(2).getRating())
                            , String.valueOf(ratingList.get(3).getRating()), String.valueOf(ratingList.get(4).getRating())
                            , String.valueOf(ratingList.get(5).getRating()), String.valueOf(ratingList.get(6).getRating()), String.valueOf(ratingList.get(7).getRating()),answer);

                    progress = new ProgressDialog(FeedbackActivity.this);

                    progress.setMessage(context.getResources().getString(R.string.Delivring_request_str));
                        progress.setProgressStyle(R.style.AppTheme_Dark_Dialog);
                        progress.setIndeterminate(true);
                    progress.setCancelable(false);
                    progress.setCanceledOnTouchOutside(false);

                    progress.show();


                    context.registerReceiver(receiver = new BroadcastReceiver() {
                        @Override
                        public void onReceive(Context context, Intent intent) {
                            String result = (String) intent.getExtras().getString("result");
                            progress.setProgress(100);
                            progress.dismiss();
                            //alertDialog.show();
                            if (result.equals("New requests accepted successfully")) {
                                Toast.makeText(context, context.getResources().getString(R.string.New_Rate_accepted_successfully_str), Toast.LENGTH_SHORT).show();

                                context.unregisterReceiver(receiver);

                            } else if (result.equals("already rated!")) {
                                Toast.makeText(context, context.getResources().getString(R.string.already_rated_str), Toast.LENGTH_SHORT).show();

                                context.unregisterReceiver(receiver);

                            } else {
                                Toast.makeText(context, context.getResources().getString(R.string.Connection_error_try_again_later_str), Toast.LENGTH_SHORT).show();
                                context.unregisterReceiver(receiver);
                            }

                        }

                    }, new IntentFilter("RateIntent"));

                }
            }
        });


    }

    private void addListenerOnRatingBar() {

        ratingList = new ArrayList<RatingBar>();
        ratingList.add((RatingBar) findViewById(R.id.Recep_ratingBar));
        ratingList.add((RatingBar) findViewById(R.id.check_in_ratingBar));
        ratingList.add((RatingBar) findViewById(R.id.check_out_ratingBar));
        ratingList.add((RatingBar) findViewById(R.id.clean_ratingBar));
        ratingList.add((RatingBar) findViewById(R.id.comfort_ratingBar));
        ratingList.add((RatingBar) findViewById(R.id.maintenance_ratingBar));
        ratingList.add((RatingBar) findViewById(R.id.quality_of_food_ratingBar));
        ratingList.add((RatingBar) findViewById(R.id.service_ratingBar));


        radioGroup1 = (RadioGroup) findViewById(R.id.group1);
    }

    public boolean validate()
    {

        if (radioGroup1.getCheckedRadioButtonId() == -1 )
        {
            return false;
        }

        return true;
    }

}