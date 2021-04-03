package com.example.doctorapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Registration extends AppCompatActivity {
    String url="http://103.230.103.142/drbookingapp/bookingapp.asmx/UserRegistration";
    EditText e1,e2,e3,e4,e5,e6,e7;
    Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        e1=findViewById(R.id.ed);
        e2=findViewById(R.id.ed1);
        e3=findViewById(R.id.ed2);
        e4=findViewById(R.id.ed3);
        e5=findViewById(R.id.ed4);
        e6=findViewById(R.id.ed5);
        e7=findViewById(R.id.ed6);
        b1=findViewById(R.id.bt1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fname=e1.getText().toString();
                String lname=e2.getText().toString();
                String username=e3.getText().toString();
                String pwd=e4.getText().toString();
                String address=e1.getText().toString();
                String phoneno=e2.getText().toString();
                String  email=e3.getText().toString();
                sendtoserverregistration(url,fname,lname,username,pwd,address,phoneno,email);
            }
        });
    }
    private void sendtoserverregistration( String a,final String fname, final String lname,final String username,final String pwd,final String address,final String phoneno,final String email){
        StringRequest sr= new StringRequest(Request.Method.POST,a,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String  Message=jsonObject.getString("Message");
                    String  Sucess=jsonObject.getString("Sucess");
                    //String status_msg=jsonObject.getString("status_msg");
                    Toast.makeText(Registration.this, Message, Toast.LENGTH_SHORT).show();

                    if(Sucess.equals("1")){

                        Intent i= new Intent(Registration.this, Dashboard.class);
                        //i.putExtra("a","hello");
                        startActivity(i);
                    }
                }
                catch(Exception e){
                    Toast.makeText(Registration.this, "error", Toast.LENGTH_SHORT).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Registration.this, "server not found", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> k= new HashMap<>();
                k.put("fname",fname);
                k.put("lname",lname);
                k.put("username",username);
                k.put("pwd",pwd);
                k.put("address",address);
                k.put("phoneno",phoneno);
                k.put("email", email);

                return k;
            }
        };

        Volley.newRequestQueue(Registration.this).add(sr);
    }


}
