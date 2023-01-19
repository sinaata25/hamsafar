package ataei.sina.hamsafar.fragments;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.funrisestudio.stepprogress.StepProgressView;

import java.util.List;

import ataei.sina.hamsafar.LogupActivity;
import ataei.sina.hamsafar.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class Logup_2_Fragment extends Fragment {
    View view;
    ImageView img;
    CircleImageView circleImageView;
    Uri img_uri;
    int SELECT_PICTURE = 200;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.logup_2_fragment,container,false);
        setupViews();
        handle();
        return view;
    }

    private void handle() {


        img.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });
        ///
        LogupActivity.check_1=new LogupActivity.Check_1() {
            @Override
            public Uri onclicked() {
                return img_uri;
            }
        };






    }




    void imageChooser() {

        // create an instance of the
        // intent of the type image
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    private void setupViews() {
        img=view.findViewById(R.id.imageview_selection);
        circleImageView=view.findViewById(R.id.circleImageView);
    }










    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    img.setVisibility(View.GONE);
                    circleImageView.setVisibility(View.VISIBLE);
                    img_uri=selectedImageUri;
                    circleImageView.setImageURI(selectedImageUri);
                }
            }
        }
    }





}
