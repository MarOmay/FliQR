package com.fliqr.dev;

import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.text.InputType;
import android.view.View;
import android.view.ViewManager;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class AnswerForm extends AppCompatActivity {

    private LinearLayout linearLayout;

    private TextView title;

    private Button generate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_form);

        linearLayout = findViewById(R.id.outer);
        title = findViewById(R.id.file_name);
        generate = findViewById(R.id.generate_button);

        Bundle bundle = getIntent().getExtras();
        String[] formData = bundle.getString("result").split("%&FORM&%");

        String head = formData[0];
        title.setText(head);

        String[] entries = formData[1].split("%&ENTRY&%");

        for (String entry: entries){
            LinearLayout childLL = addLL();
            childLL.addView(addTV(entry),0);
            childLL.addView(addET(),1);
            linearLayout.addView(childLL);
        }

        LinearLayout childLL = addLL();
        //childLL.addView(entryTextView);
        //linearLayout.addView(childLL);
    }

    private LinearLayout addLL(){
        LinearLayout linearLayout = new LinearLayout(getApplicationContext());

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        layoutParams.setMargins(20,10,20,10);

        linearLayout.setLayoutParams(layoutParams);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        GradientDrawable shape =  new GradientDrawable();
        shape.setCornerRadius( 15 );
        shape.setColor(Color.WHITE);
        linearLayout.setBackground(shape);

        return linearLayout;
    }

    private TextView addTV(String text){
        TextView textView = new TextView(getApplicationContext());

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        textView.setLayoutParams(params);

        textView.setText(text);
        textView.setTextSize(18);
        textView.setTextColor(Color.BLACK);
        textView.setClickable(true);

        GradientDrawable shape =  new GradientDrawable();
        shape.setCornerRadius( 15 );
        shape.setColor(Color.WHITE);
        textView.setBackground(shape);

        textView.setPadding(25,0,25,0);

        return textView;
    }

    private EditText addET(){
        EditText editText = new EditText(getApplicationContext());
        editText.setTextColor(Color.GRAY);

        return editText;
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