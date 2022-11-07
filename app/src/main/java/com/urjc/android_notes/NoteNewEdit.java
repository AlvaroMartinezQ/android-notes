package com.urjc.android_notes;

import android.os.Bundle;

import com.urjc.android_notes.generic.GenericValues;

public class NoteNewEdit extends GenericValues {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_new_edit);
    }
}