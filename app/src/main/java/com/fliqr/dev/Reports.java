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

            for (File file : storageDir.listFiles()){
                if(!file.isDirectory() && file.getAbsolutePath().endsWith(".xlsx")){
                    String[] temp = file.getAbsolutePath().split("/");
                    String path = temp[temp.length - 3] + "/" + temp[temp.length - 2] + "/" + temp[temp.length - 1];

                    LinearLayout childLL = addLL();
                    childLL.addView(addTV(temp[temp.length - 1], path));
                    linearLayout.addView(childLL);
                }
            }
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(), "Can't generate reports", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getApplicationContext(), "Saved: " + path, Toast.LENGTH_LONG).show();
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