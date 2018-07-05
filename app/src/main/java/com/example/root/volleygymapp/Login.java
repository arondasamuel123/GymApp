package com.example.root.volleygymapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    private static final String TAG = "event";
    EditText edtEmail;
    EditText edtPassword;
    Button btnLogin;
    private   RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        queue = Volley.newRequestQueue(this);

        edtEmail = findViewById(R.id.text_email);
        edtPassword = findViewById(R.id.text_password);
        btnLogin = findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 String email = edtEmail.getText().toString().trim();
                 String password = edtPassword.getText().toString().trim();
                volleylogin(email,password);
            }
        });
    }

//    private void volleylogin() {
//        final String email = edtEmail.getText().toString().trim();
//        String password = edtPassword.getText().toString().trim();
//
//        RequestQueue queue = Volley.newRequestQueue(this);
//        String URL = "http://gympapp.000webhostapp.com/session";
//        Map<String, String> jsonParams = new HashMap<String, String>();
//        jsonParams.put("email",email);
//        jsonParams.put("password",password);
//
//        Log.e("json params:", "" +jsonParams);
//
//        StringRequest strReq = new StringRequest(Request.Method.POST,
//                URL, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
////                Toast.makeText(RegisterActivity.this, "my res::"+response, Toast.LENGTH_SHORT).show();
//                JSONObject json = null;
//                try {
//                    json = new JSONObject(response);
//
//                    String mes = json.getString("message");
//                    Log.e("my response", ""+response);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        },new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e(TAG, "Error: " + error.getMessage());
//                Toast.makeText(getApplicationContext(),
//                        "Check connectivity: "+error.getMessage(), Toast.LENGTH_SHORT).show();
//
//            }
//        });
//
//        queue.add(strReq);
//
//
//    }

    private void volleylogin(final String my_mail, final String my_pass) {

        String endpoint = "http://gympapp.000webhostapp.com/session";
        Log.e("endpoint", endpoint);
        StringRequest strReq = new StringRequest(Request.Method.POST,
                endpoint, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            //Log.e("my response",""+response);




                try {
                    JSONObject json = new JSONObject(response);

                    String mes = json.getString("message");
                    if(mes.equalsIgnoreCase("Invalid password")){
                        Toast.makeText(Login.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();

                    }
                    JSONObject jsonItem = new JSONObject(mes);
                    String id = jsonItem.getString("id");
                    int my_id =Integer.parseInt(id);

                    if(my_id>0){
                        Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        Intent homeIntent =new Intent(getApplicationContext(),HomeActivity.class);
                        homeIntent.putExtra("user_id",id);
                        startActivity(homeIntent);
                    }else if(!(my_id>0)){
//                        Toast.makeText(Login.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        "Check connectivity: "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {

            /**
             * Passing user parameters to our server
             * @return
             */
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("email", my_mail);
                params.put("password", my_pass);
                Log.e(TAG, "Params:" + params.toString());
                return params;
            }

        };

        int socketTimeout = 0;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        strReq.setRetryPolicy(policy);

        // Adding request to request queue
        queue.add(strReq);
    }
    public void session(View view) {
        Intent signup = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(signup);
    }
}
