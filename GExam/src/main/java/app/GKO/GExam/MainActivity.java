package app.GKO.GExam;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by lilhazk on 11/02/14.
 */
public class MainActivity extends Activity {

    private Button btnTeacherLogin, btnStudentLogin;
    private TextView txtOnline;
    private AlertDialog.Builder objAlert;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_layout);
        InitialWidget();

        btnTeacherLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Online() == true) {

                    Intent objIntent = new Intent(MainActivity.this, TeacherLoginActivity.class);
                    startActivity(objIntent);
                } else {

                    objAlert = new AlertDialog.Builder(MainActivity.this);
                    objAlert.setMessage("Your Device Must Be Online to Continue");
                    objAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    objAlert.show();


                }


            }
        }); //  end of btnTeacherLogin

        btnStudentLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Online() == true) {

                    Intent objIntent = new Intent(MainActivity.this, StudentLoginActivity.class);
                    startActivity(objIntent);

                } else {

                    objAlert = new AlertDialog.Builder(MainActivity.this);
                    objAlert.setMessage("Your Device Must Be Online to Continue");
                    objAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    objAlert.show();
                }


            }
        }); //  end of btnStudentLogin

        if (Online() == true) {

            txtOnline.setText("Online");

        } else {

            txtOnline.setText("Offline");

        }







    }   //  end of onCreate

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater objMenuInflater = getMenuInflater();
        objMenuInflater.inflate(R.menu.about, menu);
        return super.onCreateOptionsMenu(menu);


    }   //  onCreateOptionsMenu

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {


            case R.id.about_menu:

                Intent objIntent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(objIntent);



        }

        return super.onOptionsItemSelected(item);
    }   //  END of onOptionsItemSelected

    private void InitialWidget() {

        btnTeacherLogin = (Button) findViewById(R.id.btnTeacherLogin);
        btnStudentLogin = (Button) findViewById(R.id.btnStudentLogin);
        txtOnline = (TextView) findViewById(R.id.txtOnline);

        Typeface tf = Typeface.createFromAsset(getAssets(), "phetsarathot.ttf");

        btnStudentLogin.setTypeface(tf);
        btnTeacherLogin.setTypeface(tf);

    }   //  end of InitialWidget

    private boolean Online() {

        Boolean result = false;

        ConnectivityManager objConnectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo objNetworkInfo = objConnectivityManager.getActiveNetworkInfo();

        if (objNetworkInfo != null && objNetworkInfo.isConnected()) {

            result = true;

        } else {
            result = false;
        }

        return result;
        }

    }   //  end of MainActivity
