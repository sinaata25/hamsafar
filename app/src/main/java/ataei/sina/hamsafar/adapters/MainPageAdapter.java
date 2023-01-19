package ataei.sina.hamsafar.adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainPageAdapter extends FragmentStateAdapter {

    List<Fragment>fragments;
    Context ctx;

    public MainPageAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle , Context context) {
        super(fragmentManager , lifecycle);
        ctx = context;
        fragments = new ArrayList<>();
    }

    public Fragment createFragment(int position) {
        return fragments.get(position);
    }
    public void addFragment(Fragment fragment) {
        fragments.add(fragment);
    }
    @Override
    public int getItemCount() {
        return fragments.size();
    }
}
