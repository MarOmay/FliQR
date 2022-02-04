package com.fliqr.dev;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
                    Intent intent = new Converter().displayResult(text.getText().toString(), windowManager, Text.this);
                    if (intent != null){
                        Text.this.startActivity(intent);
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