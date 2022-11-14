package com.urjc.android_notes.generic;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.urjc.android_notes.R;
import com.urjc.android_notes.dao.NoteDAO;
import com.urjc.android_notes.database.NotesRDatabase;

public class GenericValues extends AppCompatActivity {

    // NotesRDatabase db = Room.databaseBuilder(getApplicationContext(), NotesRDatabase.class, "notes_db").build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>\tNotes App</font>"));
        getSupportActionBar().setIcon(R.drawable.app_icon);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#DEA049")));
        // NoteDAO nd = db.noteDao();
        // nd.getAllNotes();
    }

    public void toastIt(String text) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }
}
