package app.GKO.GExam;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by lilhazk on 11/02/14.
 */
public class StudentUserTable {

    private OpenHelper objOpenHelper;
    private SQLiteDatabase writeSQLite, readSQLite;

    public static final String Table_User = "student_user_TABLE";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_USER = "User";
    public static final String COLUMN_PASSWORD = "Password";

    public StudentUserTable(Context context) {

        objOpenHelper = new OpenHelper(context);
        writeSQLite = objOpenHelper.getWritableDatabase();
        readSQLite = objOpenHelper.getReadableDatabase();



    }   //  end of Constructor

    public String[] AuthenStudentUser(String strUser) {

        try {

            String arrayDATA[] = null;
            Cursor objCursor = readSQLite.query(Table_User, new String[]
                    {COLUMN_ID, COLUMN_USER, COLUMN_PASSWORD}, COLUMN_USER + "=?", new String[]{String.valueOf(strUser)}, null, null, null, null);

            if (objCursor != null) {

                if (objCursor.moveToFirst()) {

                    arrayDATA = new String[objCursor.getColumnCount()];

                    arrayDATA[0] = objCursor.getString(0);
                    arrayDATA[1] = objCursor.getString(1);
                    arrayDATA[2] = objCursor.getString(2);

                }



            }

            objCursor.close();
            return arrayDATA;


        } catch (Exception e) {
            Log.d("Exam", "No user in Database");
            return null;
        }

    }

    public long AddStudentUserToSQLite(String strUser, String strPassword) {

        ContentValues objContentValues = new ContentValues();

        objContentValues.put(COLUMN_USER,strUser);
        objContentValues.put(COLUMN_PASSWORD, strPassword);

        return writeSQLite.insert(Table_User,null,objContentValues);

    }   // end of AddTeacherUserToSQLite
}   //  end of TeacherUserTable
