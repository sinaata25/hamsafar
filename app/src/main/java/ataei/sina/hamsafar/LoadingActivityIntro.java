package ataei.sina.hamsafar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

public class LoadingActivityIntro extends AppCompatActivity {
    LinearLayout tasvir_start;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_intro);
        tasvir_start = findViewById(R.id.tasvir_start);
        tasvir_start.setAlpha(0);
        Intent x = new Intent(LoadingActivityIntro.this, LogupActivity.class);
        tasvir_start.animate().alpha(1F).setDuration(3000).withEndAction(new Runnable() {
            @Override
            public void run() {
                startActivity(x);
            }
        });

    }
    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
    }
}