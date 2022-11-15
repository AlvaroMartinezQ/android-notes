package com.urjc.android_notes;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.urjc.android_notes.dao.NoteDAO;
import com.urjc.android_notes.database.NotesRDatabase;
import com.urjc.android_notes.generic.GenericValues;
import com.urjc.android_notes.models.Note;

import java.util.ArrayList;

public class NoteAll extends GenericValues {

    static ListView noteList;
    static ArrayList<Note> notes;
    static NotesListAdapter adapter;
    TextView noData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_all);

        // Get the layout references
        noteList = findViewById(R.id.noteListView);

        // Get the database & notes
        NotesRDatabase db = NotesRDatabase.getDatabase(this.getApplicationContext());
        NoteDAO nd = db.noteDao();
        notes = new ArrayList<>(nd.getAllNotes());
        // db.close();

        if(notes.size() > 0) {
            // Display notes only if list size is greater than 0
            adapter = new NotesListAdapter(getApplicationContext(), notes);
            noteList.setAdapter(adapter);

        } else {
            // Display a message if no notes exist in the app
            noData = findViewById(R.id.noData);
            noData.setText("Create a new note first!");
            noData.setCompoundDrawablesWithIntrinsicBounds(R.drawable.stars, 0, 0, 0);
        }
    }

    public static void removeNote(int position) {
        notes.remove(position);
        adapter.notifyDataSetChanged();
    }

}