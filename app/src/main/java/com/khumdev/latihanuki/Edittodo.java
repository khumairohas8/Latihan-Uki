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

public class Edittodo extends AppCompatActivity {
    EditText edtTitle, edtDesc, edtDate;
    Button btnUpdate, btnDelete;
    DatePickerDialog.OnDateSetListener date;
    Calendar myCalendar;
    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edittodo);

        edtTitle = findViewById(R.id.edittitle);
        edtDesc = findViewById(R.id.editdesc);
        edtDate = findViewById(R.id.editdate);
        btnUpdate = findViewById(R.id.ntnAdd);
        btnDelete = findViewById(R.id.btncancel);

        edtTitle.setText(getIntent().getStringExtra("titletodo"));
        edtDesc.setText(getIntent().getStringExtra("desctodo"));
        edtDate.setText(getIntent().getStringExtra("datetodo"));

        myCalendar = Calendar.getInstance();
        myDb = new DatabaseHelper(this);
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
                new DatePickerDialog(Edittodo.this, date, myCalendar.get(Calendar.YEAR)
                        , myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = edtTitle.getText().toString();
                String desc = edtDesc.getText().toString();
                String date = edtDate.getText().toString();
                String id = getIntent().getStringExtra("idtodo");

                if (title.equals("") || desc.equals("") || date.equals("")) {
                    if (title.equals("")) {
                        edtTitle.setError("Judul harus diisi");
                    }
                    if (desc.equals("")) {
                        edtDesc.setError("Deskripsi harus diisi");
                    }
                    if (date.equals("")) {
                        edtDate.setError("Tanggal harus diisi");
                    }
                } else {
                    boolean isUpdate = myDb.updateData(title, desc, date, id);
                    if (isUpdate) {
                        Toast.makeText(Edittodo.this,"Data berhasil diubah",Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(Edittodo.this,"Data gagal diiubah",Toast.LENGTH_SHORT).show();
                    }

                    startActivity(new Intent(Edittodo.this,MainActivity.class));
                    finish();
                }
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = getIntent().getStringExtra("idtodo");
                Integer deleteRows = myDb.deleteData(id);

                if (deleteRows>0){
                    Toast.makeText(Edittodo.this,"Data berhasil dihapus",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(Edittodo.this,"Data gagal dihapus",Toast.LENGTH_SHORT).show();
                }
                startActivity(new Intent(Edittodo.this,MainActivity.class));
                finish();
            }
        });

    }

    private void updateLabel() {
        String myFormat = "dd-MM-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(myFormat, Locale.US);
        edtDate.setText(simpleDateFormat.format(myCalendar.getTime()));
    }
}

