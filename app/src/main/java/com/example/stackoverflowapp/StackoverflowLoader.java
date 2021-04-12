package com.example.stackoverflowapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;

import androidx.room.Room;

import com.example.stackoverflowapp.Database.Answer;
import com.example.stackoverflowapp.Database.Database;
import com.example.stackoverflowapp.Database.Question;
import com.example.stackoverflowapp.Database.QuestionDao;
import com.example.stackoverflowapp.Database.QuestionDatabase;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;
import okhttp3.OkHttpClient;
import okhttp3.Response;

import org.json.*;

public class StackoverflowLoader {
    private static final String API_URL = "https://api.stackexchange.com/2.2";

    private static final String QUESTION_REQ = "/questions?" +
            "page=1" + "&" +
            "pagesize=100" + "&" +
            "order=asc" + "&" +
            "sort=creation" + "&" +
            "tagged=android" + "&" +
            "site=stackoverflow" + "&" +
            "filter=withbody";

    private static String GetAnswerRequest(int questionId) {
        return "/questions/" + questionId + "/answers?" +
                "order=desc&" +
                "sort=votes&" +
                "site=stackoverflow&" +
                "filter=withbody";
    }

    public static void GetQuestions(Context context, QuestionLoadedDelegate questionLoadedDelegate) {
        QuestionsRequest request = new QuestionsRequest(questionLoadedDelegate);
        request.execute(context);
    }

    public static void GetAnswers(int questionId, AnswerLoadedDelegate answerLoadedDelegate) {
        AnswersRequest answersRequest = new AnswersRequest(answerLoadedDelegate);
        answersRequest.execute(questionId);
    }

    private static class AnswersRequest extends AsyncTask<Object, Void, List<Answer>> {
        private AnswerLoadedDelegate answerLoadedDelegate;

        public AnswersRequest(AnswerLoadedDelegate answerLoadedDelegate) {
            this.answerLoadedDelegate = answerLoadedDelegate;
        }

        @Override
        protected List<Answer> doInBackground(Object... args) {
            int questionId = (Integer) args[0];

            Request request = new Request.Builder()
                    .url(API_URL + GetAnswerRequest(questionId))
                    .build();

            OkHttpClient client = new OkHttpClient();

            List<Answer> answersList = new ArrayList<Answer>();

            try {
                Response response = client.newCall(request).execute();

                if (response.body() == null) {
                    MyDebug.Log("answers body is null");
                } else {
                    String responseBody = response.body().string();

                    JSONObject jsonObject = new JSONObject(responseBody);

                    JSONArray answers = jsonObject.getJSONArray("items");

                    for (int i = 0; i < answers.length(); ++i) {
                        JSONObject answerObject = answers.getJSONObject(i);

                        int answerId = answerObject.getInt("answer_id");
                        String answerBody = answerObject.getString("body");
                        int answerScore = answerObject.getInt("score");

                        answersList.add(new Answer(answerId, answerBody, answerScore));
                    }
                }
            } catch (Exception ex) {
                MyDebug.Log("Answers Response error: " + ex);
            }

            return answersList;
        }

        @Override
        protected void onPostExecute(List<Answer> answers) {
            super.onPostExecute(answers);

            answerLoadedDelegate.OnTaskComplete(answers);
        }
    }

    private static class QuestionsRequest extends AsyncTask<Object, Void, List<Question>> {
        private QuestionLoadedDelegate questionLoadedDelegate;

        public QuestionsRequest(QuestionLoadedDelegate questionLoadedDelegate) {
            this.questionLoadedDelegate = questionLoadedDelegate;
        }

        @Override
        protected List<Question> doInBackground(Object... args) {
            QuestionDatabase questionsDatabase = Database.GetInstance((Context) args[0]).questionDatabase;

            Request request = new Request.Builder()
                    .url(API_URL + QUESTION_REQ)
                    .build();

            OkHttpClient client = new OkHttpClient();

            List<Question> questionsList = new ArrayList<Question>(100);

            try {
                Response response = client.newCall(request).execute();

                if (response.body() == null) {
                    MyDebug.Log("response body is null");
                } else {
                    String responseBody = response.body().string();

                    JSONObject jsonObject = new JSONObject(responseBody);

                    JSONArray questions = jsonObject.getJSONArray("items");

                    for (int i = 0; i < questions.length(); ++i) {
                        JSONObject questionObject = questions.getJSONObject(i);

                        int questionId = questionObject.getInt("question_id");
                        String questionTitle = questionObject.getString("title");
                        String questionBody = questionObject.getString("body");
                        String questionOwnerName = questionObject.getJSONObject("owner").getString("display_name");
                        int questionScore = questionObject.getInt("score");

                        questionsList.add(new Question(questionId, questionTitle, questionBody, questionOwnerName, questionScore));
                    }
                }
            } catch (Exception ex) {
                MyDebug.Log("Response error: " + ex);
            }

            QuestionDao questionDao = questionsDatabase.GetQuestionDao();

            if (questionsList.size() == 0) {
                questionsList = questionDao.GetQuestions();
            } else {
                questionDao.InsertQuestions(questionsList);
            }

            return questionsList;
        }

        @Override
        protected void onPostExecute(List<Question> questions) {
            super.onPostExecute(questions);
            questionLoadedDelegate.OnTaskComplete(questions);
        }
    }
}
