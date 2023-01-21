package ataei.sina.hamsafar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ataei.sina.hamsafar.adapters.AdapterRecycleSearchResult;
import ataei.sina.hamsafar.model.Advertisment;
import ataei.sina.hamsafar.model.City_Province;
import ataei.sina.hamsafar.statics.keys;
import ataei.sina.hamsafar.statics.urls;

public class BestDestination extends AppCompatActivity {

    TextView titr;
    ImageView back;
    List<City_Province>list_city;
    List<City_Province>list_privince;

    RecyclerView best;
    String destination="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.best_destination);
        setUpViews();
        sets();
        getIntents();
        getCities();
        handle();
    }

    private void getIntents() {
        destination=getIntent().getStringExtra("destination");
    }

    private void getsResultAds() {

        StringRequest request = new StringRequest(Request.Method.POST, urls.url_get_all_ads, new Response.Listener<String>() {
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
                        if(advertisment.getDestination().equals(destination)){
                            ads.add(advertisment);
                        }


                    }

                    best.setAdapter(new AdapterRecycleSearchResult(ads , getApplicationContext(),list_city,list_privince));
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
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        request.setRetryPolicy(new DefaultRetryPolicy());
        requestQueue.add(request);

    }

    private void sets() {
        best.setLayoutManager(new LinearLayoutManager(getApplicationContext() ,RecyclerView.VERTICAL , false));
        titr.setText( "به مقصد " + getIntent().getStringExtra("destination"));
    }

    private void handle() {

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setUpViews() {
        back = findViewById(R.id.best_back);
        titr = findViewById(R.id.best_text);
        best = findViewById(R.id.best_ads);
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


}