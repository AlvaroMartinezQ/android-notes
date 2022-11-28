package com.urjc.android_notes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.urjc.android_notes.generic.GenericValues;

public class NotesHome extends GenericValues {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_home);
    }

    public void all(View view) {
        Intent allNotes = new Intent(this, NoteAll.class);
        startActivity(allNotes);
    }

    public void newOrEdit(View view) {
        Intent newNoteEdit = new Intent(this, NoteNewEdit.class);
        startActivity(newNoteEdit);
    }

    public void filter(View view) {
        Intent filter = new Intent(this, NoteSearches.class);
        startActivity(filter);
    }
}