package com.fliqr.dev;

import java.io.File;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

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

public class CreateForm extends AppCompatActivity {

    private ScrollView scrollView;
    private LinearLayout linearLayout;
    private Button addEntry, generate;
    private TextView title, counter;

    private String formName;
    private String[] entries = new String[99];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_form);

        AlertDialog.Builder formNameAD = new AlertDialog.Builder(CreateForm.this);
        formNameAD.setTitle("Form Name");

        EditText formNameET = new EditText(getApplicationContext());
        formNameET.setInputType(InputType.TYPE_CLASS_TEXT);

        formNameAD.setView(formNameET);

        formNameAD.setPositiveButton("CREATE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String temp = formNameET.getText().toString();
                if (temp.length() < 1 || temp.equals(" ") ){
                    Toast.makeText(getApplicationContext(), "Invalid Form Name", Toast.LENGTH_SHORT).show();
                }
                else if (temp.startsWith(" ") || temp.endsWith(" ")){
                    Toast.makeText(getApplicationContext(), "Form Name can't start/end with space", Toast.LENGTH_SHORT).show();
                }
                else{
                    formName = temp;
                    title.setText(formName);
                }
            }
        });

        formNameAD.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                onBackPressed();
            }
        });

        formNameAD.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                onBackPressed();
                Toast.makeText(getApplicationContext(), "Form Name is required", Toast.LENGTH_SHORT).show();
            }
        });

        formNameAD.create().show();
        formNameET.requestFocus();

        scrollView = findViewById(R.id.scrollView);
        linearLayout = findViewById(R.id.outer);
        title = findViewById(R.id.file_name);
        counter = findViewById(R.id.counter);
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

                        counter.setText(linearLayout.getChildCount() + "/99");

                    }
                });

                entryBuilder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        counter.setText(linearLayout.getChildCount() + "/99");

                        return;
                    }
                });

                entryBuilder.create().show();

            }
        });

        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = formName + "%&FORM&%";

                int size = linearLayout.getChildCount();

                if (size < 1){
                    Toast.makeText(getApplicationContext(), "Form is empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                int ctr = 0;
                while (size > ctr){
                    LinearLayout tempLL = (LinearLayout) linearLayout.getChildAt(ctr);
                    TextView tempTV = (TextView) tempLL.getChildAt(0);
                    text = text + tempTV.getText().toString() + "%&ENTRY&%";
                    entries[ctr] = tempTV.getText().toString();
                    ctr++;
                }

                Workbook workbook = new HSSFWorkbook();

                Sheet spreadsheet = workbook.createSheet("Sheet 1");

                Row row;

                Map<String, Object []> formData = new TreeMap<String, Object[]>();

                formData.put("1", entries);

                Set<String> keyid = formData.keySet();

                int rowid = 0;

                for (String key : keyid){
                    row = spreadsheet.createRow(rowid++);
                    Object[] objArr = formData.get(key);
                    int cellid = 0;

                    for (Object obj : objArr) {
                        Cell cell = row.createCell(cellid++);
                        cell.setCellValue((String)obj);
                    }
                }

                try{

                    File storageDir = new File(Environment
                            .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/FliQR/");
                    if (!storageDir.exists())
                        storageDir.mkdirs();

                    ContextWrapper cw = new ContextWrapper(getApplicationContext());
                    File file = new File(storageDir, formName + ".xlsx");

                    FileOutputStream out = null;
                    try {
                        out = new FileOutputStream(file);
                        workbook.write(out);
                        Toast.makeText(getApplicationContext(), "Form Created!", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Error in writing workbook", Toast.LENGTH_SHORT).show();
                    }
                    finally {
                        try {
                            out.close();
                        } catch (IOException e) {
                            Toast.makeText(getApplicationContext(), "Error in creating workbook", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
                catch (Exception e){
                    Toast.makeText(getApplicationContext(), "Can't create workbook", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), "Please check app permissions", Toast.LENGTH_SHORT).show();
                }

                WindowManager windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
                Intent intent = new Converter().displayResult(text, windowManager, CreateForm.this);
                if (intent != null){
                    CreateForm.this.startActivity(intent);
                }
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
                        //removes not only the textView, but also the LinearLayout container
                        ((ViewManager)textView.getParent().getParent()).removeView((LinearLayout) textView.getParent());
                        counter.setText(linearLayout.getChildCount() + "/99");
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

        counter.setText(linearLayout.getChildCount() + "/99");

        return textView;
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