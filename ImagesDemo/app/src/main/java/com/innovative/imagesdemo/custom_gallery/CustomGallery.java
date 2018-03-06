package com.innovative.imagesdemo.custom_gallery;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.innovative.imagesdemo.R;
import com.innovative.imagesdemo.gallery_multiple_pick.ShowImageActivity;

import java.util.ArrayList;
import java.util.HashSet;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CustomGallery extends AppCompatActivity {

    @BindView(R.id.rv_gallery_images)
    RecyclerView rvGalleryImages;

    private GalleryAdapter galleryAdapter;
    private ArrayList<String> imageList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_gallery);

        ButterKnife.bind(this);
        ArrayList<String> imageUrls = loadPhotosFromNativeGallery();
        galleryAdapter = new GalleryAdapter(imageUrls, new GalleryAdapter.OnImageSelectListener() {
            @Override
            public void onImageSelect(int position, ArrayList<String> imagesList) {
                imageList.clear();
                imageList.addAll(imagesList);

            }
        }, getApplicationContext());

        rvGalleryImages.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
        rvGalleryImages.setAdapter(galleryAdapter);

    }

    @OnClick(R.id.btn_done)
    public void onClickDone(){
        new AsyncTask<Void, Void, Void>() {
            ArrayList<String> selectedImagelist = new ArrayList<>();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Void... params) {
                for (int i = 0; i < imageList.size(); i++) {
                    if (imageList.get(i).equalsIgnoreCase("")) {

                    } else {
                        HashSet<String> set = new HashSet<>();
                        if (set.add(imageList.get(i))) {
                            selectedImagelist.add(imageList.get(i));
                        }
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                /*if (selectedImagelist.size() > 10) {
                    SnackbarUtil.showWarningLongSnackbar(GalleryActivity.this, "You can select max 10 images");
                } else {

                }*/

                Intent intent = new Intent(getApplicationContext(), MultipleImageActivity.class);
                intent.putStringArrayListExtra("list", selectedImagelist);
                /*setResult(999, intent);
                finish();*/
                startActivity(intent);

            }
        }.execute();
    }

    private ArrayList<String> loadPhotosFromNativeGallery() {
        final String[] columns = {MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID};
        final String orderBy = MediaStore.Images.Media.DATE_TAKEN;
        Cursor imagecursor = managedQuery(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null, null, orderBy + " DESC");
        ArrayList<String> imageUrls = new ArrayList<>();
        for (int i = 0; i < imagecursor.getCount(); i++) {
            imagecursor.moveToPosition(i);
            int dataColumnIndex = imagecursor.getColumnIndex(MediaStore.Images.Media.DATA);
            imageUrls.add(imagecursor.getString(dataColumnIndex));
        }
        return imageUrls;
    }
}
