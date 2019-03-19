package com.example.android.weatherapp;
//qsh6Ix4Ed02mohryVoj4MD8VFH19lpug
//4SQ95Z3dlLJ7kuuG5zd2ZFXx6MThDiHZ

import android.content.Intent;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

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
import java.util.ArrayList;
import java.util.Iterator;
public class MainActivity extends AppCompatActivity {



    private EditText city;
    private Button button,button2,button3;
    public static TextView max,min,qwe;
    public static String p="";
    public static String cityCode;
    public String ss="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
            city = findViewById(R.id.editText);
            button = findViewById(R.id.button);

        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);

        button2.setEnabled(false);
        button3.setEnabled(false);
        qwe=findViewById(R.id.textView);

        max = findViewById(R.id.MAX);
        min = findViewById(R.id.MIN);


            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    button2.setEnabled(false);
                    button3.setEnabled(false);
                    cityCode=new String(city.getText().toString().toLowerCase().trim());
                    fetchdata process=new fetchdata(cityCode);
                    process.execute();
                    Toast.makeText(MainActivity.this,"City is saved",Toast.LENGTH_LONG).show();
                    button2.setEnabled(true);

                  //  fetchdataAgain process1=new fetchdataAgain(p);
                    //process1.execute();

                }
            });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button3.setEnabled(false);

                fetchdataAgain process1=new fetchdataAgain(p);
                process1.execute();
                button3.setEnabled(true);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivity.this, SecondActivity.class));

            }
        });

        }
    }
class fetchdata extends AsyncTask<Void,Void,Void> {
    String data ="";
    String dataParsed = "";
    String singleParsed ="";
    String q="";
    public fetchdata()
    {

    }
    public fetchdata(String x)
    {
           q=x;//System.out.print(q);
    }
    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url = new URL("http://dataservice.accuweather.com/locations/v1/cities/search?apikey=7GOc3AcSTTvYpQcnmjvSJMklUncJnJh5&q="+q);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while(line != null){
                line = bufferedReader.readLine();
                data = data + line;
            }
            JSONArray JA = new JSONArray(data);
                JSONObject JO = (JSONObject) JA.get(0);
                singleParsed = JO.get("Key")+"";
                dataParsed = singleParsed ;
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

        //MainActivity.qwe.setText(this.singleParsed);
        MainActivity.p=this.dataParsed;

    }
}
