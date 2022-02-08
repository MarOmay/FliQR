package com.fliqr.dev;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ErrorSubmission extends AppCompatActivity {

    public TextView errorMessage2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error_submission);

        errorMessage2 = findViewById(R.id.error_mess2);

        errorMessage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION));
                Toast.makeText(getApplicationContext(), "Make sure FliQR's access is ON", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void gotoMainActivity(View view){
        Intent intent = new Intent(ErrorSubmission.this, MainActivity.class);
        ErrorSubmission.this.startActivity(intent);
        ErrorSubmission.this.finish();
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