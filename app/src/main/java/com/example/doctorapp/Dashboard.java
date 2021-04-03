package com.example.doctorapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Dashboard extends AppCompatActivity {
    ArrayList<DoctorDepartmentSetGet> alist = new ArrayList<DoctorDepartmentSetGet>();
    ListView l;
    String url = "http://103.230.103.142/drbookingapp/bookingapp.asmx/GetDepartment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        l = findViewById(R.id.lv);
        getdata(url);
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
            View v = inflater.inflate(R.layout.doctordepartment, null);
            TextView tv = v.findViewById(R.id.t);
            tv.setText(alist.get(position).getDepartment());
            return v;
        }
    }
    private void getdata(String a) {
        StringRequest sr = new StringRequest(Request.Method.GET, a, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(MainActivity.this, "server found", Toast.LENGTH_LONG).show();
                try { //for exception handling
                    JSONObject ob = new JSONObject(response);//converting responce to json object
                    JSONArray j = ob.getJSONArray("DepartmentDetails");//json array with the table name
                    for (int i = 0; i < j.length(); i++) { // for initializing array length upto n
                        JSONObject a = j.getJSONObject(i);//json object created with parent array
                        //saving the value
                        final String departmentid = a.getString("departmentid");
                        String departmentname = a.getString("departmentname");
                        DoctorDepartmentSetGet s = new DoctorDepartmentSetGet();
                        //calling java class
                        s.setDepartment(departmentname);//setting value in the java class


                        alist.add(s);//adding value in arraylist for fetching
                        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick (AdapterView<?> adapterView, View view, int i, long l ) {
                                Intent intent=new Intent(Dashboard.this,GetDoctor.class);
                                startActivity(intent);

                                intent.putExtra("STRING_I_NEED", departmentid);
                            }
                        });


                    }
                    if (alist.size() > 0) {// if arraylist size>0

                        l.setAdapter(new Custom());
                    } else {
                        Toast.makeText(Dashboard.this, "no data found", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {// if exception happened
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Dashboard.this, "server not found", Toast.LENGTH_LONG).show();
            }
        });
        Volley.newRequestQueue(Dashboard.this).add(sr);//add volley library
    }


}
