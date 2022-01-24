package com.fliqr.dev;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class Text extends AppCompatActivity {

    private TextView text;
    private Button generate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);

        text = findViewById(R.id.editText);
        generate =  findViewById(R.id.generate_button);

        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (text.getText().toString().length() < 1){
                    Toast.makeText(getApplicationContext(), "Please input text", Toast.LENGTH_SHORT).show();
                }
                else {
                    WindowManager windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
                    Display display = windowManager.getDefaultDisplay();
                    Point point = new Point();
                    display.getSize(point);
                    int width = point.x;
                    int height = point.y;
                    int dimen = width < height ? width : height;
                    QRGEncoder qrgEncoder = new QRGEncoder(text.getText().toString(),null, QRGContents.Type.TEXT, dimen);

                    try {
                        Bitmap bitmap = qrgEncoder.encodeAsBitmap();

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] byteArray = stream.toByteArray();

                        Intent intent =  new Intent(Text.this, Output.class);
                        intent.putExtra("BitmapQR", byteArray);

                        Text.this.startActivity(intent);
                    }
                    catch (Exception e){
                        Toast.makeText(getApplicationContext(), "Can't generate QR", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }


    public void gotoOutput(View view){
        Intent intent = new Intent(Text.this, Output.class);
        Text.this.startActivity(intent);
        Text.this.finish();
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