package app.GKO.GExam;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by lilhazk on 11/02/14.
 */
public class TeacherLoginActivity extends Activity {

    private TeacherUserTable objTeacherUserTable;
    private String strQuestion, strChoice1, strChoice2, strChoice3, strChoice4 , strTrueAnswer;
    private String strUserMySQL, strPasswordMySQL, strTeacherUser, strTeacherPassword, strTeacherTruePassword, strNameSureNameSQL, strStudentIDSQL, strClassSQL, strScoreSQL, strDateSmallScaleSQL , strNameSureNameGeneralSQL, strStudentGeneralIDSQL, strClassGeneralSQL, strScoreGeneralSQL, strDateGeneralITSQL;
    private SQLiteDatabase deleteSQLite;
    private Button btnTeacherLogin;
    private EditText edtTeacherUsername, edtTeacherPassword;
    private TeacherAlertDialog objTeacherAlertDialog;
    private AlertDialog.Builder objAlert;
    private SmallScaleQuestionTable objSmallScaleQuestionTable;
    private GeneralITQuestionTable objGeneralITQuestionTable;
    private ScoreSmallScaleTable objScoreSmallScaleTable;
    private ScoreGeneralITTable objScoreGeneralITTable;
    private String[] strListSubject;
    private Spinner spnSubject;
    private int intSubject;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_login_layout);


        objTeacherUserTable = new TeacherUserTable(this);
        objSmallScaleQuestionTable = new SmallScaleQuestionTable(this);
        objScoreSmallScaleTable = new ScoreSmallScaleTable(this);
        objScoreGeneralITTable = new ScoreGeneralITTable(this);
        objGeneralITQuestionTable = new GeneralITQuestionTable(this);



        //ThreadPolicy
        if (Build.VERSION.SDK_INT > 9) {

            StrictMode.ThreadPolicy myPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(myPolicy);

        }

//        DeleteTeacherUserData();
        DeleteSmallScaleScore();
        DeleteTeacherQuestionData();
        DeleteGeneralITQuestion();
        DeleteGeneralITScore();

