package com.fliqr.dev;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ErrorSubmission extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error_submission);
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