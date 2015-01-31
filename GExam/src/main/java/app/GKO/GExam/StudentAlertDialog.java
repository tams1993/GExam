package app.GKO.GExam;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by lilhazk on 13/02/14.
 */
public class StudentAlertDialog {

    private AlertDialog.Builder objAlert;

    public void HaveSpace(Context context) {

        objAlert = new AlertDialog.Builder(context);
        objAlert.setTitle("Field is Emty");


        objAlert.setMessage("Please Fill Username And Password");
        objAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        objAlert.setCancelable(false);

        objAlert.show();

    }   //  end of HaveSpace

    public void NameClassID(Context context) {

        objAlert = new AlertDialog.Builder(context);
        objAlert.setTitle("Field is Emty");
        objAlert.setMessage("Please Fill Name, Class and ID");
        objAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();

            }
        });

        objAlert.setCancelable(false);
        objAlert.show();

    }   //  end of NameClassID

    public void UserPasswordNotTrue(Context context) {

        objAlert = new AlertDialog.Builder(context);
        objAlert.setTitle("Wrong User or Password");
        objAlert.setMessage("No User or Password in Database" + "\n" + "Please Try Again.");

        objAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();
            }
        });
        objAlert.setCancelable(false);

        objAlert.show();

    }   //  end of UserPasswordNotTrue

}   //  end of Mainclass
