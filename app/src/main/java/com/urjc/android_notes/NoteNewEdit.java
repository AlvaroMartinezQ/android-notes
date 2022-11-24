package com.urjc.android_notes;

import static com.urjc.android_notes.database.NotesRDatabase.getDatabase;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.room.Room;

import com.urjc.android_notes.dao.NoteDAO;
import com.urjc.android_notes.database.NotesRDatabase;
import com.urjc.android_notes.generic.GenericValues;
import com.urjc.android_notes.models.Note;

import java.util.ArrayList;

public class NoteNewEdit extends GenericValues {
    /* -- Class to create or edit a note -- */

    private EditText titleInput, descriptionInput;

    static ListView tagList;
    static ArrayList<String> tags;
    static TagListAdapter adapter;

    Button addNoteBtn;

    EditText newTagText;
    ImageView addTagBtn;

    private Note existingNote = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_new_edit);

        titleInput = findViewById(R.id.inputTitle);
        descriptionInput = findViewById(R.id.inputDescription);

        tagList = findViewById(R.id.tagListView);
        newTagText = findViewById(R.id.newTag);
        addTagBtn = findViewById(R.id.newTagBtn);
        addNoteBtn = findViewById(R.id.button_add);

        tags = new ArrayList<>();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Note note = (Note) getIntent().getSerializableExtra("NOTE");
            titleInput.setText(note.title);
            descriptionInput.setText(note.description);
            if (note.tags != "") {
                // Split the tags by spaces: " " & add it into the tags
                for (String s: note.tags.trim().split("\\s+")) {
                    tags.add(s);
                }
            }
            existingNote = note;

            Button addNote = findViewById(R.id.button_add);
            addNote.setText("UPDATE");
            addNote.setBackgroundColor(Color.parseColor("#DEA049"));
        }

        adapter = new TagListAdapter(getApplicationContext(), tags, false);
        tagList.setAdapter(adapter);

        addTagBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tagText = newTagText.getText().toString();
                if (tagText == null || tagText.length() == 0) {
                    toastIt("Please input a tag.");
                } else {
                    addTag(tagText);
                    newTagText.getText().clear();
                    toastIt("Added tag: " + tagText);
                }
            }
        });
    }

    public void addTag(String tag) {
        tags.add(tag);
        adapter.notifyDataSetChanged();
    }

    public static void removeTag(@NonNull View view, int position) {
        tags.remove(position);
        adapter = new TagListAdapter(view.getContext(), tags, false);
        tagList.setAdapter(adapter);
    }

    public void back(View view) {
        if (existingNote != null) {
            Intent all = new Intent(this, NoteAll.class);
            startActivity(all);
        } else {
            Intent home = new Intent(this, NotesHome.class);
            startActivity(home);
            finish();
        }
    }

    public void saveNote(View view) {
        NotesRDatabase db = NotesRDatabase.getDatabase(this.getApplicationContext());
        NoteDAO nd = db.noteDao();

        String noteTitle = titleInput.getText().toString();
        String noteDescription = descriptionInput.getText().toString();
        if (!noteTitle.isEmpty() && !noteDescription.isEmpty()) {
            // Save the note
            if (existingNote != null) {
                // If it comes from the update button delete the note
                // and create it again. Better solution would be to
                // update the note ¯\_(ツ)_/¯
                nd.deleteNote(existingNote);
            }
            toastIt("Saving: " + noteTitle);
            String noteTags = "";
            for(String tag: tags) {
                noteTags += tag + " ";
            }
            Note note = new Note(noteTitle, noteDescription, noteTags.trim());
            nd.addNote(note);

            // Play a custom sound
            MediaPlayer mp = MediaPlayer.create(this, R.raw.save);
            mp.start();

            // Redirect to all notes view
            Intent allNotes = new Intent(this, NoteAll.class);
            startActivity(allNotes);
            // Finish this activity
            finish();
        } else {
            // Inform of bad / empty parameters
            toastIt("Please complete all values.");
        }
    }

}