package com.fliqr.dev;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
        Intent intent = new Intent(MainActivity.this, ScanQR.class);
        MainActivity.this.startActivity(intent);
        */


    }

    public void gotoCreate(View view){
        Intent intent = new Intent(MainActivity.this, Create.class);
        MainActivity.this.startActivity(intent);
    }





}