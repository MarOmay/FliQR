package com.fliqr.dev;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class Converter {

    public Converter(){

    }

    //From any text passed as parameter to the instance
    public Intent displayResult(String text, WindowManager windowManager, Activity activity){

        Display display = windowManager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        int height = point.y;
        int dimen = width < height ? width : height;
        QRGEncoder qrgEncoder = new QRGEncoder(text,null, QRGContents.Type.TEXT, dimen);

        try {
            Bitmap bitmap = qrgEncoder.encodeAsBitmap();

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();

            Intent intent =  new Intent(activity, Output.class);
            intent.putExtra("BitmapQR", byteArray);

            return intent;
        }
        catch (Exception e){
            Toast.makeText(activity.getApplicationContext(), "Can't generate QR", Toast.LENGTH_SHORT).show();
        }

        return null;
    }


    //From any bitmap passed as parameter
    public String toText(){

    /*
    This is for the live camera preview for RQ detection xml layout


    */


        return "";
    }



}
