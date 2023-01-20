package ataei.sina.hamsafar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

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

import ataei.sina.hamsafar.adapters.AdapterRecycleCityPicker;
import ataei.sina.hamsafar.model.City_Province;
import ataei.sina.hamsafar.model.User;
import ataei.sina.hamsafar.statics.keys;
import ataei.sina.hamsafar.statics.urls;

public class CityPicker extends AppCompatActivity {
    RecyclerView recyclerView;
    SearchView searchView;
    List<City_Province> list;
    int mode;
    List<City_Province>list_privince;
    public static HanDa hanDa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_picker);
        setUpViews();
        getIntents();
        sets();
        getData();
        handle();
    }

    private void getIntents() {
        Intent intent=getIntent();
        mode=intent.getIntExtra("mode",0);
    }

    private void handle() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                List<City_Province>list_search=new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                    if(list.get(i).getTitle().contains(query)){
                        list_search.add(list.get(i));
                    }
                }
                recyclerView.setAdapter(new AdapterRecycleCityPicker(list_search,getApplicationContext(),list_privince,mode));

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<City_Province>list_search=new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                    if(list.get(i).getTitle().contains(newText)){
                        list_search.add(list.get(i));
                    }
                }
                recyclerView.setAdapter(new AdapterRecycleCityPicker(list_search,getApplicationContext(),list_privince,mode));
                return false;
            }
        });



        hanDa=new HanDa() {
            @Override
            public void onFinish() {
                finish();
            }
        };



    }

    private void getData() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urls.url_get_all_cities, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray=new JSONArray(response);
                    list=new ArrayList<>();
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
                            list.add(city_province);
                        }

                    }
                recyclerView.setAdapter(new AdapterRecycleCityPicker(list,getApplicationContext(),list_privince,mode));
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

    private void sets() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false));
    }

    private void setUpViews() {
        recyclerView=findViewById(R.id.rycler_city_picker);
        searchView=findViewById(R.id.city_name);
    }






        public interface HanDa{
        void onFinish();
        }



}