package com.example.stackoverflowapp.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.stackoverflowapp.Database.Question;

import java.util.List;

@Dao
public interface QuestionDao {

    @Insert(onConflict= OnConflictStrategy.REPLACE)
    void InsertQuestions(List<Question> questions);

    @Insert(onConflict=OnConflictStrategy.REPLACE)
    void InsertQuestion(Question question);

    @Query("SELECT * FROM Question")
    List<Question> GetQuestions();

    @Query("SELECT * FROM Question WHERE id=:id")
    Question GetQuestionById(int id);
}
