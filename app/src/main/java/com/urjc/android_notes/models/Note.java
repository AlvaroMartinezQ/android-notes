package com.urjc.android_notes.models;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "app_note")
public class Note implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int uid;

    @NonNull
    @ColumnInfo(name = "title")
    public String title;

    @NonNull
    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name = "tags")
    public String tags;

    @NonNull
    @ColumnInfo(name = "date_created")
    public String dateCreated;

    public Note() {
        super();
    }

    public Note(String t, String d, String ta) {
        Date now = new Date();
        this.title = t;
        this.description = d;
        this.tags = ta;
        this.dateCreated = now.toString();
    }
}
