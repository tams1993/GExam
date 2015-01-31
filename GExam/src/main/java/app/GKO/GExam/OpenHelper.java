package app.GKO.GExam;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lilhazk on 11/02/14.
 */
public class OpenHelper extends SQLiteOpenHelper {

    private static final String DATABASAE_NAME = "Examdb.db";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_CREATE_TEACHER_USER_TABLE = "create table teacher_user_TABLE (_id integer primary key, "+" User text, Password text);";
    private static final String DATABASE_CREATE_STUDENT_USER_TABLE = "create table student_user_TABLE (_id integer primary key, "+" User text, Password text);";
    private static final String DATABASE_CREATE_GENERAL_QUESTION_TABLE = "create table general_question_TABLE (_id integer primary key, "+" Question text, Choice1 text, Choice2 text, Choice3 text, Choice4 text, TrueAnswer text);";

    private static final String DATABASE_CREATE_TEACHER_QUESTION_TABLE = "create table teacher_question_TABLE (_id integer primary key, "+" Question text, Choice1 text, Choice2 text, Choice3 text, Choice4 text, TrueAnswer text);";
    private static final String DATABASE_CREATE_SCORE_SMALLSCALE_TABLE = "create table score_smallscale_TABLE (_id integer primary key, "+" StudentID text, Name_Surname text, Class text, Score text, Date text);";
    private static final String DATABASE_CREATE_SCORE_GENERALIT_TABLE = "create table score_generalit_TABLE (_id integer primary key, "+" StudentID text, Name_Surname text, Class text, Score text, Date text);";



    public OpenHelper(Context context) {

        super(context, DATABASAE_NAME, null, DATABASE_VERSION);


    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(DATABASE_CREATE_TEACHER_USER_TABLE);
        sqLiteDatabase.execSQL(DATABASE_CREATE_STUDENT_USER_TABLE);
        sqLiteDatabase.execSQL(DATABASE_CREATE_GENERAL_QUESTION_TABLE);
        sqLiteDatabase.execSQL(DATABASE_CREATE_TEACHER_QUESTION_TABLE);
        sqLiteDatabase.execSQL(DATABASE_CREATE_SCORE_SMALLSCALE_TABLE);
        sqLiteDatabase.execSQL(DATABASE_CREATE_SCORE_GENERALIT_TABLE );


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }
}   //  end of OpenHelper
