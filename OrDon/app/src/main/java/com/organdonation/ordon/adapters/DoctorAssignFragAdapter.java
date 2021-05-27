package com.organdonation.ordon.adapters;


import com.organdonation.ordon.fragments.DoctorToAssignFragment;
import com.organdonation.ordon.fragments.DonorToAssignFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class DoctorAssignFragAdapter extends FragmentPagerAdapter {
    private int numOfTabs;
    public DoctorAssignFragAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new DoctorToAssignFragment();
            case 1:
                return new DonorToAssignFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
