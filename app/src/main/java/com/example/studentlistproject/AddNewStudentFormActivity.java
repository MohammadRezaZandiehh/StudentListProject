package com.example.studentlistproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.example.studentlistproject.API.ApiService;
import com.example.studentlistproject.model.Student;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

public class AddNewStudentFormActivity extends AppCompatActivity {

    private static final String TAG = "AddNewStudentFormActivi";
    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_student_form);
        apiService = new ApiService(this, TAG);

        Toolbar toolbarAddStudent = findViewById(R.id.toolBarAddNewStudent);
        TextInputEditText firstNameET = findViewById(R.id.TextInputEditText_firstName_ddStudent);
        TextInputEditText laststNameET = findViewById(R.id.TextInputEditText_lastName_ddStudent);
        TextInputEditText courseET = findViewById(R.id.TextInputEditText_course_ddStudent);
        TextInputEditText scoreET = findViewById(R.id.TextInputEditText_score_ddStudent);
        Button saveButton = findViewById(R.id.fab_addNewStudent);

        setSupportActionBar(toolbarAddStudent);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_white_24dp);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (firstNameET.length() > 0 &&
                        laststNameET.length() > 0 &&
                        courseET.length() > 0 &&
                        scoreET.length() > 0) {

                    apiService.saveStudent(firstNameET.getText().toString(),
                            laststNameET.getText().toString(),
                            courseET.getText().toString(),
                            Integer.parseInt(scoreET.getText().toString()),
                            new ApiService.SaveStudentCallBack() {
                                @Override
                                public void onSuccess(Student student) {

                                    Intent intent = new Intent();
                                    intent.putExtra("student", student);
                                    setResult(Activity.RESULT_OK, intent);
                                    finish();

                                }

                                @Override
                                public void onError(Exception error) {
                                    Toast.makeText(AddNewStudentFormActivity.this, "خطای نا مشخص", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        apiService.cancel();
    }
}