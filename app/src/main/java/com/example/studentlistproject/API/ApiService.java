package com.example.studentlistproject.API;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.studentlistproject.MainActivity;
import com.example.studentlistproject.adapter.StudentAdapter;
import com.example.studentlistproject.model.Student;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ApiService {

    private static final String TAG = "ApiService";
    private static RequestQueue requestQueue;
    static String BASE_URL = "http://expertdevelopers.ir/api/v1/";
    private String requestTAG;
    private Gson gson;

    public ApiService(Context context, String requestTAG) {
        this.requestTAG = requestTAG;
        this.gson = new Gson();
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
    }


    //    SAVE
    public void saveStudent(String firtName, String lastName, String course, int score, SaveStudentCallBack callBack) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("first_name", firtName);
            jsonObject.put("last_name", lastName);
            jsonObject.put("course", course);
            jsonObject.put("score", score);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                BASE_URL + "experts/student",
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "onResponse" + response);

                        Student student = gson.fromJson(response.toString(), Student.class);
                        callBack.onSuccess(student);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onResponse" + error);
                callBack.onError(error);
            }
        });
        request.setTag(requestTAG);
        requestQueue.add(request);
    }


    //GET
    public void getStudents(StudentListCallBack callBack) {

        StringRequest request = new StringRequest(Request.Method.GET,
                BASE_URL + "experts/student",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "onResponse: " + response);
//........
                        Type type = TypeToken.getParameterized(ArrayList.class, Student.class).getType();
                        List<Student> studentsList = gson.fromJson(response, type);
                        callBack.onSuccess(studentsList);
//........

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBack.onError(error);
            }
        });
        request.setTag(requestTAG);
        requestQueue.add(request);
    }


    public void cancel() {
        requestQueue.cancelAll(requestTAG);
    }


    //Interfaces :
    public interface SaveStudentCallBack {
        void onSuccess(Student student);

        void onError(Exception error);
    }

    public interface StudentListCallBack {
        void onSuccess(List<Student> studentList);

        void onError(Exception error);
    }
}