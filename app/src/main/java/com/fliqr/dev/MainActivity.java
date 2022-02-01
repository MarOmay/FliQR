package com.fliqr.dev;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.jetbrains.annotations.Nullable;

public class MainActivity extends AppCompatActivity {

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
                //intentIntegrator.setBarcodeImageEnabled(true);
                intentIntegrator.setOrientationLocked(true);
                intentIntegrator.setCaptureActivity(Capture.class);
                intentIntegrator.initiateScan();

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (intentResult.getContents() != null){
            Intent intent = new Intent(MainActivity.this, RetrievedText.class);
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
        Intent intent = new Intent(MainActivity.this, DocxQr.class);
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


}