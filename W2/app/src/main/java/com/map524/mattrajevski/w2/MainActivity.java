package com.map524.mattrajevski.w2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    static final int A2INTENT = 0;

    public void buttonHandler(int buttonID) {
        Button button = (Button)findViewById(buttonID);
        switch (buttonID) {
            case R.id.A2_button:
                startActivityForResult(new Intent("com.map524.w2.mrrajevski"), 0);
                break;
            case R.id.A3_button:
                EditText greeting = (EditText)findViewById(R.id.greeting), id = (EditText)findViewById(R.id.sID),
                        name = (EditText)findViewById(R.id.sName), pin = (EditText)findViewById(R.id.PIN);
                startActivity(new Intent(this, Activity_3.class)
                        .putExtra("greeting", greeting.getText().toString())
                        .putExtra("id", id.getText().toString())
                        .putExtra("name", name.getText().toString())
                        .putExtra("pin", pin.getText().toString())
                );
                break;
            default:
                Log.i("BUTTON_HANDLER", "Unhandled button at " + button.toString());
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case A2INTENT:
                if (resultCode == RESULT_OK) {
                    Bundle msg = data.getExtras();
                    Toast.makeText(this, msg.getString("msg"), Toast.LENGTH_LONG).show();

                }
                else {
                    Toast.makeText(this, "FAILED", Toast.LENGTH_LONG).show();
                    Log.i("DEBUG", "Result canceled");
                }
                break;
            default:
                Log.i("DEBUG", "Unhandled requestCode - " + requestCode);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.A2_button).setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        buttonHandler(R.id.A2_button);
                    }
                }
        );

        findViewById(R.id.A3_button).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        buttonHandler(R.id.A3_button);
                    }
                }
        );
    }

    /* onActivityResult will be called by the Android framework once the activity that started with startActivityForResult() completes.
    *
    */

}
