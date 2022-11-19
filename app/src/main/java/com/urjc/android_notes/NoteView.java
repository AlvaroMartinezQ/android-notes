package com.urjc.android_notes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.urjc.android_notes.dao.NoteDAO;
import com.urjc.android_notes.database.NotesRDatabase;
import com.urjc.android_notes.generic.GenericValues;
import com.urjc.android_notes.models.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteView extends GenericValues {

    private Note note;
    private TextView title, description, date;
    private ImageView delete;
    private ListView tagList;
    private TagListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_view);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            title = findViewById(R.id.noteTitle);
            description = findViewById(R.id.noteDescription);
            date = findViewById(R.id.date);
            delete = findViewById(R.id.remove);

            note = (Note) getIntent().getSerializableExtra("NOTE");
            title.setText(note.title);
            description.setText(note.description);
            date.setText(note.dateCreated);
            if (note.tags != "") {
                // Render the tag list
                ArrayList<String> tags = new ArrayList<>();
                for (String s: note.tags.trim().split("\\s+")) {
                    tags.add(s);
                }
                tagList = findViewById(R.id.tagListView);
                adapter = new TagListAdapter(getApplicationContext(), tags);
                tagList.setAdapter(adapter);
            }

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Delete the note
                    NotesRDatabase db = NotesRDatabase.getDatabase(view.getContext());
                    NoteDAO nd = db.noteDao();
                    nd.deleteNote(note);

                    // Navigate to all notes tab
                    Intent noteAll = new Intent(view.getContext(), NoteAll.class);
                    startActivity(noteAll);
                    finish();
                }
            });
        } else {
            // If no data is passed, return to the all notes view
            // Should not happen
            Intent noteAll = new Intent(this, NoteAll.class);
            startActivity(noteAll);
            finish();
        }
    }

    public void edit(View view) {
        Intent editNote = new Intent(this, NoteNewEdit.class);
        editNote.putExtra("NOTE", note);
        editNote.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(editNote);
        finish();
    }

    public void goBack(View view) {
        Intent allNotes = new Intent(this, NoteAll.class);
        startActivity(allNotes);
    }

}