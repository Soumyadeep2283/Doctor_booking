package com.example.doctorapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DrBooking extends AppCompatActivity {
    String url ="http://103.230.103.142/drbookingapp/bookingapp.asmx/BookingDr";
    TextView textView1,textView2;
    String message,Sucess,  timeSlotid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dr_booking);
        textView1=findViewById(R.id.tv1);
        textView2=findViewById(R.id.tv2);
        Intent i=getIntent();
        timeSlotid=i.getStringExtra("timeslotid");
        Log.e("tag8","tsi-----------"+ timeSlotid);
        getdata(url);
    }
    private void getdata(String a) {
        StringRequest sr = new StringRequest(Request.Method.POST, a, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    message = jsonObject .getString("Message");
                    Sucess=jsonObject .getString("Sucess");
                    //Toast.makeText(DrBooking.this, message, Toast.LENGTH_SHORT).show();
                    CustomDialogClass cdd=new CustomDialogClass(DrBooking.this);
                    cdd.setCancelable(false);
                    cdd.show();
                    if(Sucess.equals("1")) {// if arraylist size>0

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DrBooking.this, "server not found", Toast.LENGTH_SHORT).show();
            }
        }){

//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                HashMap<String, String> k = new HashMap<>();
//                k.put("doctorid", IdAndDate.DoctorId);
//                k.put("userid",IdAndDate.userid );
//                k.put("timeslotid",IdAndDate.timeslotid);
//                k.put("bookingdate", IdAndDate.Date);
//                return k;
//            }

        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            HashMap<String, String> k = new HashMap<>();
            k.put("doctorid", IdAndDate.DoctorId);
            Log.e("id", "A" + IdAndDate.DoctorId);
            k.put("userid",IdAndDate.userid );
            Log.e("id", "B" + IdAndDate.userid);
            k.put("timeslotid",timeSlotid);
            Log.e("id", "C" + timeSlotid);
            k.put("bookingdate",IdAndDate.Date);
            Log.e("id", "D" + IdAndDate.Date);
            return k;
        }};

    Volley.newRequestQueue(DrBooking.this).add(sr);
}
//+++++++++
public class CustomDialogClass extends Dialog implements android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button OK;
    public TextView textView;





    public CustomDialogClass(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog);
        OK = (Button) findViewById(R.id.btn_yes);
        textView=findViewById(R.id.txt_dia);
        textView.setText( message);
        OK.setOnClickListener(this);



    }


    @Override
    public void onClick(View view) {


        switch ( view.getId()) {
            case R.id.btn_yes:
                Intent i= new Intent(DrBooking.this,Dashboard.class);
                startActivity(i);
                break;

            default:
                break;
        }
    }
    }
}









