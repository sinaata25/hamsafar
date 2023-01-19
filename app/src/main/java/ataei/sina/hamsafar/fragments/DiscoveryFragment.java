package ataei.sina.hamsafar.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ataei.sina.hamsafar.R;
import ataei.sina.hamsafar.adapters.AdapterRecycleAds;
import ataei.sina.hamsafar.adapters.AdapterRecycleSpecial;
import ataei.sina.hamsafar.model.Advertisment;
import ataei.sina.hamsafar.statics.keys;
import ataei.sina.hamsafar.statics.urls;

public class DiscoveryFragment extends Fragment {
    @Nullable
    View view;
    RecyclerView special;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.discovery_page,container,false);
        setUpViews();
        sets();
        getData();
        return view;
    }

    private void getData() {
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
    }

    private void setUpViews() {
        special = view.findViewById(R.id.special);
    }

}
