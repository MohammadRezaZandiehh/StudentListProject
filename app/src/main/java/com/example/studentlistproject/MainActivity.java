package com.example.studentlistproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.icu.text.StringSearch;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.example.studentlistproject.API.ApiService;
import com.example.studentlistproject.adapter.StudentAdapter;
import com.example.studentlistproject.model.Student;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    RecyclerView recyclerVeiwMainStudent;
    static Integer ADD_STUDENT_REQUEST_ID = 1001;
    StudentAdapter studentAdapter;
    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiService = new ApiService(this, TAG);

        recyclerVeiwMainStudent = findViewById(R.id.recyclerVeiwMainStudent);
        Toolbar toolbarMain = findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbarMain);

        ExtendedFloatingActionButton addNewStudentBtn = findViewById(R.id.E_FAB_main);
        addNewStudentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MainActivity.this, AddNewStudentFormActivity.class), ADD_STUDENT_REQUEST_ID);

            }
        });

        apiService.getStudents(new ApiService.StudentListCallBack() {
            @Override
            public void onSuccess(List<Student> studentList) {

                recyclerVeiwMainStudent.setLayoutManager(new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false));
                studentAdapter = new StudentAdapter(studentList, MainActivity.this);
                recyclerVeiwMainStudent.setAdapter(studentAdapter);
            }

            @Override
            public void onError(Exception error) {
                Toast.makeText(MainActivity.this, "خطای نا مشخص", Toast.LENGTH_SHORT).show();
            }
        });

        //.........
        recyclerVeiwMainStudent.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy < 0 && !addNewStudentBtn.isShown())
                    addNewStudentBtn.show();
                else if (dy > 0 && addNewStudentBtn.isShown())
                    addNewStudentBtn.hide();
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
        //.........

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == ADD_STUDENT_REQUEST_ID && resultCode == Activity.RESULT_OK) {
            if (data != null && studentAdapter != null && recyclerVeiwMainStudent != null) {
                Student student = data.getParcelableExtra("student");
                studentAdapter.addStudent(student);
                recyclerVeiwMainStudent.smoothScrollToPosition(0);
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        apiService.cancel();
    }

    //......22
//    public void delete(int position) {
//
//        try {
//            studentAdapter.studentList.remove(position);
//            notifyItemRemoved(position);
//        } catch (IndexOutOfBoundsException ex) {
//            ex.printStackTrace();
//        }
//    }
    //......22
}