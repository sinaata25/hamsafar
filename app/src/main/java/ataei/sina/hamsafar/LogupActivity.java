package ataei.sina.hamsafar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.funrisestudio.stepprogress.StepProgressView;

import ataei.sina.hamsafar.adapters.AdapterViewPagerLogup;
import ataei.sina.hamsafar.fragments.Logup_1_Fragment;
import ataei.sina.hamsafar.fragments.Logup_2_Fragment;
import ataei.sina.hamsafar.fragments.Logup_3_Fragment;

public class LogupActivity extends AppCompatActivity {
ViewPager viewPager;
AdapterViewPagerLogup adapterViewPagerLogup;
StepProgressView stepProgressView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logup_activity);
        setUpViews();
        setUps();
    }

    private void setUps() {
        adapterViewPagerLogup=new AdapterViewPagerLogup(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,getApplicationContext());
        adapterViewPagerLogup.addfragment(new Logup_1_Fragment());
        adapterViewPagerLogup.addfragment(new Logup_2_Fragment());
        adapterViewPagerLogup.addfragment(new Logup_3_Fragment());
        viewPager.setAdapter(adapterViewPagerLogup);
        stepProgressView.setStepsCount(3);

    }

    private void setUpViews() {
        viewPager=findViewById(R.id.viewPager_logup);
        stepProgressView=findViewById(R.id.vStepProgress);
    }
}