package com.fliqr.dev;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

//jireh
public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_STORAGE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button scan = findViewById(R.id.scanqr_button);
        scan.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(MainActivity.this);
                intentIntegrator.setBeepEnabled(false);
                intentIntegrator.setOrientationLocked(true);
                intentIntegrator.setCaptureActivity(Capture.class);
                intentIntegrator.initiateScan();

            }
        });

        requestStorage();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (intentResult.getContents() != null){

            Intent intent = null;

            if(intentResult.getContents().contains("%&FORM&%") && intentResult.getContents().contains("%&ENTRY&%")){
                intent = new Intent(MainActivity.this, AnswerForm.class);
            }
            else if(intentResult.getContents().contains("%&SUBMIT&%") && intentResult.getContents().contains("%&ANSWER&%")){

                boolean success = new Converter().addRecord(intentResult.getContents(), MainActivity.this);
                if (success){
                    intent = new Intent(MainActivity.this, Submitted.class);
                }
                else {
                    intent = new Intent(MainActivity.this, ErrorSubmission.class);
                }
            }
            else {
                intent = new Intent(MainActivity.this, RetrievedText.class);
            }

            Bundle bundle = new Bundle();
            bundle.putString("result", intentResult.getContents());
            intent.putExtras(bundle);
            startActivity(intent);
        }
        else {
            Toast.makeText(getApplicationContext(), "No result found", Toast.LENGTH_SHORT).show();
        }
    }

    public void gotoCreate(View view){
        Intent intent = new Intent(MainActivity.this, Create.class);
        MainActivity.this.startActivity(intent);
    }

    public void gotoScanQR(View view){
        Intent intent = new Intent(MainActivity.this, ScanQR.class);
        MainActivity.this.startActivity(intent);
    }

    public void gotoDocxQr(View view){
        Intent intent = new Intent(MainActivity.this, Reports.class);
        MainActivity.this.startActivity(intent);
    }

    public void gotoImportQr(View view){
        Intent intent = new Intent(MainActivity.this, ImportQr.class);
        MainActivity.this.startActivity(intent);
    }

    public void back(View view){
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    private void Jireh(){
        System.out.print("pogi");
    }

    private void requestStorage(){
        if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            //requestStorage();
            //Toast.makeText(getApplicationContext(),"Storage access is required",Toast.LENGTH_SHORT);
        }
        else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_STORAGE);
            }
            else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_STORAGE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        if (requestCode == PERMISSION_REQUEST_STORAGE){
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //Allowed
            }
            else {
                Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

}