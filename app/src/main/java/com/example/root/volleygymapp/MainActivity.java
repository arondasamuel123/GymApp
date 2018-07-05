package com.example.root.volleygymapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.root.volleygymapp.api.ApiUrl;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "event";
    EditText editFirstName;
    EditText editLastName;
    EditText editEmail;
    EditText editPassword;
    Button btnSignup;
    Button btnSignin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editFirstName = findViewById(R.id.txt_firstname);
        editLastName = findViewById(R.id.txt_lastname);
        editEmail = findViewById(R.id.txt_email);
        editPassword = findViewById(R.id.txt_password);
        btnSignup = findViewById(R.id.btn_register);
        btnSignin = findViewById(R.id.btn_submit);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volleycall();
            }
        });
    }

    private void volleycall() {
        String firstname = editFirstName.getText().toString().trim();
        String lastname = editLastName.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();

        RequestQueue queue = Volley.newRequestQueue(this);

        String URL = "http://gympapp.000webhostapp.com/store";
        Map<String, String> jsonParams = new HashMap<String, String>();
        jsonParams.put("firstname",firstname);
        jsonParams.put("lastname",lastname);
        jsonParams.put("email",email);
        jsonParams.put("password",password);

        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, URL, new JSONObject(jsonParams),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String message = null;
                        try {
                            message = (String) response.get("message");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (message.equals("Registration Successful")) {
                            Toast.makeText(MainActivity.this, "Successful Registration", Toast.LENGTH_SHORT).show();
                        }else{
                            Log.d(TAG,"JSON"+response);
                        }
                    }


                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG,"Error" +error
                        + "\nmessage" +error.getMessage());
                    }
                });

        queue.add(postRequest);
    }

    public void selected(View view) {
        Intent login = new Intent(getApplicationContext(),Login.class);
        startActivity(login);
    }
}
