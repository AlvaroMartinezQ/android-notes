package com.urjc.android_notes.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.urjc.android_notes.models.Note;

import java.util.List;

@Dao
public interface NoteDAO {
    @Query("SELECT * FROM app_note")
    List<Note> getAllNotes();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addNote(Note ... notes);

    @Delete
    void deleteNote(Note note);

    @Query("SELECT * FROM app_note WHERE title = :title")
    List<Note> getNotesByTitle(String title);

    @Query("SELECT * FROM app_note WHERE description = :description")
    List<Note> getNotesByDescription(String description);

    @Query("SELECT * FROM app_note WHERE tags LIKE '%' || :tag || '%'")
    List<Note> getNotesByTag(String tag);

    @Query("DELETE FROM app_note")
    void deleteAllNotes();
}
