package com.fliqr.dev;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.ViewManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class CreateForm extends AppCompatActivity {

    private LinearLayout linearLayout;
    private Button addEntry, generate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_form);

        linearLayout = findViewById(R.id.outer);
        addEntry = findViewById(R.id.add_entry);
        generate = findViewById(R.id.generate_button);

        addEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView entryTextView = addTV("");

                AlertDialog.Builder entryBuilder = new AlertDialog.Builder(CreateForm.this);
                entryBuilder.setTitle("New entry");

                EditText entry = new EditText(getApplicationContext());
                entry.setInputType(InputType.TYPE_CLASS_TEXT);

                entryBuilder.setView(entry);

                entryBuilder.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        entryTextView.setText(entry.getText().toString());

                        if(entryTextView.getText().toString().length() < 1){
                            Toast.makeText(getApplicationContext(), "Entry is empty", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            LinearLayout childLL = addLL();
                            childLL.addView(entryTextView);
                            linearLayout.addView(childLL);
                        }

                    }
                });

                entryBuilder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        return;
                    }
                });

                entryBuilder.create().show();

            }
        });

    }

    private LinearLayout addLL(){
        LinearLayout linearLayout = new LinearLayout(getApplicationContext());

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        layoutParams.setMargins(20,10,20,10);

        linearLayout.setLayoutParams(layoutParams);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        return linearLayout;
    }

    private TextView addTV(String text){
        TextView textView = new TextView(getApplicationContext());

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        textView.setLayoutParams(params);

        textView.setText(text);
        textView.setTextSize(18);
        textView.setClickable(true);

        GradientDrawable shape =  new GradientDrawable();
        shape.setCornerRadius( 15 );
        shape.setColor(Color.WHITE);
        textView.setBackground(shape);

        textView.setPadding(25,0,25,10);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(CreateForm.this);

                builder.setTitle("Modify entry");
                builder.setMessage("Delete this entry?");
                builder.setPositiveButton("DELETE", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ((ViewManager)textView.getParent()).removeView(textView);
                    }
                });

                builder.setNegativeButton("EDIT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String text = textView.getText().toString();

                        AlertDialog.Builder edit = new AlertDialog.Builder(CreateForm.this);
                        edit.setTitle("Edit entry");

                        EditText editText = new EditText(getApplicationContext());
                        editText.setText(text);

                        edit.setView(editText);

                        edit.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                textView.setText(editText.getText().toString());
                            }
                        });

                        edit.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                return;
                            }
                        });

                        edit.create().show();

                    }
                });

                try {
                    builder.create().show();
                }
                catch (Exception e){
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }

            }
        });

        return textView;
    }

    public void back(View view){
        finish();
    }

}