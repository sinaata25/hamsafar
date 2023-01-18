package ataei.sina.hamsafar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageView;

import ataei.sina.hamsafar.adapters.main_page_adapter;
import ataei.sina.hamsafar.fragments.DiscoveryFragment;
import ataei.sina.hamsafar.fragments.HistoryFragment;
import ataei.sina.hamsafar.fragments.HomeFragment;
import ataei.sina.hamsafar.fragments.ProfileFragment;

public class MainActivity extends AppCompatActivity {

    ViewPager2 main_viewpager;
    ImageView Home_bot , Discovery_bot , History_bot , Profile_bot;
    main_page_adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        setUpViews();
        adapter = new main_page_adapter(getSupportFragmentManager(), getLifecycle() , getApplicationContext());
        adapter.addFragment(new HomeFragment());
        adapter.addFragment(new DiscoveryFragment());
        adapter.addFragment(new HistoryFragment());
        adapter.addFragment(new ProfileFragment());

        main_viewpager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        main_viewpager.setCurrentItem(0);
        main_viewpager.setAdapter(adapter);
        main_viewpager.setUserInputEnabled(false);

    }

    private void unselected(int x) {
        if(x == 0){
            Home_bot.setImageResource(R.mipmap.home);
            return;
        }
        if(x == 1){
            Discovery_bot.setImageResource(R.mipmap.discover);
            return;
        }
        if(x == 2){
            History_bot.setImageResource(R.mipmap.history);
            return;
        }
        if(x == 3){
            Profile_bot.setImageResource(R.mipmap.profile);
            return;
        }
    }


    private void setUpViews() {
        main_viewpager = findViewById(R.id.main_viewpager);
        Home_bot = findViewById(R.id.Home_bot);
        Discovery_bot = findViewById(R.id.Discovery_bot);
        History_bot = findViewById(R.id.History_bot);
        Profile_bot = findViewById(R.id.Profile_bot);
    }


    public void Home_clicked(View view) {

        unselected(main_viewpager.getCurrentItem());
        Home_bot.setImageResource(R.mipmap.home_selected);
        main_viewpager.setCurrentItem(0);


    }
    public void Discovery_clicked(View view) {

        unselected(main_viewpager.getCurrentItem());
        Discovery_bot.setImageResource(R.mipmap.dicover_selected);
        main_viewpager.setCurrentItem(1);

    }
    public void History_clicked(View view) {

        unselected(main_viewpager.getCurrentItem());
        History_bot.setImageResource(R.mipmap.history_selected);
        main_viewpager.setCurrentItem(2);

    }
    public void Profile_clicked(View view) {

        unselected(main_viewpager.getCurrentItem());
        Profile_bot.setImageResource(R.mipmap.profile_selected);
        main_viewpager.setCurrentItem(3);

    }
}