package com.example.root.volleygymapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.ArrayList;
import java.util.List;

public class GymInstructorFragment extends Fragment {
    private static final String URL = "http://gympapp.000webhostapp.com/instructors";
    List<Instructor> instructorList;
    RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_instructor,null);
        return rootview;


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        adapter = new ItemAdapter(getActivity(), instructorList);
        recyclerView.setAdapter(adapter);

        instructorList = new ArrayList<>();


        instructors();

    }

    private void instructors() {

        RequestQueue queue = Volley.newRequestQueue(this.getActivity());

        StringRequest instRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Log.e("my response", "" + response);
                try{
                    JSONObject obj= new JSONObject(response);
                    JSONArray ins = obj.getJSONArray("instructors");
                    //Log.e("My Array", ""+ins);

                    for(int i=0; i<ins.length();i++){
                        JSONObject instObject = ins.getJSONObject(i);

                        instructorList.add(new Instructor(

                                instObject.getString("name"),
                                instObject.getString("email"),
                                instObject.getString("gender"),
                                instObject.getString("phonenumber"),
                                instObject.getString("image")
                        ));



                    }
                    Log.e("array:",""+instructorList);
                    adapter.notifyDataSetChanged();

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

            queue.add(instRequest);
    }
}
