package com.example.doctorapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class GetDoctor extends AppCompatActivity {
    Button btn1,btn2;
    EditText et1;
    int myear,mmonth,mday;
    String newString;
    private static String date;

    String url="http://103.230.103.142/drbookingapp/bookingapp.asmx/GetDoctor";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_doctor);
        et1=findViewById(R.id.et1);
        btn1=findViewById(R.id.btn1);
        btn2=findViewById(R.id.btn2);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                newString= null;
            } else {
                newString= extras.getString("STRING_I_NEED");
            }
        } else {
            newString= (String) savedInstanceState.getSerializable("STRING_I_NEED");
        }
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c= Calendar.getInstance();
                myear=c.get(Calendar.YEAR);
                mmonth=c.get(Calendar.MONTH);
                mday=c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog DP=new DatePickerDialog(   GetDoctor.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        i1=i1+1;
                        et1.setText(i2+"/"+i1+"/"+i);
                        String date=(i2+"/"+i1+"/"+i);

                    }
                },myear,mmonth,mday);
                DP.show();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick ( View view ) {
                sendtoserver(url);
            }
        }); }

    private void sendtoserver( String a){
        StringRequest sr= new StringRequest(Request.Method.POST,a,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("DoctorSchedule");
                    String Sucess = null;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject innerobject = jsonArray.getJSONObject(i);
                        String scheduleday =innerobject.getString("scheduleday");
                        JSONObject doctorde=innerobject.getJSONObject("doctorde");
                        String doctorname =doctorde.getString("doctorname");
                        JSONObject schdet=innerobject.getJSONObject("doctorde");
                        String starttime = schdet.getString("starttime");
                        String endtime = schdet.getString("endtime");

                        Sucess = jsonObject.getString("Sucess");
                    }


                    if (Sucess.equals("1")) {

                        Intent i = new Intent(GetDoctor.this, GetDoctorDetails.class);
                        //i.putExtra("a","hello");
                        startActivity(i);
                    }
                }
                catch(Exception e){
                    Toast.makeText(GetDoctor.this, "error", Toast.LENGTH_SHORT).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse( VolleyError error) {
                Toast.makeText(GetDoctor.this, "server not found", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> k= new HashMap<>();
                k.put("departmentid",newString);
                k.put("bookingdate",date);
                Log.e("bookingdate", "dakh3" + date);
                return k;
            }
        };

        Volley.newRequestQueue(GetDoctor.this).add(sr);

    }
    public static String getDateValue() {
        return date;
    }

}
