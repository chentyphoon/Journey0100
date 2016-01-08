package com.example.kp2101.journey0100;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    private final Bundle atofdata;
    public PagerAdapter(FragmentManager fm, int NumOfTabs, Bundle data) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        atofdata = data;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                MemberActivity tab1 = new MemberActivity();
                tab1.setArguments(this.atofdata);
                return tab1;
            case 1:
                ConsumeActivity tab2 = new ConsumeActivity();
                tab2.setArguments(this.atofdata);
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}