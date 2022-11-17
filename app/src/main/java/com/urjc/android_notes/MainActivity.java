package com.urjc.android_notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.urjc.android_notes.generic.GenericValues;

public class MainActivity extends GenericValues {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void login(View view) {
        setContentView(R.layout.activity_main);
    }

    public void startApp(View view) {
        Intent startApp = new Intent(this, NotesHome.class);
        startActivity(startApp);
    }
}