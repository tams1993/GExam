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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by lilhazk on 14/02/14.
 */
public class TeacherGeneralITQuestionPage extends Activity{

    private EditText edtQuestion, edtChoice1, edtChoice2, edtChoice3, edtChoice4;
    private RadioGroup ragChoice;
    private Button btnReset, btnSave;
    private String strStudentGeneralIDSQL, strNameSureNameGeneralSQL, strClassGeneralSQL, strScoreGeneralSQL, strDateGeneralITSQL;
    private String strQuestion, strChoice1, strChoice2, strChoice3, strChoice4, strAnswer = "1", strTrueAnswer , strGenQuestion, strGenChoice1, strGenChoice2,strGenChoice3,strGenChoice4 ;
    private AlertDialog.Builder objAlert;
    private GeneralITQuestionTable objGeneralITQuestionTable;
    private SmallScaleQuestionTable objSmallScaleQuestionTable;
    private ScoreGeneralITTable objScoreGeneralITTable;
    private SQLiteDatabase deleteSQLite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        objGeneralITQuestionTable = new GeneralITQuestionTable(this);
        objSmallScaleQuestionTable = new SmallScaleQuestionTable(this);
        objScoreGeneralITTable = new ScoreGeneralITTable(this);

        setContentView(R.layout.teacher_generalit_page);





        initialWidget();


        //ThreadPolicy
        if (Build.VERSION.SDK_INT > 9) {

            StrictMode.ThreadPolicy myPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(myPolicy);

        }


