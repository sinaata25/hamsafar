package ataei.sina.hamsafar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import ataei.sina.hamsafar.adapters.MainPageAdapter;
import ataei.sina.hamsafar.fragments.DiscoveryFragment;
import ataei.sina.hamsafar.fragments.BookMarkFragment;
import ataei.sina.hamsafar.fragments.HomeFragment;
import ataei.sina.hamsafar.fragments.ProfileFragment;

public class MainActivity extends AppCompatActivity {

    ViewPager2 main_viewpager;
    ImageView Home_bot , Discovery_bot , BookMark_bot , Profile_bot;
    MainPageAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        setUpViews();
        adapter = new MainPageAdapter(getSupportFragmentManager(), getLifecycle() , getApplicationContext());
        adapter.addFragment(new HomeFragment());
        adapter.addFragment(new DiscoveryFragment());
        adapter.addFragment(new BookMarkFragment());
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
            BookMark_bot.setImageResource(R.mipmap.bookmark);
            return;
        }
        if(x == 3){
            Profile_bot.setImageResource(R.mipmap.profile);
        }
    }


    private void setUpViews() {
        main_viewpager = findViewById(R.id.main_viewpager);
        Home_bot = findViewById(R.id.Home_bot);
        Discovery_bot = findViewById(R.id.Discovery_bot);
        BookMark_bot = findViewById(R.id.BookMark_bot);
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
    public void BookMark_clicked(View view) {

        unselected(main_viewpager.getCurrentItem());
        BookMark_bot.setImageResource(R.mipmap.bookmark_selected);
        main_viewpager.setCurrentItem(2);

    }
    public void Profile_clicked(View view) {

        unselected(main_viewpager.getCurrentItem());
        Profile_bot.setImageResource(R.mipmap.profile_selected);
        main_viewpager.setCurrentItem(3);

    }
}