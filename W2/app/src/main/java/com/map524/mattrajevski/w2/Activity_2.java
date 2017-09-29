package com.map524.mattrajevski.w2;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Activity_2 extends AppCompatActivity {

    public void ok_button_handler() {
        // Create a reply to send back to MainActivity using an intent to store the reply //

        Intent result = new Intent();
        // Get text in EditText box so it can be included in the reply //
        EditText editText = (EditText)findViewById(R.id.editText);
        // Extract string stored in editText //
        String message = editText.getText().toString();
        //add message as an extra to the "result" Intent //
        result.putExtra("message", message);

        // If the activity was started with startActivityForResult(), well will need the following line to end the activity //
        setResult(Activity.RESULT_OK, result);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        // Button handler for ok_button //

        Button ok = (Button)findViewById(R.id.ok_button);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ok_button_handler();
            }
            }
        );
    }
}
