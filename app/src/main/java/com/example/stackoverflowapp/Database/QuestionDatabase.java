package com.example.stackoverflowapp.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.stackoverflowapp.Database.Question;
import com.example.stackoverflowapp.Database.QuestionDao;

@Database(entities = {Question.class}, version = 1)
public abstract class QuestionDatabase extends RoomDatabase {
    public abstract QuestionDao GetQuestionDao();
}
