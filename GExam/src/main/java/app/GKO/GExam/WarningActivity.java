package app.GKO.GExam;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by lilhazk on 02/03/14.
 */
public class WarningActivity extends Activity{

    private TextView txtSubject, txtTime;
    private Button btnStart;
    private String strSubject, strTime, strID, strClass, strName, strCurrentDateAndTime;
    private Integer intSubject;
    private AlertDialog.Builder objAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.warning_layout);

        Initial();

        strSubject = getIntent().getExtras().getString("mySubject");
        strClass = getIntent().getExtras().getString("myClass");
        strID = getIntent().getExtras().getString("myID");
        strName = getIntent().getExtras().getString("myNameSurname");

        intSubject = getIntent().getExtras().getInt("mySwitch");

        Log.d("Exam", strName);

        txtSubject.setText(strSubject);

        // time

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss   dd/MM/yyyy");

         strCurrentDateAndTime = sdf.format(new Date());

        txtTime.setText(strCurrentDateAndTime);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                WarningAlert();




            }
        });

    }   //  end of onCreate

    private void WarningAlert() {

        objAlert = new AlertDialog.Builder(this);
        objAlert.setTitle("Last Warning");
        objAlert.setMessage("Are You Sure");
        objAlert.setPositiveButton("Yes, I'm Sure", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();

                try {

                    switch (intSubject) {

                        case 0:

                            Intent objIntentSmall = new Intent(WarningActivity.this, StudentSmallScaleAnswerPage.class);
                            objIntentSmall.putExtra("myID", strID);
                            objIntentSmall.putExtra("myClass", strClass);
                            objIntentSmall.putExtra("myName", strName);
                            objIntentSmall.putExtra("myDate", strCurrentDateAndTime);

                            startActivity(objIntentSmall);
                            break;


                        case 1:
                            Intent objIntentGeneral = new Intent(WarningActivity.this, StudentGeneralITAnswerActivity.class);
                            objIntentGeneral.putExtra("myID", strID);
                            objIntentGeneral.putExtra("myClass", strClass);
                            objIntentGeneral.putExtra("myName", strName);
                            objIntentGeneral.putExtra("myDate", strCurrentDateAndTime);

                            startActivity(objIntentGeneral);
                            break;
                    }

                } catch (Exception e) {

                    Log.d("Exam", "Error " + e.toString());

                }



            }
        });


        objAlert.setNegativeButton("No, Not Yet", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();
            }
        });
        objAlert.setCancelable(false);
        objAlert.show();


    }   //  end of WarningAlert

    private void Initial() {

        txtSubject = (TextView) findViewById(R.id.txtSubject);
        txtTime = (TextView) findViewById(R.id.txtTime);
        btnStart = (Button) findViewById(R.id.btnStart);

        Typeface tf = Typeface.createFromAsset(getAssets(), "phetsarathot.ttf");
        txtTime.setTypeface(tf);
        txtSubject.setTypeface(tf);
        btnStart.setTypeface(tf);

    }   //  end of Initial
}   //  end of WarningActivity
