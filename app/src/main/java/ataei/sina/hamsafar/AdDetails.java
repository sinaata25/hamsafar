package ataei.sina.hamsafar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ataei.sina.hamsafar.model.Advertisment;

public class AdDetails extends AppCompatActivity {
        TextView detail,origin,destination,date,time,car,username,ask,price;
        ImageView back,cover_image;
    Advertisment advertisment;
    String img="";
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
        img=intent.getStringExtra("image");
        advertisment= (Advertisment) intent.getSerializableExtra("adv");
    }

    private void sets() {
        detail.setText(advertisment.getDescription());
        origin.setText(advertisment.getOrigin());
        destination.setText(advertisment.getDestination());
        time.setText(advertisment.getTime());
        date.setText(advertisment.getDate());
        username.setText(advertisment.getName());
        car.setText(advertisment.getCar());
        if(!img.equals("")){
            Picasso.get()
                    .load(img)
                    .fit()
                    .into(cover_image);
        }
        price.setText(advertisment.getPrice()+" تومان");

    }

    private void setUpViews() {
        detail=findViewById(R.id.detail_description);
        origin=findViewById(R.id.detail_text_origin);
        destination=findViewById(R.id.detail_text_destination);
        time=findViewById(R.id.detail_text_time);
        date=findViewById(R.id.detail_text_date);
        username=findViewById(R.id.detail_driver_name);
        back=findViewById(R.id.detail_buttom_back);
        cover_image=findViewById(R.id.imageView5);
        ask=findViewById(R.id.detail_buttom_order);
        price=findViewById(R.id.detail_buttom_price);
        car=findViewById(R.id.detail_text_car);
    }

    private void handle() {

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),AskToTrip.class);
                intent.putExtra("origin", advertisment.getOrigin());
                intent.putExtra("destination", advertisment.getDestination());
                intent.putExtra("date", advertisment.getDate());
                intent.putExtra("time" , advertisment.getTime());
                startActivity(intent);
            }
        });



    }



}