package ataei.sina.hamsafar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ataei.sina.hamsafar.model.Advertisment;

public class AdDetails extends AppCompatActivity {
        TextView detail,origin,destination,date,time,car,username;

        ImageView back;
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
        origin.setText(advertisment.getOrigin());
        destination.setText(advertisment.getDestination());
        time.setText(advertisment.getTime());
        date.setText(advertisment.getDate());
        username.setText(advertisment.getName());

    }

    private void setUpViews() {
        detail=findViewById(R.id.detail_description);
        origin=findViewById(R.id.detail_text_origin);
        destination=findViewById(R.id.detail_text_destination);
        time=findViewById(R.id.detail_text_time);
        date=findViewById(R.id.detail_text_date);
        username=findViewById(R.id.detail_driver_name);
        back=findViewById(R.id.detail_buttom_back);
    }

    private void handle() {

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

   public interface DataL{
        void onGetL(Advertisment advertisment);
    }




}