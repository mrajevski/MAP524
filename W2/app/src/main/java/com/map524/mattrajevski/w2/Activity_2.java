package com.map524.mattrajevski.w2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Activity_2 extends AppCompatActivity {

    public void buttonHandler(int buttonID) {
        Button button = (Button)findViewById(buttonID);
        switch (buttonID) {
            case R.id.ok_button:
                String s = ((EditText)findViewById(R.id.A2Text)).getText().toString();
                String msg = (!s.equalsIgnoreCase("")) ? s : "No message :(";
                setResult(RESULT_OK, new Intent().putExtra("msg", msg));
                finish();
                break;
            default:
                Toast.makeText(this, "Unhandled button at :" + button.toString(), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        findViewById(R.id.ok_button).setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buttonHandler(R.id.ok_button);
                }
            });
    }
}
