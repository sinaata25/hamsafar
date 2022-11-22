package ataei.sina.hamsafar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import android.widget.TextView;
import android.widget.Toast;

import com.jakode.verifycodeedittext.VerifyCodeEditText;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import ataei.sina.hamsafar.interfaces.VerificationSmsListener;

public class SmsActivation extends AppCompatActivity {
    VerifyCodeEditText verifyCodeEditText;
    TextView textView_num;
    String number="";
    private final int SMS_REQUEST_CODE = 100;
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



    }

    private void setups() {
     verifyCodeEditText=findViewById(R.id.verifyCodeEditText2);
     textView_num=findViewById(R.id.textView_num);
    }






    private void requestReceiveSMSpermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(SmsActivation.this, Manifest.permission.RECEIVE_SMS)) {

            new AlertDialog.Builder(this)
                    .setTitle("درخواست مجوز")
                    .setMessage("برای عملکرد صحیح برنامه باید دسترسی به دریافت پیامکتایید شود")
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

        } else {

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