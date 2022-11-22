package ataei.sina.hamsafar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.jakode.verifycodeedittext.VerifyCodeEditText;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

public class SmsActivation extends AppCompatActivity {
    VerifyCodeEditText verifyCodeEditText;
    TextView textView_num;
    String number="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sms_activation);
        setups();
        sets();
        handle();
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

    }

    private void setups() {
     verifyCodeEditText=findViewById(R.id.verifyCodeEditText2);
     textView_num=findViewById(R.id.textView_num);
    }
}