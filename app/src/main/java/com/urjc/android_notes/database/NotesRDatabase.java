package com.urjc.android_notes.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.urjc.android_notes.dao.NoteDAO;
import com.urjc.android_notes.models.Note;

@Database(entities = {Note.class}, version = 1)
public abstract class NotesRDatabase extends RoomDatabase {
    /*
    The database implementation
     */

    public abstract NoteDAO noteDao();

    private static NotesRDatabase INSTANCE;

    public static NotesRDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                    context.getApplicationContext(), NotesRDatabase.class, "notes_database")
                    .allowMainThreadQueries().build();
        }
        return INSTANCE;
    }
}
