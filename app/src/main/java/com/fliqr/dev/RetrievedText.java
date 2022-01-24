package com.fliqr.dev;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class RetrievedText extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieved_text);
    }

    public void gotoMainActivity(View view){
        Intent intent = new Intent(RetrievedText.this, MainActivity.class);
        RetrievedText.this.startActivity(intent);
        RetrievedText.this.finish();
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