package app.GKO.GExam;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
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
 * Created by lilhazk on 24/02/14.
 */
public class ListGeneralitQuestionActivity extends ListActivity {

    private GeneralITQuestionTable objGeneralITQuestionTable;
    private String strQuestion , strChoice1, strChoice2, strChoice3, strChoice4, strCorrectAnswer;
    private String strTeacherQuestion, strTeacherChoice1, strTeacherChoice2, strTeacherChoice3, strTeacherChoice4;
    private SimpleCursorAdapter objAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        //Policy
//
//        if (Build.VERSION.SDK_INT > 7) {
//
//            StrictMode.ThreadPolicy objPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//            StrictMode.setThreadPolicy(objPolicy);
//
//        }
//
//        //  Connect Http
//
//        InputStream objInputStream = null;
//        String strJSON = "";
//
//        try {
//
//            HttpClient objHttpClient = new DefaultHttpClient();
//            HttpPost objHttpPost = new HttpPost("http://gko-dev.netau.net/Exam/php_get_question.php");
//            HttpResponse objHttpResponse = objHttpClient.execute(objHttpPost);
//            HttpEntity objHttpEntity = objHttpResponse.getEntity();
//            objInputStream = objHttpEntity.getContent();
//
//        } catch (Exception e) {
//
//            Log.d("Exam", "Connect Http ==> " + e.toString());
//
//        }
//
//        //JSON to String
//
//        try {
//
//            BufferedReader objBufferedReader = new BufferedReader(new InputStreamReader(objInputStream, "UTF-8"));
//            StringBuilder objStringBuilder = new StringBuilder();
//            String strLine = null;
//
//            while (( strLine = objBufferedReader.readLine() )!= null ) {
//
//                objStringBuilder.append(strLine);
//            }
//
//            objInputStream.close();
//            strJSON = objStringBuilder.toString();
//
//        } catch (Exception e) {
//            Log.d("Exam", "JSON to String ==> " + e.toString());
//
//        }
//
//        // take mySQL to SQLite
//
//        try {
//
//            final JSONArray objJsonArray = new JSONArray(strJSON);
//
//            for (int i = 0; i <= objJsonArray.length(); i++) {
//
//                JSONObject objJSONObject = objJsonArray.getJSONObject(i);
//
//                strQuestion = objJSONObject.getString("Question");
//                strChoice1 = objJSONObject.getString("Choice1");
//                strChoice2 = objJSONObject.getString("Choice2");
//                strChoice3 = objJSONObject.getString("Choice3");
//                strChoice4 = objJSONObject.getString("Choice4");
//
//                long insertID = objTeacherSmallScaleQuestionPage.
//
//            }
//
//        } catch (Exception e) {
//        }

        // ListView
        objGeneralITQuestionTable = new GeneralITQuestionTable(this);
        Cursor QuestionList = objGeneralITQuestionTable.ReadAllData();
        String[] form = new String[]{GeneralITQuestionTable.COL_QUESTION};
        int[] target = new int[]{R.id.txtQuestion};
        objAdapter = new SimpleCursorAdapter(this, R.layout.list_all_question, QuestionList, form, target);
        setListAdapter(objAdapter);




    }   //  end of onCreate

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Cursor selectItem = (Cursor) l.getItemAtPosition(position);


        Intent objIntent = new Intent(ListGeneralitQuestionActivity.this, InfoQuestionActivity.class);

        strQuestion = selectItem.getString(selectItem.getColumnIndex(GeneralITQuestionTable.COL_QUESTION));
        strChoice1 = selectItem.getString(selectItem.getColumnIndex(GeneralITQuestionTable.COL_CHOICE1));
        strChoice2 = selectItem.getString(selectItem.getColumnIndex(GeneralITQuestionTable.COL_CHOICE2));
        strChoice3 = selectItem.getString(selectItem.getColumnIndex(GeneralITQuestionTable.COL_CHOICE3));
        strChoice4 = selectItem.getString(selectItem.getColumnIndex(GeneralITQuestionTable.COL_CHOICE4));
        strCorrectAnswer = selectItem.getString(selectItem.getColumnIndex(GeneralITQuestionTable.COL_TRUEANSWER));

        objIntent.putExtra("Question", strQuestion);
        objIntent.putExtra("Choice1", strChoice1);
        objIntent.putExtra("Choice2", strChoice2);
        objIntent.putExtra("Choice3", strChoice3);
        objIntent.putExtra("Choice4", strChoice4);
        objIntent.putExtra("CorrectAnswer", strCorrectAnswer);


        startActivity(objIntent);

        Log.d("Exam", strQuestion);
        Log.d("Exam", strChoice1);
        Log.d("Exam", strChoice2);
        Log.d("Exam", strChoice3);
        Log.d("Exam", strChoice4);






    }   //  end of onListItemClick

}   //  end of ListQuestionAcitivity
