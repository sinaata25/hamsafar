package ataei.sina.hamsafar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import java.util.HashMap;
import java.util.Map;


import ataei.sina.hamsafar.adapters.MainPageAdapter;
import ataei.sina.hamsafar.fragments.DiscoveryFragment;
import ataei.sina.hamsafar.fragments.BookMarkFragment;
import ataei.sina.hamsafar.fragments.HomeFragment;
import ataei.sina.hamsafar.fragments.ProfileFragment;
import ataei.sina.hamsafar.model.User;
import ataei.sina.hamsafar.statics.keys;
import ataei.sina.hamsafar.statics.urls;

public class MainActivity extends AppCompatActivity {

    ViewPager2 main_viewpager;
    ImageView Home_bot , Discovery_bot , BookMark_bot , Profile_bot,add_bot;
    MainPageAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        setUpViews();
        handle();
        getCurrentUser();

    }

    private void getCurrentUser() {
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("shared", Context.MODE_PRIVATE);
        String number= sharedPref.getString("number","");
        User user=new User();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urls.url_get_user_by_phone, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray=new JSONArray(response);
                    JSONObject jsonObject=jsonArray.getJSONObject(0);
                    user.setId(jsonObject.getInt("id"));
                    user.setUsername(jsonObject.getString("username"));
                    user.setName(jsonObject.getString("name"));
                    user.setFamily(jsonObject.getString("family"));
                    user.setPhone(jsonObject.getString("phone"));
                    SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("shared", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putInt("id",user.getId());
                    editor.putString("username",user.getUsername());
                    editor.putString("name",user.getName());
                    editor.putString("family",user.getFamily());
                    editor.apply();
                } catch (JSONException e) {
                    System.out.println(e.getMessage());
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
                param.put("key", keys.key_get_user_by_phone);
                param.put("phone", number);
                return param;
            }

        };
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy());
        requestQueue.add(stringRequest);


    }

    private void handle() {
        adapter = new MainPageAdapter(getSupportFragmentManager(), getLifecycle() , getApplicationContext());
        adapter = new MainPageAdapter(getSupportFragmentManager(), getLifecycle() , getApplicationContext());
        adapter.addFragment(new HomeFragment());
        adapter.addFragment(new DiscoveryFragment());
        adapter.addFragment(new BookMarkFragment());
        adapter.addFragment(new ProfileFragment());

        main_viewpager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        main_viewpager.setCurrentItem(0);
        main_viewpager.setAdapter(adapter);
        main_viewpager.setUserInputEnabled(false);
    }

    private void unselected(int x) {
        if(x == 0){
            Home_bot.setImageResource(R.mipmap.home);
            return;
        }
        if(x == 1){
            Discovery_bot.setImageResource(R.mipmap.discover);
            return;
        }
        if(x == 2){
            BookMark_bot.setImageResource(R.mipmap.bookmark);
            return;
        }
        if(x == 3){
            Profile_bot.setImageResource(R.mipmap.profile);
        }
    }


    private void setUpViews() {
        main_viewpager = findViewById(R.id.main_viewpager);
        Home_bot = findViewById(R.id.Home_bot);
        Discovery_bot = findViewById(R.id.Discovery_bot);
        BookMark_bot = findViewById(R.id.BookMark_bot);
        Profile_bot = findViewById(R.id.Profile_bot);
        add_bot=findViewById(R.id.imageView6);
    }


    public void Home_clicked(View view) {

        unselected(main_viewpager.getCurrentItem());
        Home_bot.setImageResource(R.mipmap.home_selected);
        main_viewpager.setCurrentItem(0);


    }
    public void Discovery_clicked(View view) {

        unselected(main_viewpager.getCurrentItem());
        Discovery_bot.setImageResource(R.mipmap.dicover_selected);
        main_viewpager.setCurrentItem(1);

    }

    public void add_clicked(View view) {
        Intent intent=new Intent(getApplicationContext(),NewAdv.class);
        startActivity(intent);
    }
    public void BookMark_clicked(View view) {

        unselected(main_viewpager.getCurrentItem());
        BookMark_bot.setImageResource(R.mipmap.bookmark_selected);
        main_viewpager.setCurrentItem(2);

    }
    public void Profile_clicked(View view) {

        unselected(main_viewpager.getCurrentItem());
        Profile_bot.setImageResource(R.mipmap.profile_selected);
        main_viewpager.setCurrentItem(3);

    }
}