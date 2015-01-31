package app.GKO.GExam;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

/**
 * Created by TAM-NB on 2/25/14.
 */
public class ListGeneralStudentActivity extends ListActivity{

    private ScoreGeneralITTable objScoreGeneralITTable;
    private String strStudentID, strNameSurname, strClass, strScore, strDate;
    private int intClass;
    private SimpleCursorAdapter objAdapter;
    private SQLiteDatabase mSqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // List View
        intClass = getIntent().getExtras().getInt("intclass");

        switch (intClass) {

            case 0:

                mSqLiteDatabase = openOrCreateDatabase("Examdb.db", MODE_PRIVATE, null);

                objScoreGeneralITTable = new ScoreGeneralITTable(this);
                Cursor CursorGeneralITScore3IT1 = mSqLiteDatabase.rawQuery("SELECT * FROM score_generalit_TABLE WHERE Class ='3IT-1' ", null);
                String[] form3IT1 = new String[]{ScoreGeneralITTable.COL_NAMESURNAME};
                int[] target3IT1 = new int[]{R.id.txtStundentList};
                objAdapter = new SimpleCursorAdapter(this, R.layout.list_all_student, CursorGeneralITScore3IT1, form3IT1, target3IT1);
                setListAdapter(objAdapter);

                break;

            case 1:

                mSqLiteDatabase = openOrCreateDatabase("Examdb.db", MODE_PRIVATE, null);

                objScoreGeneralITTable = new ScoreGeneralITTable(this);
                Cursor CursorGeneralITScore3IT2 = mSqLiteDatabase.rawQuery("SELECT * FROM score_generalit_TABLE WHERE Class ='3IT-2' ", null);
                String[] form3IT2 = new String[]{ScoreGeneralITTable.COL_NAMESURNAME};
                int[] target3IT2 = new int[]{R.id.txtStundentList};
                objAdapter = new SimpleCursorAdapter(this, R.layout.list_all_student, CursorGeneralITScore3IT2, form3IT2, target3IT2);
                setListAdapter(objAdapter);

                break;

            case 2:

                mSqLiteDatabase = openOrCreateDatabase("Examdb.db", MODE_PRIVATE, null);

                objScoreGeneralITTable = new ScoreGeneralITTable(this);
                Cursor CursorGeneralITScore3IT3 = mSqLiteDatabase.rawQuery("SELECT * FROM score_generalit_TABLE WHERE Class ='3IT-3' ", null);
                String[] form3IT3 = new String[]{ScoreGeneralITTable.COL_NAMESURNAME};
                int[] target3IT3 = new int[]{R.id.txtStundentList};
                objAdapter = new SimpleCursorAdapter(this, R.layout.list_all_student, CursorGeneralITScore3IT3, form3IT3, target3IT3);
                setListAdapter(objAdapter);

                break;

            case 3:

                mSqLiteDatabase = openOrCreateDatabase("Examdb.db", MODE_PRIVATE, null);

                objScoreGeneralITTable = new ScoreGeneralITTable(this);
                Cursor CursorGeneralITScore3COM = mSqLiteDatabase.rawQuery("SELECT * FROM score_generalit_TABLE WHERE Class ='3COM' ", null);
                String[] form3COM = new String[]{ScoreGeneralITTable.COL_NAMESURNAME};
                int[] target3COM = new int[]{R.id.txtStundentList};
                objAdapter = new SimpleCursorAdapter(this, R.layout.list_all_student, CursorGeneralITScore3COM, form3COM, target3COM);
                setListAdapter(objAdapter);

                break;

        }




    }   //  end of onCreate

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Cursor selectItem = (Cursor) l.getItemAtPosition(position);
        Intent objIntent = new Intent(ListGeneralStudentActivity.this, InfoStudentActivity.class);

        strStudentID = selectItem.getString(selectItem.getColumnIndex(ScoreGeneralITTable.COL_STUDENTID));
        strClass = selectItem.getString(selectItem.getColumnIndex(ScoreGeneralITTable.COL_CLASS));
        strNameSurname = selectItem.getString(selectItem.getColumnIndex(ScoreGeneralITTable.COL_NAMESURNAME));
        strScore = selectItem.getString(selectItem.getColumnIndex(ScoreGeneralITTable.COL_SCORE));
        strDate = selectItem.getString(selectItem.getColumnIndex(ScoreGeneralITTable.COL_DATE));



        Log.d("Exam", String.valueOf(intClass));

        objIntent.putExtra("StudentID", strStudentID);
        objIntent.putExtra("Class", strClass);
        objIntent.putExtra("Name", strNameSurname);
        objIntent.putExtra("Score", strScore);
        objIntent.putExtra("Date", strDate);

        startActivity(objIntent);

    }   //  end of onListItemClick
}   //  end of ListGeneralStudentActivity
