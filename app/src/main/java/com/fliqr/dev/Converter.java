package com.fliqr.dev;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Environment;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

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

    public boolean addRecord(String submitData, Activity activity){

        String targetForm = submitData.split("%&SUBMIT&%")[0];
        String[] entries = submitData.split("%&SUBMIT&%")[1].split("%&ANSWER&%");

        try {
            File storageDir = new File(Environment
                    .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/FliQR/");
            FileInputStream fileInputStream = new FileInputStream(new File(storageDir,targetForm + ".xlsx"));

            Workbook workbook = new HSSFWorkbook(fileInputStream);

            Sheet spreadsheet = workbook.getSheetAt(0);

            Row row;

            Map<String, Object []> formData = new TreeMap<String, Object[]>();


            FormulaEvaluator formulaEvaluator = workbook.getCreationHelper().createFormulaEvaluator();
            int ctrRow = 1;
            for(Row row2: spreadsheet) {

                String[] tempData = new String[99];
                int tempCtr = 0;

                for(Cell cell: row2) {
                    tempData[tempCtr] = cell.getStringCellValue();
                    tempCtr++;
                }

                formData.put(ctrRow+"",tempData);
                ctrRow++;
            }

            formData.put(ctrRow+"", entries);

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

            fileInputStream.close();

            FileOutputStream out = null;
            try {
                out = new FileOutputStream(new File(storageDir, targetForm + ".xlsx"));
                workbook.write(out);
                Toast.makeText(activity.getApplicationContext(), "Form Updated!", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(activity.getApplicationContext(), "Error in writing workbook", Toast.LENGTH_SHORT).show();
                return false;
            }
            finally {
                try {
                    out.close();
                } catch (IOException e) {
                    Toast.makeText(activity.getApplicationContext(), "Error in creating workbook", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }


        }
        catch (Exception e){
            return false;
        }

        return true;
    }



}
