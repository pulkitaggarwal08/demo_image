package com.innovative.imagesdemo.gallery_multiple_pick;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.innovative.imagesdemo.R;
import com.innovative.imagesdemo.adapters.ShowImageAdapter;
import com.innovative.imagesdemo.model.Images;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShowImageActivity extends AppCompatActivity {

    @BindView(R.id.rv_images)
    RecyclerView rvImages;

    ArrayList<Images> imagesArrayList;
    ArrayList<Images> arrayList = new ArrayList<>();

    String getData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_image);

        ButterKnife.bind(this);

        rvImages.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));

        imagesArrayList = (ArrayList<Images>) getIntent().getSerializableExtra("imagesArrayList");

        if (imagesArrayList != null) {

            rvImages.setAdapter(new ShowImageAdapter(imagesArrayList, getApplicationContext()));
        }
        else {
            getData = getIntent().getStringExtra("list");

            arrayList.add(new Images(getData));
            rvImages.setAdapter(new ShowImageAdapter(arrayList, getApplicationContext()));
        }
    }

}
