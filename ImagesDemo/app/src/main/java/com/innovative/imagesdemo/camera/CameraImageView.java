package com.innovative.imagesdemo.camera;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.innovative.imagesdemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CameraImageView extends AppCompatActivity {

    @BindView(R.id.iv_image)
    ImageView ivCameraImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_image_view);

        ButterKnife.bind(this);

        String imageUri = getIntent().getStringExtra("image");

        Glide.with(getApplicationContext())
                .load(Uri.parse(imageUri))
                .into(ivCameraImage);

    }

}
