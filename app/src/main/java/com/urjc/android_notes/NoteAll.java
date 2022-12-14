package com.urjc.android_notes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

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
        setContentView(R.layout.activity_note_all);
        super.onCreate(savedInstanceState);

        // Get the layout references
        noteList = findViewById(R.id.noteListView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        fetchData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        activateMusicBtn();
    }

    private void fetchData() {
        // Get the database & notes
        NotesRDatabase db = NotesRDatabase.getDatabase(this.getApplicationContext());
        NoteDAO nd = db.noteDao();
        notes = new ArrayList<>(nd.getAllNotes());

        noData = findViewById(R.id.noData);

        if(notes.size() > 0) {
            // Display notes only if list size is greater than 0
            adapter = new NotesListAdapter(getApplicationContext(), notes);
            noteList.setAdapter(adapter);
        } else {
            // Display a message if no notes exist in the app
            displayNoData();
        }
    }

    public static void removeNote(@NonNull View view, int position) {
        notes.remove(position);
        adapter = new NotesListAdapter(view.getContext(), notes);
        noteList.setAdapter(adapter);
        if (notes.size() <= 0) {
            displayNoData();
        }
    }

    public static void displayNoData() {
        noData.setText(R.string.txt_create_note_first);
        noData.setCompoundDrawablesWithIntrinsicBounds(R.drawable.stars, 0, 0, 0);
    }

    public void back(View view) {
        Intent home = new Intent(this, NotesHome.class);
        startActivity(home);
    }

}