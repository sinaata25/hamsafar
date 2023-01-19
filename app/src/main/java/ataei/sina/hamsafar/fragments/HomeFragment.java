package ataei.sina.hamsafar.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ataei.sina.hamsafar.R;

public class HomeFragment extends Fragment {
    @Nullable
    TextView textView_welcome;
    View view;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.home_page,container,false);
        setupViews();
        sets();
        return view;
    }

    private void sets() {
        SharedPreferences sharedPref = getContext().getSharedPreferences("shared", Context.MODE_PRIVATE);
        String name=sharedPref.getString("name","");
        textView_welcome.setText("خوش اومدی "+name);
    }

    private void setupViews() {
        textView_welcome=view.findViewById(R.id.textView5_home);
    }

}
