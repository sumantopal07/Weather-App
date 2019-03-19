package com.example.android.weatherapp;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

class fetchdataAgain extends AsyncTask<Void,Void,Void> {
    String data ="";
    String dataParsed = "";
    String singleParsed ="";
    //float temp;
    float minimum,maximum;
    String q="";
    public fetchdataAgain()
    {

    }
    public fetchdataAgain(String x)
    {
        q=x;//System.out.print(q);
    }
    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url = new URL("http://dataservice.accuweather.com/forecasts/v1/daily/1day/"+q+"?apikey=7GOc3AcSTTvYpQcnmjvSJMklUncJnJh5");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while(line != null){
                line = bufferedReader.readLine();
                data = data + line;
            }

            JSONObject JA = new JSONObject(data);
            //for(int i =0 ;i <JA.length(); i++){
            JSONArray JO = (JSONArray) JA.get("DailyForecasts");
            JSONObject JE = (JSONObject) JO.get(0);
            JSONObject JF = (JSONObject) JE.get("Temperature");
            JSONObject JG = (JSONObject) JF.get("Minimum");
            JSONObject JK = (JSONObject) JF.get("Maximum");
            minimum=Float.parseFloat(JG.get("Value")+"");
            minimum=(float)((minimum-32)*(0.5556));
            maximum=Float.parseFloat(JK.get("Value")+"");
            maximum=(float)((maximum-32)*(0.5556));
            singleParsed = maximum+"";
            dataParsed = minimum+"" ;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        MainActivity.max.setText(this.singleParsed);
        MainActivity.min.setText(this.dataParsed);

    }
}
