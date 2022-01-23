package com.fliqr.dev;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Create extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);


    }

    public void back(View view){
        finish();
    }

    public void gotoText(View view){
        Intent intent = new Intent(Create.this, Text.class);
        Create.this.startActivity(intent);
    }

    public void gotoDocx(View view){
        Intent intent = new Intent(Create.this, DocxQr.class);
        Create.this.startActivity(intent);
    }

}