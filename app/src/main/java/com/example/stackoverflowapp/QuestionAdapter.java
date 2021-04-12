package com.example.stackoverflowapp;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stackoverflowapp.Database.Question;

import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {

    private List<Question> questions;
    private ViewGroup parent;

    public static class QuestionViewHolder extends RecyclerView.ViewHolder {
        private final TextView questionTitle;
        private final TextView questionAuthor;
        private final TextView questionScore;

        public QuestionViewHolder(View view) {
            super(view);

            questionTitle = (TextView) view.findViewById(R.id.question_title);
            questionAuthor = (TextView) view.findViewById(R.id.question_author);
            questionScore = (TextView) view.findViewById(R.id.question_score);
        }
    }

    public QuestionAdapter(List<Question> questionsDataSet) {
        this.questions = questionsDataSet;
    }

    @Override
    public QuestionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.parent = parent;

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.question_row, parent, false);

        return new QuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
        Question question = questions.get(position);

        holder.questionAuthor.setText(question.ownerName);
        holder.questionTitle.setText(question.title);
        holder.questionScore.setText(String.valueOf(question.score));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RunAnswerActivity(question);
            }
        });
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    private void RunAnswerActivity(Question question) {
        Intent intent = new Intent(parent.getContext(), AnswerActivity.class);
        intent.putExtra(AnswerActivity.QUESTION_TAG, question);
        parent.getContext().startActivity(intent);
    }
}
