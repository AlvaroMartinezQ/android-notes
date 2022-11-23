package com.urjc.android_notes;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.urjc.android_notes.dao.NoteDAO;
import com.urjc.android_notes.database.NotesRDatabase;
import com.urjc.android_notes.generic.GenericValues;
import com.urjc.android_notes.models.Note;
import com.urjc.android_notes.dialogs.CustomDatePicker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NoteSearches extends GenericValues implements DatePickerDialog.OnDateSetListener {

    Spinner options;
    String[] filters = {"Title", "Description", "Tags", "Date"};
    TextView data;
    ListView noteList;
    NotesListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_searchs);

        options = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, filters);
        options.setAdapter(adapter);
        data = findViewById(R.id.data);
        noteList = findViewById(R.id.noteListView);

        options.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView.getItemAtPosition(i).toString() == filters[3]) {
                    openDateDialog();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Should not happen
            }
        });
    }

    public void search(View view) {
        String spinnerSelection = options.getSelectedItem().toString();
        String userInput = data.getText().toString();
        toastIt(userInput);
        if (userInput != "") {
            NotesRDatabase db = NotesRDatabase.getDatabase(this.getApplicationContext());
            NoteDAO nd = db.noteDao();
            List<Note> notes;
            switch (spinnerSelection) {
                case "Title": {
                    notes = nd.getNotesByTitle(userInput);
                    break;
                }
                case "Description": {
                    notes = nd.getNotesByDescription(userInput);
                    break;
                }
                case "Tags": {
                    notes = nd.getNotesByTag(userInput);
                    break;
                }
                default: {
                    // Should not happen
                    return;
                }
            }
            adapter = new NotesListAdapter(getApplicationContext(), new ArrayList<>(notes));
            noteList.setAdapter(adapter);
            toastIt(notes.size() + " note(s) found");
        }
    }

    public void openDateDialog() {
        DialogFragment datePicker = new CustomDatePicker();
        datePicker.show(getSupportFragmentManager(), "Date picker");
    }

    @Override
    public void onDateSet(DatePicker datePicker, int y, int m, int d) {
        // Get the database
        NotesRDatabase db = NotesRDatabase.getDatabase(this.getApplicationContext());
        NoteDAO nd = db.noteDao();
        List<Note> notes = nd.getAllNotes();
        List<Note> filteredNotes = new ArrayList<>();
        // Search for date
        int c = 0;
        for (Note note: notes) {
            try {
                // Get the date and parse it back from String to Date
                Date noteDate = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(note.dateCreated);
                // Generate a calendar to get the date values
                Calendar cal = Calendar.getInstance();
                cal.setTime(noteDate);
                // Compare them to the passed ones from the datePicker
                if (cal.get(Calendar.YEAR) == y && cal.get(Calendar.MONTH) == m && cal.get(Calendar.DAY_OF_MONTH) == d) {
                    c++;
                    filteredNotes.add(note);
                }
            } catch (Exception e) {
                toastIt("Exception!");
            }
        }
        adapter = new NotesListAdapter(getApplicationContext(), new ArrayList<>(filteredNotes));
        noteList.setAdapter(adapter);
        toastIt(filteredNotes.size() + " note(s) found");
    }
}