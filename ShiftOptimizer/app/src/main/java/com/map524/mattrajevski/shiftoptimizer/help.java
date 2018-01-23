package com.map524.mattrajevski.shiftoptimizer;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class help extends AppCompatActivity {

    MediaPlayer up = null, down = null, more = null;
    boolean play = false;

    public void stop() {
        if (up.isPlaying()) {
            up.pause();
            //upSound.reset();
        }

        if (down.isPlaying()) {
            down.pause();
            //downSound.reset();
        }

        if (more.isPlaying()) {
            more.pause();
            //moreSound.reset();
        }
    }


    public void onClickHandler(int id) {
        switch (id) {
            case R.id.up:
                stop();
                if (!play) {
                    up.start();
                    play = true;
                }
                else {
                    play = false;
                }
                break;
            case R.id.down:
                stop();
                if (!play) {
                    down.start();
                    play = true;
                }
                else {
                    play = false;
                }
                break;
            case R.id.more:
                stop();
                if (!play) {
                    more.start();
                    play = true;
                }
                else {
                    play = false;
                }
                break;
            case R.id.no:
                Toast.makeText(this, "No sound is played as there is no action to be performed", Toast.LENGTH_LONG).show();
                break;
            default:
                Log.i("UNHANDLED CLICK: ", toString());
                break;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        up = MediaPlayer.create(this, R.raw.up_sound);
        down = MediaPlayer.create(this, R.raw.down_sound);
        more = MediaPlayer.create(this, R.raw.up_sound);

        findViewById(R.id.up).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickHandler(R.id.up);
            }
        });

        findViewById(R.id.down).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickHandler(R.id.down);
            }
        });

        findViewById(R.id.more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickHandler(R.id.more);
            }
        });

        findViewById(R.id.no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickHandler(R.id.no);
            }
        });
    }
}
