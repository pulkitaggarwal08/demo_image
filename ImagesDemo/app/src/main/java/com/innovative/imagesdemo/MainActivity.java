package com.innovative.imagesdemo;

import android.Manifest;
import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.innovative.imagesdemo.camera.CameraImageView;
import com.innovative.imagesdemo.config.AppConstant;
import com.innovative.imagesdemo.custom_gallery.CustomGallery;
import com.innovative.imagesdemo.gallery_multiple_pick.ShowImageActivity;
import com.innovative.imagesdemo.gallery_single_pick.SingleImageActivity;
import com.innovative.imagesdemo.model.Images;
import com.innovative.imagesdemo.util.CommonUtil;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CAMERA_CODE = 901;
    private static final int REQUEST_CUSTOM_GALLERY_CODE = 902;
    private static final int REQUEST_SINGLE_PICK_IMAGE_CODE = 903;
    private static final int REQUEST_MULTIPLE_PICK_IMAGE_CODE = 904;
    private static final int REQUEST_MULTIPLE_PICK_DATA_TO_SHARE = 905;

    private Uri fileUri;
    private File filepath, dir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
    }

    /*Through Camera*/
    @OnClick(R.id.btn_camera)
    public void onClickCamera() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (CommonUtil.checkAndRequestPermission(MainActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    AppConstant.MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE)) {
                cameraIntent();
            }
        } else {
            cameraIntent();
        }
    }

    /*Through Gallery*/
    @OnClick(R.id.btn_gallery)
    public void onClickGallerySinglePick() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (CommonUtil.checkAndRequestPermission(MainActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    AppConstant.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE_SINGLE_PICKER)) {
                gallerySinglePickerIntent();
            }
        } else {
            gallerySinglePickerIntent();
        }
    }

    /*Through Gallery Multiple Images*/
    @OnClick(R.id.btn_multiple_pick_gallery)
    public void onClickGalleryMultiplePick() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (CommonUtil.checkAndRequestPermission(MainActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    AppConstant.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE_MULTIPLE_PICKER)) {
                galleryMultiplePickerIntent();
            }
        } else {
            galleryMultiplePickerIntent();
        }
    }

    /*Through Custom Gallery*/
    @OnClick(R.id.btn_custom_gallery)
    public void onClickCustomGallery() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (CommonUtil.checkAndRequestPermission(MainActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    AppConstant.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE)) {
                customGalleryIntent();
            }
        } else {
            customGalleryIntent();
        }
    }

    /*Share Data*/
    @OnClick(R.id.btn_share)
    public void onClickShareData() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (CommonUtil.checkAndRequestPermission(MainActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    AppConstant.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE)) {
                btn_share_data();
            }
        } else {
            btn_share_data();
        }
    }

    /*Make Pdf*/
    @OnClick(R.id.btn_make_pdf)
    public void OnClickMakePdf() {

        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        Font catFontBold = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
        Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 22, Font.BOLD | Font.UNDERLINE, BaseColor.GRAY);
        Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
        Font smallNormal = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.NORMAL);
        Font normal = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);

        OutputStream output;

        filepath = Environment.getExternalStorageDirectory();
        dir = new File(filepath.getAbsolutePath() + "/test/pdf/");
        dir.mkdirs();

        Date date = new Date();
        CharSequence sequence = DateFormat.format("HHmmss", date.getTime());
        String dateFormat = sequence.toString();

        String myFile = dateFormat + "_test.pdf";

        try {
            output = new FileOutputStream("/sdcard/test/pdf/" + myFile);

            PdfWriter docWriter = PdfWriter.getInstance(document, output);
            document.open();

            addMetaData(docWriter, document, catFontBold, titleFont, smallBold, smallNormal, normal);

//            addTitlePage(docWriter, document, catFontBold, titleFont, smallBold, smallNormal, normal);
//            addContent(docWriter, document, catFontBold, titleFont, smallBold, smallNormal, normal);
            document.close();

            Toast.makeText(this, "Pdf generated", Toast.LENGTH_SHORT).show();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    private void addMetaData(PdfWriter docWriter, Document document, Font catFontBold, Font titleFont,
                             Font smallBold, Font smallNormal, Font normal) throws DocumentException {

        BaseFont bfBold = null;
        BaseFont bfnormal = null;

        try {
            bfnormal = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            bfBold = BaseFont.createFont(BaseFont.TIMES_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        PdfContentByte cb = docWriter.getDirectContent();

        /*Add Headings*/
        createHeadings(bfnormal, cb, 50, 780, "Heading 1");
        createHeadings(bfnormal, cb, 277, 720, "Heading 2");
        createHeadings(bfnormal, cb, 375, 780, "Heading 3");

        Drawable drawable = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            drawable = getResources().getDrawable(R.mipmap.ic_launcher, getApplicationContext().getTheme());
        } else {
            drawable = getResources().getDrawable(R.mipmap.ic_launcher);
        }
        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
        Bitmap bitmap = bitmapDrawable.getBitmap();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);

        try {
            Image image = Image.getInstance(byteArrayOutputStream.toByteArray());
            image.scalePercent(25);
            image.setAlignment(Element.ALIGN_CENTER);

            document.add(image);

        } catch (BadElementException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        document.newPage();

        /*Add Headings*/
        createHeadings(bfBold, cb, 50, 780, "Heading 4 \n\n");

        Paragraph elements = new Paragraph(30f, " ");

        /*Add Table*/
        PdfPTable table = new PdfPTable(6);
        document.add(elements);

        table.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.setWidthPercentage(100f);

        table.addCell("Cell 1");
        table.addCell("Cell 2");
        table.addCell("Cell 3");
        table.addCell("Cell 4");
        table.addCell("Cell 5");
        table.addCell("Cell 6");

        document.add(table);

        createHeadings(bfBold, cb, 50, 510, "Heading 5");
        createHeadings(bfnormal, cb, 375, 510, "Heading 6");
        createHeadings(bfnormal, cb, 375, 490, "Heading 7");

    }

    private void createLargeHeadings(BaseFont bfnormal, PdfContentByte cb, float x, float y, String text) {

        cb.beginText();
        cb.setFontAndSize(bfnormal, 10);
        cb.setTextMatrix(x, y);
        cb.showText(text.trim());
        cb.endText();
    }

    private void createHeadings(BaseFont bfnormal, PdfContentByte cb, float x, float y, String text) {

        cb.beginText();
        cb.setFontAndSize(bfnormal, 10);
        cb.setTextMatrix(x, y);
        cb.showText(text.trim());
        cb.endText();

    }

    private void addTitlePage(PdfWriter docWriter, Document document, Font catFontBold, Font titleFont,
                              Font smallBold, Font smallNormal, Font normal) {

    }

    private void addContent(PdfWriter docWriter, Document document, Font catFontBold, Font titleFont,
                            Font smallBold, Font smallNormal, Font normal) {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case AppConstant.MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE:

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    cameraIntent();
                } else {
                    CommonUtil.checkAndRequestPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE, AppConstant.MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
                }

                break;

            case AppConstant.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    customGalleryIntent();
                } else {
                    CommonUtil.checkAndRequestPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE, AppConstant.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }

                break;

            case AppConstant.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE_SINGLE_PICKER:

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    gallerySinglePickerIntent();
                } else {
                    CommonUtil.checkAndRequestPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE, AppConstant.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }

                break;

            case AppConstant.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE_MULTIPLE_PICKER:

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    galleryMultiplePickerIntent();
                } else {
                    CommonUtil.checkAndRequestPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE, AppConstant.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }

                break;
        }
    }

    public void cameraIntent() {

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(getExternalCacheDir(), String.valueOf(System.currentTimeMillis()) + ".jpg");
        fileUri = Uri.fromFile(file);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        //check is there any app available to do this
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(cameraIntent, REQUEST_CAMERA_CODE);
        }
    }

    private void gallerySinglePickerIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_SINGLE_PICK_IMAGE_CODE);
    }

    private void galleryMultiplePickerIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_MULTIPLE_PICK_IMAGE_CODE);
    }

    public void shareText(View view) {
        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.setType("text/plain");
        String shareBodyText = "Your sharing message goes here";
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject/Title");
        intent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
        startActivity(Intent.createChooser(intent, "Choose sharing method"));
    }


    private void customGalleryIntent() {
        Intent intent = new Intent(getApplicationContext(), CustomGallery.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_CAMERA_CODE:

                String getData = getImageContentUri(getApplicationContext(),
                        new File((fileUri + "").substring(7, (fileUri + "").length()))) + "";

                Intent intent = new Intent(getApplicationContext(), CameraImageView.class);
                intent.putExtra("image", getData);
                startActivity(intent);

                break;

            case REQUEST_SINGLE_PICK_IMAGE_CODE:

                if (data != null) {

                    Intent singleIntent = new Intent(getApplicationContext(), SingleImageActivity.class);
                    singleIntent.putExtra("image", data.getData() + "");
                    startActivity(singleIntent);
                }

                break;

            case REQUEST_MULTIPLE_PICK_IMAGE_CODE:


                if (data == null) {
                    System.out.println("");
                    return;
                }

                if (data.getData() != null) {

                    /*Send PDf*/
                    String pdfData = data.getData().toString();
                    String temp[] = pdfData.split("content://com.estrongs.files");

                    String fullPath = temp[temp.length - 1];

                    if (fullPath.equalsIgnoreCase(".pdf")) {
                        pdfData(Uri.parse("file://" + fullPath));
                    } else {
                        /*Single Image*/
                        if (data != null) {
                            if (pdfData.contains("content://com.android.providers.media")) {
                                String path = RealPathUtil.getRealPathFromURI_API19(this, Uri.parse(pdfData));

                                Log.v("path", path + "");

                                Intent singleDataIntent = new Intent(getApplicationContext(), ShowImageActivity.class);
                                singleDataIntent.putExtra("list", path);
                                startActivity(singleDataIntent);

                            } else {
                                String path = getRealPathFromURI(Uri.parse(pdfData));

                                Log.v("path", path + "");

                                Intent multipleIntent = new Intent(getApplicationContext(), ShowImageActivity.class);
                                multipleIntent.putExtra("list", path);
                                startActivity(multipleIntent);

                            }
                        }
                    }
                } else {
                    /*Send Multiple Images*/
                    ArrayList<Images> imagesArrayList = new ArrayList<>();
                    String imagePath = null;

                    if (data != null) {
                        ClipData clipData = data.getClipData();

                        if (clipData != null) {
                            for (int i = 0; i < clipData.getItemCount(); i++) {
                                ClipData.Item item = clipData.getItemAt(i);
                                Uri uri = item.getUri();
                                Log.v("uri", uri + "");

                                //In case you need image's absolute path
                                if (Build.VERSION.SDK_INT < 11)
                                    imagePath = RealPathUtil.getRealPathFromURI_BelowAPI11(this, uri);

                                    // SDK >= 11 && SDK < 19
                                else if (Build.VERSION.SDK_INT < 19)
                                    imagePath = RealPathUtil.getRealPathFromURI_API11to18(this, uri);

                                    // SDK > 19 (Android 4.4)
                                else
                                    imagePath = RealPathUtil.getRealPathFromURI_API19(this, uri);


                                Log.v("path", imagePath + "");
                                imagesArrayList.add(new Images(imagePath));
                            }
                        }
                    }
                    Intent multipleIntent = new Intent(getApplicationContext(), ShowImageActivity.class);
                    multipleIntent.putExtra("imagesArrayList", imagesArrayList);
                    startActivity(multipleIntent);
                }

                break;

            case REQUEST_CUSTOM_GALLERY_CODE:

                break;

            case REQUEST_MULTIPLE_PICK_DATA_TO_SHARE:

                if (data == null) {
                    return;
                }

                if (data.getData() != null) {

                    /*Send PDf*/
                    String pdfData = data.getData().toString();
                    String temp[] = pdfData.split("content://com.estrongs.files");

                    String fullPath = temp[temp.length - 1];

                    if (fullPath.equalsIgnoreCase(".pdf")) {
                        pdfData(Uri.parse("file://" + fullPath));
                    } else {
                        /*Single Image*/
                        if (data != null) {
                            if (pdfData.contains("content://com.android.providers.media.documents")) {
                                String path = RealPathUtil.getRealPathFromURI_API19(this, Uri.parse(pdfData));

                                Log.v("path", path + "");
                                singleData(Uri.parse("file://" + path));
                            } else {
                                String path = getRealPathFromURI(Uri.parse(pdfData));

                                Log.v("path", path + "");
                                singleData(Uri.parse("file://" + path));
                            }
                        }
                    }
                } else {
                    /*Send Multiple Images*/
                    ArrayList<Uri> dataList = new ArrayList<>();

                    if (data != null) {
                        ClipData clipData = data.getClipData();
                        if (clipData != null) {
                            for (int i = 0; i < clipData.getItemCount(); i++) {
                                ClipData.Item item = clipData.getItemAt(i);
                                Uri uri = item.getUri();
                                Log.v("uri", uri + "");

                                //In case you need image's absolute path
                                String path;
                                if (Build.VERSION.SDK_INT < 11)
                                    path = RealPathUtil.getRealPathFromURI_BelowAPI11(this, uri);

                                    // SDK >= 11 && SDK < 19
                                else if (Build.VERSION.SDK_INT < 19)
                                    path = RealPathUtil.getRealPathFromURI_API11to18(this, uri);

                                    // SDK > 19 (Android 4.4)
                                else
                                    path = RealPathUtil.getRealPathFromURI_API19(this, uri);


                                Log.v("path", path + "");
                                dataList.add(Uri.parse("file://" + path));
                            }
                        }
                    }
                    listDataToShare(dataList);
                }
                break;
        }
    }

    private void pdfData(Uri parse) {

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("*/*");
//        shareIntent.setType("application/pdf");
        shareIntent.putExtra(Intent.EXTRA_STREAM, parse);
        startActivity(Intent.createChooser(shareIntent, "Share Data"));

    }

    private void singleData(Uri parse) {

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("*/*");
        shareIntent.putExtra(Intent.EXTRA_STREAM, parse);
        startActivity(Intent.createChooser(shareIntent, "Share Data"));

    }

    private void btn_share_data() {

        Intent intent = new Intent();
        intent.setType("*/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(Intent.createChooser(intent, "Select Data"), REQUEST_MULTIPLE_PICK_DATA_TO_SHARE);
    }

    private void listDataToShare(ArrayList<Uri> dataList) {

        Intent shareIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
        shareIntent.setType("*/*");
        shareIntent.putExtra(Intent.EXTRA_STREAM, dataList);
        startActivity(Intent.createChooser(shareIntent, "Share Data"));

    }

    public static Uri getImageContentUri(Context context, File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
            cursor.close();
            return Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

}
