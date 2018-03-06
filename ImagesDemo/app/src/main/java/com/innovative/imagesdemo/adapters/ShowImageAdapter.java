package com.innovative.imagesdemo.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.innovative.imagesdemo.R;
import com.innovative.imagesdemo.model.Images;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by pulkit on 2/8/17.
 */

public class ShowImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Images> imagesList;
    private OnImageSelectListener onImageSelectListener;
    private Context context;

    public interface OnImageSelectListener {
        void onImageSelect(int position, ArrayList<String> imagesList);
    }

    public ShowImageAdapter(List<Images> imagesList, Context context) {
        this.imagesList = imagesList;
        this.onImageSelectListener = onImageSelectListener;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GalleryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.show_list_image_items, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        final GalleryViewHolder holder = (GalleryViewHolder) viewHolder;

        String imageUrl = imagesList.get(position).getImagePath();

        Glide.with(context)
                .load(imageUrl)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return imagesList.size();
    }

    class GalleryViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_gallery_image)
        ImageView imageView;

        public GalleryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.iv_gallery_image)
        public void onClickImage() {


        }

    }
}
