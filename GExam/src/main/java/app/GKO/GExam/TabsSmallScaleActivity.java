package app.GKO.GExam;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TabHost;

public class TabsSmallScaleActivity extends Activity {

    TabHost mTabHost;
    private SQLiteDatabase mSqLiteDatabase;
    private ScoreSmallScaleTable objScoreSmallScaleTable;
    private SimpleCursorAdapter objAdapter;
    private String strStudentID, strClass, strNameSurname, strScore, strDate;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabs_layout);


        mTabHost = (TabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup();

        mTabHost.addTab(mTabHost.newTabSpec("tab_test1").setIndicator("3IT-1").setContent(R.id.tab3it1));
        mTabHost.addTab(mTabHost.newTabSpec("tab_test2").setIndicator("3IT-2").setContent(R.id.tab3it2));
        mTabHost.addTab(mTabHost.newTabSpec("tab_test3").setIndicator("3IT-3").setContent(R.id.tab3it3));
        mTabHost.addTab(mTabHost.newTabSpec("tab_test4").setIndicator("3COM").setContent(R.id.tab3com));


        mTabHost.setCurrentTab(0);

        final ListView List3IT1 = (ListView) findViewById(R.id.listView3IT1);
        final ListView List3IT2 = (ListView) findViewById(R.id.listView3IT2);
        final ListView List3IT3 = (ListView) findViewById(R.id.listView3IT3);
        final ListView List3COM = (ListView) findViewById(R.id.listView3COM);


        mSqLiteDatabase = openOrCreateDatabase("Examdb.db", MODE_PRIVATE, null);

        // 3IT-1

        objScoreSmallScaleTable = new ScoreSmallScaleTable(this);
        Cursor CursorSmallScale3IT1 = mSqLiteDatabase.rawQuery("SELECT * FROM score_smallscale_TABLE WHERE Class ='3IT-1' ", null);
        String[] form3IT1 = new String[]{ScoreSmallScaleTable.COL_NAMESURNAME};


        int[] target3IT1 = new int[]{R.id.txtStundentList};
        objAdapter = new SimpleCursorAdapter(this, R.layout.list_all_student, CursorSmallScale3IT1, form3IT1, target3IT1);
        List3IT1.setAdapter(objAdapter);

        List3IT1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                Cursor selectItem = (Cursor) List3IT1.getItemAtPosition(i);
                Intent objIntent = new Intent(TabsSmallScaleActivity.this, InfoStudentActivity.class);

                strStudentID = selectItem.getString(selectItem.getColumnIndex(ScoreSmallScaleTable.COL_STUDENTID));
                strClass = selectItem.getString(selectItem.getColumnIndex(ScoreSmallScaleTable.COL_CLASS));
                strNameSurname = selectItem.getString(selectItem.getColumnIndex(ScoreSmallScaleTable.COL_NAMESURNAME));
                strScore = selectItem.getString(selectItem.getColumnIndex(ScoreSmallScaleTable.COL_SCORE));
                strDate = selectItem.getString(selectItem.getColumnIndex(ScoreSmallScaleTable.COL_DATE));

                objIntent.putExtra("StudentID", strStudentID);
                objIntent.putExtra("Class", strClass);
                objIntent.putExtra("Name", strNameSurname);
                objIntent.putExtra("Score", strScore);
                objIntent.putExtra("Date", strDate);

                startActivity(objIntent);

            }
        });

        // 3IT-2

        objScoreSmallScaleTable = new ScoreSmallScaleTable(this);
        Cursor CursorSmallScale3IT2 = mSqLiteDatabase.rawQuery("SELECT * FROM score_smallscale_TABLE WHERE Class ='3IT-2' ", null);
        String[] form3IT2 = new String[]{ScoreSmallScaleTable.COL_NAMESURNAME};
        int[] target3IT2 = new int[]{R.id.txtStundentList};
        objAdapter = new SimpleCursorAdapter(this, R.layout.list_all_student, CursorSmallScale3IT2, form3IT2, target3IT2);
        List3IT2.setAdapter(objAdapter);

        List3IT2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                Cursor selectItem = (Cursor) List3IT2.getItemAtPosition(i);
                Intent objIntent = new Intent(TabsSmallScaleActivity.this, InfoStudentActivity.class);

                strStudentID = selectItem.getString(selectItem.getColumnIndex(ScoreSmallScaleTable.COL_STUDENTID));
                strClass = selectItem.getString(selectItem.getColumnIndex(ScoreSmallScaleTable.COL_CLASS));
                strNameSurname = selectItem.getString(selectItem.getColumnIndex(ScoreSmallScaleTable.COL_NAMESURNAME));
                strScore = selectItem.getString(selectItem.getColumnIndex(ScoreSmallScaleTable.COL_SCORE));
                strDate = selectItem.getString(selectItem.getColumnIndex(ScoreSmallScaleTable.COL_DATE));

                objIntent.putExtra("StudentID", strStudentID);
                objIntent.putExtra("Class", strClass);
                objIntent.putExtra("Name", strNameSurname);
                objIntent.putExtra("Score", strScore);
                objIntent.putExtra("Date", strDate);

                startActivity(objIntent);

            }
        });

        // 3IT-3

        objScoreSmallScaleTable = new ScoreSmallScaleTable(this);
        Cursor CursorSmallScale3IT3 = mSqLiteDatabase.rawQuery("SELECT * FROM score_smallscale_TABLE WHERE Class ='3IT-3' ", null);
        String[] form3IT3 = new String[]{ScoreSmallScaleTable.COL_NAMESURNAME};
        int[] target3IT3 = new int[]{R.id.txtStundentList};
        objAdapter = new SimpleCursorAdapter(this, R.layout.list_all_student, CursorSmallScale3IT3, form3IT3, target3IT3);
        List3IT3.setAdapter(objAdapter);

        List3IT3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                Cursor selectItem = (Cursor) List3IT3.getItemAtPosition(i);
                Intent objIntent = new Intent(TabsSmallScaleActivity.this, InfoStudentActivity.class);

                strStudentID = selectItem.getString(selectItem.getColumnIndex(ScoreSmallScaleTable.COL_STUDENTID));
                strClass = selectItem.getString(selectItem.getColumnIndex(ScoreSmallScaleTable.COL_CLASS));
                strNameSurname = selectItem.getString(selectItem.getColumnIndex(ScoreSmallScaleTable.COL_NAMESURNAME));
                strScore = selectItem.getString(selectItem.getColumnIndex(ScoreSmallScaleTable.COL_SCORE));
                strDate = selectItem.getString(selectItem.getColumnIndex(ScoreSmallScaleTable.COL_DATE));

                objIntent.putExtra("StudentID", strStudentID);
                objIntent.putExtra("Class", strClass);
                objIntent.putExtra("Name", strNameSurname);
                objIntent.putExtra("Score", strScore);
                objIntent.putExtra("Date", strDate);

                startActivity(objIntent);

            }
        });

        // 3COM

        objScoreSmallScaleTable = new ScoreSmallScaleTable(this);
        Cursor CursorSmallScale3COM = mSqLiteDatabase.rawQuery("SELECT * FROM score_smallscale_TABLE WHERE Class ='3COM' ", null);
        String[] form3COM = new String[]{ScoreSmallScaleTable.COL_NAMESURNAME};
        int[] target3COM = new int[]{R.id.txtStundentList};
        objAdapter = new SimpleCursorAdapter(this, R.layout.list_all_student, CursorSmallScale3COM, form3COM, target3COM);
        List3COM.setAdapter(objAdapter);

        List3COM.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                Cursor selectItem = (Cursor) List3COM.getItemAtPosition(i);
                Intent objIntent = new Intent(TabsSmallScaleActivity.this, InfoStudentActivity.class);

                strStudentID = selectItem.getString(selectItem.getColumnIndex(ScoreSmallScaleTable.COL_STUDENTID));
                strClass = selectItem.getString(selectItem.getColumnIndex(ScoreSmallScaleTable.COL_CLASS));
                strNameSurname = selectItem.getString(selectItem.getColumnIndex(ScoreSmallScaleTable.COL_NAMESURNAME));
                strScore = selectItem.getString(selectItem.getColumnIndex(ScoreSmallScaleTable.COL_SCORE));
                strDate = selectItem.getString(selectItem.getColumnIndex(ScoreSmallScaleTable.COL_DATE));

                objIntent.putExtra("StudentID", strStudentID);
                objIntent.putExtra("Class", strClass);
                objIntent.putExtra("Name", strNameSurname);
                objIntent.putExtra("Score", strScore);
                objIntent.putExtra("Date", strDate);

                startActivity(objIntent);

            }
        });


    }   //  end of onCreate





}
