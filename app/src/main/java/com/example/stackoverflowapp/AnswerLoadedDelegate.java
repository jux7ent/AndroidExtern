package com.example.stackoverflowapp;

import com.example.stackoverflowapp.Database.Answer;
import com.example.stackoverflowapp.Database.Question;

import java.util.List;

public interface AnswerLoadedDelegate {
    public void OnTaskComplete(List<Answer> answerList);
}
