package com.khumdev.latihanuki;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<ToDo> list = new ArrayList<ToDo>();
    ToDoAdapter toDoAdapter;
    DatabaseHelper myDb;
    RecyclerView rvTodo;
    FloatingActionButton fabAdd ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);
        list.addAll(myDb.getAllData());
        rvTodo = findViewById(R.id.rvTodo);
        rvTodo.setLayoutManager(new LinearLayoutManager(this));
        toDoAdapter = new ToDoAdapter(MainActivity.this,list);
        toDoAdapter.notifyDataSetChanged();
        rvTodo.setAdapter(toDoAdapter);
        fabAdd = findViewById(R.id.Fab);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Addtodo.class));
                finish();

            }
        });


    }
}