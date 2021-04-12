package com.example.stackoverflowapp;

import com.example.stackoverflowapp.Database.Question;

import java.util.List;

public interface QuestionLoadedDelegate {
    public void OnTaskComplete(List<Question> questions);
}
