package ataei.sina.hamsafar.fragments;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

import ataei.sina.hamsafar.BestDestination;
import ataei.sina.hamsafar.CityPicker;
import ataei.sina.hamsafar.R;
import ataei.sina.hamsafar.SearchResult;
import ataei.sina.hamsafar.adapters.AdapterRecycleAds;
import ataei.sina.hamsafar.adapters.AdapterRecycleSpecial;
import ataei.sina.hamsafar.adapters.AdapterRecycleSuggested;
import ataei.sina.hamsafar.model.Advertisment;
import ataei.sina.hamsafar.model.City_Province;
import ataei.sina.hamsafar.statics.keys;
import ataei.sina.hamsafar.statics.urls;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.api.PersianPickerDate;
import ir.hamsaa.persiandatepicker.api.PersianPickerListener;
import ir.hamsaa.persiandatepicker.util.PersianCalendarUtils;

public class DiscoveryFragment extends Fragment {
    @Nullable
    View view;
    TextView time_pick,origin,destination,search_button;
    TextView best_city1 , best_city2 , best_city3 , best_city4 , best_city5 , best_city6;

    ImageView city_image1 , city_image2 , city_image3 , city_image4 , city_image5 , city_image6;
    RecyclerView special,suggested_recycler;
    List<City_Province>list_city;
    List<City_Province>list_privince;
    public static DataInter dataInter;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.discovery_page,container,false);
        setUpViews();
        sets();
        getCities();
        handle();
        return view;
    }

    private void handle() {
        time_pick.setOnClickListener(V->datePick());
        origin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), CityPicker.class);
                intent.putExtra("mode",0);
                startActivity(intent);
            }
        });
        destination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), CityPicker.class);
                intent.putExtra("mode",1);
                startActivity(intent);
            }
        });
        dataInter=new DataInter() {
            @Override
            public void onChoosedOrigin(String city) {
                origin.setText(city);
            }

            @Override
            public void onChoosedDestination(String city) {
            destination.setText(city);
            }
        };


        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if(!origin.getText().toString().equals("") && !destination.getText().toString().equals("") && !time_pick.getText().toString().equals("") ){
                        Intent intent=new Intent(getContext(), SearchResult.class);
                        intent.putExtra("origin",origin.getText().toString() );
                        intent.putExtra("destination",destination.getText().toString() );
                        intent.putExtra("date",time_pick.getText().toString());
                        startActivity(intent);
                    }

            }
        });

        city_image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), BestDestination.class);
                intent.putExtra("destination",best_city1.getText().toString() );
                startActivity(intent);
            }
        });

        city_image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), BestDestination.class);
                intent.putExtra("destination",best_city2.getText().toString() );
                startActivity(intent);
            }
        });

        city_image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), BestDestination.class);
                intent.putExtra("destination",best_city3.getText().toString() );
                startActivity(intent);
            }
        });

        city_image4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), BestDestination.class);
                intent.putExtra("destination",best_city4.getText().toString() );
                startActivity(intent);
            }
        });

        city_image5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), BestDestination.class);
                intent.putExtra("destination",best_city5.getText().toString() );
                startActivity(intent);
            }
        });

        city_image6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), BestDestination.class);
                intent.putExtra("destination",best_city6.getText().toString() );
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
                        advertisment.setCar(jObject.getString("car"));
                        ads.add(advertisment);
                    }

                    suggested_recycler.setAdapter(new AdapterRecycleSuggested(ads , getContext(),list_city,list_privince));
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
                        advertisment.setCar(jObject.getString("car"));
                        ads.add(advertisment);
                    }

                    special.setAdapter(new AdapterRecycleSpecial(ads , getContext(),list_city,list_privince));
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
        list_city=new ArrayList<>();
        list_privince=new ArrayList<>();
    }

    private void setUpViews() {
        special = view.findViewById(R.id.special);
        suggested_recycler=view.findViewById(R.id.suggestion);
        time_pick=view.findViewById(R.id.go_time);
        origin=view.findViewById(R.id.origin);
        destination=view.findViewById(R.id.destination);
        search_button=view.findViewById(R.id.search_button);
        city_image1 = view.findViewById(R.id.discovery_img1);
        city_image2 = view.findViewById(R.id.discovery_img2);
        city_image3 = view.findViewById(R.id.discovery_img3);
        city_image4 = view.findViewById(R.id.discovery_img4);
        city_image5 = view.findViewById(R.id.discovery_img5);
        city_image6 = view.findViewById(R.id.discovery_img6);
        best_city1 = view.findViewById(R.id.discovery_text_img1);
        best_city2 = view.findViewById(R.id.discovery_text_img2);
        best_city3 = view.findViewById(R.id.discovery_text_img3);
        best_city4 = view.findViewById(R.id.discovery_text_img4);
        best_city5 = view.findViewById(R.id.discovery_text_img5);
        best_city6 = view.findViewById(R.id.discovery_text_img6);
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
                    getSpecialAdData();
                    getSuggestedAdData();
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



    public interface DataInter{
        void onChoosedOrigin(String city);
        void onChoosedDestination(String city);
    }



}
