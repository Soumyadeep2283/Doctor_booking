package com.example.doctorapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetDoctorDetails extends AppCompatActivity {
    ImageView im1,im2,im3;
    String id,date;
    ListView lv;
    private static String dId,sId;
    List<GetDoctorDetailsSetGet> alist = new ArrayList<GetDoctorDetailsSetGet>();


    String url ="http://103.230.103.142/drbookingapp/bookingapp.asmx/GetDoctor";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_doctor_details);
        im1=findViewById(R.id.im1);
        im2=findViewById(R.id.im2);
        im3=findViewById(R.id.im3);
        lv = findViewById(R.id.lv1);

        Intent intent=getIntent();
        id=intent.getStringExtra("id");
        date=intent.getStringExtra("date");
        getdata(url);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick ( AdapterView<?> adapterView, View view, int i, long l ) {


                Intent intent=new Intent(GetDoctorDetails.this,DoctorAppoinMentScheDule.class);
                dId=alist.get(i).getDoctorid() ;
                sId= alist.get(i).getScheduleid() ;
                Log.e("tag8","tag8"+dId);
                Log.e("tag8","tag8"+sId);
                startActivity(intent);

            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView<?> adapterView, View view, int i, long l ) {


                Intent intent=new Intent(GetDoctorDetails.this,DoctorAppoinMentScheDule.class);
                dId=alist.get(i).getDoctorid() ;
                sId= alist.get(i).getScheduleid() ;
                Log.e("tag8","tag8"+dId);
                Log.e("tag8","tag8"+sId);
                startActivity(intent);

            }
        });}
    public class Custom extends BaseAdapter {
        @Override
        public int getCount() {
            return alist.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {

            LayoutInflater inflater = getLayoutInflater();
            View v = inflater.inflate(R.layout.doctordetails, null);
            TextView tv1 = v.findViewById(R.id.tv1);
            TextView tv2 = v.findViewById(R.id.tv2);
            TextView tv3 = v.findViewById(R.id.tv3);
            TextView tv4 = v.findViewById(R.id.tv4);
            tv1.setText(alist.get(position).getDoctorname());
            tv2.setText(alist.get(position).getScheduleday());
            tv3.setText(alist.get(position).getStarttime());
            tv4.setText(alist.get(position).getEndtime());
            return v;
        }
    }
    private void getdata(String a) {
        StringRequest sr = new StringRequest(Request.Method.POST, a, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.e("json object", "dakh1" + jsonObject);
                    JSONArray jsonArray = jsonObject.getJSONArray("DoctorSchedule");
                    String Sucess = jsonObject.getString("Sucess");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject innerobject = jsonArray.getJSONObject(i);
                        String scheduleday = innerobject.getString("scheduleday");
                        JSONObject doctorde = innerobject.getJSONObject("doctorde");
                        String doctorid = doctorde.getString("doctorid");
                        String doctorname = doctorde.getString("doctorname");
                        JSONObject schdet=innerobject.getJSONObject("schdet");
                        String scheduleid = schdet.getString("scheduleid");
                        Log.e("tag4","scheduleid"+scheduleid);
                        String  starttime = schdet.getString("starttime");
                        Log.e("tag4","startt"+starttime);
                        String  endtime = schdet.getString("endtime");
                        GetDoctorDetailsSetGet s=new GetDoctorDetailsSetGet() ;

                        s.setDoctorname(doctorname);
                        s.setScheduleday(scheduleday);
                        s.setStarttime(starttime);
                        s.setEndtime(endtime);
                        s.setDoctorid(doctorid);
                        s.setScheduleid(scheduleid);
                        alist.add(s);

                    }



                    if (alist.size() > 0) {// if arraylist size>0

                        lv.setAdapter(new Custom());
                    } else {
                        Toast.makeText(GetDoctorDetails.this, "no data found", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(GetDoctorDetails.this, "server not found", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> k = new HashMap<>();
                k.put("departmentid", id);
                Log.e("id", "dakh2" + id);
                k.put("bookingdate", date);
                Log.e("bookingdate", "dakh3" + date);
                return k;
            }
        };

        Volley.newRequestQueue(GetDoctorDetails.this).add(sr);
    }

    public static String getDoctorIdValue() {
        return dId;
    }


    public static String getScheduleIdValue() {
        return sId;
    }

}
