package com.map524.mattrajevski.w2;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public void handleA2click() {
        Toast.makeText(this, "You clicked A2_button", Toast.LENGTH_LONG).show(); // Don't forget the .show() //

        // Use an implicit Intent to launch Activity_2 //
        Intent a2intent = new Intent("com.map524.lab2.mrrajevski");
        startActivity(a2intent);
        // startActivity() will not listen or handle a result back from secondActivity //
        // To use another activity as a "dialog", use startActivityForResult() instead //
        // startActivityForResult() expects an Intent


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Button Handler //
        // Get a reference to the button by using findViewById() which returns a java reference //
        // Define a click handling method for the button //
        // NOTE: this style is called an "Anonymous Temporary Class" //
        Button a2 = (Button)findViewById(R.id.A2_button);
        a2.setOnClickListener(
                new View.OnClickListener() { // Override the onClick method so that it is no longer abstract //
                    public void onClick(View v) { // Always add behaviour to a method defined in MainActivity to do the work //
                        handleA2click();
                    }
                }
        );
    }

    /* onActivityResult will be called by the Android framework once the activity that started with startActivityForResult() completes.
    *
    */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Handler //
        if (requestCode == 1) {
            // Check if user pressed OK or hit back button //
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra("message");
                //TextView text = (TextView)findViewById(R.id.);
                //text = ;
            }
        }
    }

}
