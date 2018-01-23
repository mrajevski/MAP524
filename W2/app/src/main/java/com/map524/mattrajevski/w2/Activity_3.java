package com.map524.mattrajevski.w2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Activity_3 extends AppCompatActivity {


   public void setData() {
       Bundle extras = getIntent().getExtras();

       ((TextView)findViewById(R.id.A3greeting)).setText(extras.getString("greeting"));
       ((TextView)findViewById(R.id.A3name)).setText("My name is " + extras.getString("name") + "");
       ((TextView)findViewById(R.id.A3details)).setText("My student ID is " + extras.getString("id") + " and my PIN is " + extras.getString("pin"));
   }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);

        setData();
    }
}
