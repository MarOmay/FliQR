package com.fliqr.dev;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ExcelQr extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excel_qr);
    }

    public void back(View view){
        finish();
    }

    public void gotoOutput(View view){
        Intent intent = new Intent(ExcelQr.this, Output.class);
        ExcelQr.this.startActivity(intent);
        ExcelQr.this.finish();
    }
}