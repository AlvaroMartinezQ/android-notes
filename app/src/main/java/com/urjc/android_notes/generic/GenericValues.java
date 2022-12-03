package com.urjc.android_notes.generic;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.urjc.android_notes.R;

public class GenericValues extends AppCompatActivity {

    MediaPlayer mp;
    protected boolean music = false; // False by default

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //activateMusicBtn();
    }

    public void activateMusicBtn() {
        SharedPreferences sp = getSharedPreferences("app_music", Context.MODE_PRIVATE);
        String spMusic = sp.getString("music", "");
        ImageView musicBtn = findViewById(R.id.music);
        toastIt(spMusic);
        if (spMusic.equals("ON")) {
            musicBtn.setImageResource(R.drawable.music_on);
            mp = MediaPlayer.create(getApplicationContext(), R.raw.sound);
        } else {
            musicBtn.setImageResource(R.drawable.music_off);
        }
        SharedPreferences.Editor editor = sp.edit();
        musicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!music) {
                    // Start playing
                    music = true;
                    musicBtn.setImageResource(R.drawable.music_on);
                    mp = MediaPlayer.create(getApplicationContext(), R.raw.sound);
                    mp.start();
                    editor.putString("music", "ON");
                } else {
                    // Stop playing
                    music = false;
                    musicBtn.setImageResource(R.drawable.music_off);
                    mp.stop();
                    mp.release();
                    editor.putString("music", "OFF");
                }
                editor.commit();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences sp = getSharedPreferences("app_music", Context.MODE_PRIVATE);
        String spMusic = sp.getString("music", "");
        if (spMusic.equals("ON")) {
            mp.stop();
            mp.release();
        }
    }

    public void toastIt(String text) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }
}
