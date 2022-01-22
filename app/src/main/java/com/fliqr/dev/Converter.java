package com.fliqr.dev;

import android.graphics.Bitmap;
import android.widget.Toast;

import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;

import com.google.common.util.concurrent.ListenableFuture;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class Converter {



    private String content;

    public Converter(String content){
        this.content = content;
    }

    //From any text passed as parameter to the instance
    public Bitmap toQR(){
        QRGEncoder encoder;

        encoder = new QRGEncoder(this.content, null, QRGContents.Type.TEXT, 300);
        try{
            return encoder.encodeAsBitmap();
        }
        catch (Exception E){
            Toast.makeText(null, "Conversion Failed", Toast.LENGTH_SHORT).show();
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
