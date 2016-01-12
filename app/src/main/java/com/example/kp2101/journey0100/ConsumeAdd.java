package com.example.kp2101.journey0100;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by KP2101 on 2016/1/7.
 */
public class ConsumeAdd extends AppCompatActivity implements
        android.widget.CompoundButton.OnCheckedChangeListener {

    final int LOCATION_CODE = 777;
    Button btnAddC;
    EditText edtCName;
    EditText edtCDollar;
    TextView tvLoc;
    String cName;
    ImageView ivPic;
    int cDollar;
    String cLocation="";
    String cLon="";
    String cLat="";
    String cPic;
    String cDescrip="";
    Double lon;
    Double lat;
    GlobalVariable globalVariable;
    ShowMap mapFrag;
    Uri imageUri = null;
    LinearLayout llLocation;

    ListView lvMember;
    List<ConsumeMember> consumemembers = null;
    ConsumeMemberAdapter consumememberAdapter = null;
    Map<Integer, String> checkmember = new HashMap<Integer, String>();
    private LocationManager mLocationManager;
    private Location location;
    private String fileName="consume-";
    private FileInputStream inputImage = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.consume_add);


        globalVariable = (GlobalVariable) getApplicationContext();
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
//            public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
//
//            }
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        lon = location.getLongitude();
        lat = location.getLatitude();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("新增消費");

        btnAddC = (Button) findViewById(R.id.btnAddC);
        edtCName = (EditText) findViewById(R.id.edtCName);
        edtCDollar = (EditText) findViewById(R.id.edtCDollar);
        ivPic = (ImageView) findViewById(R.id.ivPic);
        //ivPic.setImageResource(android.R.drawable.ic_menu_gallery);
        llLocation = (LinearLayout) findViewById(R.id.llLocation);
        tvLoc = (TextView) findViewById(R.id.txtLoc);

        lvMember = (ListView) findViewById(R.id.lvMember);
        getConsumeMemberList();

//        mapFrag = ShowMap.newInstance(lat, lon);
//        getFragmentManager().beginTransaction()
//                .replace(R.id.flMap,mapFrag)
//                .commit();

        llLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             //   Log.d("JA getItem Jid",jId);
                Intent myIntent = new Intent(ConsumeAdd.this,LocationActivity.class);
                myIntent.putExtra("lon", String.valueOf(lon));
                myIntent.putExtra("lat", String.valueOf(lat));
                Log.d("intent", myIntent.toString());
                startActivityForResult(myIntent, LOCATION_CODE);

            }
        });


        ivPic.setImageResource(R.drawable.ic_insert_photo_white_48dp);

        ivPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivityForResult(ImageSelector.select(), ImageSelector.SELECT_PHOTO);

//                Intent photoIntent = new Intent(Intent.ACTION_PICK);
//                photoIntent.setType("image/*");
//                startActivityForResult(photoIntent, SELECT_PHOTO);
            }
        });
//        //點ListView觸發檢視行程
//        lvMember.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//            }
//        });

        //更改總金額觸發
        edtCDollar.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                setNeed();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });




        //點確定觸發新增消費
        btnAddC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cName = edtCName.getText().toString();
                if(edtCDollar.getText().equals("")){
                    cDollar=0;
                }else {
                    cDollar = Integer.parseInt(edtCDollar.getText().toString());
                }
                refreshList();



