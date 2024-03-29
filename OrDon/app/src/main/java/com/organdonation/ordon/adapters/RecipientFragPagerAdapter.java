package com.organdonation.ordon.adapters;

import com.organdonation.ordon.fragments.RecievedFragment;
import com.organdonation.ordon.fragments.RecieverWaitFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class RecipientFragPagerAdapter extends FragmentPagerAdapter {
    private int numOfTabs;
    public RecipientFragPagerAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new RecievedFragment();
            case 1:
                return new RecieverWaitFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
