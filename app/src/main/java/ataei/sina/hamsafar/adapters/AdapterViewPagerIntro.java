package ataei.sina.hamsafar.adapters;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class AdapterViewPagerIntro extends FragmentPagerAdapter {
    List<Fragment>list;
    Context context;

    public AdapterViewPagerIntro(@NonNull FragmentManager fm, int behavior,Context context) {
        super(fm, behavior);
        list=new ArrayList<>();
        this.context=context;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        return list.get(position);

    }

    @Override
    public int getCount() {
        return list.size();
    }


    public void addfragment(Fragment fragment){
        try {
            list.add(fragment);
        }catch (Exception e){
            Log.i("TAG", "Error pager add: "+e.getMessage());
        }
    }


}
