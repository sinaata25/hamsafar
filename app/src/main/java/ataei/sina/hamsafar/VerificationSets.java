package ataei.sina.hamsafar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.transition.Transition;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.telephony.PhoneNumberUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
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

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import ataei.sina.hamsafar.statics.keys;
import ataei.sina.hamsafar.statics.urls;

public class VerificationSets extends AppCompatActivity {
    FloatingActionButton floatingActionButton;
    EditText editText;
    MotionLayout motionLayout;
    int canSend=1;
    LinearLayout linearLayout_phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verification_sets);
        setUpViews();
        handles();
        handleinters();
    }

    private void handles() {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText.getText().length()!=10){
                    Toast.makeText(getApplicationContext(),"فرمت وارد شده صحیح نمی باشد",Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent=new Intent(VerificationSets.this,SmsActivation.class);
                    requestToServerForSms(editText.getText().toString());
                    intent.putExtra("number",editText.getText().toString());
                    startActivity(intent);
                }



            }
        });







    }



    void  requestToServerForSms(String number){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, urls.url_login_verif, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("....................................................\n"+error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param;
                param = new HashMap<>();
                param.put("key", keys.key_login_verif);
                param.put("phone", number);
                param.put("can_send", String.valueOf(canSend));
                canSend = 0;
                return param;
            }
        };


        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy());
        requestQueue.add(stringRequest);
    }


   void handleinters(){
       KeyboardVisibilityEvent.setEventListener(
               VerificationSets.this,
               new KeyboardVisibilityEventListener() {
                   @Override
                   public void onVisibilityChanged(boolean isOpen) {
                       if(editText.getContentDescription().equals("no")){
                           ((MotionLayout)findViewById(R.id.motionlayout)).transitionToEnd();
                           linearLayout_phone.setBackgroundResource(R.drawable.cardstyle3);
                           editText.setContentDescription("yes");
                       }else {
                           ((MotionLayout)findViewById(R.id.motionlayout)).transitionToStart();
                           linearLayout_phone.setBackgroundResource(R.drawable.cardstyle4);
                           editText.setContentDescription("no");
                       }

                   }
               });

       /////////////////////////
       editText.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {

           }

           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {
               int m=s.length();
               String sd= String.valueOf(s);
               if(m==1 && sd.equals("0")){
                   editText.setText("");
               }
           }

           @Override
           public void afterTextChanged(Editable s) {

           }
       });
    }




    private void setUpViews() {
        floatingActionButton=findViewById(R.id.floatingActionButton);
        editText=findViewById(R.id.edittext_phone);
        linearLayout_phone=findViewById(R.id.linearLayout_phone);
    }

}