package com.innovative.imagesdemo.gallery_single_pick;

import android.net.Uri;
import android.support.annotation.BinderThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.innovative.imagesdemo.R;
import com.innovative.imagesdemo.zoom.TouchImageView;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SingleImageActivity extends AppCompatActivity {

    @BindView(R.id.iv_image)
    TouchImageView ivCameraImage;
//    ImageView ivCameraImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_image);


        ButterKnife.bind(this);

        String imageUri = getIntent().getStringExtra("image");
        Picasso.with(getApplicationContext())
                .load(Uri.parse(imageUri))
                .resize(1200, 1800)
                .centerInside()
                .into(ivCameraImage);

    }

}
