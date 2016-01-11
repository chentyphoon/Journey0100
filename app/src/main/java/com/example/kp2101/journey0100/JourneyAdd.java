package com.example.kp2101.journey0100;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.login.LoginManager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.List;

/**
 * Created by KP2101 on 2016/1/7.
 */
public class JourneyAdd  extends AppCompatActivity {


    Button btnAddJ;
    //ImageButton imageButton;
    ImageView ivAddPic;
    EditText edtJName;
    String jName;
    GlobalVariable globalVariable;
    Uri imageUri = null;
    //Bitmap photo = null;
    //private String postUrl = "http://140.112.107.30/WebProject/journey/upload.php";
    private String fileName="journey-";
    private FileInputStream inputImage = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.journey_add);
        globalVariable = (GlobalVariable) getApplicationContext();
        ivAddPic = (ImageView) findViewById(R.id.ivAddPic);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("新增旅程");

        btnAddJ = (Button) findViewById(R.id.btnAddJ);
        edtJName = (EditText) findViewById(R.id.edtJName);

        ivAddPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivityForResult(ImageSelector.select(), ImageSelector.SELECT_PHOTO);

//                Intent photoIntent = new Intent(Intent.ACTION_PICK);
//                photoIntent.setType("image/*");
//                startActivityForResult(photoIntent, SELECT_PHOTO);
            }
        });


        btnAddJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jName = edtJName.getText().toString();
                Log.d("jname=", jName);
                String id = JourneyDB.addJourney(jName, globalVariable.uId);
                Toast.makeText(JourneyAdd.this, "新增成功", Toast.LENGTH_LONG).show();
                try {
                    inputImage = (FileInputStream) getContentResolver().openInputStream(imageUri);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                ImageUploader imageUploader = new ImageUploader(fileName);
                imageUploader.uploadFile(id, inputImage);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ImageSelector.SELECT_PHOTO && resultCode == RESULT_OK) {
            Log.d("data", data.getDataString());
            imageUri = data.getData();
            ivAddPic.setImageURI(imageUri);
            //startActivityForResult(ImageSelector.doCrop(imageUri), ImageSelector.CROP_OK);
            //test
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                imageUri = ImageSelector.bitmapToFile(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //test end
            //reduceImage();

            //ivPhoto.setImageURI(imageUri);
        }else {
            if (requestCode == ImageSelector.CROP_OK) {
                Bundle extras = data.getExtras();
                if (extras != null) {
                    Bitmap photo = (Bitmap) extras.get("data");
                    ivAddPic.setImageBitmap(photo);
                    imageUri = ImageSelector.bitmapToFile(photo);

                }
                //imageUri = data.getData();


            }
        }
    }




//    private void doCropPhoto(Bitmap bmp) {
//        Intent intent = getCropImageIntent(bmp);
//        startActivityForResult(intent, 1);
//    }
//
//    public static Intent getCropImageIntent(Bitmap data) {
//        Intent intent = new Intent("com.android.camera.action.CROP");
//        intent.setType("image/*");
//        intent.putExtra("data", data);
//        intent.putExtra("crop", "true");// crop=true 有這句才能叫出裁剪頁面.
//        intent.putExtra("aspectX", 1);// 这兩項為裁剪框的比例.
//        intent.putExtra("aspectY", 1);// x:y=1:1
//        intent.putExtra("outputX", 90);//回傳照片比例X
//        intent.putExtra("outputY", 90);//回傳照片比例Y
//        intent.putExtra("return-data", true);
//        return intent;
//    }

//    public static Bitmap scaleImage(InputStream is, Uri photoUri) throws IOException {
//        BitmapFactory.Options dbo = new BitmapFactory.Options();
//        dbo.inJustDecodeBounds = true;
//        BitmapFactory.decodeStream(is, null, dbo);
//        is.close();
//
//        int rotatedWidth, rotatedHeight;
//        int orientation = getOrientation(context, photoUri);
//
//        if (orientation == 90 || orientation == 270) {
//            rotatedWidth = dbo.outHeight;
//            rotatedHeight = dbo.outWidth;
//        } else {
//            rotatedWidth = dbo.outWidth;
//            rotatedHeight = dbo.outHeight;
//        }
//
//        Bitmap srcBitmap;
//        is = context.getContentResolver().openInputStream(photoUri);
//        if (rotatedWidth > MAX_IMAGE_DIMENSION || rotatedHeight > MAX_IMAGE_DIMENSION) {
//            float widthRatio = ((float) rotatedWidth) / ((float) MAX_IMAGE_DIMENSION);
//            float heightRatio = ((float) rotatedHeight) / ((float) MAX_IMAGE_DIMENSION);
//            float maxRatio = Math.max(widthRatio, heightRatio);
//
//            // Create the bitmap from file
//            BitmapFactory.Options options = new BitmapFactory.Options();
//            options.inSampleSize = (int) maxRatio;
//            srcBitmap = BitmapFactory.decodeStream(is, null, options);
//        } else {
//            srcBitmap = BitmapFactory.decodeStream(is);
//        }
//        is.close();
//
//    /*
//     * if the orientation is not 0 (or -1, which means we don't know), we
//     * have to do a rotation.
//     */
//        if (orientation > 0) {
//            Matrix matrix = new Matrix();
//            matrix.postRotate(orientation);
//
//            srcBitmap = Bitmap.createBitmap(srcBitmap, 0, 0, srcBitmap.getWidth(),
//                    srcBitmap.getHeight(), matrix, true);
//        }
//
//        String type = context.getContentResolver().getType(photoUri);
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        if (type.equals("image/png")) {
//            srcBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
//        } else if (type.equals("image/jpg") || type.equals("image/jpeg")) {
//            srcBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//        }
//        byte[] bMapArray = baos.toByteArray();
//        baos.close();
//        return BitmapFactory.decodeByteArray(bMapArray, 0, bMapArray.length);
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch(id){
            case R.id.action_settings:
                return true;
            case R.id.action_account:
                Intent intent = new Intent(this, accountActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_logout:
                LoginManager.getInstance().logOut();
                Intent intent2 = new Intent(this, MainActivity.class);
                startActivity(intent2);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
