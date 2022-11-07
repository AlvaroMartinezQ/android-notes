package com.urjc.android_notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.urjc.android_notes.generic.GenericValues;

public class MainActivity extends GenericValues {

    public Button start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start = this.findViewById(R.id.buttonStart);
        /*
        Button one = this.findViewById(R.id.btnTest);
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.save);
        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.start();
            }
        });
         */
    }

    public void startApp(View view) {
        Intent startApp = new Intent(this, NotesHome.class);
        startActivity(startApp);
    }
}