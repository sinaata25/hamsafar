package ataei.sina.hamsafar.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ataei.sina.hamsafar.MainActivity;
import ataei.sina.hamsafar.R;


public class Intro_4_Fragment extends Fragment {

    Button button;

    @Nullable
    View view;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      view=inflater.inflate(R.layout.intro_4_fragment,container,false);
      setUpViews();
      sets();
    return view;
    }

    private void sets() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setUpViews() {
        button=view.findViewById(R.id.button_vorood_intro);
    }
}
