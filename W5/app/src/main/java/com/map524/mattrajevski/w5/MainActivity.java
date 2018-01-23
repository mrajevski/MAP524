package com.map524.mattrajevski.w5;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    // Java references resources using an int instead of an address
    public static int[] icons = { R.drawable.google, R.drawable.reddit, R.drawable.android };
    Context c;

    public void openWebpage(String url) {
        Uri webpage = Uri.parse(url);
        Intent open = new Intent(Intent.ACTION_VIEW, webpage);
        if (open.resolveActivity(getPackageManager()) != null) {
            startActivity(open);
        }

    }

    public void onClickHandler(int ID) {
        switch (ID) {
            case R.id.help:
                    Toast.makeText(this, "The main screen provides links to different webpages stored in an array", Toast.LENGTH_LONG).show();
                break;
            case R.id.about:
                    startActivity(new Intent(this, about.class));
                break;
            default:
                Toast.makeText(this, "Unhandled click: " + ID, Toast.LENGTH_SHORT).show();
                break;

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.about:
                onClickHandler(R.id.about);
                return true;
            case R.id.help:
                onClickHandler(R.id.help);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        c = this;
        final String[] descriptions = getResources().getStringArray(R.array.description_array),
        URIs = getResources().getStringArray(R.array.uri_array);

        ListView listView = (ListView)findViewById(R.id.urlList);
        customAdapter customList = new customAdapter(this, descriptions, icons);
        listView.setAdapter(customList);


        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        openWebpage(URIs[position]);
                        Log.i("DEBUG", position + "");
                    }
                }
        );


    }
}
