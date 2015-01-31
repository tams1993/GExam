package app.GKO.GExam;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
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
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by lilhazk on 11/02/14.
 */
public class StudentLoginActivity extends Activity {

    private StudentUserTable objStudentUserTable;
    private String strUserMySQL;
    private String strPasswordMySQL;
    private String strStudentUser;
    private String strStudentPassword;
    private String strStudentTruePassword;
    private String[] strListClass;
    private String strQuestion, strChoice1, strChoice2, strChoice3, strChoice4, strTrueAnswer, strNameSurname,strClass, strID;
    private SQLiteDatabase deleteSQLite;
    private EditText edtStudentUser, edtStudentPassword,edtID, edtName;
    private Button btnStudentLogin;
    private AlertDialog.Builder objAlert;
    private StudentAlertDialog objStudentAlertDialog;
    private SmallScaleQuestionTable objSmallScaleQuestionTable;
    private GeneralITQuestionTable objGeneralITQuestionTable;
    private Spinner spnSubject, spnClass;
    private String[] strListSubject;
    private int intSubject, intClass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_login_layout);

        objStudentUserTable = new StudentUserTable(this);
        objSmallScaleQuestionTable = new SmallScaleQuestionTable(this);
        objGeneralITQuestionTable = new GeneralITQuestionTable(this);

        //ThreadPolicy
        if (Build.VERSION.SDK_INT > 9) {

            StrictMode.ThreadPolicy myPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(myPolicy);

        }
        InitialWidget();


