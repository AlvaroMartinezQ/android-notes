package com.urjc.android_notes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.urjc.android_notes.NoteNewEdit;
import com.urjc.android_notes.R;
import com.urjc.android_notes.dao.NoteDAO;
import com.urjc.android_notes.database.NotesRDatabase;
import com.urjc.android_notes.models.Note;

import java.util.ArrayList;

public class NotesListAdapter extends ArrayAdapter<Note> {

    ArrayList<Note> notes;
    Context context;

    public NotesListAdapter(Context context, ArrayList<Note> items) {
        super(context, R.layout.all_notes_list, items);
        this.notes = items;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.all_notes_list, null);

            TextView number = convertView.findViewById(R.id.number);
            number.setText(position + 1 + "."); // Does not require translation
            TextView title = convertView.findViewById(R.id.note_title);
            title.setText(notes.get(position).title);
            ImageView editBtn = convertView.findViewById(R.id.edit);
            ImageView viewBtn = convertView.findViewById(R.id.view);
            ImageView deleteBtn = convertView.findViewById(R.id.remove);

            editBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Navigate to the edit Activity
                    Intent editNote = new Intent(view.getContext(), NoteNewEdit.class);
                    editNote.putExtra("NOTE", notes.get(position));
                    editNote.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    view.getContext().startActivity(editNote);
                }
            });

            viewBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Navigate to the view Activity
                    Intent viewNote = new Intent(view.getContext(), NoteView.class);
                    viewNote.putExtra("NOTE", notes.get(position));
                    viewNote.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    view.getContext().startActivity(viewNote);
                }
            });

            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Remove the item if clicked
                    NotesRDatabase db = NotesRDatabase.getDatabase(view.getContext());
                    NoteDAO nd = db.noteDao();
                    Note toDelete = notes.get(position);
                    nd.deleteNote(toDelete);
                    NoteAll.removeNote(view, position);
                }
            });
        }
        return convertView;
    }
}
