package com.matslath.matsprojekt;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private ArrayAdapter<Blomsterkvast> adapter;
    private ArrayList<Blomsterkvast> blommigheter = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Starta om sidan startar här
        Button startaOmsidan = findViewById(R.id.startaAbout);

        startaOmsidan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View niceViewOpener) {
                Intent intent = new Intent(MainActivity.this, about.class);
                intent.putExtra("name", "Ett värde som skickas med från MainActivity.java");
                startActivity(intent);
            }
        });

        //Adapter och hämtning av json data till listview börjar här
        adapter = new ArrayAdapter<Blomsterkvast>(this,R.layout.blommig_lista,R.id.textViewBlommListan, blommigheter);
        ListView myListview = findViewById(R.id.listigtViewId);
        myListview.setAdapter(adapter);
        myListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "Blomman " + blommigheter.get(position) + " säljs av " + blommigheter.get(position).getCompany() + ". Blomman trivs bäst " + blommigheter.get(position).getLocation() + " och kostar "+ blommigheter.get(position).getCost() +":- och är ca " + blommigheter.get(position).getSize() + "cm stor.", Toast.LENGTH_SHORT).show();
            }
        });
        new JsonTask().execute("https://wwwlab.iit.his.se/brom/kurser/mobilprog/dbservice/admin/getdataasjson.php?type=i19matla");
    }

    //Internetkod börjar här

    @SuppressLint("StaticFieldLeak")
    private class JsonTask extends AsyncTask<String, String, String> {

        private HttpURLConnection connection = null;
        private BufferedReader reader = null;

        protected String doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuilder builder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null && !isCancelled()) {
                    builder.append(line).append("\n");
                }
                return builder.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
        //Användande och utskrift av data till appen samt loggen startar här
        @Override
        protected void onPostExecute(String json) {
            Log.d("TAG", json);
            Gson gson = new Gson();
            Blomsterkvast[] blommor = gson.fromJson(json, Blomsterkvast[].class);
            blommigheter.clear();

            for(int i = 0; i < blommor.length; i++) {
                Log.d("Shottabalulu", "onPostExecute: Berget heter " + blommor[i].getName());
                blommigheter.add(blommor[i]);
            }

            adapter.notifyDataSetChanged();
        }
    }
}