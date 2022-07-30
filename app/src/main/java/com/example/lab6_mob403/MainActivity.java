package com.example.lab6_mob403;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    Button btnRead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textview);
        btnRead = findViewById(R.id.btn_read);
        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getJSON_Array_of_objects();
            }
        });

    }

    public void getStringVolley() {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        String url = "https://www.google.com/";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //lay ve 1000 ky thu
                        textView.setText("Kq: " + response.substring(0, 1000));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textView.setText(error.getMessage());
            }
        });

        queue.add(stringRequest);
    }

    String strJSON = "";

    public void getJSON_Array_of_objects() {
        strJSON = "";

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        String url = "https://tucaomypham.000webhostapp.com/android_networking_mob403/person_array.json";

        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject person = response.getJSONObject(i);//lay ve tung doi tuong
                        String name = person.getString("name");
                        String email = person.getString("email");
                        JSONObject phone = person.getJSONObject("phone");
                        String mobile = phone.getString("mobile");
                        String home = phone.getString("home");

                        strJSON += "Name: " + name + "\n\n";
                        strJSON += "email: " + email + "\n\n";
                        strJSON += "mobile: " + mobile + "\n\n";
                        strJSON += "home: " + home + "\n\n";

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                textView.setText(strJSON);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textView.setText(error.getMessage());
            }
        });

        queue.add(request);

    }
}