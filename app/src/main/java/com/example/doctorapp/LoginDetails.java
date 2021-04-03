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
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.doctorapp.IdAndDate.userid;

public class LoginDetails extends AppCompatActivity {
    String url="http://103.230.103.142/drbookingapp/bookingapp.asmx/UserLogin";
    EditText e1,e2;
    Button b1,b2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_details);
        e1 = findViewById(R.id.ed);
        e2 = findViewById(R.id.ed1);
        b1 = findViewById(R.id.bt);
        b2 = findViewById(R.id.bt1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uname = e1.getText().toString();
                String pass = e2.getText().toString();
                sendtoserver(url, uname, pass);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginDetails.this, Dashboard.class);
                //i.putExtra("a","hello");
                startActivity(i);

            }
        });
    }
   private void sendtoserver(String a,final String user, final String pass){
       StringRequest sr= new StringRequest(Request.Method.POST, a, new Response.Listener<String>() {
           @Override
           public void onResponse(String response) {
               try {
                   JSONObject jsonObject = new JSONObject(response);
                   JSONObject innerobject = jsonObject.getJSONObject("UserDetails");
                   String userid = innerobject.getString("userid");
                   String fname = innerobject.getString("fname");
                   String lname = innerobject.getString("lname");
                   String username = innerobject.getString("username");
                   String pwd = innerobject.getString("pwd");
                   String address = innerobject.getString("address");
                   String phoneno = innerobject.getString("phoneno");
                   String email = innerobject.getString("email");
                   String Sucess = jsonObject.getString("Sucess");
                   //String status_msg=jsonObject.getString("status_msg");
                   Toast.makeText(LoginDetails.this, " successful", Toast.LENGTH_SHORT).show();

                   if (Sucess.equals("1")) {

                       Intent i = new Intent(LoginDetails.this, Registration.class);
                       //i.putExtra("a","hello");
                       startActivity(i);
                   }
               } catch (Exception e) {
                   Toast.makeText(LoginDetails.this, "error", Toast.LENGTH_SHORT).show();
               }
           }
       }, new ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError error) {

           }
       }){
           @Override
           protected Map<String, String> getParams() throws AuthFailureError {
               HashMap<String,String> k= new HashMap<>();
               k.put("username",user);
               k.put("pwd",pass);
               return k;
           }
       };

       Volley.newRequestQueue(LoginDetails.this).add(sr);
    }
    public static String getUseridValue() {
        return userid ;
    }
}

