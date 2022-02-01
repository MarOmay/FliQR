package com.fliqr.dev;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Submitted extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submitted);
    }

    public void gotoMainActivity(View view){
        Intent intent = new Intent(Submitted.this, MainActivity.class);
        Submitted.this.startActivity(intent);
        Submitted.this.finish();
    }
}