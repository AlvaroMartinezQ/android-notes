package com.urjc.android_notes.generic;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;

import androidx.appcompat.app.AppCompatActivity;

import com.urjc.android_notes.R;

public class GenericValues extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>\tNotes App</font>"));
        getSupportActionBar().setIcon(R.drawable.app_icon);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#DEA049")));
    }
}
