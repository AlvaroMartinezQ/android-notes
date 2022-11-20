package com.urjc.android_notes;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.urjc.android_notes.generic.GenericValues;

public class NoteSearches extends GenericValues {

    Spinner options;
    String[] filters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_searchs);

        filters = new String[] {"Title", "Description"};
        options = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, filters);
        options.setAdapter(adapter);
    }
}