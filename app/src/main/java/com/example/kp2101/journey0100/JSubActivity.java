package com.example.kp2101.journey0100;



import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;


/**
 * Created by TonyLabNew on 2016/1/8.
 */
public class JSubActivity extends AppCompatActivity {
    String jId;
    String jName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jsub_main);

        //抓全域變數uId
        GlobalVariable globalVariable = (GlobalVariable)getApplicationContext();

        //抓上一個activity傳來的jId
        jId = getIntent().getExtras().getString("jId");
        jName = getIntent().getExtras().getString("jName");

        //把jId傳到fragment，用bundle綁
        Bundle bundle = new Bundle();
        bundle.putString("jId",jId);

        //Set Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(jName);

        //Set Tabbar
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("成員"));
        tabLayout.addTab(tabLayout.newTab().setText("消費"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount(), bundle);//多傳bundle
        viewPager.setAdapter(adapter);


        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });





    }
}
