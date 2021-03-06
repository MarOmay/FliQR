package com.fliqr.dev;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import android.view.ViewManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class Reports extends AppCompatActivity {

    private TextView textView;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        textView = findViewById(R.id.file_name);
        linearLayout = findViewById(R.id.linearLayout);

        try {
            File storageDir = new File(Environment
                    .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/FliQR/");

            if (!storageDir.exists())
                storageDir.mkdirs();

            int ctr= 0;
            for (File file : storageDir.listFiles()){
                if(!file.isDirectory() && file.getAbsolutePath().endsWith(".xlsx")){
                    String[] temp = file.getAbsolutePath().split("/");
                    String path = temp[temp.length - 3] + "/" + temp[temp.length - 2] + "/" + temp[temp.length - 1];

                    LinearLayout childLL = addLL();
                    childLL.addView(addTV(temp[temp.length - 1], path));
                    linearLayout.addView(childLL);

                    ctr++;
                }
            }

            if (ctr == 0){
                Toast.makeText(getApplicationContext(), "No records yet", Toast.LENGTH_LONG).show();
            }
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(), "Can't generate reports", Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(), "Please check app permissions", Toast.LENGTH_SHORT).show();
        }



    }

    private LinearLayout addLL(){
        LinearLayout linearLayout = new LinearLayout(getApplicationContext());

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        layoutParams.setMargins(20,10,20,10);

        linearLayout.setLayoutParams(layoutParams);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        return linearLayout;
    }

    private TextView addTV(String text, String path){
        TextView textView = new TextView(getApplicationContext());

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        textView.setLayoutParams(params);

        textView.setText(text);
        textView.setTextSize(18);
        textView.setClickable(true);

        GradientDrawable shape =  new GradientDrawable();
        shape.setCornerRadius( 15 );
        shape.setColor(Color.WHITE);
        textView.setBackground(shape);

        textView.setPadding(25,0,25,10);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(Reports.this);

                builder.setTitle("Options");
                builder.setMessage("Delete or locate this entry?");
                builder.setPositiveButton("DELETE", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {



                        ///separte

                        AlertDialog.Builder yesNO = new AlertDialog.Builder(Reports.this);
                        yesNO.setTitle("Delete this file?");
                        yesNO.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                File file = new File(Environment
                                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/FliQR/" + textView.getText().toString());

                                file.delete();


                                //removes not only the textView, but also the LinearLayout container
                                ((ViewManager)textView.getParent().getParent()).removeView((LinearLayout) textView.getParent());
                            }
                        });

                        yesNO.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //No code
                            }
                        });

                        yesNO.create().show();
                    }
                });

                builder.setNegativeButton("LOCATE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Toast.makeText(getApplicationContext(), "Saved: " + path, Toast.LENGTH_LONG).show();

                    }
                });

                try {
                    builder.create().show();
                }
                catch (Exception e){
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }

            }
        });



        return textView;

    }

    public void back(View view){
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

}