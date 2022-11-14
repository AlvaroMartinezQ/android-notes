package com.urjc.android_notes.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.urjc.android_notes.models.Note;

import java.util.List;

@Dao
public interface NoteDAO {
    @Query("SELECT * FROM app_note")
    List<Note> getAllNotes();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addNote(Note ... notes);
}
