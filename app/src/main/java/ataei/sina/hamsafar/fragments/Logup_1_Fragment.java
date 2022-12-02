package ataei.sina.hamsafar.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import ataei.sina.hamsafar.LogupActivity;
import ataei.sina.hamsafar.R;

public class Logup_1_Fragment extends Fragment {
    View view;
    ImageView imageView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.logup_1_fragment,container,false);
        setUpViews();
        handle();
        return view;
    }

    private void setUpViews() {
        imageView=view.findViewById(R.id.imageView3);
    }

    private void handle() {
        KeyboardVisibilityEvent.setEventListener(
                getActivity(),
                new KeyboardVisibilityEventListener() {
                    @Override
                    public void onVisibilityChanged(boolean isOpen) {
                        if(isOpen){
                            imageView.setVisibility(View.GONE);
                        }else {
                            imageView.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }


}
