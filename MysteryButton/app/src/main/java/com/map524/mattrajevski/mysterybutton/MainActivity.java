package com.map524.mattrajevski.mysterybutton;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //This will demonstrate how to add a View dynamically to one of your LinearLayouts at runtime

        //ex. create a new TextView and add it to your LinearLayout (vertical)
        TextView tempText = new TextView(this);
        tempText.setText("This is just to demo adding Views at runtime - you will add the text from the AutocompleteTextView");
        //add it to our LinearLayout
        //we must first get a reference to the LinearLayout...
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.main_layout);
        //before we add our TextView to the LinearLayout we need to specify some layout parameters..
        //we do this through a helper class called LayoutParams
        //LinearLayout.LayoutParams constructor arguments are (width suggestion, height suggestion)
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        //add the new view (in this case a TextView) to the LinearLayout
        linearLayout.addView(tempText, layoutParams);

        //TODO: change the code so there is a button handler that adds TextViews in response to button clicks
        //TODO: the text comes from the content the user entered into the AutoCompleteTextView
        //TODO: populate the "dictionary" used by the AutoCompleteTextView using an ArrayAdapter for Strings
    }
}
