package com.skyfree.ring.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.skyfree.ring.R;
import com.skyfree.ring.fragment.FragmentMoreApp;
import com.skyfree.ring.fragment.FragmentRingTone;

/**
 * Created by KienBeu on 4/2/2018.
 */

public class PageAdapter extends FragmentStatePagerAdapter {

    private Context mContext;

    public PageAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment frag = null;
        switch (position){
            case 0:
                frag = new FragmentRingTone();
                break;
            case 1:
                frag = new FragmentMoreApp();
                break;
        }

        return frag;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position){
            case 0:
                title = mContext.getString(R.string.ring_tone);
                break;
            case 1:
                title = mContext.getString(R.string.more_app);
                break;
        }
        return title;
    }
}