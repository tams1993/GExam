package app.GKO.GExam;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by lilhazk on 08/03/14.
 */
public class TeacherAlertDialog {

    private AlertDialog.Builder objAlert;

    public void HaveSpace(Context context) {

        objAlert = new AlertDialog.Builder(context);
        objAlert.setTitle("Field is Emty");
        objAlert.setMessage("Please Fill Username and Password");
        objAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

            }
        });
        objAlert.show();
        objAlert.setCancelable(false);

    }   //  end of HaveSpace

    public void UserPasswordNotTrue(Context context) {

        objAlert = new AlertDialog.Builder(context);
        objAlert.setTitle("Wrong User or Password");
        objAlert.setMessage("No User or Password in Database" + "\n" + "Please Try Again");
        objAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        objAlert.show();
        objAlert.setCancelable(false);

    }   //  end of UserPasswordNotTrue

}   //  end of TeacherAlertDialog
