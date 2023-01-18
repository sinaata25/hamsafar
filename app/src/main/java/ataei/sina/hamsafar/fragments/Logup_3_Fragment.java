package ataei.sina.hamsafar.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.funrisestudio.stepprogress.StepProgressView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ataei.sina.hamsafar.LogupActivity;
import ataei.sina.hamsafar.MainActivity;
import ataei.sina.hamsafar.R;
import ataei.sina.hamsafar.SmsActivation;
import ataei.sina.hamsafar.adapters.AdapterRecyclerFavCategories;
import ataei.sina.hamsafar.model.Favourite_Categories;
import ataei.sina.hamsafar.model.User;
import ataei.sina.hamsafar.statics.keys;
import ataei.sina.hamsafar.statics.urls;

public class Logup_3_Fragment extends Fragment {
    View view;
    RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.logup_3_fragment,container,false);
        setUpViews();
        sets();
        getAllFavourites();
        handle();
        return view;
    }

    private void sets() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));

    }

    private void setUpViews() {
        recyclerView=view.findViewById(R.id.recycler_logup3);
    }

    private void handle() {
        LogupActivity.check_2=new LogupActivity.Check() {
            @Override
            public void onclicked(ViewPager viewPager, StepProgressView stepProgressView) {
            stepProgressView.nextStep(true);
            }
        };

    }




    void getAllFavourites(){
        List<List<Favourite_Categories>>list=new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urls.url_get_all_favourite_categories, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    List<Favourite_Categories>sec_list=new ArrayList<>();
                    JSONArray jsonArray=new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        Favourite_Categories favourite_categories=new Favourite_Categories();
                        favourite_categories.setId(jsonObject.getInt("id"));
                        favourite_categories.setName(jsonObject.getString("name"));
                        favourite_categories.setUrl(jsonObject.getString("url"));
                        sec_list.add(favourite_categories);
                        if((i+1)%3==0){
                            list.add(sec_list);
                            sec_list=new ArrayList<>();
                        }

                    }

                    recyclerView.setAdapter(new AdapterRecyclerFavCategories(list,getContext()));

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
                param.put("key", keys.key_get_all_favourite_categories);
                return param;
            }

        };
        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy());
        requestQueue.add(stringRequest);
    }




}
