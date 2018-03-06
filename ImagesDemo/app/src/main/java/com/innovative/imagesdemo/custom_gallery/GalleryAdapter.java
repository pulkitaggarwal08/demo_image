package com.innovative.imagesdemo.custom_gallery;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.innovative.imagesdemo.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by pulkit on 30/7/17.
 */

public class GalleryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> imagesList;
    private OnImageSelectListener onImageSelectListener;
    private Context context;

    private ArrayList<String> tempImageList;
    private ArrayList<Boolean> checkUncheckImageList;


    public interface OnImageSelectListener {
        void onImageSelect(int position, ArrayList<String> imagesList);
    }

    public GalleryAdapter(List<String> imagesList, OnImageSelectListener onImageSelectListener, Context context) {
        this.imagesList = imagesList;
        this.onImageSelectListener = onImageSelectListener;
        this.context = context;

        tempImageList = new ArrayList<>(imagesList.size());
        checkUncheckImageList = new ArrayList<>(imagesList.size());
        for (int i = 0; i < imagesList.size(); i++) {
            checkUncheckImageList.add(false);
            tempImageList.add("");
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GalleryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_gallery_images_items, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        final GalleryViewHolder holder = (GalleryViewHolder) viewHolder;

        String imageUrl = imagesList.get(position);

        Glide.with(context)
                .load("file://" + imageUrl)
                .into(holder.imageView);

        if(checkUncheckImageList.get(position)){
            holder.checkBox.setChecked(true);
        }else{
            holder.checkBox.setChecked(false);
        }


    }

    @Override
    public int getItemCount() {
        return imagesList.size();
    }

    class GalleryViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_gallery_image)
        ImageView imageView;
        @BindView(R.id.cb_image)
        CheckBox checkBox;

        public GalleryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.iv_gallery_image)
        public void onClickImage() {

            if (checkUncheckImageList.get(getLayoutPosition())) {
                checkUncheckImageList.set(getLayoutPosition(), false);
                if (tempImageList.size() > 0) {
                    if (getLayoutPosition() < tempImageList.size()) {
                        tempImageList.set(getLayoutPosition(), "");
                    }
                }

                checkBox.setChecked(false);
                Log.v("Hi deleted position", getLayoutPosition() + "");
                onImageSelectListener.onImageSelect(getLayoutPosition(), tempImageList);
            } else {
                checkUncheckImageList.set(getLayoutPosition(), true);
                tempImageList.set(getLayoutPosition(), imagesList.get(getLayoutPosition()));

                checkBox.setChecked(true);
                Log.v("Hi insert at", getLayoutPosition() + "");
                onImageSelectListener.onImageSelect(getLayoutPosition(), tempImageList);
            }
        }

    }
}