//                cLocation="";
//                cLon="";
//                cLat="";
//                cPic="";
//                cDescrip="";

                String cId=ConsumeDB.addConsume(globalVariable.jId, cName, cDollar,cLocation,cLon,cLat,cPic,cDescrip);
                ConsumeDB.addUserConsume(globalVariable.jId,cId,consumemembers);
                Toast.makeText(ConsumeAdd.this,"新增成功",Toast.LENGTH_LONG).show();

                try {
                    inputImage = (FileInputStream) getContentResolver().openInputStream(imageUri);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                ImageUploader imageUploader = new ImageUploader(fileName);
                imageUploader.uploadFile(cId, inputImage);
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
            //ivAddPic.setImageURI(imageUri);
            startActivityForResult(ImageSelector.doCrop(imageUri), ImageSelector.CROP_OK);
            //test
//            try {
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
//                imageUri = ImageSelector.bitmapToFile(bitmap);
//                ivPic.setImageURI(imageUri);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            //test end
            //reduceImage();

            //ivPhoto.setImageURI(imageUri);
        }else {
            if (requestCode == ImageSelector.CROP_OK) {
                Bundle extras = data.getExtras();
                if (extras != null) {
                    Bitmap photo = (Bitmap) extras.get("data");
                    imageUri = ImageSelector.bitmapToFile(photo);
                    ivPic.setImageURI(imageUri);
                }
                //imageUri = data.getData();


            }
        }

        if(requestCode==LOCATION_CODE){
            lat = Double.parseDouble(data.getExtras().getString("lat"));
            lon = Double.parseDouble(data.getExtras().getString("lon"));
            cLat = data.getExtras().getString("lat");
            cLon = data.getExtras().getString("lon");
            cLocation = data.getExtras().getString("location");
            tvLoc.setText(cLocation);

        }
    }

    //撈下半部member list
    public void getConsumeMemberList(){

        consumemembers = MemberDB.consumeMemberAddList(globalVariable.jId);
        consumememberAdapter = new ConsumeMemberAdapter(this, consumemembers);

        //建立自定Adapter物件
        lvMember.setAdapter(consumememberAdapter);


    }

    //抓有哪幾個member要攤錢
    @Override
    public void onCheckedChanged(CompoundButton buttonView,boolean isChecked){
        Log.d("onCheckedChanged", "onCheckedChanged");
        int pos = lvMember.getPositionForView(buttonView);

        if(pos != ListView.INVALID_POSITION){
            ConsumeMember consumemember = consumemembers.get(pos);
            Log.d("consumemember", consumemember.toString());
            consumemember.setSelected(isChecked);
            //Toast.makeText(this,"Selected:"+consumemember.getuId()+";status:"+isChecked,Toast.LENGTH_SHORT).show();

            //將checked的人存到map中
            if(isChecked){
                checkmember.put(Integer.valueOf(pos), consumemember.getuId());
            }else{
                checkmember.remove(Integer.valueOf(pos));

            }
            setNeed();
       }
    }






    //重新分配每個member的應付金額
    public void setNeed(){
        if(checkmember.size()!=0 && !edtCDollar.getText().toString().equals("")){


        //Log.d("checkmember", checkmember.toString());
        //Log.d("cDollar", edtCDollar.getText().toString());
        //Log.d("checkmember size", Integer.toString(checkmember.size()));


        //計算平均分攤
        cDollar = Integer.parseInt(edtCDollar.getText().toString());
        Log.d("cDollar", String.valueOf(cDollar));


        int remainder=cDollar%checkmember.size();
        Log.d("remainder", String.valueOf(remainder));

        int mean=(cDollar-remainder)/checkmember.size();
        Log.d("mean", String.valueOf(mean));




        //重設每個pos的應付金額
        for(int pos=0;pos<consumemembers.size();pos++) {

            if(checkmember.get(pos)!=null) {
                if(remainder!=0) {
                    consumemembers.get(pos).setNeed(String.valueOf(mean+1));
                    remainder--;
                }else{
                    consumemembers.get(pos).setNeed(String.valueOf(mean));
                }

            }else{
                consumemembers.get(pos).setNeed("0");
            }

        }
        refreshList();
        }


    }

    //更新list
    public void refreshList(){

        List<ConsumeMember> cmNew = new ArrayList<ConsumeMember>();
        cmNew.addAll(consumemembers);
        consumememberAdapter.updateReceiptsList(cmNew);
    }



}
