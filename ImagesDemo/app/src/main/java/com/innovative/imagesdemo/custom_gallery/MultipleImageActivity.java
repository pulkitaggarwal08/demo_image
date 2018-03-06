package com.innovative.imagesdemo.custom_gallery;

/**
 * Created by pulkit on 21/10/17.
 */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.innovative.imagesdemo.R;
import com.innovative.imagesdemo.adapters.MultipleImageAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MultipleImageActivity extends AppCompatActivity {

    @BindView(R.id.rv_images)
    RecyclerView rvImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_image);

        ButterKnife.bind(this);

        ArrayList<String> list = getIntent().getStringArrayListExtra("list");
        rvImages.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        rvImages.setAdapter(new MultipleImageAdapter(list, getApplicationContext()));

    }

}
