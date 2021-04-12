package com.example.stackoverflowapp.Database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Question implements Serializable {

    @PrimaryKey(autoGenerate = false)
    public int id;

    @ColumnInfo(name="title")
    public String title;

    @ColumnInfo(name="body")
    public String body;

    @ColumnInfo(name="ownerName")
    public String ownerName;

    @ColumnInfo(name="score")
    public int score;

    private Question() {}

    public Question(int id, String title, String body, String ownerName, int score) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.ownerName = ownerName;
        this.score = score;
    }
}