//        SyncGeneralITQuestionToSQLite();
//        SyncScoreGeneralITToSQLite();
//        SyncSmallScaleQuestiontoSQLite();
//        SyncScoreSmallScaleToSQLite();
//        SyncTeacherUserToSQLite();
        initialWidget();

        btnTeacherLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {

                    strTeacherUser = edtTeacherUsername.getText().toString().trim();
                    strTeacherPassword = edtTeacherPassword.getText().toString().trim();

                    if ((strTeacherUser.equals("")) | (strTeacherPassword.equals(""))) {

                        Log.d("Exam", "Have Space");
                        objTeacherAlertDialog = new TeacherAlertDialog();
                        objTeacherAlertDialog.HaveSpace(TeacherLoginActivity.this);

                    } else {
                        Log.d("Exam", "No Space");

                        new Loading().execute();
//                        CheckTeacherUserPassword();





                    }

                } catch (Exception e) {


                }

            }
        }); //  end of btnTeacherLogin

        SubjectSpinner();




    }   //  end of onCreate

      public class Loading extends AsyncTask<Void, Integer, Void> {

        ProgressDialog objPD;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            objPD = new ProgressDialog(TeacherLoginActivity.this);
            objPD.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            objPD.setTitle("Loading...");
            objPD.setMessage("Authenticating...");
            objPD.setCancelable(false);
            objPD.setIndeterminate(false);

            objPD.show();

//            final Timer t = new Timer();
//            t.schedule(new TimerTask() {
//                public void run() {
//                    objPD.dismiss(); // when the task active then close the dialog
//                    t.cancel(); // also just top the timer thread, otherwise, you may receive a crash report
//
//                }
//            }, 2000);



        }   //  end of onPreExecute

        @Override
        protected Void doInBackground(Void... voids) {




            try {

//                SyncGeneralITQuestionToSQLite();
//                SyncScoreGeneralITToSQLite();
//                SyncSmallScaleQuestiontoSQLite();
//                SyncScoreSmallScaleToSQLite();
                SyncTeacherUserToSQLite();




            } catch (Exception e) {


                e.printStackTrace();


            }

            return null;
        }   //  end of doInBackground


          @Override
          protected void onCancelled() {
              super.onCancelled();


          } //  end of onCancelled

          @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);



            objPD.dismiss();
            CheckTeacherUserPassword();





        }   // end of onPostExecute
    }   //  end of Loading






    private void SubjectSpinner() {

        spnSubject = (Spinner) findViewById(R.id.spnSubject);
        strListSubject = getResources().getStringArray(R.array.my_subject);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(TeacherLoginActivity.this, android.R.layout.simple_spinner_item, strListSubject);
        myAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        spnSubject.setAdapter(myAdapter);


        spnSubject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                intSubject = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                intSubject = 0;
            }
        });


    }   //  end of SubjectSpinner


    private void CheckTeacherUserPassword() {

        try {

            String arrayDATA[] = objTeacherUserTable.AuthenTeacherUser(strTeacherUser);
            strTeacherTruePassword = arrayDATA[2];
            Log.d("Exam", "TruePassword = " +strTeacherTruePassword);


            if (strTeacherPassword.equals(strTeacherTruePassword)) {

                Log.d("Exam", "Authentication Successful");
                TeacherAlertAndIntent();

            } else {

                objTeacherAlertDialog = new TeacherAlertDialog();
                objTeacherAlertDialog.UserPasswordNotTrue(TeacherLoginActivity.this);
            }

        } catch (Exception e) {
            Log.d("Exam", "No User in Database");
            objTeacherAlertDialog = new TeacherAlertDialog();
            objTeacherAlertDialog.UserPasswordNotTrue(TeacherLoginActivity.this);
        }

    }   //  end of CheckTeacherUserPassword

    private void TeacherAlertAndIntent() {

        objAlert = new AlertDialog.Builder(this);
        objAlert.setTitle("Authentication Successful");
        objAlert.setMessage("Welcome:    " + strTeacherUser);
        objAlert.setPositiveButton("Go to Add Question Page", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                DeleteTeacherUserData();

                switch (intSubject) {

                    case 0:
                        Intent objIntent = new Intent(TeacherLoginActivity.this, TeacherSmallScaleQuestionPage.class);
                        startActivity(objIntent);
                        break;

                    case 1:
                        Intent objIntent1 = new Intent(TeacherLoginActivity.this, TeacherGeneralITQuestionPage.class);
                        startActivity(objIntent1);
                        break;


                }

            }
        });
        objAlert.setCancelable(false);

        objAlert.show();

    }

    private void initialWidget() {

        btnTeacherLogin = (Button) findViewById(R.id.btnTeacherLogin);
        edtTeacherUsername = (EditText) findViewById(R.id.edtTeacherUsername);
        edtTeacherPassword = (EditText) findViewById(R.id.edtTeacherPassword);

        Typeface tf = Typeface.createFromAsset(getAssets(), "phetsarathot.ttf");
        btnTeacherLogin.setTypeface(tf);
        edtTeacherPassword.setTypeface(tf);
        edtTeacherUsername.setTypeface(tf);

    }   //  end of initialWidget

    public void DeleteTeacherUserData() {

    deleteSQLite = openOrCreateDatabase("Examdb.db", MODE_PRIVATE, null);

    Cursor objCursor = deleteSQLite.rawQuery("SELECT*FROM teacher_user_TABLE", null);

    int intCursor = objCursor.getCount();

    for (int i = 1; i <= intCursor; i++) {

        deleteSQLite.delete("teacher_user_TABLE", "_id" + "=" + i, null);

    }

}   //  end of DeleteStudentUserData

    public void DeleteSmallScaleScore() {

        deleteSQLite = openOrCreateDatabase("Examdb.db", MODE_PRIVATE, null);

        Cursor objCursor = deleteSQLite.rawQuery("SELECT*FROM score_smallscale_TABLE", null);

        int intCursor = objCursor.getCount();

        for (int i = 1; i <= intCursor; i++) {

            deleteSQLite.delete("score_smallscale_TABLE", "_id" + "=" + i, null);

        }

    }   //  end of DeleteSmallScaleScore

    public void SyncScoreSmallScaleToSQLite() {

    InputStream objInputStream = null;
    String strJSON = "";

    //Connect to HTTP

    try {

        HttpClient objHttpClient = new DefaultHttpClient();
        HttpPost objHttpPost = new HttpPost("http://gko-dev.netau.net/Exam/php_get_score1.php");
        HttpResponse objHttpResponse = objHttpClient.execute(objHttpPost);
        HttpEntity objHttpEntity = objHttpResponse.getEntity();
        objInputStream = objHttpEntity.getContent();

    } catch (Exception e) {

        Log.d("Exam", "Connect Http error " + e.toString());
    }

    //JSON to String

    try {

        BufferedReader objBufferedReader = new BufferedReader(new InputStreamReader(objInputStream, "UTF-8"));
        StringBuilder objStringBuilder = new StringBuilder();
        String strLine = null;

        while ((strLine = objBufferedReader.readLine())!=null) {

            objStringBuilder.append(strLine);

        }

        objInputStream.close();
        strJSON = objStringBuilder.toString();

    } catch (Exception e) {

        Log.d("Exam", "JSON to String Error " + e.toString());

    }

    //  take mySQL to SQLite

    try {

        final JSONArray objJSONArray = new JSONArray(strJSON);

        for (int i = 0; i < objJSONArray.length(); i++) {


            JSONObject objJSONObject = objJSONArray.getJSONObject(i);
            strStudentIDSQL = objJSONObject.getString("StudentID");
            strNameSureNameSQL = objJSONObject.getString("Name_Surname");
            strClassSQL = objJSONObject.getString("Class");
            strScoreSQL = objJSONObject.getString("Score");
            strDateSmallScaleSQL = objJSONObject.getString("Date");


            long insertID = objScoreSmallScaleTable.AddDataScoreToSQLite(strStudentIDSQL, strNameSureNameSQL, strClassSQL, strScoreSQL, strDateSmallScaleSQL);


        }

    } catch (Exception e) {

        Log.d("Exam", "Parsing Data Error " +e.toString());
        Log.d("Exam", strClassSQL+ strScoreSQL+ strNameSureNameSQL+ strStudentIDSQL);
    }

}   //  end of SyncScoreSmallScaleToSQLite


    public void DeleteGeneralITScore() {

        deleteSQLite = openOrCreateDatabase("Examdb.db", MODE_PRIVATE, null);

        Cursor objCursor = deleteSQLite.rawQuery("SELECT*FROM score_generalit_TABLE", null);

        int intCursor = objCursor.getCount();

        for (int i = 1; i <= intCursor; i++) {

            deleteSQLite.delete("score_generalit_TABLE", "_id" + "=" + i, null);

        }

    }   //  end of DeleteSmallScaleScore

    public void SyncScoreGeneralITToSQLite() {

        InputStream objInputStream = null;
        String strJSON = "";

        //Connect to HTTP

        try {

            HttpClient objHttpClient = new DefaultHttpClient();
            HttpPost objHttpPost = new HttpPost("http://gko-dev.netau.net/Exam/php_get_score2.php");
            HttpResponse objHttpResponse = objHttpClient.execute(objHttpPost);
            HttpEntity objHttpEntity = objHttpResponse.getEntity();
            objInputStream = objHttpEntity.getContent();

        } catch (Exception e) {

            Log.d("Exam", "Connect Http error " + e.toString());
        }

        //JSON to String

        try {

            BufferedReader objBufferedReader = new BufferedReader(new InputStreamReader(objInputStream, "UTF-8"));
            StringBuilder objStringBuilder = new StringBuilder();
            String strLine = null;

            while ((strLine = objBufferedReader.readLine())!=null) {

                objStringBuilder.append(strLine);

            }

            objInputStream.close();
            strJSON = objStringBuilder.toString();

        } catch (Exception e) {

            Log.d("Exam", "JSON to String Error " + e.toString());

        }

        //  take mySQL to SQLite

        try {

            final JSONArray objJSONArray = new JSONArray(strJSON);

            for (int i = 0; i < objJSONArray.length(); i++) {


                JSONObject objJSONObject = objJSONArray.getJSONObject(i);
                strStudentGeneralIDSQL = objJSONObject.getString("StudentID");
                strNameSureNameGeneralSQL = objJSONObject.getString("Name_Surname");
                strClassGeneralSQL = objJSONObject.getString("Class");
                strScoreGeneralSQL = objJSONObject.getString("Score");
                strDateGeneralITSQL = objJSONObject.getString("Date");


                long insertID = objScoreGeneralITTable.AddDataScoreToSQLite(strStudentGeneralIDSQL, strNameSureNameGeneralSQL, strClassGeneralSQL, strScoreGeneralSQL,strDateGeneralITSQL);


            }

        } catch (Exception e) {

            Log.d("Exam", "Parsing Data Error " +e.toString());
            Log.d("Exam", strClassGeneralSQL+ strScoreGeneralSQL+ strStudentGeneralIDSQL+ strNameSureNameGeneralSQL);

        }

    }   //  end of SyncScoreSmallScaleToSQLite

    public void SyncTeacherUserToSQLite() {
        InputStream objInputStream = null;
        String strJSON = "";

        // connect HTTP

        try {

            HttpParams httpParams = new BasicHttpParams();

            int timeoutconnection = 10000;

            HttpConnectionParams.setConnectionTimeout(httpParams, timeoutconnection);

            int timeoutsocket = 10000;

            HttpConnectionParams.setSoTimeout(httpParams, timeoutsocket);

            HttpClient objHttpClient = new DefaultHttpClient(httpParams);
            HttpPost objHttpPost = new HttpPost("http://gko-dev.netau.net/Exam/php_get_user.php");
            HttpResponse objHttpResponse = objHttpClient.execute(objHttpPost);
            HttpEntity objHttpEntity = objHttpResponse.getEntity();
            objInputStream = objHttpEntity.getContent();




        } catch (Exception e) {

            Log.d("Exam", "Error Connect" + e.toString());
        }

        // create strJSON

        try {

            BufferedReader objBufferedReader = new BufferedReader(new InputStreamReader(objInputStream, "UTF-8"));
            StringBuilder objStringBuilder = new StringBuilder();
            String strLine = null;

            while ((strLine = objBufferedReader.readLine()) != null) {

                objStringBuilder.append(strLine);

            }

            objInputStream.close();
            strJSON = objStringBuilder.toString();

        } catch (Exception e) {

            Log.d("Exam", "Error Convert JSON " + e.toString());

        }

        // Sync to SQLite

        try {


            final JSONArray objJSONArray = new JSONArray(strJSON);

            for (int i = 0; i < objJSONArray.length(); i++) {

                JSONObject objJSONObject = objJSONArray.getJSONObject(i);
                strUserMySQL = objJSONObject.getString("Username");
                strPasswordMySQL = objJSONObject.getString("Password");

                long insertID = objTeacherUserTable.AddTeacherUserToSQLite(strUserMySQL, strPasswordMySQL);

            }

        } catch (Exception e) {

            Log.d("Exam", "Parasing Data " + e.toString());
        }

    }   //  end of SyncTeacherUserToSQLite

    public void SyncSmallScaleQuestiontoSQLite() {

    InputStream objInputStream = null;
    String strJSON = "";

    //Connect to HTTP

    try {

        HttpClient objHttpClient = new DefaultHttpClient();
        HttpPost objHttpPost = new HttpPost("http://gko-dev.netau.net/Exam/php_get_question.php");
        HttpResponse objHttpResponse = objHttpClient.execute(objHttpPost);
        HttpEntity objHttpEntity = objHttpResponse.getEntity();
        objInputStream = objHttpEntity.getContent();

    } catch (Exception e) {

        Log.d("Exam", "Connect Http error " + e.toString());
    }

    //JSON to String

    try {

        BufferedReader objBufferedReader = new BufferedReader(new InputStreamReader(objInputStream, "UTF-8"));
        StringBuilder objStringBuilder = new StringBuilder();
        String strLine = null;

        while ((strLine = objBufferedReader.readLine())!=null) {

            objStringBuilder.append(strLine);

        }

        objInputStream.close();
        strJSON = objStringBuilder.toString();

    } catch (Exception e) {

        Log.d("Exam", "JSON to String Error " + e.toString());

    }

    //  take mySQL to SQLite

    try {

        final JSONArray objJSONArray = new JSONArray(strJSON);

        for (int i = 0; i < objJSONArray.length(); i++) {


            JSONObject objJSONObject = objJSONArray.getJSONObject(i);
            strQuestion = objJSONObject.getString("Question");
            strChoice1 = objJSONObject.getString("Choice1");
            strChoice2 = objJSONObject.getString("Choice2");
            strChoice3 = objJSONObject.getString("Choice3");
            strChoice4 = objJSONObject.getString("Choice4");
            strTrueAnswer = objJSONObject.getString("Answer");

            long insertID = objSmallScaleQuestionTable.AddDataQuestionToSQLite(strQuestion, strChoice1, strChoice2, strChoice3, strChoice4, strTrueAnswer);


        }

    } catch (Exception e) {

        Log.d("Exam", "Parsing Data Error " +e.toString());
    }

}   //  end of SyncGeneralITQuestionToSQLite

    public void DeleteGeneralITQuestion() {

        deleteSQLite = openOrCreateDatabase("Examdb.db", MODE_PRIVATE, null);

        Cursor objCursor = deleteSQLite.rawQuery("SELECT*FROM general_question_TABLE", null);

        int intCursor = objCursor.getCount();

        for (int i = 1; i <= intCursor; i++) {

            deleteSQLite.delete("general_question_TABLE", "_id" + "=" + i, null);

        }

    }   //  end of DeleteGeneralITQuestion

    public void SyncGeneralITQuestionToSQLite() {

        InputStream objInputStream = null;
        String strJSON = "";

        //Connect to HTTP

        try {

            HttpClient objHttpClient = new DefaultHttpClient();
            HttpPost objHttpPost = new HttpPost("http://gko-dev.netau.net/Exam/php_get_question2.php");
            HttpResponse objHttpResponse = objHttpClient.execute(objHttpPost);
            HttpEntity objHttpEntity = objHttpResponse.getEntity();
            objInputStream = objHttpEntity.getContent();

        } catch (Exception e) {

            Log.d("Exam", "Connect Http error " + e.toString());
        }

        //JSON to String

        try {

            BufferedReader objBufferedReader = new BufferedReader(new InputStreamReader(objInputStream, "UTF-8"));
            StringBuilder objStringBuilder = new StringBuilder();
            String strLine = null;

            while ((strLine = objBufferedReader.readLine())!=null) {

                objStringBuilder.append(strLine);

            }

            objInputStream.close();
            strJSON = objStringBuilder.toString();

        } catch (Exception e) {

            Log.d("Exam", "JSON to String Error " + e.toString());

        }

        //  take mySQL to SQLite

        try {

            final JSONArray objJSONArray = new JSONArray(strJSON);

            for (int i = 0; i < objJSONArray.length(); i++) {


                JSONObject objJSONObject = objJSONArray.getJSONObject(i);
                strQuestion = objJSONObject.getString("Question");
                strChoice1 = objJSONObject.getString("Choice1");
                strChoice2 = objJSONObject.getString("Choice2");
                strChoice3 = objJSONObject.getString("Choice3");
                strChoice4 = objJSONObject.getString("Choice4");
                strTrueAnswer = objJSONObject.getString("Answer");

                long insertID = objGeneralITQuestionTable.AddDataGeneralITToSQLite(strQuestion, strChoice1, strChoice2, strChoice3, strChoice4, strTrueAnswer);


            }

        } catch (Exception e) {

            Log.d("Exam", "Parsing Data Error " +e.toString());
        }

    }   //  end of ScaleQuestiontoSQLite

    public void DeleteTeacherQuestionData() {

        deleteSQLite = openOrCreateDatabase("Examdb.db", MODE_PRIVATE, null);

        Cursor objCursor = deleteSQLite.rawQuery("SELECT*FROM teacher_question_TABLE", null);

        int intCursor = objCursor.getCount();

        for (int i = 1; i <= intCursor; i++) {

            deleteSQLite.delete("teacher_question_TABLE", "_id" + "=" + i, null);

        }

    }   //  end of DeleteStudentUserData




}   //  end of MainActivity
