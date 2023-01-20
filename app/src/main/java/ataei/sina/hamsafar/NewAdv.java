package ataei.sina.hamsafar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ataei.sina.hamsafar.adapters.AdapterRecycleCityPicker;
import ataei.sina.hamsafar.fragments.HomeFragment;
import ataei.sina.hamsafar.model.City_Province;
import ataei.sina.hamsafar.statics.keys;
import ataei.sina.hamsafar.statics.urls;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.api.PersianPickerDate;
import ir.hamsaa.persiandatepicker.api.PersianPickerListener;

public class NewAdv extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
        ImageView back;
        EditText desc,car,price;
        TextView time,date,origin,destination,post;
        TimePickerDialog timePickerDialog;
        public static DataCh dataCh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_adv);
        setUpViews();
        handle();
    }

    private void handle() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        date.setOnClickListener(V->datePick());
        origin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),CityPicker.class);
                intent.putExtra("mode",2);
                startActivity(intent);
            }
        });
        destination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),CityPicker.class);
                intent.putExtra("mode",3);
                startActivity(intent);
            }
        });

        dataCh=new DataCh() {
            @Override
            public void onChoosedOrigin(String city) {
                origin.setText(city);
            }

            @Override
            public void onChoosedDestination(String city) {
            destination.setText(city);
            }
        };

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog=TimePickerDialog.newInstance(NewAdv.this,true);
                timePickerDialog.setAccentColor(Color.parseColor("#3892EE"));
                timePickerDialog.show(getSupportFragmentManager(),"");
            }
        });



        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(!desc.getText().toString().equals("") && !desc.getText().toString().equals("") &&
                    !desc.getText().toString().equals("") &&!desc.getText().toString().equals("") &&
                    !desc.getText().toString().equals("") && !desc.getText().toString().equals("") &&
                    !desc.getText().toString().equals("") ){
                    reqData();
            }


            }
        });




    }


    private void setUpViews() {
        back=findViewById(R.id.post_back);
        desc=findViewById(R.id.post_text_description);
        car=findViewById(R.id.editTextTextPersonName3);
        price=findViewById(R.id.editTextNumberDecimal);
        post=findViewById(R.id.post_take_trip);
        time=findViewById(R.id.editTextTime);
        origin=findViewById(R.id.post_text_origin);
        destination=findViewById(R.id.post_text_destination);
        date=findViewById(R.id.editTextDate2);
    }


    private void reqData() {
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("shared", Context.MODE_PRIVATE);
        String name=sharedPref.getString("name","")+" "+sharedPref.getString("family","");
        String id_user=String.valueOf(sharedPref.getInt("id",0));
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urls.url_post_trip, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                HomeFragment.adComplete.complete();
            Toast.makeText(getApplicationContext(),"آگهی شما با موفقیت ثبت شد!",Toast.LENGTH_SHORT).show();
            finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param;
                param = new HashMap<>();
                param.put("key", keys.key_post_trip);
                param.put("name", name);
                param.put("origin", origin.getText().toString());
                param.put("destination", destination.getText().toString());
                param.put("time", time.getText().toString());
                param.put("date", date.getText().toString());
                param.put("price", price.getText().toString());
                param.put("description", desc.getText().toString());
                param.put("car", car.getText().toString());
                param.put("id_user", id_user);
                return param;
            }

        };
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy());
        requestQueue.add(stringRequest);
    }


    void datePick(){
        PersianDatePickerDialog picker=new PersianDatePickerDialog(NewAdv.this);
        picker.setPositiveButtonString("باشه")
                .setNegativeButton("بیخیال")
                .setTodayButton("امروز")
                .setTodayButtonVisible(true)
                .setPickerBackgroundDrawable(R.drawable.cardstyle10)
                .setMinYear(PersianDatePickerDialog.THIS_YEAR)
                .setMaxYear(PersianDatePickerDialog.THIS_YEAR)
                .setActionTextSize(20)
                .setMaxDay(PersianDatePickerDialog.THIS_DAY)
                .setInitDate(1401, 12, 1)
                .setActionTextColor(Color.GRAY)
                .setTitleType(PersianDatePickerDialog.WEEKDAY_DAY_MONTH_YEAR)
                .setShowInBottomSheet(true)
                .setListener(new PersianPickerListener() {
                    @Override
                    public void onDateSelected(@NotNull PersianPickerDate persianPickerDate) {
                        date.setText(persianPickerDate.getPersianYear() + "/" + persianPickerDate.getPersianMonth() + "/" + persianPickerDate.getPersianDay());
                    }

                    @Override
                    public void onDismissed() {

                    }
                });

        picker.show();
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
            time.setText(hourOfDay+":"+minute);
            timePickerDialog.dismiss();
    }

    public interface DataCh{
        void onChoosedOrigin(String city);
        void onChoosedDestination(String city);
    }



}