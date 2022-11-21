package com.urjc.android_notes;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.urjc.android_notes.dao.NoteDAO;
import com.urjc.android_notes.database.NotesRDatabase;
import com.urjc.android_notes.generic.GenericValues;
import com.urjc.android_notes.models.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteSearches extends GenericValues {

    Spinner options;
    String[] filters;
    TextView data;
    ListView noteList;
    NotesListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_searchs);

        filters = new String[] {"Title", "Description", "Tags"};
        options = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, filters);
        options.setAdapter(adapter);
        data = findViewById(R.id.data);
        noteList = findViewById(R.id.noteListView);
    }

    public void search(View view) {
        String spinnerSelection = options.getSelectedItem().toString();
        String userInput = data.getText().toString();
        NotesRDatabase db = NotesRDatabase.getDatabase(this.getApplicationContext());
        NoteDAO nd = db.noteDao();
        List<Note> notes;
        switch (spinnerSelection){
            case "Title": {
                notes = nd.getNotesByTitle(userInput);
                break;
            } case "Description": {
                notes = nd.getNotesByDescription(userInput);
                break;
            } case "Tags": {
                notes = nd.getNotesByTag(userInput);
                break;
            } default: {
                // Should not happen
                return;
            }
        }
        adapter = new NotesListAdapter(getApplicationContext(), new ArrayList<>(notes));
        noteList.setAdapter(adapter);
        toastIt(notes.size() + " notes found");
    }
}