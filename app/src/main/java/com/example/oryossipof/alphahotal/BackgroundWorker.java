package com.example.oryossipof.alphahotal;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;


public class BackgroundWorker extends AsyncTask<String,Void,String> {
    private static String typeToCheck = "";
    Context context;
    AlertDialog alertDialog;

    BackgroundWorker(Context ctx) {
        context = ctx;
    }

    @Override
    protected String doInBackground(String... params) {
        String type = params[0];

        String ServerUrl =  InformationUtils.url;

        if (type.equals("login")) {
            typeToCheck = "login";
            String result = handleBack(type, params, ServerUrl);
            return result;
        } else if (type.equals("questionnaires")) {
            typeToCheck = "questionnaires";
            String questionnaires_url = ServerUrl;
            try {
                String origin = params[1];
                String adult = params[2];
                String gender = params[3];
                String vegeterian = params[4];
                String vegan = params[5];
                String married = params[6];
                String children = params[7];
                String forPleasure = params[8];
                String isGroup = params[9];
                String roomNum = params[10];

                URL url = new URL(questionnaires_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");

                /*********/
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                /************/

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_date = URLEncoder.encode("origin", "UTF-8") + "=" + URLEncoder.encode(origin, "UTF-8")
                        + "&" + URLEncoder.encode("adult", "UTF-8") + "=" + URLEncoder.encode(adult, "UTF-8")
                        + "&" + URLEncoder.encode("gender", "UTF-8") + "=" + URLEncoder.encode(gender, "UTF-8")
                        + "&" + URLEncoder.encode("vegeterian", "UTF-8") + "=" + URLEncoder.encode(vegeterian, "UTF-8")
                        + "&" + URLEncoder.encode("vegan", "UTF-8") + "=" + URLEncoder.encode(vegan, "UTF-8")
                        + "&" + URLEncoder.encode("married", "UTF-8") + "=" + URLEncoder.encode(married, "UTF-8")
                        + "&" + URLEncoder.encode("children", "UTF-8") + "=" + URLEncoder.encode(children, "UTF-8")
                        + "&" + URLEncoder.encode("forPleasure", "UTF-8") + "=" + URLEncoder.encode(forPleasure, "UTF-8")
                        + "&" + URLEncoder.encode("isGroup", "UTF-8") + "=" + URLEncoder.encode(isGroup, "UTF-8")
                        + "&" + URLEncoder.encode("room_num", "UTF-8") + "=" + URLEncoder.encode(roomNum, "UTF-8")
                        + "&" + URLEncoder.encode("todo", "UTF-8") + "=" + URLEncoder.encode("insertQuestionnaires", "UTF-8");

                bufferedWriter.write(post_date);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();


                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";

                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                if (result.equals("New record created successfully")) {
                    try {
                        String roomNumber = params[10];
                        url = new URL(ServerUrl);
                        httpURLConnection = (HttpURLConnection) url.openConnection();
                        httpURLConnection.setRequestMethod("POST");

                        /*********/
                        httpURLConnection.setDoOutput(true);
                        httpURLConnection.setDoInput(true);
                        /************/

                        outputStream = httpURLConnection.getOutputStream();
                        bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                        post_date = URLEncoder.encode("room_num", "UTF-8") + "=" + URLEncoder.encode(roomNumber, "UTF-8")
                                + "&" + URLEncoder.encode("todo", "UTF-8") + "=" + URLEncoder.encode("updateDidQuestionnaires", "UTF-8");

                        bufferedWriter.write(post_date);
                        bufferedWriter.flush();
                        bufferedWriter.close();
                        outputStream.close();


                        inputStream = httpURLConnection.getInputStream();
                        bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                        result = "";
                        line = "";

                        while ((line = bufferedReader.readLine()) != null) {
                            result += line;
                        }
                        bufferedReader.close();
                        inputStream.close();
                        httpURLConnection.disconnect();

                        return result;


                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

                return result;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (type.equals("didQuestionnairesBefore")) {
            typeToCheck = "didQuestionnairesBefore";
            String result = handleBack(type,params,ServerUrl);
            return result;
        } else if (type.equals("insertNewRequest")) {
            typeToCheck = "insertNewRequest";
            String result = handleBack(type,params,ServerUrl);
            return result;


        } else if (type.equals("orderTaxi")) {
            typeToCheck = "orderTaxi";
            String result = handleBack(type,params,ServerUrl);
            return result;
        }
        else if (type.equals("RateEmployee")) {
            typeToCheck = "RateEmployee";
            String result = handleBack(type,params,ServerUrl);
            return result;
        }
        else if (type.equals("RateHotel")) {
            typeToCheck = "RateHotel";
            String result = handleBack(type,params,ServerUrl);
            return result;
        }
        else if (type.equals("rateDish")) {
            typeToCheck = "rateDish";
            String result = handleBack(type,params,ServerUrl);
            return result;
        }
        else if (type.equals("rateTrip")) {
            typeToCheck = "rateTrip";
            String result = handleBack(type,params,ServerUrl);
            return result;
        }




        return null;

    }

    public static String handleBack(String type, String[] params, String urlStr) {
        try {

            //String roomNum = params[1];
            //String password = params[2];

            URL url = new URL(urlStr);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");

            /*********/
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            /************/


            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

            String post_date = "";
            for (int i = 1; i <= params.length; i++) {
                if (i == 1) {
                    post_date += URLEncoder.encode("params" + i, "UTF-8") + "=" + URLEncoder.encode(params[i], "UTF-8");
                } else if (i == params.length) {
                    post_date += "&" + URLEncoder.encode("todo", "UTF-8") + "=" + URLEncoder.encode(type, "UTF-8");
                } else
                    post_date += "&" + URLEncoder.encode("params" + i, "UTF-8") + "=" + URLEncoder.encode(params[i], "UTF-8");
            }


            bufferedWriter.write(post_date);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();


            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            String result = "";
            String line = "";

            while ((line = bufferedReader.readLine()) != null) {
                result += line;
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();

            return result;


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    protected void onPreExecute () {

        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Login Status");



    }

    @Override
    protected void onPostExecute (String result){
        if (typeToCheck == "questionnaires") {
            Intent intent = new Intent("resultIntent3");
            intent.putExtra("result", result);
            context.sendBroadcast(intent);

        }
        else if(typeToCheck == "didQuestionnairesBefore")
        {
            Intent intent = new Intent("resultIntent2");
            intent.putExtra("result", result);
            context.sendBroadcast(intent);

        }


        else if (typeToCheck == "insertNewRequest")
        {
            Intent intent = new Intent("resultIntent4");
            intent.putExtra("result", result);
            context.sendBroadcast(intent);

        }
        else if (typeToCheck == "RateEmployee")
        {
            Intent intent = new Intent("RateIntent");
            intent.putExtra("result", result);
            context.sendBroadcast(intent);

        }
        else if (typeToCheck == "RateHotel")
        {
            Intent intent = new Intent("RateIntent");
            intent.putExtra("result", result);
            context.sendBroadcast(intent);

        }

        else if (typeToCheck == "rateDish")
        {
            Intent intent = new Intent("rateDish");
            intent.putExtra("result", result);
            context.sendBroadcast(intent);

        }
        else if(typeToCheck == "rateTrip")
        {
            Intent intent = new Intent("rateTrip");
            intent.putExtra("result", result);
            context.sendBroadcast(intent);
        }
        else
        {
            Intent intent = new Intent("resultIntent");
            intent.putExtra("result", result);
            context.sendBroadcast(intent);

        }



    }

    @Override
    protected void onProgressUpdate (Void...values){
        super.onProgressUpdate(values);
    }
}

