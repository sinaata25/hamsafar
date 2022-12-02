package ataei.sina.hamsafar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

import ataei.sina.hamsafar.adapters.AdapterViewPagerIntro;
import ataei.sina.hamsafar.fragments.Intro_1_Fragment;
import ataei.sina.hamsafar.fragments.Intro_2_Fragment;
import ataei.sina.hamsafar.fragments.Intro_3_Fragment;
import ataei.sina.hamsafar.fragments.Intro_4_Fragment;

public class IntroActivity extends AppCompatActivity {
ViewPager viewPager;
AdapterViewPagerIntro adapterViewPagerIntro;
WormDotsIndicator dotsIndicator;
TextView close;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro);
        setUpViews();
        sets();
    }

    private void sets() {

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("shared", Context.MODE_PRIVATE);
        adapterViewPagerIntro=new AdapterViewPagerIntro(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,getApplicationContext());
        adapterViewPagerIntro.addfragment(new Intro_1_Fragment());
        adapterViewPagerIntro.addfragment(new Intro_2_Fragment());
        adapterViewPagerIntro.addfragment(new Intro_3_Fragment());
        adapterViewPagerIntro.addfragment(new Intro_4_Fragment());
        viewPager.setAdapter(adapterViewPagerIntro);
        dotsIndicator.attachTo(viewPager);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt("first_time",0);
                editor.apply();
                finish();
                Intent intent=new Intent(getApplicationContext(), VerificationSets.class);
                startActivity(intent);

            }
        });



    }

    private void setUpViews() {
        viewPager=findViewById(R.id.viewpager_intro);
        dotsIndicator=findViewById(R.id.dotindicator);
        close=findViewById(R.id.close_intro);
    }
}