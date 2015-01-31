package app.GKO.GExam;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by TAM-NB on 2/25/14.
 */
public class ScoreGeneralITTable {

    private OpenHelper objHelper;
    private SQLiteDatabase writeSQLite, readSQLite;

    public static final String SCORE_GENERALIT_TABLE = "score_generalit_TABLE";
    public static final String COL_ID = "_id";
    public static final String COL_STUDENTID = "StudentID";
    public static final String COL_NAMESURNAME = "Name_Surname";
    public static final String COL_CLASS = "Class";
    public static final String COL_SCORE = "Score";
    public static final String COL_DATE = "Date";

    public ScoreGeneralITTable(Context context) {

        objHelper = new OpenHelper(context);
        writeSQLite = objHelper.getWritableDatabase();
        readSQLite = objHelper.getReadableDatabase();



    }   //  end of Constructor

    public long AddDataScoreToSQLite(String strStudentID, String strNameSurname, String strClass, String strScore, String strDate) {

        ContentValues objContentValues = new ContentValues();
        objContentValues.put(COL_STUDENTID, strStudentID);
        objContentValues.put(COL_NAMESURNAME, strNameSurname);
        objContentValues.put(COL_CLASS, strClass);
        objContentValues.put(COL_SCORE, strScore);
        objContentValues.put(COL_DATE, strDate);


        return writeSQLite.insert(SCORE_GENERALIT_TABLE, null, objContentValues);
    }

    public Cursor ReadAllData() {

        Cursor objCursor = readSQLite.query(SCORE_GENERALIT_TABLE, new String[]{COL_ID, COL_STUDENTID, COL_NAMESURNAME, COL_CLASS, COL_SCORE, COL_DATE }, null, null, null, null, null);

        if (objCursor != null) {
            objCursor.moveToFirst();

        }

        return objCursor;

    }   //  end of ReadAllData



}   //  end of ScoreGeneralITTable
