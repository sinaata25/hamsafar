package ataei.sina.hamsafar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class AskToTrip extends AppCompatActivity {

    TextView destination , origin , date , time , number , confirm;
    ImageView back , min , add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ask_to_trip);
        setUpViews();
        sets();
        holder();
    }

    private void holder() {
        back.setOnClickListener(v->back());
        min.setOnClickListener(view ->min());
        add.setOnClickListener(view ->add() );
    }

    private void sets() {
        origin.setText(getIntent().getStringExtra("origin"));
        destination.setText(getIntent().getStringExtra("destination"));
        time.setText(getIntent().getStringExtra("time"));
        date.setText(getIntent().getStringExtra("date"));
    }

    private void setUpViews() {
        destination = findViewById(R.id.ask_trip_destination);
        origin = findViewById(R.id.ask_trip_origin);
        date = findViewById(R.id.ask_trip_date);
        time = findViewById(R.id.ask_trip_time);
        number = findViewById(R.id.ask_number);
        back = findViewById(R.id.ask_trip_back);
        min = findViewById(R.id.ask_mines_people);
        add = findViewById(R.id.ask_add_people);
        confirm = findViewById(R.id.ask_trip_buttom_order);
    }

    public void add(){
        if(Integer.valueOf(number.getText().toString()) < 4){
            int x = Integer.valueOf(number.getText().toString());
            x++;
            number.setText(String.valueOf(x));

        }
    }

    public void min(){
        if(Integer.valueOf(number.getText().toString()) > 1){
            int x = Integer.valueOf(number.getText().toString());
            x--;
            number.setText(String.valueOf(x));

        }
    }

    public void back(){
        finish();
    }
}