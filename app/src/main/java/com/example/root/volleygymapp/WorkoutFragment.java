package com.example.root.volleygymapp;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class WorkoutFragment extends Fragment {
    private EditText location;
    private EditText reps;
    private EditText exerciseType;
    private EditText sets;
    private int day;
    private int month;
    private int year;
    private EditText txtDate;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_workout, null);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        location = view.findViewById(R.id.txt_location);
        exerciseType = view.findViewById(R.id.txt_exercisetype);
        reps = view.findViewById(R.id.int_reps);
        sets = view.findViewById(R.id.int_sets);
        txtDate = view.findViewById(R.id.txt_date);


        SharedPreferences prefs = this.getActivity().getSharedPreferences("UserPref", MODE_PRIVATE);
        final String restoredText = prefs.getString("log_id", null);

        view.findViewById(R.id.btn_date).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {

                        txtDate.setText(year + "-" + (month + 1) + "-" +day);

                    }
                },year, month,day);
                datePickerDialog.show();
            }
        });

        view.findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(), "This is the Workout Fragment", Toast.LENGTH_SHORT).show();
                String loc = location.getText().toString().trim();
                String exercise = exerciseType.getText().toString().trim();
                String r = reps.getText().toString().trim();
                String s = sets.getText().toString().trim();
                String date = txtDate.getText().toString().trim();

                workout(restoredText,loc, exercise,r,s,date);

            }
        });

    }

    private void workout(String restoredText, String loc, String exercise, String r, String s, String date) {

        RequestQueue queue = Volley.newRequestQueue(this.getActivity());

        String URL = "http://gympapp.000webhostapp.com/confirm";

        Map<String, String> jsonParams = new HashMap<String, String>();
        jsonParams.put("location",loc);
        jsonParams.put("exercise_type",exercise);
        jsonParams.put("reps",r);
        jsonParams.put("sets",s);
        jsonParams.put("date",date);
        jsonParams.put("user_id",restoredText);


        JsonObjectRequest json = new JsonObjectRequest(Request.Method.POST, URL, new JSONObject(jsonParams),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Log.e("my response", "" + response);

                        Toast.makeText(getActivity(), "Workout Added", Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", "Error: " + error.getMessage());
                Toast.makeText(getActivity().getApplicationContext(),
                        "Check connectivity: "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        );

        queue.add(json);

    }



}


