package com.example.stackoverflowapp.Database;

import android.content.Context;
import android.provider.ContactsContract;

import androidx.room.Room;

public class Database {
    private static Database instance;
    public QuestionDatabase questionDatabase;

    private Database() {}

    public static Database GetInstance(Context context) {
        if (instance == null) {
            instance = new Database();

            instance.questionDatabase = Room.databaseBuilder(context,
                    QuestionDatabase.class, "questions_database").build();
        }

        return instance;
    }
}
