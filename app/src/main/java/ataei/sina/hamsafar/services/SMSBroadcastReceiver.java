package ataei.sina.hamsafar.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import ataei.sina.hamsafar.VerificationSets;

public class SMSBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {

            Bundle mBundle = intent.getExtras();
            SmsMessage[] msg;
            String smsFrom="";
            String smsBody="";

            if (mBundle != null) {
                try {
                    Object[] mPdus = (Object[]) mBundle.get("pdus");
                    msg = new SmsMessage[mPdus.length];

                    for (int i = 0; i < mPdus.length; i++) {
                        msg[i] = SmsMessage.createFromPdu((byte[]) mPdus[i]);
                        smsFrom = msg[i].getOriginatingAddress();
                        smsBody = msg[i].getMessageBody();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            String numberOnly= smsBody.replaceAll("[^0-9]", "");
            VerificationSets.verificationSmsListener.onRecive(numberOnly);
        }

    }
}
