package com.example.kp2101.journey0100;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;
import java.util.List;


public class JourneyActivity extends AppCompatActivity {

    //撈JourneyList
    private ListView lvJourney;
    private List<Journey> journeys=null;
    private JourneyAdapter journeyAdapter=null;
    GlobalVariable globalVariable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.journey_main);



        //抓全域變數uId
        globalVariable = (GlobalVariable) getApplicationContext();
        //globalVariable.init();
        //globalVariable.init();
        globalVariable.uId = "4"; //chentyphoon@yahoo.com.tw

        globalVariable.initImageLoader();
        globalVariable.initCacheDir();
        //Set Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("旅程列表");

//        File cacheDir = StorageUtils.getCacheDirectory(this);
//        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
//                .memoryCacheExtraOptions(90, 90) // default = device screen dimensions
//                .diskCacheExtraOptions(480, 800, null)
//                .threadPoolSize(3) // default
//                .threadPriority(Thread.NORM_PRIORITY - 2) // default
//                .tasksProcessingOrder(QueueProcessingType.FIFO) // default
//                .denyCacheImageMultipleSizesInMemory()
//                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
//                .memoryCacheSize(2 * 1024 * 1024)
//                .memoryCacheSizePercentage(13) // default
//                .diskCache(new UnlimitedDiskCache(cacheDir)) // default
//                .diskCacheSize(50 * 1024 * 1024)
//                .diskCacheFileCount(100)
//                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
//                .imageDownloader(new BaseImageDownloader(this)) // default
//                .imageDecoder(new BaseImageDecoder(false))
//                .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
//                .writeDebugLogs()
//                .build();
//        Log.d("builder", "ok");
//        ImageLoader.getInstance().init(config);
//        Log.d("config", "ok");
        //撈JourneyList
        getJourneyList();

        //點右下角按鈕觸發新增行程
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabAddJourney);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                Intent callsub = new Intent();
                callsub.setClass(JourneyActivity.this,JourneyAdd.class);
                startActivity(callsub);

            }
        });

        //點ListView觸發檢視行程
        lvJourney.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Journey j = (Journey) journeyAdapter.getItem(position);
                String jId = j.getjId();
                String jName = j.getjName();
                Log.d("JA getItem Jid",jId);
                Intent myIntent = new Intent(JourneyActivity.this,JSubActivity.class);
                myIntent.putExtra("jId", jId);
                myIntent.putExtra("jName", jName);
                startActivity(myIntent);

            }
        });

        lvJourney.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Journey j = (Journey) journeyAdapter.getItem(position);
                String jId = j.getjId();
                JourneyDB.deleteJourney(jId);
                return false;
            }
        });



    }

//    private void initializeImageLoader() {
//
//        //ImageLoader.getInstance().init(globalVariable.config);
//        File cacheDir = StorageUtils.getCacheDirectory(this.getApplicationContext());
//        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this.getApplicationContext())
//                .memoryCacheExtraOptions(90, 90) // default = device screen dimensions
//                .diskCacheExtraOptions(480, 800, null)
//                .threadPoolSize(3) // default
//                .threadPriority(Thread.NORM_PRIORITY - 2) // default
//                .tasksProcessingOrder(QueueProcessingType.FIFO) // default
//                .denyCacheImageMultipleSizesInMemory()
//                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
//                .memoryCacheSize(2 * 1024 * 1024)
//                .memoryCacheSizePercentage(13) // default
//                .diskCache(new UnlimitedDiskCache(cacheDir)) // default
//                .diskCacheSize(50 * 1024 * 1024)
//                .diskCacheFileCount(100)
//                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
//                .imageDownloader(new BaseImageDownloader(this.getApplicationContext())) // default
//                .imageDecoder(new BaseImageDecoder(false))
//                .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
//                .writeDebugLogs()
//                .build();
//        ImageLoader.getInstance().init(config);
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onRestart() {
        getJourneyList();
        //Log.d("JA status","restart");
        super.onRestart();
    }

    @Override
    protected void onResume() {
        //Log.d("JA status","resume");
        super.onResume();
    }
    public void getJourneyList(){
        //initializeImageLoader();
        lvJourney = (ListView) findViewById(R.id.lvJourney);
        journeys = JourneyDB.journeyList(globalVariable.uId);
        journeyAdapter = new JourneyAdapter(this, journeys);
        //Log.d("get", journeys.toString());

        //建立自定Adapter物件

        lvJourney.setAdapter(journeyAdapter);
        //Log.d("adaper", "after");
        //journeyAdapter.registerDataSetObserver();
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if(journeyAdapter!=null){
//            journeyAdapter.destroy();
//        }
//    }
}
