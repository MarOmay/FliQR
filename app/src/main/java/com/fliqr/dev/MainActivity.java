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
        try{
        Button button = (Button) findViewById(R.id.create_button);
        Toast.makeText(MainActivity.this, "Stage 1", Toast.LENGTH_SHORT).show();
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Ok pa", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, CreatePage.class);
                MainActivity.this.startActivity(intent);
            }
        });
    }
        catch (Exception e){
            Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
        }

    }





}