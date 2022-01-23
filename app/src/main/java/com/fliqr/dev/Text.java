package com.fliqr.dev;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Text extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
    }

    public void back(View view){
        finish();
    }

    public void gotoOutput(View view){
        Intent intent = new Intent(Text.this, Output.class);
        Text.this.startActivity(intent);
        Text.this.finish();
    }

}