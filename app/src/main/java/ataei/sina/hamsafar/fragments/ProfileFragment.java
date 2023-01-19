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

public class ProfileFragment extends Fragment {
    @Nullable
    View view;
    TextView name,username;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.profile_page,container,false);
        setUpViews();
        setUps();
        return view;
    }

    private void setUps() {
        SharedPreferences sharedPref = getContext().getSharedPreferences("shared", Context.MODE_PRIVATE);
        String _name=sharedPref.getString("name","");
        String _family=sharedPref.getString("family","");
        String _username=sharedPref.getString("username","");
        name.setText(_name+" "+_family);
        username.setText(_username);
    }

    private void setUpViews() {
        name=view.findViewById(R.id.profile_name);
        username=view.findViewById(R.id.id_name);
    }

}
