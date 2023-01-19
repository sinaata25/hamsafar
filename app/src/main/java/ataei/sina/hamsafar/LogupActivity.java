package ataei.sina.hamsafar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.funrisestudio.stepprogress.StepProgressView;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ataei.sina.hamsafar.adapters.AdapterViewPagerLogup;
import ataei.sina.hamsafar.fragments.Logup_1_Fragment;
import ataei.sina.hamsafar.fragments.Logup_2_Fragment;
import ataei.sina.hamsafar.fragments.Logup_3_Fragment;
import ataei.sina.hamsafar.statics.keys;
import ataei.sina.hamsafar.statics.urls;

public class LogupActivity extends AppCompatActivity {
ViewPager viewPager;
AdapterViewPagerLogup adapterViewPagerLogup;
StepProgressView stepProgressView;
String name,username,family;
public static Check check;
    Uri img;
    public static Check_1 check_1;
    public static Check check_2;
    TextView go;
    String number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logup_activity);
        setUpViews();
        setUps();
        handle();
    }

    private void handle() {
        KeyboardVisibilityEvent.setEventListener(
                LogupActivity.this,
                new KeyboardVisibilityEventListener() {
                    @Override
                    public void onVisibilityChanged(boolean isOpen) {
                        if(isOpen){
                            go.setVisibility(View.GONE);
                        }else {
                            go.setVisibility(View.VISIBLE);
                        }
                    }
                });
        //////////
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewPager.getCurrentItem()==0){
                    List<String>list=check.onclicked();
                    if(list.size()==3){
                    if( !(list.get(0).equals("")) &&  !(list.get(1).equals("")) && !(list.get(2).equals(""))  ){
                        username=list.get(0);
                        name=list.get(1);
                        family=list.get(2);
                        viewPager.setCurrentItem(1);
                        stepProgressView.nextStep(true);
                    }}

                }else if(viewPager.getCurrentItem()==1){
                    viewPager.setCurrentItem(2);
                    stepProgressView.nextStep(true);
                    img= check_1.onclicked();
                }else {
                    update();
                    stepProgressView.nextStep(true);
                }

            }
        });
    }


    void update(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urls.url_logup, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("shared", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt("login",1);
                editor.putString("number",number);
                editor.apply();
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
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
                param.put("key", keys.key_logup);
                param.put("number",number);
                param.put("username",username);
                param.put("name",name);
                param.put("family",family);
                param.put("url", ""+img);
                return param;
            }

        };
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy());
        requestQueue.add(stringRequest);

    }

    private void setUps() {
        Intent intent=getIntent();
        number=intent.getStringExtra("number");
        adapterViewPagerLogup=new AdapterViewPagerLogup(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,getApplicationContext());
        adapterViewPagerLogup.addfragment(new Logup_1_Fragment());
        adapterViewPagerLogup.addfragment(new Logup_2_Fragment());
        adapterViewPagerLogup.addfragment(new Logup_3_Fragment());
        viewPager.setAdapter(adapterViewPagerLogup);
        stepProgressView.setStepsCount(3);
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                viewPager.setCurrentItem(viewPager.getCurrentItem());
                return true;
            }
        });

    }

    private void setUpViews() {
        viewPager=findViewById(R.id.viewPager_logup);
        stepProgressView=findViewById(R.id.vStepProgress);
        go=findViewById(R.id.textView6);

    }
    public interface Check{
        List<String> onclicked();
    }
    public interface Check_1{
       Uri onclicked();
    }


}