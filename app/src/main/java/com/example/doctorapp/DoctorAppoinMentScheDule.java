package com.example.doctorapp;

import android.annotation.SuppressLint;
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
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DoctorAppoinMentScheDule extends AppCompatActivity {

    private static String timeS;


    String url = "http://103.230.103.142/drbookingapp/bookingapp.asmx/GetAppointmentSchedule";
    ListView lv;


    List<DoctorAppoinmentScheduleSetGet> alist = new ArrayList<DoctorAppoinmentScheduleSetGet>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_appoin_ment_sche_dule);
        lv = findViewById(R.id.lv3);

        getdata(url);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                Intent intent = new Intent(DoctorAppoinMentScheDule.this, DrBooking.class);
                timeS = alist.get(i).getTimeslotid();
                intent.putExtra("timeslotid",timeS);
                Log.e("timeSlotid", "timeSlotid=======" + timeS);

                startActivity(intent);

            }
        });
    }
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
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = getLayoutInflater();
            @SuppressLint("ViewHolder") View v = inflater.inflate(R.layout.appoinment, null);
            TextView tv1 = v.findViewById(R.id.tv1);
            TextView tv2 = v.findViewById(R.id.tv2);
            ImageView im = v.findViewById(R.id.im);
            tv1.setText(alist.get(position).getSlotfrom());
            tv2.setText(alist.get(position).getSlotto());

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
                    JSONArray jsonArray = jsonObject.getJSONArray("availableschedule");
                    String Sucess = jsonObject.getString("Sucess");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject innerobject = jsonArray.getJSONObject(i);
                        String timeslotid = innerobject.getString("timeslotid");
                        String status = innerobject.getString("status");
                        String slotfrom = innerobject.getString("slotfrom");
                        String slotto = innerobject.getString("slotto");
                        DoctorAppoinmentScheduleSetGet s = new DoctorAppoinmentScheduleSetGet();
                        s.setTimeslotid(timeslotid);
                        s.setStatus(status);
                        s.setSlotfrom(slotfrom);
                        s.setSlotto(slotto);

                        alist.add(s);
                    }
                    lv.setAdapter(new Custom());
                    if (Sucess.equals("1")) {


                    }
                } catch (Exception e) {
                    Toast.makeText(DoctorAppoinMentScheDule.this, "error", Toast.LENGTH_SHORT).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DoctorAppoinMentScheDule.this, "server not found", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> k = new HashMap<>();
                k.put("scheduleid", IdAndDate.ScheduleId);
                Log.e("scheduleid", "dakh9" + IdAndDate.ScheduleId);
                k.put("date", IdAndDate.Date);
                Log.e("bookingdate", "dakh10" + IdAndDate.Date);

                k.put("doctorid", IdAndDate.DoctorId);
                Log.e("doctorid", "dakh11" + IdAndDate.DoctorId);
                return k;
            }
        };

        Volley.newRequestQueue(DoctorAppoinMentScheDule.this).add(sr);
    }

    public static String gettimeSlotidValue ()
    {

        return timeS;
    }

}
