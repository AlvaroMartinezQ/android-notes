package com.urjc.android_notes;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.urjc.android_notes.generic.GenericValues;

import java.util.ArrayList;

public class NoteNewEdit extends GenericValues {
    /*
    Class to create or edit a note
     */

    ListView tagList;
    ArrayList<String> tags;
    ArrayAdapter<String> adapter;

    EditText newTagText;
    ImageView addTagBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_new_edit);

        tagList = findViewById(R.id.tagListView);
        newTagText = findViewById(R.id.newTag);
        addTagBtn = findViewById(R.id.newTagBtn);

        tagList.setTranscriptMode(ListView.TRANSCRIPT_MODE_NORMAL);
        tagList.setStackFromBottom(true);

        tags = new ArrayList<>();

        tagList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                toastIt("Removed " + tags.get(i));
                removeTag(i);
                return false;
            }
        });

        adapter = new ArrayAdapter<>(
                getApplicationContext(), android.R.layout.simple_list_item_1, tags
        );
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

    public void removeTag(int i) {
        tags.remove(i);
        adapter.notifyDataSetChanged();
    }

}