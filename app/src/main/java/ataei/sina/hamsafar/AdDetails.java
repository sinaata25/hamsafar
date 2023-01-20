package ataei.sina.hamsafar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import ataei.sina.hamsafar.model.Advertisment;

public class AdDetails extends AppCompatActivity {
        TextView detail,origin,destination,date,time,car,username;
    Advertisment advertisment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ad_details);
        setUpViews();
        getIntents();
        sets();
        handle();
    }

    private void getIntents() {
        Intent intent=getIntent();
        advertisment= (Advertisment) intent.getSerializableExtra("adv");
    }

    private void sets() {
        detail.setText(advertisment.getDescription());

    }

    private void setUpViews() {
        detail=findViewById(R.id.detail_description);
    }

    private void handle() {

    }

   public interface DataL{
        void onGetL(Advertisment advertisment);
    }


}