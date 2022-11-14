package com.urjc.android_notes;

import static com.urjc.android_notes.database.NotesRDatabase.getDatabase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.room.Room;

import com.urjc.android_notes.dao.NoteDAO;
import com.urjc.android_notes.database.NotesRDatabase;
import com.urjc.android_notes.generic.GenericValues;
import com.urjc.android_notes.models.Note;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NoteNewEdit extends GenericValues {
    /* -- Class to create or edit a note -- */

    private EditText titleInput, descriptionInput;

    static ListView tagList;
    static ArrayList<String> tags;
    static TagListAdapter adapter;

    EditText newTagText;
    ImageView addTagBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_new_edit);

        titleInput = findViewById(R.id.inputTitle);
        descriptionInput = findViewById(R.id.inputDescription);

        tagList = findViewById(R.id.tagListView);
        newTagText = findViewById(R.id.newTag);
        addTagBtn = findViewById(R.id.newTagBtn);

        tags = new ArrayList<>();

        /*
        tagList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                toastIt("Removed " + tags.get(i));
                removeTag(i);
                return false;
            }
        });
        */

        adapter = new TagListAdapter(getApplicationContext(), tags);
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

    public static void removeTag(int i) {
        tags.remove(i);
        adapter.notifyDataSetChanged();
    }

    public void back(View view) {
        Intent homeScreen = new Intent(this, NotesHome.class);
        startActivity(homeScreen);
    }

    public void saveNote(View view) {
        NotesRDatabase db = NotesRDatabase.getDatabase(this.getApplicationContext());
        NoteDAO nd = db.noteDao();

        String noteTitle = titleInput.getText().toString();
        String noteDescription = descriptionInput.getText().toString();
        if (!noteTitle.isEmpty() && !noteDescription.isEmpty()) {
            // Save the note
            toastIt("Saving: " + noteTitle);
            String noteTags = "";
            for(String tag: tags) {
                noteTags += tag + " ";
            }
            Note note = new Note(noteTitle, noteDescription, noteTags.trim());
            nd.addNote(note);
        } else {
            // Inform of bad / empty parameters
            toastIt("Please complete all values.");
        }

        List<Note> notes;
        notes=nd.getAllNotes();
        toastIt(notes.size() + "");
    }

}