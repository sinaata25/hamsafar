package ataei.sina.hamsafar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

public class LoadingActivityIntro extends AppCompatActivity {
    LinearLayout tasvir_start;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_intro);
        tasvir_start = findViewById(R.id.tasvir_start);
        tasvir_start.setAlpha(0);
        tasvir_start.animate().alpha(1F).setDuration(3000).withEndAction(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void run() {
                if(checkConnection()){
                    SharedPreferences sharedPref =getSharedPreferences("shared",Context.MODE_PRIVATE);
                    int first_time = sharedPref.getInt("first_time",1);
                    int login = sharedPref.getInt("login",0);
                    if(first_time==0 && login==0 ){
                        Intent x = new Intent(LoadingActivityIntro.this, VerificationSets.class);
                        startActivity(x);
                    }else if(first_time==1 && login==0){
                        Intent x = new Intent(LoadingActivityIntro.this, IntroActivity.class);
                        startActivity(x);
                    }else if( first_time==0 && login==1){
                        Intent x = new Intent(LoadingActivityIntro.this, MainActivity.class);
                        startActivity(x);
                    }

                }else {
                    Toast.makeText(getApplicationContext(),"عدم اتصال دستگاه به اینترنت",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    boolean checkConnection(){
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        }
        else{
            connected = false;}
        return connected;
    }

}