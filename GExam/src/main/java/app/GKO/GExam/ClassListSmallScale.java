package app.GKO.GExam;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by lilhazk on 09/03/14.
 */
public class ClassListSmallScale extends Activity{

    private Button btn3it1, btn3it2, btn3it3, btn3com;
    private int intclass = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.class_list_layout);

        initial();

        btn3it1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent objIntent3it1 = new Intent(ClassListSmallScale.this, ListStudentSmallScaleActivity.class);
                objIntent3it1.putExtra("intclass", intclass = 0);
                startActivity(objIntent3it1);

            }
        });

        btn3it2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent objIntent3it2 = new Intent(ClassListSmallScale.this, ListStudentSmallScaleActivity.class);
                objIntent3it2.putExtra("intclass", intclass = 1);
                startActivity(objIntent3it2);

            }
        });

        btn3it3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent objIntent3it3 = new Intent(ClassListSmallScale.this, ListStudentSmallScaleActivity.class);
                objIntent3it3.putExtra("intclass", intclass = 2);
                startActivity(objIntent3it3);

            }
        });

        btn3com.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent objIntent3com = new Intent(ClassListSmallScale.this, ListStudentSmallScaleActivity.class);
                objIntent3com.putExtra("intclass", intclass = 3);
                startActivity(objIntent3com);

            }
        });


    }   //  end of onCreate

    private void initial() {

        btn3com = (Button) findViewById(R.id.btn3com);
        btn3it1 = (Button) findViewById(R.id.btn3it1);
        btn3it2 = (Button) findViewById(R.id.btn3it2);
        btn3it3 = (Button) findViewById(R.id.btn3it3);

    }   //  end of initial


}   //  end of ClassListGeneralIT
