package com.fliqr.dev;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

public class Submitted extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submitted);

        playSound(R.raw.success);

    }

    public void playSound(int sound){
        MediaPlayer mp = MediaPlayer.create(getApplicationContext(), sound);
        mp.start();
    }

    public void gotoMainActivity(){
        Intent intent = new Intent(Submitted.this, MainActivity.class);
        Submitted.this.startActivity(intent);
        Submitted.this.finish();
    }

    public void gotoMainActivity(View view){
        Intent intent = new Intent(Submitted.this, MainActivity.class);
        Submitted.this.startActivity(intent);
        Submitted.this.finish();
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