package app.GKO.GExam;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by lilhazk on 03/03/14.
 */
public class ExitAcitivity extends Activity {

    private Button btnExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finish_layout);

        btnExit = (Button) findViewById(R.id.btnExit);
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent objIntent = new Intent(Intent.ACTION_MAIN);
                objIntent.addCategory(Intent.CATEGORY_HOME);
                objIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(objIntent);

                android.os.Process.killProcess(android.os.Process.myPid());//   kill process

            }
        });
    }
}   //  end of ExitAcitivity
