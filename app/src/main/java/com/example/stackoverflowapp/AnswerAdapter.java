package com.example.stackoverflowapp;

import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stackoverflowapp.Database.Answer;

import java.util.List;

public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.AnswerViewHolder> {

    private List<Answer> answers;

    public static class AnswerViewHolder extends RecyclerView.ViewHolder {
        private final TextView answerBody;
        private final TextView answerScore;

        public AnswerViewHolder(View view) {
            super(view);

            answerBody = (TextView) view.findViewById(R.id.answer_body);
            answerScore = (TextView) view.findViewById(R.id.answer_score);
        }
    }

    public AnswerAdapter(List<Answer> answersDataSet) {
        this.answers = answersDataSet;
    }

    @Override
    public AnswerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.answer_row, parent, false);

        return new AnswerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnswerViewHolder holder, int position) {
        Answer answer = answers.get(position);

        holder.answerBody.setText(Html.fromHtml(answer.body));
        holder.answerScore.setText(String.valueOf(answer.score));
    }

    @Override
    public int getItemCount() {
        return answers.size();
    }
}