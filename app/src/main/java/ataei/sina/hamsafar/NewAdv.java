package ataei.sina.hamsafar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.jetbrains.annotations.NotNull;

import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.api.PersianPickerDate;
import ir.hamsaa.persiandatepicker.api.PersianPickerListener;

public class NewAdv extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
        ImageView back;
        EditText desc,car,price;
        TextView time,date,origin,destination,post;
        TimePickerDialog timePickerDialog;
        public static DataCh dataCh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_adv);
        setUpViews();
        handle();
    }

    private void handle() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        date.setOnClickListener(V->datePick());
        origin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),CityPicker.class);
                intent.putExtra("mode",2);
                startActivity(intent);
            }
        });
        destination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),CityPicker.class);
                intent.putExtra("mode",3);
                startActivity(intent);
            }
        });

        dataCh=new DataCh() {
            @Override
            public void onChoosedOrigin(String city) {
                origin.setText(city);
            }

            @Override
            public void onChoosedDestination(String city) {
            destination.setText(city);
            }
        };

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog=TimePickerDialog.newInstance(NewAdv.this,true);
                timePickerDialog.setAccentColor(Color.parseColor("#3892EE"));
                timePickerDialog.show(getSupportFragmentManager(),"");
            }
        });




    }


    private void setUpViews() {
        back=findViewById(R.id.post_back);
        desc=findViewById(R.id.post_text_description);
        car=findViewById(R.id.editTextTextPersonName3);
        price=findViewById(R.id.editTextNumberDecimal);
        post=findViewById(R.id.post_take_trip);
        time=findViewById(R.id.editTextTime);
        origin=findViewById(R.id.post_text_origin);
        destination=findViewById(R.id.post_text_destination);
        date=findViewById(R.id.editTextDate2);
    }


    void datePick(){
        PersianDatePickerDialog picker=new PersianDatePickerDialog(NewAdv.this);
        picker.setPositiveButtonString("باشه")
                .setNegativeButton("بیخیال")
                .setTodayButton("امروز")
                .setTodayButtonVisible(true)
                .setPickerBackgroundDrawable(R.drawable.cardstyle10)
                .setMinYear(PersianDatePickerDialog.THIS_YEAR)
                .setMaxYear(PersianDatePickerDialog.THIS_YEAR)
                .setActionTextSize(20)
                .setMaxDay(PersianDatePickerDialog.THIS_DAY)
                .setInitDate(1401, 12, 1)
                .setActionTextColor(Color.GRAY)
                .setTitleType(PersianDatePickerDialog.WEEKDAY_DAY_MONTH_YEAR)
                .setShowInBottomSheet(true)
                .setListener(new PersianPickerListener() {
                    @Override
                    public void onDateSelected(@NotNull PersianPickerDate persianPickerDate) {
                        date.setText(persianPickerDate.getPersianYear() + "/" + persianPickerDate.getPersianMonth() + "/" + persianPickerDate.getPersianDay());
                    }

                    @Override
                    public void onDismissed() {

                    }
                });

        picker.show();
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
            time.setText(hourOfDay+":"+minute);
            timePickerDialog.dismiss();
    }

    public interface DataCh{
        void onChoosedOrigin(String city);
        void onChoosedDestination(String city);
    }



}