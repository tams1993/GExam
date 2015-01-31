package app.GKO.GExam;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by lilhazk on 14/02/14.
 */
public class GeneralITQuestionTable {

    private OpenHelper objOpenHelper;
    private SQLiteDatabase writeSQLite, readSQLite;

    public static final String TEACHER_QUESTION_TABLE = "general_question_TABLE";
    public static final String COL_ID = "_id";
    public static final String COL_QUESTION = "Question";
    public static final String COL_CHOICE1 = "Choice1";
    public static final String COL_CHOICE2 = "Choice2";
    public static final String COL_CHOICE3 = "Choice3";
    public static final String COL_CHOICE4 = "Choice4";
    public static final String COL_TRUEANSWER = "TrueAnswer";

    public GeneralITQuestionTable(Context context) {

        objOpenHelper = new OpenHelper(context);
        writeSQLite = objOpenHelper.getWritableDatabase();
        readSQLite = objOpenHelper.getReadableDatabase();


    }   //  end of Constructor

    public long AddDataGeneralITToSQLite(String strQuestion, String strChoice1, String strChoice2, String strChoice3, String strChoice4, String strTrueAnswer) {

        ContentValues objContentValues = new ContentValues();
        objContentValues.put(COL_QUESTION, strQuestion);
        objContentValues.put(COL_CHOICE1, strChoice1);
        objContentValues.put(COL_CHOICE2, strChoice2);
        objContentValues.put(COL_CHOICE3, strChoice3);
        objContentValues.put(COL_CHOICE4, strChoice4);
        objContentValues.put(COL_TRUEANSWER, strTrueAnswer);

        return writeSQLite.insert(TEACHER_QUESTION_TABLE, null, objContentValues);
    }

    public Cursor ReadAllData() {

        Cursor objCursor = readSQLite.query(TEACHER_QUESTION_TABLE, new String[]{COL_ID, COL_QUESTION, COL_CHOICE1, COL_CHOICE2, COL_CHOICE3, COL_CHOICE4, COL_TRUEANSWER}, null, null, null, null, null);

        if (objCursor != null) {
            objCursor.moveToFirst();

        }

        return objCursor;

    }   //  end of ReadAllData





}   //  end of SmallScaleQuestionTable
