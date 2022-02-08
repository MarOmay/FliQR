package com.fliqr.dev;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;

import java.io.BufferedInputStream;

public class ImportQr extends AppCompatActivity {

    private final int PERMISSION_REQUEST_STORAGE = 0;

    private static final int PICK_IMAGE = 1;
    private Button choose, retrieve;
    private Bitmap bmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_qr);

        choose = findViewById(R.id.choose_button);
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                getIntent.setType("image/*");

                Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickIntent.setType("image/*");

                Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

                startActivityForResult(chooserIntent, PICK_IMAGE);
            }
        });

        retrieve = findViewById(R.id.retrieve_button);
        retrieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Decode from Image to Qr
                String result = scanQRImage(bmp);
                if (result.length() > 0){

                    Intent intent = null;

                    if(result.contains("%&FORM&%") && result.contains("%&ENTRY&%")){
                        intent = new Intent(ImportQr.this, AnswerForm.class);
                    }
                    else if(result.contains("%&SUBMIT&%") && result.contains("%&ANSWER&%")){

                        boolean success = new Converter().addRecord(result, ImportQr.this);
                        if (success){
                            intent = new Intent(ImportQr.this, Submitted.class);
                        }
                        else {
                            intent = new Intent(ImportQr.this, ErrorSubmission.class);
                        }
                    }
                    else {
                        intent = new Intent(ImportQr.this, RetrievedText.class);
                    }

                    Bundle bundle = new Bundle();
                    bundle.putString("result", result);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(), "No result found", Toast.LENGTH_SHORT).show();
                }
            }
        });

        retrieve.setEnabled(false);

        //requestStorage();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE) {
            try{
                BufferedInputStream bufferedInputStream = new BufferedInputStream(getContentResolver().openInputStream(data.getData()));
                bmp = BitmapFactory.decodeStream(bufferedInputStream);
                //Detect QR portion ng photo then display sa image view
                ImageView preview = findViewById(R.id.outputQr);
                preview.setImageBitmap(bmp);
                retrieve.setEnabled(true);
            }
            catch (Exception e){
                Toast.makeText(getApplicationContext(), "Unable to choose file", Toast.LENGTH_SHORT).show();
            }

        }
    }

    public String scanQRImage(Bitmap bMap) {
        String contents = null;

        int[] intArray = new int[bMap.getWidth()*bMap.getHeight()];
        //copy pixel data from the Bitmap into the 'intArray' array
        bMap.getPixels(intArray, 0, bMap.getWidth(), 0, 0, bMap.getWidth(), bMap.getHeight());

        LuminanceSource source = new RGBLuminanceSource(bMap.getWidth(), bMap.getHeight(), intArray);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

        Reader reader = new MultiFormatReader();
        try {
            Result result = reader.decode(bitmap);
            contents = result.getText();
        }
        catch (Exception e) {
            retrieve.setEnabled(false);
            Toast.makeText(getApplicationContext(), "Cannot decode file", Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(), "Please choose a valid file", Toast.LENGTH_SHORT).show();
            finish();
            startActivity(getIntent());
            overridePendingTransition(0, 0);
        }
        return contents;
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