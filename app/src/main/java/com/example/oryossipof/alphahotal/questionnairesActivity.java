package com.example.oryossipof.alphahotal;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

public class questionnairesActivity extends Activity {
    private Button sendBt;
    private RadioGroup radioGroup1,radioGroup2, radioGroup3,radioGroup4,radioGroup5,radioGroup6,radioGroup7,radioGroup8 ;
    private RadioButton adult,  male, vegeterian, vegan, married, children, pleasute, group;
    private Spinner spinner ;
    private EditText originText;
    private TextView err;
    private BroadcastReceiver receiver;
    private  String roonNum;
    private int index ;
    private String[] AreaList = {"Middle East", "South America" , "East Europe", "Western Europe", "South Asia", "Mediterranean","Africa"};
    private String area;
    private ProgressDialog progress ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_questionnaires);
        roonNum = getIntent().getStringExtra("roomNum");

        sendBt = (Button) findViewById(R.id.sendbt);
        radioGroup1 = (RadioGroup) findViewById(R.id.group1);
        radioGroup2 = (RadioGroup) findViewById(R.id.group2);
        radioGroup3 = (RadioGroup) findViewById(R.id.group3);
        radioGroup4 = (RadioGroup) findViewById(R.id.group4);
        radioGroup5 = (RadioGroup) findViewById(R.id.group5);
        radioGroup6 = (RadioGroup) findViewById(R.id.group6);
        radioGroup7 = (RadioGroup) findViewById(R.id.group7);
        radioGroup8 = (RadioGroup) findViewById(R.id.group8);
        spinner = (Spinner) findViewById(R.id.spinner_ip);
        err = (TextView) findViewById(R.id.validationerr);

        progress= new ProgressDialog(questionnairesActivity.this);

        final Spinner spinner = (Spinner) findViewById(R.id.spinner_ip);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                index = spinner.getSelectedItemPosition();
                area = AreaList[index];

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                index = spinner.getSelectedItemPosition();
                area = AreaList[index];


            }
        });



        sendBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!validate())
                {
                    err.setVisibility(View.VISIBLE);
                }
                else
                {
                    err.setVisibility(View.INVISIBLE);
                    String roomNum = getIntent().getStringExtra("roomNum");
                    String type = "questionnaires";
                    BackgroundWorker backgroundWorker = new BackgroundWorker(questionnairesActivity.this);
                    String answers[] = getAnswers();
                    backgroundWorker.execute(type,answers[0],answers[1],answers[2],answers[3],answers[4],answers[5],answers[6],answers[7],answers[8],roomNum);
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
                            String result = (String)intent.getExtras().getString("result");


                            if(result.equals("Record updated successfully"))
                            {
                                progress.dismiss();
                                Intent intent1 = new Intent(questionnairesActivity.this, MainActivity.class).putExtra("roomNum",roonNum);
                                startActivity(intent1);
                                try {
                                    this.finalize();
                                } catch (Throwable throwable) {
                                    throwable.printStackTrace();
                                }
                                unregisterReceiver(receiver);
                                finish();

                            }

                            else
                            {
                                AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                                alertDialog.setTitle("Questionnaires Result");
                                alertDialog.setMessage(result);
                                alertDialog.show();
                                unregisterReceiver(receiver);
                            }
                        }
                    }, new IntentFilter("resultIntent3"));

                }
            }
        });
    }

    public String[] getAnswers() {
        adult = (RadioButton) findViewById(R.id.over15bt);
        male = (RadioButton) findViewById(R.id.femalebt);
        vegeterian = (RadioButton) findViewById(R.id.vegYesbt);
        vegan = (RadioButton) findViewById(R.id.veganYesbt);
        married = (RadioButton) findViewById(R.id.marriedYesbt);
        children = (RadioButton) findViewById(R.id.childrenYesbt);
        pleasute = (RadioButton) findViewById(R.id.bussinessbt);
        group = (RadioButton) findViewById(R.id.groupYesbt);



        String answers[] = new String[9];
        answers[0] = area;
        if (adult.isChecked())
            answers[1] = "1";
        else
            answers[1] = "0";

        if (male.isChecked())
            answers[2] = "1";
        else
            answers[2] = "0";

        if (vegeterian.isChecked())
            answers[3] = "1";
        else
            answers[3] = "0";

        if (vegan.isChecked())
            answers[4] = "1";
        else
            answers[4] = "0";

        if (married.isChecked())

            answers[5] = "1";
        else
            answers[5] = "0";

        if (children.isChecked())
            answers[6] = "1";
        else
            answers[6] = "0";

        if (pleasute.isChecked())
            answers[7] = "1";
        else
            answers[7] = "0";
        if (group.isChecked())

            answers[8] = "1";
        else
            answers[8] = "0";




        return answers;


    }


    public boolean validate()
    {

        if (radioGroup1.getCheckedRadioButtonId() == -1 || radioGroup2.getCheckedRadioButtonId() == -1 || radioGroup3.getCheckedRadioButtonId() == -1 || radioGroup4.getCheckedRadioButtonId() == -1 || radioGroup5.getCheckedRadioButtonId() == -1 || radioGroup6.getCheckedRadioButtonId() == -1 || radioGroup7.getCheckedRadioButtonId() == -1 || radioGroup8.getCheckedRadioButtonId() == -1)
        {
            return false;
        }

        return true;
    }




}