        ragChoice.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                switch (i) {

                    case R.id.radChoice1:

                        strAnswer = "1";

                        break;

                    case R.id.radChoice2:

                        strAnswer = "2";
                        break;

                    case R.id.radChoice3:

                        strAnswer = "3";
                        break;

                    case R.id.radChoice4:

                        strAnswer = "4";
                        break;

                }

            }
        });


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {

                    strQuestion = edtQuestion.getText().toString().trim();
                    strChoice1 = edtChoice1.getText().toString().trim();
                    strChoice2 = edtChoice2.getText().toString().trim();
                    strChoice3 = edtChoice3.getText().toString().trim();
                    strChoice4 = edtChoice4.getText().toString().trim();

                } catch (Exception e) {

                }



                MakeSureAlertDialog();

            }

        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                edtQuestion.setText("");
                edtChoice1.setText("");
                edtChoice2.setText("");
                edtChoice3.setText("");
                edtChoice4.setText("");

            }
        });



    }   //  end of onCreate

    @Override
    public void onBackPressed() {
        objAlert = new AlertDialog.Builder(this);
        objAlert.setTitle("Are You Sure You Want To Leave?");
        objAlert.setPositiveButton("Yes, I'm Sure", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


                dialogInterface.dismiss();
                finish();

            }
        });

        objAlert.setNegativeButton("No, Not Yet", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        objAlert.show();




    }   //  end of onBackPressed

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater objMenuInflater = getMenuInflater();
        objMenuInflater.inflate(R.menu.teacher_list, menu);


        super.onCreateOptionsMenu(menu);
        return true;
    }   //  end of onCreateOptionsMenu

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menu_list_quetion:

                new LoadingGeneralITQuestion().execute();
                break;




            case R.id.menu_list_student:
                new LoadingScoreGeneralIT().execute();
                break;

        }

        return super.onOptionsItemSelected(item);
    }   //  end of onOptionsItemSelected

    private void MakeSureAlertDialog() {

        objAlert = new AlertDialog.Builder(this);
        objAlert.setTitle("Your Information");
        objAlert.setMessage("Question = " + strQuestion + "\n" + "Choice1 = " + strChoice1 + "\n" + "Choice2 = " + strChoice2 + "\n" + "Choice3 = " + strChoice3 + "\n" + "Choice4 = " + strChoice4 + "\n" + "True Answer = " + strAnswer);
        objAlert.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                AddDataToMySQL();
                dialogInterface.dismiss();

                edtQuestion.setText("");
                edtChoice1.setText("");
                edtChoice2.setText("");
                edtChoice3.setText("");
                edtChoice4.setText("");

            }


        });

        objAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        objAlert.setCancelable(false);
        objAlert.show();


    }   //  end of MakeSureAlertDialog

    private void AddDataToMySQL() {

        //  setPolicy

        if (Build.VERSION.SDK_INT > 7) {

            StrictMode.ThreadPolicy myPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(myPolicy);

        }

        // Connect and Post

        try {

            ArrayList<NameValuePair> objNameValuePair = new ArrayList<NameValuePair>();

            objNameValuePair.add(new BasicNameValuePair("isAdd", "true"));
            objNameValuePair.add(new BasicNameValuePair("Question", strQuestion));
            objNameValuePair.add(new BasicNameValuePair("Choice1", strChoice1));
            objNameValuePair.add(new BasicNameValuePair("Choice2", strChoice2));
            objNameValuePair.add(new BasicNameValuePair("Choice3", strChoice3));
            objNameValuePair.add(new BasicNameValuePair("Choice4", strChoice4));
            objNameValuePair.add(new BasicNameValuePair("Answer", strAnswer));

            HttpClient objHttpClient = new DefaultHttpClient();
            HttpPost objHttpPost = new HttpPost("http://gko-dev.netau.net/Exam/php_add_question2.php");
            objHttpPost.setEntity(new UrlEncodedFormEntity(objNameValuePair, "UTF-8"));
            objHttpClient.execute(objHttpPost);

        } catch (Exception e) {

            Log.d("Exam", "Connect and Post Error ====>" + e.toString());

        }




    }   //  end of AddDataToMySQL

    private void initialWidget() {

        edtQuestion = (EditText) findViewById(R.id.edtQuestion);
        edtChoice1 = (EditText) findViewById(R.id.edtChoice1);
        edtChoice2 = (EditText) findViewById(R.id.edtChoice2);
        edtChoice3 = (EditText) findViewById(R.id.edtChoice3);
        edtChoice4 = (EditText) findViewById(R.id.edtChoice4);
        btnReset = (Button) findViewById(R.id.btnReset);
        btnSave = (Button) findViewById(R.id.btnSave);
        ragChoice = (RadioGroup) findViewById(R.id.ragChoice);

        Typeface tf = Typeface.createFromAsset(getAssets(), "phetsarathot.ttf");

        edtChoice1.setTypeface(tf);
        edtChoice2.setTypeface(tf);
        edtChoice3.setTypeface(tf);
        edtChoice4.setTypeface(tf);
        btnReset.setTypeface(tf);
        btnSave.setTypeface(tf);
        edtQuestion.setTypeface(tf);

    }   //  end of initialWidget

    public class LoadingGeneralITQuestion extends AsyncTask<Void, Integer, Void> {

        ProgressDialog objPD;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            objPD = new ProgressDialog(TeacherGeneralITQuestionPage.this);
            objPD.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            objPD.setTitle("Loading...");
            objPD.setMessage("Please Wait...");
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

                //ThreadPolicy
                if (Build.VERSION.SDK_INT > 9) {

                    StrictMode.ThreadPolicy myPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(myPolicy);

                }



                DeleteGeneralITQuestion();
                SyncGeneralITQuestionToSQLite();






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
            Intent objIntent = new Intent(TeacherGeneralITQuestionPage.this, ListGeneralitQuestionActivity.class);
            startActivity(objIntent);






        }   // end of onPostExecute
    }   //  end of Loading GeneralITQuestion

    public class LoadingScoreGeneralIT extends AsyncTask<Void, Integer, Void> {

        ProgressDialog objPD;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            objPD = new ProgressDialog(TeacherGeneralITQuestionPage.this);
            objPD.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            objPD.setTitle("Loading...");
            objPD.setMessage("Please Wait...");
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

                //ThreadPolicy
                if (Build.VERSION.SDK_INT > 9) {

                    StrictMode.ThreadPolicy myPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(myPolicy);

                }



                DeleteGeneralITScore();
                SyncScoreGeneralITToSQLite();






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
            Intent objIntentStudent = new Intent(TeacherGeneralITQuestionPage.this, TabsGeneralITActivity.class);
            startActivity(objIntentStudent);






        }   // end of onPostExecute
    }   //  end of LoadingScoreGeneralIT

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
                strGenQuestion = objJSONObject.getString("Question");
                strGenChoice1 = objJSONObject.getString("Choice1");
                strGenChoice2 = objJSONObject.getString("Choice2");
                strGenChoice3 = objJSONObject.getString("Choice3");
                strGenChoice4 = objJSONObject.getString("Choice4");
                strTrueAnswer = objJSONObject.getString("Answer");

                long insertID = objGeneralITQuestionTable.AddDataGeneralITToSQLite(strGenQuestion, strGenChoice1, strGenChoice2, strGenChoice3, strGenChoice4, strTrueAnswer);


            }

        } catch (Exception e) {

            Log.d("Exam", "Parsing Data Error " +e.toString());
        }

    }   //  end of SyncSmallScaleQuestiontoSQLite

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

    public void DeleteGeneralITScore() {

        deleteSQLite = openOrCreateDatabase("Examdb.db", MODE_PRIVATE, null);

        Cursor objCursor = deleteSQLite.rawQuery("SELECT*FROM score_generalit_TABLE", null);

        int intCursor = objCursor.getCount();

        for (int i = 1; i <= intCursor; i++) {

            deleteSQLite.delete("score_generalit_TABLE", "_id" + "=" + i, null);

        }

    }   //  end of DeleteGeneralITScore

    public void DeleteGeneralITQuestion() {

        deleteSQLite = openOrCreateDatabase("Examdb.db", MODE_PRIVATE, null);

        Cursor objCursor = deleteSQLite.rawQuery("SELECT*FROM general_question_TABLE", null);

        int intCursor = objCursor.getCount();

        for (int i = 1; i <= intCursor; i++) {

            deleteSQLite.delete("general_question_TABLE", "_id" + "=" + i, null);

        }

    }   //  end of DeleteGeneralITQuestion

}   //  end of TeacherSmallScaleQuestionPage
