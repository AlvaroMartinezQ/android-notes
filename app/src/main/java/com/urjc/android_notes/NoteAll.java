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
    static TextView noData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_all);

        // Get the layout references
        noteList = findViewById(R.id.noteListView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        fetchData();
    }

    private void fetchData() {
        // Get the database & notes
        NotesRDatabase db = NotesRDatabase.getDatabase(this.getApplicationContext());
        NoteDAO nd = db.noteDao();
        notes = new ArrayList<>(nd.getAllNotes());

        if(notes.size() > 0) {
            // Display notes only if list size is greater than 0
            adapter = new NotesListAdapter(getApplicationContext(), notes);
            noteList.setAdapter(adapter);

        } else {
            // Display a message if no notes exist in the app
            noData = findViewById(R.id.noData);
            displayNoData();
        }
    }

    public static void removeNote(int position) {
        notes.remove(position);
        adapter.notifyDataSetChanged();
        if (notes.size() <= 0) {
            displayNoData();
        }
    }

    public static void displayNoData() {
        noData.setText(R.string.txt_create_note_first);
        noData.setCompoundDrawablesWithIntrinsicBounds(R.drawable.stars, 0, 0, 0);
    }

}