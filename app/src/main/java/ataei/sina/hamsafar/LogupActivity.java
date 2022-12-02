package ataei.sina.hamsafar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.funrisestudio.stepprogress.StepProgressView;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import ataei.sina.hamsafar.adapters.AdapterViewPagerLogup;
import ataei.sina.hamsafar.fragments.Logup_1_Fragment;
import ataei.sina.hamsafar.fragments.Logup_2_Fragment;
import ataei.sina.hamsafar.fragments.Logup_3_Fragment;

public class LogupActivity extends AppCompatActivity {
ViewPager viewPager;
AdapterViewPagerLogup adapterViewPagerLogup;
StepProgressView stepProgressView;
TextView go;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logup_activity);
        setUpViews();
        setUps();
        handle();
    }

    private void handle() {
        KeyboardVisibilityEvent.setEventListener(
                LogupActivity.this,
                new KeyboardVisibilityEventListener() {
                    @Override
                    public void onVisibilityChanged(boolean isOpen) {
                        if(isOpen){
                            go.setVisibility(View.GONE);
                        }else {
                            go.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }

    private void setUps() {
        adapterViewPagerLogup=new AdapterViewPagerLogup(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,getApplicationContext());
        adapterViewPagerLogup.addfragment(new Logup_1_Fragment());
        adapterViewPagerLogup.addfragment(new Logup_2_Fragment());
        adapterViewPagerLogup.addfragment(new Logup_3_Fragment());
        viewPager.setAdapter(adapterViewPagerLogup);
        stepProgressView.setStepsCount(3);
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

    }

    private void setUpViews() {
        viewPager=findViewById(R.id.viewPager_logup);
        stepProgressView=findViewById(R.id.vStepProgress);
        go=findViewById(R.id.textView6);
    }
}