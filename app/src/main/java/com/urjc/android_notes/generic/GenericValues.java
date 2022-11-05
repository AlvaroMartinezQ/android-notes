package com.urjc.android_notes.generic;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.urjc.android_notes.R;

public class GenericValues extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("\tNotes saver");
        getSupportActionBar().setIcon(R.drawable.app_icon);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#DEA049")));
    }
}
