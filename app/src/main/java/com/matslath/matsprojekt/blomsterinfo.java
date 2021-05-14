package com.matslath.matsprojekt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class blomsterinfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blomsterinfo);

        TextView headingFlower = findViewById(R.id.headingFlower);
        TextView textView = findViewById(R.id.name);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String name = extras.getString("name");
            String company = extras.getString("company");
            String location = extras.getString("location");
            int cost = extras.getInt("cost");
            int size = extras.getInt("size");

            headingFlower.setText(name);
            textView.setText("Blomman " + name + " säljs av " + company + ". Blomman trivs bäst " + location + " och kostar " + cost + ":- och är ca " + size + "cm stor.");
        }

        Button close = findViewById(R.id.closeBlomsterinfo);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}