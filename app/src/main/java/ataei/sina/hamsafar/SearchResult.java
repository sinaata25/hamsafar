package ataei.sina.hamsafar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

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

import ataei.sina.hamsafar.adapters.AdapterRecycleSearchResult;
import ataei.sina.hamsafar.adapters.AdapterRecycleSpecial;
import ataei.sina.hamsafar.model.Advertisment;
import ataei.sina.hamsafar.model.City_Province;
import ataei.sina.hamsafar.statics.keys;
import ataei.sina.hamsafar.statics.urls;

public class SearchResult extends AppCompatActivity {


    RecyclerView result_ads;
    ImageView result_back;
    List<City_Province>list_city;
    List<City_Province>list_privince;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result);
        setUpViews();
        sets();
        getCities();
        handle();
    }

    private void handle() {
        result_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getsResultAds() {

        StringRequest request = new StringRequest(Request.Method.POST, urls.url_search_ad, new Response.Listener<String>() {
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

                    result_ads.setAdapter(new AdapterRecycleSearchResult(ads , getApplicationContext(),list_city,list_privince));
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
                param.put("key", keys.key_saerch_ad);
                param.put("origin", getIntent().getStringExtra("origin"));
                param.put("destination",getIntent().getStringExtra("destination") );
                param.put("date", getIntent().getStringExtra("date"));
                return param;
            }

        };
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        request.setRetryPolicy(new DefaultRetryPolicy());
        requestQueue.add(request);

    }

    private void sets() {
        result_ads.setLayoutManager(new LinearLayoutManager(getApplicationContext() ,RecyclerView.VERTICAL , false));
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
                   getsResultAds();
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
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy());
        requestQueue.add(stringRequest);
    }



    private void setUpViews() {
        result_ads = findViewById(R.id.result_ads);
        result_back = findViewById(R.id.result_back);

    }
}