//        DeleteStudentUserData();
        DeleteSmallScaleQuestionData();
        DeleteGeneralITQuestionData();

        ArrayAdapter<String> myAdapter1 = new ArrayAdapter<String>(StudentLoginActivity.this, android.R.layout.simple_spinner_item,strListClass);
        myAdapter1.setDropDownViewResource(android.R.layout.simple_list_item_1);
        spnClass.setAdapter(myAdapter1);


        spnClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                intClass = i;

                switch (intClass) {

                    case 0:

                        strClass = "3IT-1";

                        break;

                    case 1:
                        strClass = "3IT-2";

                        break;

                    case 2:
                        strClass = "3IT-3";

                        break;

                    case 3:
                        strClass = "3COM";

                        break;

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

                intClass = 0;

            }
        });






        btnStudentLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {

                    strStudentUser = edtStudentUser.getText().toString().trim();
                    strStudentPassword = edtStudentPassword.getText().toString().trim();
//                    strClass = edtClass.getText().toString().trim();
                    strNameSurname = edtName.getText().toString().trim();
                    strID = edtID.getText().toString().trim();

                    if ((strStudentUser.equals("")) | (strStudentPassword.equals(""))) {

                        Log.d("Exam", "Have Space");
                        objStudentAlertDialog = new StudentAlertDialog();
                        objStudentAlertDialog.HaveSpace(StudentLoginActivity.this);



                    }else if ((strNameSurname.equals("")| strClass.equals("") | strID.equals(""))) {

                        objStudentAlertDialog = new StudentAlertDialog();
                        objStudentAlertDialog.NameClassID(StudentLoginActivity.this);

                    } else {
                        Log.d("Exam", "No Space");
                        Log.d("Exam", strClass);




//                        CheckStudentUserPassword();
                        new Loading().execute();

                    }

                } catch (Exception e) {


                }



            }
        }); //  end of btnTeacherLogin

        // Spinner Adapter


        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(StudentLoginActivity.this, android.R.layout.simple_spinner_item,strListSubject);
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





    }   //  end of onCreate

    public class Loading extends AsyncTask<Void, Integer, Void> {

        ProgressDialog objPD;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            objPD = new ProgressDialog(StudentLoginActivity.this);
            objPD.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            objPD.setTitle("Loading...");
            objPD.setMessage("Authenticating...");
            objPD.setCancelable(false);
            objPD.setIndeterminate(false);

            objPD.show();



        }   //  end of onPreExecute

        @Override
        protected Void doInBackground(Void... voids) {



            try {

                SyncStudentUserToSQLite();

                SyncSmallScaleQuestiontoSQLite();
                SyncGeneralITQuestiontoSQLite(); // parsing error, need SQLdatabase










            } catch (Exception e) {




                e.printStackTrace();


            }

            return null;
        }   //  end of doInBackground



        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);



            objPD.dismiss();
            CheckStudentUserPassword();





        }   // end of onPostExecute
    }   //  end of Loading





    private void CheckStudentUserPassword() {

        try {

            String arrayDATA[] = objStudentUserTable.AuthenStudentUser(strStudentUser);
            strStudentTruePassword = arrayDATA[2];
            Log.d("Exam", "TruePassword = " + strStudentTruePassword);


            if (strStudentPassword.equals(strStudentTruePassword)) {

                Log.d("Exam", "Authentication Successful");
                StudentAlertAndIntent();

            } else {

                objStudentAlertDialog = new StudentAlertDialog();
                objStudentAlertDialog.UserPasswordNotTrue(StudentLoginActivity.this);
            }

        } catch (Exception e) {
            Log.d("Exam", "No Password in Database");
            objStudentAlertDialog = new StudentAlertDialog();
            objStudentAlertDialog.UserPasswordNotTrue(StudentLoginActivity.this);
        }

    }

    private void StudentAlertAndIntent() {

        objAlert = new AlertDialog.Builder(this);
        objAlert.setTitle("Authentication Successful");
        objAlert.setMessage("Welcome:    " + strNameSurname);
        objAlert.setPositiveButton("Next", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                DeleteStudentUserData();


                switch (intSubject) {

                    case 0:

                        Intent objIntentCisco = new Intent(StudentLoginActivity.this, WarningActivity.class);

                        objIntentCisco.putExtra("myID", strID);
                        objIntentCisco.putExtra("myClass", strClass);
                        objIntentCisco.putExtra("myNameSurname", strNameSurname);
                        objIntentCisco.putExtra("mySubject", "ASEAN");
                        objIntentCisco.putExtra("mySwitch", intSubject);

                        startActivity(objIntentCisco);

                        break;

                    case 1:

                        Intent objIntentGeneral = new Intent(StudentLoginActivity.this, WarningActivity.class);

                        objIntentGeneral.putExtra("myID", strID);
                        objIntentGeneral.putExtra("myClass", strClass);
                        objIntentGeneral.putExtra("myNameSurname", strNameSurname);
                        objIntentGeneral.putExtra("mySubject", "General Knowledge");
                        objIntentGeneral.putExtra("mySwitch", intSubject);

                        startActivity(objIntentGeneral);
                        break;


                }


            }
        });
        objAlert.setCancelable(false);

        objAlert.show();



    }   //  end of StudentAlertAndIntent

    private void InitialWidget() {

        edtStudentPassword = (EditText) findViewById(R.id.edtStudentPassword);
        edtStudentUser = (EditText) findViewById(R.id.edtStudentUsername);
        btnStudentLogin = (Button) findViewById(R.id.btnStudentLogin);
        spnSubject = (Spinner) findViewById(R.id.spnSubject);
        strListSubject = getResources().getStringArray(R.array.my_subject);
        strListClass = getResources().getStringArray(R.array.my_class);
        edtName = (EditText) findViewById(R.id.edtName);
        edtID = (EditText) findViewById(R.id.edtID);
        spnClass = (Spinner) findViewById(R.id.spnClass);

        Typeface tf = Typeface.createFromAsset(getAssets(), "phetsarathot.ttf");
        edtStudentUser.setTypeface(tf);
        edtStudentPassword.setTypeface(tf);
        edtID.setTypeface(tf);
        edtName.setTypeface(tf);
        btnStudentLogin.setTypeface(tf);



    }   //  end of InitialWidget

    private void DeleteStudentUserData() {

        deleteSQLite = openOrCreateDatabase("Examdb.db", MODE_PRIVATE, null);

        Cursor objCursor = deleteSQLite.rawQuery("SELECT*FROM student_user_TABLE", null);

        int intCursor = objCursor.getCount();

        for (int i = 1; i <= intCursor; i++) {

            deleteSQLite.delete("student_user_TABLE", "_id" + "=" + i, null);

        }

    }   //  end of DeleteStudentUserData


    private void SyncStudentUserToSQLite() {
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
            HttpPost objHttpPost = new HttpPost("http://gko-dev.netau.net/Exam/php_get_student_user.php");
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

                long insertID = objStudentUserTable.AddStudentUserToSQLite(strUserMySQL, strPasswordMySQL);

            }

        } catch (Exception e) {

            Log.d("Exam", "Parasing Data " + e.toString());
        }



    }   //  end of SyncStudentUserToSQLite

    private void SyncGeneralITQuestiontoSQLite() {

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

    }   //  end of SyncGeneralITQuestiontoSQLite

    private void SyncSmallScaleQuestiontoSQLite() {

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

    }   //  end of SyncSmallScaleQuestiontoSQLite

    private void DeleteSmallScaleQuestionData() {

        deleteSQLite = openOrCreateDatabase("Examdb.db", MODE_PRIVATE, null);

        Cursor objCursor = deleteSQLite.rawQuery("SELECT*FROM teacher_question_TABLE", null);

        int intCursor = objCursor.getCount();

        for (int i = 1; i <= intCursor; i++) {

            deleteSQLite.delete("teacher_question_TABLE", "_id" + "=" + i, null);

        }

    }   //  end of DeleteStudentUserData

    private void DeleteGeneralITQuestionData() {

        deleteSQLite = openOrCreateDatabase("Examdb.db", MODE_PRIVATE, null);

        Cursor objCursor = deleteSQLite.rawQuery("SELECT*FROM general_question_TABLE", null);

        int intCursor = objCursor.getCount();

        for (int i = 1; i <= intCursor; i++) {

            deleteSQLite.delete("general_question_TABLE", "_id" + "=" + i, null);

        }

    }   //  end of DeleteGeneralITQuestionData





}   //  end of MainActivity
