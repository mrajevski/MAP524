package com.map524.mattrajevski.mysterybutton;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    LinearLayout linearLayout;
    LinearLayout.LayoutParams layoutParams;
    TextView t;
    AutoCompleteTextView a;

    public void handleButtonClick() {
        if (!a.getText().toString().equalsIgnoreCase("")) {
            String definition = a.getText().toString();
            switch (a.getText().toString().toLowerCase()) {
                case "activity":
                    definition += " - Is what is displayed to the user for interaction.";
                    break;
                case "avd":
                    definition += " - It manages all the virtual devices on the local machine.";
                    break;
                case "art":
                    definition += "";
                    break;
                case "dalvik":
                    definition += "";
                    break;
                case "intent":
                    definition += " - They're used to pass messages to other activities to preform a certain action.";
                    break;
                case "intent filter":
                    definition += "";
                    break;
                case "explicit intent":
                    definition += "";
                    break;
                case "implicit intent":
                    definition += " - ";
                    break;
                case "logcat":
                    definition += "";
                    break;
                case "bundle":
                    definition += "";
                    break;
                case "gradle":
                    definition += "";
                    break;
                case "android device monitor":
                    definition += "";
                    break;
                case "sdk manager":
                    definition += " - It displays a list of all sdk versions, and allows the developer to install/update/configure each one.";
                    break;
                case "minsdkversion":
                    definition += " - It is the lowest api level that the app can run on.";
                    break;
                default:
                    definition += " not found.";
            }
            t.setText(definition);
            linearLayout.addView(t, layoutParams);
            t = new TextView(this);
        }
        else {
            Toast.makeText(this, "Text box is empty.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linearLayout = (LinearLayout)findViewById(R.id.definitions);
        layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        t = new TextView(this);
        a = (AutoCompleteTextView)findViewById(R.id.autoText);
        a.setAdapter(
            new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.definitions)
            )
        );

        findViewById(R.id.mystery).setOnClickListener(
            new View.OnClickListener() {
                public void onClick(View v) {
                    handleButtonClick();
                }
            }
        );

        //TODO: change the code so there is a button handler that adds TextViews in response to button clicks
        //TODO: the text comes from the content the user entered into the AutoCompleteTextView
        //TODO: populate the "dictionary" used by the AutoCompleteTextView using an ArrayAdapter for Strings
    }
}
