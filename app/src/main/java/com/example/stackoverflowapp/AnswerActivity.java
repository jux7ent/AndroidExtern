package com.example.stackoverflowapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.stackoverflowapp.Database.Answer;
import com.example.stackoverflowapp.Database.Question;
import android.text.Html;

import java.util.List;

public class AnswerActivity extends AppCompatActivity {
    public static final String QUESTION_TAG = "QuestionID";

    private RecyclerView answersRV;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        Question question = (Question) getIntent().getSerializableExtra(QUESTION_TAG);

        InitViews();
        SetQuestionData(question);

        ShowProgressBar();
        StackoverflowLoader.GetAnswers(question.id, new AnswerLoadedDelegate() {
            @Override
            public void OnTaskComplete(List<Answer> answerList) {
                answersRV.setAdapter(new AnswerAdapter(answerList));
                ShowAnswersList();
            }
        });
    }

    private void SetQuestionData(Question question) {
        ((TextView) findViewById(R.id.question_author_answer)).setText(question.ownerName);
        ((TextView) findViewById(R.id.question_title_answer)).setText(question.title);
        ((TextView) findViewById(R.id.question_body_answer)).setText(Html.fromHtml(question.body));
        ((TextView) findViewById(R.id.question_score_answer)).setText(String.valueOf(question.score));
    }

    private void InitViews() {
        answersRV = findViewById(R.id.answers_list);
        answersRV.setLayoutManager(new LinearLayoutManager(this));
        answersRV.setAdapter(null);

        progressBar = findViewById(R.id.answers_progress_bar);
    }

    private void ShowProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
        answersRV.setVisibility(View.INVISIBLE);
    }

    private void ShowAnswersList() {
        progressBar.setVisibility(View.INVISIBLE);
        answersRV.setVisibility(View.VISIBLE);
    }
}