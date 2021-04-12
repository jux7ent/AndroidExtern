package com.example.stackoverflowapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stackoverflowapp.Database.Question;

import java.util.List;

public class QuestionsActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private RecyclerView questionsRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_questions);

        InitViews();

        ShowProgressBar();

        StackoverflowLoader.GetQuestions(getApplicationContext(), new QuestionLoadedDelegate() {
            @Override
            public void OnTaskComplete(List<Question> questions) {
                questionsRV.setAdapter(new QuestionAdapter(questions));
                ShowQuestionsList();
            }
        });
    }

    private void InitViews() {
        questionsRV = findViewById(R.id.questions_list);
        questionsRV.setLayoutManager(new LinearLayoutManager(this));
        questionsRV.setAdapter(null);

        progressBar = findViewById(R.id.questions_progres_bar);
    }

    private void ShowProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
        questionsRV.setVisibility(View.INVISIBLE);
    }

    private void ShowQuestionsList() {
        progressBar.setVisibility(View.INVISIBLE);
        questionsRV.setVisibility(View.VISIBLE);
    }
}