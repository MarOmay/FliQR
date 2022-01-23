package com.fliqr.dev;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Output extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_output);
    }

    public void gotoMainActivity(View view){
        Intent intent = new Intent(Output.this, MainActivity.class);
        Output.this.startActivity(intent);
        Output.this.finish();
    }

}