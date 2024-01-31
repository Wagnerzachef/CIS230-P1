package com.example.quiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

import com.example.quiz.QuizDatabase.*;
public class QuizDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "lotrQuiz.db";
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase db;

    public QuizDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
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
                QuestionsTable.COLUMN_ANSWER_NR + " INTEGER" +
                ")";

        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        fillQuestionsTable();
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
        onCreate(db);
    }

    private void fillQuestionsTable() {
        Question q1 = new Question("Who wrote the musical score for the Hobbit movies?", "Howard Shore", "Hans Zimmer", "John Williams", 1);
        addQuestion(q1);
        Question q2 = new Question("When did the first Lord of The Rings book come out?", "1956", "1954", "1955", 2);
        addQuestion(q2);
        Question q3 = new Question("When was Peter Jackson born?", "1960", "1959", "1961", 3);
        addQuestion(q3);
        Question q4 = new Question("When did the first Lord of The Rings Movie come out?", "2001", "2002", "2003", 1);
        addQuestion(q4);
        Question q5 = new Question("Where were the Lord of The Rings movies primarily filmed?", "America", "New Zealand", "France", 2);
        addQuestion(q5);
        Question q6 = new Question("Where was the animated version of the Lord of The Rings released?", "1983", "1980", "1978", 3);
        addQuestion(q6);
        Question q7 = new Question("How old is Bilbo Baggins at the start of The Fellowship Of The Ring?", "111", "110", "109", 1);
        addQuestion(q7);
        Question q8 = new Question("In what month were all the 'Lord of the Rings' movies released?", "October", "December", "September", 2);
        addQuestion(q8);
        Question q9 = new Question("What does Eowyn mean?", "Horse Joy", "Bird Fly", "Mouse Jump", 1);
        addQuestion(q9);
        Question q10 = new Question("The last bastion between Minas Tirith and Mordor was where?", "Dol Amorth", "Imladris", "Osgiliath", 3);
        addQuestion(q10);
    }

    private void addQuestion(Question question) {
        ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuestionsTable.COLUMN_OPTION1, question.getOption1());
        cv.put(QuestionsTable.COLUMN_OPTION2, question.getOption2());
        cv.put(QuestionsTable.COLUMN_OPTION3, question.getOption3());
        cv.put(QuestionsTable.COLUMN_ANSWER_NR, question.getAnswerNr());
        db.insert(QuestionsTable.TABLE_NAME, null, cv);
    }

    public List<Question> getAllQuestions() {
        List<Question> questionList = new ArrayList<>();
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
                questionList.add(question);
            } while (c.moveToNext());
        }

        c.close();
        return questionList;
    }
}
