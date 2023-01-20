package ataei.sina.hamsafar.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ataei.sina.hamsafar.R;
import ataei.sina.hamsafar.adapters.AdapterRecycleAds;
import ataei.sina.hamsafar.adapters.AdapterRecycleCityPicker;
import ataei.sina.hamsafar.model.Advertisment;
import ataei.sina.hamsafar.model.City_Province;
import ataei.sina.hamsafar.statics.keys;
import ataei.sina.hamsafar.statics.urls;

public class HomeFragment extends Fragment {

    RecyclerView aghahiha;
    TextView textView_welcome;
    LinearLayout linearLayoutHome;
    ConstraintLayout constraintLayoutLoading;
    List<City_Province>list_city;
    List<City_Province>list_privince;
    @Nullable
    View view;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.home_page,container,false);
        setUpViews();
        sets();
        getCities();
        return view;
    }

    private void getData() {

        StringRequest req = new StringRequest(Request.Method.POST, urls.url_get_all_ads, new Response.Listener<String>() {
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


                aghahiha.setAdapter(new AdapterRecycleAds(ads , getContext(),list_city,list_privince));
                    linearLayoutHome.setVisibility(View.VISIBLE);
                    constraintLayoutLoading.setVisibility(View.GONE);
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
                param.put("key", keys.key_get_all_ads);
                return param;
            }

        };
        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        req.setRetryPolicy(new DefaultRetryPolicy());
        requestQueue.add(req);
    }

    private void getCities() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urls.url_get_all_cities, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray=new JSONArray(response);
                    list_city=new ArrayList<>();
                    list_privince=new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        City_Province city_province=new City_Province();
                        city_province.setId(jsonObject.getInt("id"));
                        city_province.setParent(jsonObject.getInt("parent"));
                        city_province.setTitle(jsonObject.getString("title"));
                        city_province.setImage(jsonObject.getString("image"));
                        if(i<31){
                            list_privince.add(city_province);
                        }else {
                            list_city.add(city_province);
                        }

                    }
                    getData();
                } catch (JSONException e) {
                    e.printStackTrace();
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
                param.put("key", keys.key_get_all_cities);
                return param;
            }

        };
        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy());
        requestQueue.add(stringRequest);
    }

    private void sets() {
        aghahiha.setLayoutManager(new LinearLayoutManager(getContext() ,RecyclerView.VERTICAL , false));
        SharedPreferences sharedPref = getContext().getSharedPreferences("shared", Context.MODE_PRIVATE);
        String name=sharedPref.getString("name","");
        textView_welcome.setText("خوش اومدی "+name);
    }

    private void setUpViews() {
        aghahiha =view.findViewById(R.id.aghahiha);
        textView_welcome=view.findViewById(R.id.textView5_home);
        linearLayoutHome=view.findViewById(R.id.linear_home_main);
        constraintLayoutLoading=view.findViewById(R.id.constraint_home_loading);
    }



}
