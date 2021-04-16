package com.khumdev.latihanuki;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Addtodo extends AppCompatActivity {

    EditText edtTitle,edtDesc,edtDate;
    Button btnSubmit,btnCancel;
    DatabaseHelper myDb;
    DatePickerDialog.OnDateSetListener date;
    Calendar myCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtodo);

        edtTitle =findViewById(R.id.edittitle);
        edtDesc = findViewById(R.id.editdesc);
        edtDate = findViewById(R.id.editdate);
        btnSubmit = findViewById(R.id.ntnAdd);
        btnCancel = findViewById(R.id.btncancel);

        myDb = new DatabaseHelper(this);
        myCalendar = Calendar.getInstance();
        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR,year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);

                updateLabel(); //memanggil fungsi updateLabel()
            }
        };
        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Addtodo.this,date,myCalendar.get(Calendar.YEAR)
                ,myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = edtTitle.getText().toString();
                String date = edtDate.getText().toString();
                String desc = edtDesc.getText().toString();
                if (title.equals("") || date.equals("") || desc.equals("")){
                    if(title.equals("")){
                        edtTitle.setError("Judul harus diisi");
                    }
                    if (desc.equals("")){
                        edtDesc.setError("Deskripsi harus diisi");
                    }
                    if (date.equals("")) {
                        edtDate.setError("Tanggal harus diisi");
                    }
                }else{
                    boolean isInserted = myDb.insertData(title,desc,date);
                    if (isInserted){
                        Toast.makeText(Addtodo.this,"Data berhasil ditambahkan",Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(Addtodo.this,"Data gagal ditambahkan",Toast.LENGTH_SHORT).show();
                    }
                    startActivity(new Intent(Addtodo.this,MainActivity.class));
                    finish();
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Addtodo.this,MainActivity.class));
                finish();
            }
        });
    }



    private  void updateLabel(){
        String myFormat = "dd-MM-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(myFormat , Locale.US);
        edtDate.setText(simpleDateFormat.format(myCalendar.getTime()));
    }
}