package com.example.root.volleygymapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.content.Context.MODE_PRIVATE;

public class PastWorkoutFragment extends Fragment {
     TextView mTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pastworkout,null);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        mTextView =view.findViewById(R.id.fetch);

        SharedPreferences prefs = this.getActivity().getSharedPreferences("UserPref", MODE_PRIVATE);
        String restoredText = prefs.getString("log_id", null);

        pastWorkouts(restoredText);
    }

    private void pastWorkouts(String restoredText) {

        String URL = "http://gympapp.000webhostapp.com/workout/"+restoredText;
        RequestQueue queue = Volley.newRequestQueue(this.getActivity());

        StringRequest workRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Log.e("my response", "" +response);
                try {
                    JSONObject obj = new JSONObject(response);
                    JSONArray mes = obj.getJSONArray("sessions");

                    //Log.e("My response", ""+mes);
                    for (int i = 0; i< mes.length(); i++){
                        JSONObject workObject = mes.getJSONObject(i);
                        String location = workObject.getString("location");
                        String exercisetype = workObject.getString("exercise_type");
                        Integer reps = workObject.getInt("reps");
                        Integer sets = workObject.getInt("sets");
                        String date = workObject.getString("date");

                        mTextView.append("Location:"+location+ "\nExercise Type:"+ exercisetype+ "\nReps : " + reps+ "\nSets : "+sets+"Date: "+date);
                        mTextView.append("\n\n");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


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

        queue.add(workRequest);



    }
}
