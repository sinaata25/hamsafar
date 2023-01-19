package ataei.sina.hamsafar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jakode.verifycodeedittext.VerifyCodeEditText;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import ataei.sina.hamsafar.interfaces.Finish;
import ataei.sina.hamsafar.interfaces.VerificationResponse;
import ataei.sina.hamsafar.interfaces.VerificationSmsListener;
import ataei.sina.hamsafar.model.User;
import ataei.sina.hamsafar.statics.keys;
import ataei.sina.hamsafar.statics.urls;

public class SmsActivation extends AppCompatActivity {
    VerifyCodeEditText verifyCodeEditText;
    TextView textView_num,textView_timer;
    FloatingActionButton floatingActionButton;
    String correctCode="";
    int time_left=180;
    String number="";
    int there_is_user=0;
   public static Finish finish;
    private final int SMS_REQUEST_CODE = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sms_activation);
        setups();
        sets();
        handle();
        handleInters();
    }

    private void handleInters() {
        VerificationSets.verificationResponse=new VerificationResponse() {
            @Override
            public void onGetResponse(String code) {
                        correctCode=code;
            }
        };

    }

    private void sets() {
        Intent intent=getIntent();
        number=intent.getStringExtra("number");
        textView_num.setText("(+98)"+number);
    }

    private void handle() {
        KeyboardVisibilityEvent.setEventListener(
                SmsActivation.this,
                new KeyboardVisibilityEventListener() {
                    @Override
                    public void onVisibilityChanged(boolean isOpen) {
                        if(verifyCodeEditText.getContentDescription().equals("no")){
                            ((MotionLayout)findViewById(R.id.motionlayout_sec)).transitionToEnd();
                            verifyCodeEditText.setContentDescription("yes");
                        }else {
                            ((MotionLayout)findViewById(R.id.motionlayout_sec)).transitionToStart();
                            verifyCodeEditText.setContentDescription("no");
                        }

                    }
                });
        /////sms permition
        if (ContextCompat.checkSelfPermission(SmsActivation.this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            requestReceiveSMSpermission();
        }
        VerificationSets.verificationSmsListener=new VerificationSmsListener() {
            @Override
            public void onRecive(String sms) {
                verifyCodeEditText.setText(sms);
            }
        };

    /////////
        handleTime();

     /////////
     floatingActionButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             if(correctCode.equals(verifyCodeEditText.getText())){
                    request();
                finish.onFinish();
             }
         }
     });

    }


    void request(){
        User user=new User();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urls.url_get_user_by_phone, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONArray jsonArray=new JSONArray(response);
                    JSONObject jsonObject=jsonArray.getJSONObject(0);
                    user.setUsername((String) jsonObject.get("username"));
                    SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("shared", Context.MODE_PRIVATE);
                    if(user.getUsername().equals("")){
                        Intent intent=new Intent(SmsActivation.this,LogupActivity.class);
                        intent.putExtra("number","+98"+number);
                        startActivity(intent);
                        finish();
                    }else {
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putInt("login",1);
                        String num="+98"+number;
                        editor.putString("number",num);
                        editor.apply();
                        finish();
                        Intent intent=new Intent(SmsActivation.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }






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
                param.put("key", keys.key_get_user_by_phone);
                param.put("phone", "98"+number);
                return param;
            }

        };
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy());
        requestQueue.add(stringRequest);
    }

    private void handleTime() {
        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(time_left>=1){
                        time_left--;
                        int sec=time_left%60;
                        int min=time_left/60;
                        String sec_str="";
                        String min_str="";
                        if(sec<10){
                            sec_str="0"+sec;
                        }else {
                            sec_str=String.valueOf(sec);
                        }
                        if(min<10){
                            min_str="0"+min;
                        }else {
                            min_str=String.valueOf(min);
                        }
                        textView_timer.setText(min_str+":"+sec_str);
                    }

                    }
                });
            }
        },1000,1000);

    }

    private void setups() {
     verifyCodeEditText=findViewById(R.id.verifyCodeEditText2);
     textView_num=findViewById(R.id.textView_num);
     textView_timer=findViewById(R.id.textView4);
     floatingActionButton=findViewById(R.id.floatAction);
    }






    private void requestReceiveSMSpermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(SmsActivation.this, Manifest.permission.RECEIVE_SMS)) {

/*            new AlertDialog.Builder(this)
                    .setTitle("درخواست مجوز")
                    .setMessage("برای پر کردن خودکار پیامک تایید ")
                    .setPositiveButton("موافقم", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            reqPermission();

                        }
                    })
                    .setNegativeButton("لغو", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            dialogInterface.dismiss();

                        }
                    })
                    .create()
                    .show();

        } else {*/

            reqPermission();

        }

    }




    private void reqPermission() {

        ActivityCompat.requestPermissions(SmsActivation.this, new String[] {Manifest.permission.RECEIVE_SMS}, SMS_REQUEST_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == SMS_REQUEST_CODE) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(this, "مجوز تایید شد", Toast.LENGTH_SHORT).show();

            } else {

                Toast.makeText(this, "مجوز رد شد", Toast.LENGTH_SHORT).show();

            }

        }

    }





}