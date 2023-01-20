package ataei.sina.hamsafar.fragments;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ataei.sina.hamsafar.CityPicker;
import ataei.sina.hamsafar.R;
import ataei.sina.hamsafar.adapters.AdapterRecycleAds;
import ataei.sina.hamsafar.adapters.AdapterRecycleSpecial;
import ataei.sina.hamsafar.adapters.AdapterRecycleSuggested;
import ataei.sina.hamsafar.model.Advertisment;
import ataei.sina.hamsafar.statics.keys;
import ataei.sina.hamsafar.statics.urls;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.api.PersianPickerDate;
import ir.hamsaa.persiandatepicker.api.PersianPickerListener;
import ir.hamsaa.persiandatepicker.util.PersianCalendarUtils;

public class DiscoveryFragment extends Fragment {
    @Nullable
    View view;
    TextView time_pick,origin,destination;
    RecyclerView special,suggested_recycler;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.discovery_page,container,false);
        setUpViews();
        sets();
        getSpecialAdData();
        getSuggestedAdData();
        handle();
        return view;
    }

    private void handle() {
        time_pick.setOnClickListener(V->datePick());
        origin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), CityPicker.class);
                startActivity(intent);
            }
        });
        destination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), CityPicker.class);
                startActivity(intent);
            }
        });
    }

    void datePick(){
        PersianDatePickerDialog picker=new PersianDatePickerDialog(getContext());
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
                        time_pick.setText(persianPickerDate.getPersianYear() + "/" + persianPickerDate.getPersianMonth() + "/" + persianPickerDate.getPersianDay());
                    }

                    @Override
                    public void onDismissed() {

                    }
                });

        picker.show();
    }

    private void getSuggestedAdData() {
        StringRequest request = new StringRequest(Request.Method.POST, urls.url_get_suggested_ads, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    List<Advertisment> ads = new ArrayList<>();
                    JSONArray jArray = new JSONArray(response);
                    for(int i = 0 ; i<jArray.length() ; i++){
                        JSONObject jObject = jArray.getJSONObject(i);
                        Advertisment advertisment = new Advertisment();
                        advertisment.setId(jObject.getInt("id"));
                        advertisment.setId_user(jObject.getInt("id_user"));
                        advertisment.setOrigin(jObject.getString("origin"));
                        advertisment.setDestination(jObject.getString("destination"));
                        advertisment.setDescription(jObject.getString("description"));
                        advertisment.setTime(jObject.getString("time"));
                        advertisment.setDate(jObject.getString("date"));
                        advertisment.setPrice(jObject.getInt("price"));
                        advertisment.setName(jObject.getString("name"));
                        ads.add(advertisment);
                    }

                    suggested_recycler.setAdapter(new AdapterRecycleSuggested(ads , getContext()));
                } catch (JSONException e) {
                    System.out.println(e);
                }
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
                param.put("key", keys.key_get_suggested_ads);
                return param;
            }

        };
        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        request.setRetryPolicy(new DefaultRetryPolicy());
        requestQueue.add(request);


    }

    private void getSpecialAdData() {
        StringRequest request = new StringRequest(Request.Method.POST, urls.url_get_special_ads, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    List<Advertisment> ads = new ArrayList<>();
                    JSONArray jArray = new JSONArray(response);
                    for(int i = 0 ; i<jArray.length() ; i++){
                        JSONObject jObject = jArray.getJSONObject(i);
                        Advertisment advertisment = new Advertisment();
                        advertisment.setId(jObject.getInt("id"));
                        advertisment.setId_user(jObject.getInt("id_user"));
                        advertisment.setOrigin(jObject.getString("origin"));
                        advertisment.setDestination(jObject.getString("destination"));
                        advertisment.setDescription(jObject.getString("description"));
                        advertisment.setTime(jObject.getString("time"));
                        advertisment.setDate(jObject.getString("date"));
                        advertisment.setPrice(jObject.getInt("price"));
                        advertisment.setName(jObject.getString("name"));
                        ads.add(advertisment);
                    }

                    special.setAdapter(new AdapterRecycleSpecial(ads , getContext()));
                } catch (JSONException e) {
                    System.out.println(e);
                }
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
                param.put("key", keys.key_get_special_ads);
                return param;
            }

        };
        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        request.setRetryPolicy(new DefaultRetryPolicy());
        requestQueue.add(request);
    }

    private void sets() {
        special.setLayoutManager(new LinearLayoutManager(getContext() ,RecyclerView.VERTICAL , false));
        suggested_recycler.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
    }

    private void setUpViews() {
        special = view.findViewById(R.id.special);
        suggested_recycler=view.findViewById(R.id.suggestion);
        time_pick=view.findViewById(R.id.go_time);
        origin=view.findViewById(R.id.origin);
        destination=view.findViewById(R.id.destination);
    }

}
