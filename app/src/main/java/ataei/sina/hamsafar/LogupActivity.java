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
public static Check check;
    public static Check check_1;
    public static Check check_2;
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
        //////////
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewPager.getCurrentItem()==0){
                    check.onclicked(viewPager,stepProgressView);
                }else if(viewPager.getCurrentItem()==1){
                    check_1.onclicked(viewPager,stepProgressView);
                }else {
                    check_2.onclicked(viewPager,stepProgressView);
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
                viewPager.setCurrentItem(viewPager.getCurrentItem());
                return true;
            }
        });

    }

    private void setUpViews() {
        viewPager=findViewById(R.id.viewPager_logup);
        stepProgressView=findViewById(R.id.vStepProgress);
        go=findViewById(R.id.textView6);

    }
    public interface Check{
        void onclicked(ViewPager viewPager,StepProgressView stepProgressView);
    }
    public interface Check_1{
        void onclicked(ViewPager viewPager,StepProgressView stepProgressView);
    }
    public interface Check_2{
        void onclicked(ViewPager viewPager,StepProgressView stepProgressView);
    }

}