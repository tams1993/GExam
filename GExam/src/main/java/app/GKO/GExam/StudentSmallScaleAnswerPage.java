package app.GKO.GExam;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by TAM-NB on 2/16/14.
 */
public class StudentSmallScaleAnswerPage extends Activity {

    private TextView txtQuestion, txtChoice1, txtChoice2, txtChoice3, txtChoice4,txtCountDown,txtQuestionNumber;
    private Button btnNext;
    private int counter = 0;
    private int score = 0;

    private AlertDialog.Builder objAlert;
    private CountDownTimer objCountDown;
    private SQLiteDatabase mSqLiteDatabase;
    private RadioGroup ragChoice;
    private String strStudentAnswer = "", CorrectAnswer, strID, strClass, strNameSurname,strCurrentDateAndTime;

    int data[] = new int[20];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_answer_layout);

        InitialWidget();
        CountDown();

        //receive ID, Class, NameAndSurname

         strID = getIntent().getExtras().getString("myID");
         strClass = getIntent().getExtras().getString("myClass");
         strNameSurname = getIntent().getExtras().getString("myName");
        strCurrentDateAndTime = getIntent().getExtras().getString("myDate");

        Log.d("Exam", "ID = " + strID + "Class = " + strClass + "NameAndSurname = " + strNameSurname +"Date = " +strCurrentDateAndTime);


        // First Random Question

        txtQuestionNumber.setText("1: ");


        mSqLiteDatabase = openOrCreateDatabase("Examdb.db", MODE_PRIVATE, null);

        Cursor CursorRow = mSqLiteDatabase.rawQuery("SELECT * FROM teacher_question_TABLE", null);

        Log.d("Exam", "data row =  " + String.valueOf(CursorRow.getCount()));



        data = randomIntArray(data.length, 1, CursorRow.getCount());



        String str = "";
        for(int i = 0 ; i < data.length ; i++)
            str += String.valueOf(data[i]) + "  ";
        Log.i("Exam", str);



        CursorRow.moveToFirst();
        while (CursorRow.isAfterLast() == false) {
            if (CursorRow.getInt(CursorRow.getColumnIndex("_id")) == data[0]) {

                Log.d("Exam", "data[0] = "+ String.valueOf(data[0]));
                String Question = CursorRow.getString(CursorRow.getColumnIndex("Question"));
                String Choice1 = CursorRow.getString(CursorRow.getColumnIndex("Choice1"));
                String Choice2 = CursorRow.getString(CursorRow.getColumnIndex("Choice2"));
                String Choice3 = CursorRow.getString(CursorRow.getColumnIndex("Choice3"));
                String Choice4 = CursorRow.getString(CursorRow.getColumnIndex("Choice4"));
                 CorrectAnswer = CursorRow.getString(CursorRow.getColumnIndex("TrueAnswer"));
                Log.d("Exam", "Correct Answer data[0]= "+ CorrectAnswer);

                txtQuestion.setText(Question);
                txtChoice1.setText(Choice1);
                txtChoice2.setText(Choice2);
                txtChoice3.setText(Choice3);
                txtChoice4.setText(Choice4);

            } else {

            }


            CursorRow.moveToNext();
        }

        ragStudentChoice();






        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (strStudentAnswer.equals("")) {

                    ChooseChoice();

                } else {

                    Next();
                    CheckScore();


                    Log.d("Exam", strStudentAnswer);

                }





            }
        });








    }   //  end of onCreate

    public void ragStudentChoice() {

        ragChoice.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                switch (i) {

                    case R.id.radStudentChoice1:

                        strStudentAnswer = "1";

                        break;

                    case R.id.radStudentChoice2:

                        strStudentAnswer = "2";

                        break;

                    case R.id.radStudentChoice3:

                        strStudentAnswer = "3";
                        break;

                    case R.id.radStudentChoice4:

                        strStudentAnswer = "4";

                        break;

                }

            }
        });

    }   //  ragStudentChoice

    @Override
    public void onBackPressed() {

        objAlert = new AlertDialog.Builder(this);
        objAlert.setTitle("Cannot Back!");
        objAlert.setMessage("Once You Enter The Exam Page, You Cannot Get Out Until You Finish.");
        objAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();


            }


        });
        objAlert.setCancelable(false);

        objAlert.show();


    }   //  end of onBackPressed

    private void InitialWidget() {

        txtQuestion = (TextView) findViewById(R.id.txtQuestion);
        txtChoice1 = (TextView) findViewById(R.id.txtChoice1);
        txtChoice2 = (TextView) findViewById(R.id.txtChoice2);
        txtChoice3 = (TextView) findViewById(R.id.txtChoice3);
        txtChoice4 = (TextView) findViewById(R.id.txtChoice4);
        btnNext = (Button) findViewById(R.id.btnNext);
        txtCountDown = (TextView) findViewById(R.id.txtCountdown);
        txtQuestionNumber = (TextView) findViewById(R.id.txtQuestionNumber);
        ragChoice = (RadioGroup) findViewById(R.id.ragStudentChoice);

        Typeface tf = Typeface.createFromAsset(getAssets(), "phetsarathot.ttf");

        txtChoice1.setTypeface(tf);
        txtChoice2.setTypeface(tf);
        txtChoice3.setTypeface(tf);
        txtChoice4.setTypeface(tf);
        txtCountDown.setTypeface(tf);
        txtQuestion.setTypeface(tf);
        txtQuestionNumber.setTypeface(tf);
        btnNext.setTypeface(tf);


    }   //  end of InitialWidget







    private void Next() {


        if (this.counter <= 18) {  // = 20 question





            this.counter++;
            NextAlert();


        } else {



            NextAlertAfter();

            txtCountDown.setText("");


            objCountDown.cancel();

        }


    }   //  end of Next



    public void NextAlert() {



        objAlert = new AlertDialog.Builder(this);
        objAlert.setTitle("Next Question");
        objAlert.setMessage("Move to Next Question");
        objAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


                txtQuestionNumber.setText(String.valueOf(counter + 1)+"");

                dialogInterface.dismiss();

                mSqLiteDatabase = openOrCreateDatabase("Examdb.db", MODE_PRIVATE, null);

                Cursor CursorRow = mSqLiteDatabase.rawQuery("SELECT * FROM teacher_question_TABLE", null);

                CursorRow.moveToFirst();
                while (CursorRow.isAfterLast() == false) {
                    if (CursorRow.getInt(CursorRow.getColumnIndex("_id")) == data[counter]) {

//                        Log.d("Exam", "id = 1");
                        Log.d("Exam", "data["+ counter + "] = "  + String.valueOf(data[counter]));
                        String Question = CursorRow.getString(CursorRow.getColumnIndex("Question"));
                        String Choice1 = CursorRow.getString(CursorRow.getColumnIndex("Choice1"));
                        String Choice2 = CursorRow.getString(CursorRow.getColumnIndex("Choice2"));
                        String Choice3 = CursorRow.getString(CursorRow.getColumnIndex("Choice3"));
                        String Choice4 = CursorRow.getString(CursorRow.getColumnIndex("Choice4"));
                        CorrectAnswer = CursorRow.getString(CursorRow.getColumnIndex("TrueAnswer"));

                        Log.d("Exam", Question);
                        Log.d("Exam", "data[" + counter + "] correct Answer = " + CorrectAnswer);

                        txtQuestion.setText(Question);
                        txtChoice1.setText(Choice1);
                        txtChoice2.setText(Choice2);
                        txtChoice3.setText(Choice3);
                        txtChoice4.setText(Choice4);

                    }








                    CursorRow.moveToNext();
                }


                objCountDown.cancel();
                CountDown();


            }


        });

        objAlert.setCancelable(false);


        objAlert.show();





    }

    private void NextAlertAfter() {

        objAlert = new AlertDialog.Builder(this);
        objAlert.setTitle("Finish");
        objAlert.setMessage("Thank You For Your Hardwork ");
        objAlert.setPositiveButton("Thank You too", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                AddScoreToMySQL();

                Intent objIntent = new Intent(StudentSmallScaleAnswerPage.this, ExitAcitivity.class);
                startActivity(objIntent);

//                Intent objIntent = new Intent(Intent.ACTION_MAIN);
//                objIntent.addCategory(Intent.CATEGORY_HOME);
//                objIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(objIntent);
//
//                android.os.Process.killProcess(android.os.Process.myPid());//   kill process
//                System.exit(0);
//                finish();
                dialogInterface.dismiss();

            }
        });

        objAlert.setCancelable(false);


        objAlert.show();


    }   //  end of NextAlertAfter

    private void CountDown() {


       objCountDown = new CountDownTimer(120000, 100) {

            public void onTick(long l) {


                int secondsLeft = 0;




                if (Math.round((float) l / 1000.0f) != secondsLeft) {
                    secondsLeft = Math.round((float) l / 1000.0f);

                    // time countdown

                    txtCountDown.setText("seconds remaining: " + String.format("%02d:%02d:%02d", secondsLeft / 3600, (secondsLeft % 3600) / 60, (secondsLeft % 60)));

                }
//                Log.i("Exam", "ms=" + l + " till finished=" + secondsLeft);
            }

            public void onFinish() {

                Next();

                txtCountDown.setText("Time's up!!!");

//                NextAlertAfter();

            }

        }.start();



    }

    public int[] randomIntArray(int count, int min, int max) {



        Random r = new Random();
        int[] data = new int[count];
        for (int i = 0; i < count; i++) {

            data[i] = -1;

        }

        for (int i = 0; i < count; i++) {

            int n = -1;
            boolean st = true;
            while (st) {

                st = false;

                n = r.nextInt((max-min) + 1) + min;
                for (int j = 0; j < data.length; j++)

                    if (n == data[j])
                        st = true;




            }
            data[i] = n;


        }
        return data;




    }   //  end of randomIntArray

    public void ChooseChoice() {

        objAlert = new AlertDialog.Builder(this);
        objAlert.setTitle("Please Select Your Answer.");
        objAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();

            }
        });
        objAlert.show();

    }   //  end of ChooseChoice


    public void CheckScore() {

        if (strStudentAnswer.equals(CorrectAnswer)) {

            score = score + 1;

            Log.d("Exam", "Score = " + score);

        }


    }

    public void AddScoreToMySQL() {

        if (Build.VERSION.SDK_INT > 7) {

            StrictMode.ThreadPolicy myPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(myPolicy);

        }

        //  Connect and Post

        try {

            ArrayList<NameValuePair> objNameValuePairs = new ArrayList<NameValuePair>();
            objNameValuePairs.add(new BasicNameValuePair("isAdd", "true"));
            objNameValuePairs.add(new BasicNameValuePair("Score", String.valueOf(score)));
            objNameValuePairs.add(new BasicNameValuePair("StudentID", strID));
            objNameValuePairs.add(new BasicNameValuePair("Name_Surname", strNameSurname));
            objNameValuePairs.add(new BasicNameValuePair("Class", strClass));
            objNameValuePairs.add(new BasicNameValuePair("Date", strCurrentDateAndTime));

            HttpClient objHttpClient = new DefaultHttpClient();
            HttpPost objHttpPost = new HttpPost("http://gko-dev.netau.net/Exam/php_add_score1.php");
            objHttpPost.setEntity(new UrlEncodedFormEntity(objNameValuePairs, "UTF-8"));
            objHttpClient.execute(objHttpPost);

            Log.d("Exam", "String score = " + String.valueOf(score));

        } catch (Exception e) {

            Log.d("Exam", "Connect and Post Error ====>" + e.toString());

        }

    }   //  end of AddScoreToMySQL



}   //  end of StudentSmallScaleAnswerPage
