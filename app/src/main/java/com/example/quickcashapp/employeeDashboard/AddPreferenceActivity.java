package com.example.quickcashapp.employeeDashboard;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class AddPreferenceActivity extends AppCompatActivity {
    private Context context;
    public AddPreferenceActivity(Context context){
        this.context = context;
        showMessage();
    }

    protected void showMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setMessage("then click 'Yes'");
        builder.setTitle("Add to Preference?");
        builder.setCancelable(false);

        builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
            dialog.dismiss();
            Toast.makeText(context, "Yes", Toast.LENGTH_SHORT).show();
        });

        builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
            dialog.dismiss();
            Toast.makeText(context, "No", Toast.LENGTH_SHORT).show();

        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}