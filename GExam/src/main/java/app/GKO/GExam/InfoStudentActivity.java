package app.GKO.GExam;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by lilhazk on 25/02/14.
 */
public class InfoStudentActivity extends Activity{

    private TextView txtListName, txtListClass, txtListID, txtListScore, txtListDate;
    private String strName, strID, strClass, strScore, strDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_list_student);

        Initial();

        strName = getIntent().getExtras().getString("Name");
        strClass = getIntent().getExtras().getString("Class");
        strID = getIntent().getExtras().getString("StudentID");
        strScore = getIntent().getExtras().getString("Score");
        strDate = getIntent().getExtras().getString("Date");


        txtListScore.setText(strScore);
        txtListName.setText(strName);
        txtListID.setText(strID);
        txtListClass.setText(strClass);
        txtListDate.setText(strDate);

        Typeface tf = Typeface.createFromAsset(getAssets(), "phetsarathot.ttf");

        txtListDate.setTypeface(tf);
        txtListClass.setTypeface(tf);
        txtListID.setTypeface(tf);
        txtListName.setTypeface(tf);
        txtListScore.setTypeface(tf);




    }   //  end of onCreate

    public void Initial() {

        txtListClass = (TextView) findViewById(R.id.txtListClass);
        txtListID = (TextView) findViewById(R.id.txtListID);
        txtListName = (TextView) findViewById(R.id.txtListName);
        txtListScore = (TextView) findViewById(R.id.txtListScore);
        txtListDate = (TextView) findViewById(R.id.txtDate);

    }   //  end of Initial

}   //  end of InfoStudentActivity
