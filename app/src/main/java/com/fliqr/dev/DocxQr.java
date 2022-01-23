package com.fliqr.dev;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class DocxQr extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docx_qr);
    }

    public void back(View view){
        finish();
    }

    public void gotoOutput(View view){
        Intent intent = new Intent(DocxQr.this, Output.class);
        DocxQr.this.startActivity(intent);
        DocxQr.this.finish();
    }
}