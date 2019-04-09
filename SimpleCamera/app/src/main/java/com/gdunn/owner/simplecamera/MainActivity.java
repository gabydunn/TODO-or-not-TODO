package com.gdunn.owner.simplecamera;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {

    ImageView imageview;
    public static String filePath;
    public static String fileName;
    Bitmap bitmap;
    private static final int FIND_IMAGE = 1;
    private static final int TAKE_IMAGE =2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageview = findViewById(R.id.imageview);
        filePath = Environment.getExternalStorageDirectory() + "/images/";
        fileName = "Example.jpg";
//        StrictMode.VmPolicy.Builder bulider = new StrictMode.VmPolicy.Builder();
//        StrictMode.setVmPolicy(bulider.build());
        if(Build.VERSION.SDK_INT>=24){
            try{
                Method m = StrictMode.class.getMethod("disableDeathOnUriExposure");
                m.invoke(null);
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }


    }
    public void onButtonClick(View v)
    {
        switch(v.getId())
        {
            case R.id.button_find_image:
            {
                Intent intent = new Intent();
                //set type to any image
                intent.setType("Image/*");
                //launch image folder
                intent.setAction(Intent.ACTION_GET_CONTENT);
                //filter to openable files
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent,FIND_IMAGE);
                break;
            }
            case R.id.button_take_image:
            {
                File file = new File(filePath + fileName);
                Uri fileUri = Uri.fromFile(file);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                startActivityForResult(intent, TAKE_IMAGE);
                break;
            }
            case R.id.imageview_button:
            {
                BitmapDrawable drawable = (BitmapDrawable) imageview.getDrawable();
                Bitmap image = drawable.getBitmap();
                WallpaperManager manager = WallpaperManager.getInstance(this);
                try{
                    Toast.makeText(this, "Wallpaper changed", Toast.LENGTH_LONG).show();
                    manager.setBitmap(image);
                }
                catch (IOException ioe)
                {
                    Toast.makeText(this, "ERROR: " + ioe, Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {

        if(resultCode == Activity.RESULT_OK && requestCode == FIND_IMAGE)
        {
            try
            {
                if(bitmap != null)
                {
                    bitmap.recycle();
                }
                InputStream stream = getContentResolver().openInputStream(intent.getData());
                bitmap = BitmapFactory.decodeStream(stream);
                stream.close();
                imageview.setImageBitmap(bitmap);

            }
            catch(Exception e)
            {
                //log
            }
        }
        else if (resultCode == Activity.RESULT_OK && requestCode == TAKE_IMAGE)
        {
            BitmapFactory.Options options = new BitmapFactory.Options();
            //fraction the size of the image (4 = 1/4 scale)
            options.inSampleSize = 4;

            String completeFileName = filePath + fileName;
            Bitmap bitMap = BitmapFactory.decodeFile(completeFileName, options);
            imageview.setImageBitmap(bitMap);

        }
        super.onActivityResult(requestCode, resultCode, intent);
    }
}
