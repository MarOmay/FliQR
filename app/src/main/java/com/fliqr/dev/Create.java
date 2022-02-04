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
        Create.this.finish();
    }

    public void gotoDocxQr(View view){ //CREATE FORM
        Intent intent = new Intent(Create.this, CreateForm.class);
        Create.this.startActivity(intent);
    }

}