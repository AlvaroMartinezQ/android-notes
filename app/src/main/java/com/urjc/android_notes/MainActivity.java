package com.urjc.android_notes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.urjc.android_notes.generic.GenericValues;

public class MainActivity extends GenericValues {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}