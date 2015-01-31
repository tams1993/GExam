package app.GKO.GExam;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

/**
 * Created by TAM-NB on 2/25/14.
 */
public class InfoQuestionActivity extends ActionBarActivity{

    private TextView txtQuestion, txtChoice1, txtChoice2, txtChoice3, txtChoice4, txtCorrectAnswer;
    private String strQuestion, strChoice1, strChoice2, strChoice3, strChoice4, strCorrectAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_list_all_question);

        Initial();

        strQuestion = getIntent().getExtras().getString("Question");
        strChoice1 = getIntent().getExtras().getString("Choice1");
        strChoice2 = getIntent().getExtras().getString("Choice2");
        strChoice3 = getIntent().getExtras().getString("Choice3");
        strChoice4 = getIntent().getExtras().getString("Choice4");
        strCorrectAnswer = getIntent().getExtras().getString("CorrectAnswer");



        Typeface tf = Typeface.createFromAsset(getAssets(), "phetsarathot.ttf");

        txtQuestion.setTypeface(tf);
        txtChoice1.setTypeface(tf);
        txtChoice2.setTypeface(tf);
        txtChoice3.setTypeface(tf);

        txtChoice4.setTypeface(tf);
        txtCorrectAnswer.setTypeface(tf);


        txtQuestion.setText(" "+strQuestion);
        txtChoice1.setText(" "+strChoice1);
        txtChoice2.setText(" "+strChoice2);
        txtChoice3.setText(" "+strChoice3);
        txtChoice4.setText(" "+strChoice4);
        txtCorrectAnswer.setText(" " + strCorrectAnswer);



    }   //  end of onCreate

    public void Initial() {

        txtQuestion = (TextView) findViewById(R.id.txtQuestionInfo);
        txtChoice1 = (TextView) findViewById(R.id.txtChoice1Info);
        txtChoice2 = (TextView) findViewById(R.id.txtChoice2Info);
        txtChoice3 = (TextView) findViewById(R.id.txtChoice3Info);
        txtChoice4 = (TextView) findViewById(R.id.txtChoice4Info);
        txtCorrectAnswer = (TextView) findViewById(R.id.txtCorrectAnswer);

    }   //  end of Initial



}   //  end of InfoQuestionActivity
