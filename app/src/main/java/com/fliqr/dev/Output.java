package com.fliqr.dev;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Output extends AppCompatActivity {

    private Bitmap bitmap;

    private ImageView result;
    private Button save, close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_output);

        byte[] byteArray = getIntent().getByteArrayExtra("BitmapQR");
        bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        result = findViewById(R.id.outputQr);
        result.setImageBitmap(bitmap);

        save = findViewById(R.id.save_button);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Generate date and time for unique file name
                String filename = "";
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy-HHmm.ss");
                Date date = new Date();
                filename = formatter.format(date) + ".jpeg";

                try {
                    File storageDir = new File(Environment
                            .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/FliQR/");
                    if (!storageDir.exists())
                        storageDir.mkdirs();

                    ContextWrapper cw = new ContextWrapper(getApplicationContext());
                    File file = new File(storageDir, filename);

                    FileOutputStream fos = null;
                    try {
                        fos = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fos);
                        Toast.makeText(getApplicationContext(), "Image saved", Toast.LENGTH_SHORT).show();
                        fos.flush();
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Saving image unsuccessful", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    } finally {
                        try {
                            fos.close();
                        } catch (IOException e) {
                            Toast.makeText(getApplicationContext(), "Saving image unsuccessful", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }

                    Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    Uri contentUri = Uri.fromFile(file);
                    mediaScanIntent.setData(contentUri);
                    cw.sendBroadcast(mediaScanIntent);

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Saving image unsuccessful", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });

        close = findViewById(R.id.close_button);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Output.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                Output.this.startActivity(intent);
            }
        });
    }

    public void gotoMainActivity(View view){
        Intent intent = new Intent(Output.this, MainActivity.class);
        Output.this.startActivity(intent);
        Output.this.finish();
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