package com.example.oryossipof.alphahotal;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SigninActivity extends Activity {
    private Button loginBT;
    private  BackgroundWorker backgroundWorker,backgroundWorker2;
    private EditText roomNum, password;
    BroadcastReceiver receiver;
    BroadcastReceiver receiver2;
    private ProgressDialog progress ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signin);

        loginBT = (Button) findViewById(R.id.signinBT);
        password = (EditText) findViewById(R.id.passInput);
        roomNum = (EditText) findViewById(R.id.roomNumInput);
        progress= new ProgressDialog(SigninActivity.this);
        loginBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!CheckInternet(SigninActivity.this))
                {
                    Toast.makeText(SigninActivity.this,getResources().getString(R.string.Connection_error_try_again_later_str),Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!validate())
                    return;
                String type = "login";
                backgroundWorker = new BackgroundWorker(SigninActivity.this);
                backgroundWorker.execute(type,roomNum.getText().toString(),password.getText().toString());
                progress.setMessage(getResources().getString(R.string.Authenticating));
                progress.setProgressStyle(R.style.AppTheme_Dark_Dialog);
                progress.setIndeterminate(true);
                progress.setCancelable(false);
                progress.setCanceledOnTouchOutside(false);
                progress.show();
                loginBT.setEnabled(false);

                registerReceiver(receiver =new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        String result = (String)intent.getExtras().getString("result");

                        if(result.equals("login success")) {
                            //////////////////////////////////////////
                            // need to check if the user has done questionnaires before/

                            String type = "didQuestionnairesBefore";
                            backgroundWorker2 = new BackgroundWorker(SigninActivity.this);
                            backgroundWorker2.execute(type, roomNum.getText().toString());

                            registerReceiver(receiver2 = new BroadcastReceiver() {

                                @Override
                                public void onReceive(Context context, Intent intent) {
                                    String result = (String)intent.getExtras().getString("result");
                                    if (result.equals("used"))
                                    {

                                        Intent intent1 = new Intent(SigninActivity.this, MainActivity.class).putExtra("roomNum",roomNum.getText().toString());
                                        startActivity(intent1);
                                        try {

                                            this.finalize();
                                        } catch (Throwable throwable) {
                                            throwable.printStackTrace();
                                        }
                                        progress.dismiss();
                                        unregisterReceiver(receiver);
                                        unregisterReceiver(receiver2);
                                        finish();
                                    }
                                    else
                                    {

                                        unregisterReceiver(receiver);
                                        unregisterReceiver(receiver2);
                                        Intent intent1 = new Intent(SigninActivity.this, questionnairesActivity.class).putExtra("roomNum",roomNum.getText().toString());
                                        startActivity(intent1);
                                        try {
                                            this.finalize();
                                        } catch (Throwable throwable) {
                                            throwable.printStackTrace();
                                        }
                                        progress.dismiss();
                                        finish();
                                    }


                                }
                            }, new IntentFilter("resultIntent2"));;


                        }        /////////////////////////////////////////
                        else
                        {
                            progress.dismiss();
                            loginBT.setEnabled(true);
                            AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                            alertDialog.setTitle(getResources().getString(R.string.login_result_str));
                            alertDialog.setMessage(getResources().getString(R.string.login_failed_str));
                            alertDialog.show();

                            unregisterReceiver(receiver);


                        }


                    }

                }, new IntentFilter("resultIntent"));
            }
        });
    }

    public boolean validate() {
        boolean valid = true;

        String roomId = roomNum.getText().toString();

        if(roomId.isEmpty()) {
            roomNum.setError("can't be empty");
            valid = false;
        }
        else {
            roomNum.setError(null);
        }

        String pass= password.getText().toString();
        if(pass.isEmpty()) {
            password.setError("can't be empty");
            valid = false;
        }
        else {
            password.setError(null);
        }

        return valid;
    }

    public static boolean CheckInternet(Context context)
    {
        ConnectivityManager connec = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        android.net.NetworkInfo wifi = connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        android.net.NetworkInfo mobile = connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        return wifi.isConnected() || mobile.isConnected();
    }

}
