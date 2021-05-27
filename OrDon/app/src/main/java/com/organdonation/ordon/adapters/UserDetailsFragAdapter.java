package com.organdonation.ordon.adapters;

import com.organdonation.ordon.fragments.UserDonationFragment;
import com.organdonation.ordon.fragments.UserRequestsFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class UserDetailsFragAdapter extends FragmentPagerAdapter {
    private int numOfTabs;
    private String id;
    public UserDetailsFragAdapter(FragmentManager fm, int numOfTabs,String id) {
        super(fm);
        this.numOfTabs=numOfTabs;
        this.id=id;

    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                UserRequestsFragment frReq=new UserRequestsFragment();
                frReq.setId(id);
                return frReq;
            case 1:
                UserDonationFragment frDon=new UserDonationFragment();
                                     frDon.setId(id);
                return frDon;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
