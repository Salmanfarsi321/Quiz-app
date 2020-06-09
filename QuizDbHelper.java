package com.example.myawesomequiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.example.myawesomequiz.QuizContract.*;

import java.util.ArrayList;
import java.util.List;

public class QuizDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MyAwesomeQuiz.db";
    private static final int DATABASE_VERSION = 1;
    private SQLiteDatabase db;

    public QuizDbHelper( Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuestionsTable.TABLE_NAME + " ( " +
                QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionsTable.COLUMN_ANSWER_NR + " INTEGER, " +
                QuestionsTable.COLUMN_DIFFICULTY + " TEXT" +
                ")";
        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        fillQuestionsTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
        onCreate(db);
    }

    private void fillQuestionsTable() {
        Question q1 = new Question("Who built the Jama masjid?", "Akbar", "Shahjahan", "Jahangir", 2,Question.DIFFICULTY_EASY);
        addQuestion(q1);
        Question q2 = new Question("Bihu is the folk dance of which state?", "Assam", "Manipur", "Sikkim", 1,Question.DIFFICULTY_EASY);
        addQuestion(q2);
        Question q3 = new Question("Who is the fastest man in the world?", "Milka singh", "Usain bolt", "Justin Gatlin", 2,Question.DIFFICULTY_EASY);
        addQuestion(q3);
        Question q4 = new Question("Which is the World's smallest country?", "Maldives", "Indonesia", "Vatican city", 3,Question.DIFFICULTY_EASY);
        addQuestion(q4);
        Question q5 = new Question("what is the color of octopus blood?", "Red", "Green", "Blue", 3,Question.DIFFICULTY_EASY);
        addQuestion(q5);
        Question q6 = new Question("which is the highest peak in India?", "Kanchenjunga", "Mt.Everest", "K2", 1,Question.DIFFICULTY_MEDIUM);
        addQuestion(q6);
        Question q7 = new Question("Who is known as the nightingale of India?", "Mother Teresa", "Indira Gandhi", "sarojini Naidu", 3,Question.DIFFICULTY_MEDIUM);
        addQuestion(q2);
        Question q8 = new Question("Where is the biggest statue of Jesus Christ?", "Brazil", "USA", "Australia", 1,Question.DIFFICULTY_MEDIUM);
        addQuestion(q8);
        Question q9 = new Question("who discovered zero?", "galilieo", "Aryabhatta", "Newton", 2,Question.DIFFICULTY_MEDIUM);
        addQuestion(q9);
        Question q10 = new Question("who was the first man or women to go into space?", "Yuri gagarin", "Neil Armstrong", "kalpana chawla", 1,Question.DIFFICULTY_MEDIUM);
        addQuestion(q10);
        Question q11 = new Question("who wrote the book Macbeth?", "J.K.Rowling", "Leo Tolstoy", "Shakespeare", 3,Question.DIFFICULTY_HARD);
        addQuestion(q11);
        Question q12 = new Question("Titanic sank in which ocean?", "Atlantic Ocean", "Pacific ocean", "Antartica ocean", 1,Question.DIFFICULTY_HARD);
        addQuestion(q12);
        Question q13 = new Question("what is the capital of Oman?", "Muscat", "Jeddah", "Riyadh", 1,Question.DIFFICULTY_HARD);
        addQuestion(q13);
        Question q14 = new Question("Brass is an alloy of?", "Copper and Iron", "Zinc and Iron", "Copper and Zinc", 3,Question.DIFFICULTY_HARD);
        addQuestion(q14);
        Question q15 = new Question("Durand Cup is associated with which Game?", "Volleyball", "Hockey", "Football", 3,Question.DIFFICULTY_HARD);
        addQuestion(q15);
    }

    private void addQuestion(Question question) {
        ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuestionsTable.COLUMN_OPTION1, question.getOption1());
        cv.put(QuestionsTable.COLUMN_OPTION2, question.getOption2());
        cv.put(QuestionsTable.COLUMN_OPTION3, question.getOption3());
        cv.put(QuestionsTable.COLUMN_ANSWER_NR, question.getAnswerNr());
        cv.put(QuestionsTable.COLUMN_DIFFICULTY, question.getDifficulty());
        db.insert(QuestionsTable.TABLE_NAME, null, cv);
    }

    public ArrayList<Question> getAllQuestions() {

        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME, null);
        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                question.setDifficulty(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_DIFFICULTY)));
                questionList.add(question);
            } while (c.moveToNext());
        }
        c.close();
        return questionList;
    }

    public ArrayList<Question> getQuestions(String difficulty) {

        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        String[] selectionArgs = new String[]{difficulty};
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME +
                " WHERE " + QuestionsTable.COLUMN_DIFFICULTY + " = ?", selectionArgs);
        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                question.setDifficulty(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_DIFFICULTY)));
                questionList.add(question);
            } while (c.moveToNext());
        }
        c.close();
        return questionList;
    }
}
