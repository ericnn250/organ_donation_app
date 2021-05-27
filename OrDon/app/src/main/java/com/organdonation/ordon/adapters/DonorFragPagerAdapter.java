package com.organdonation.ordon.adapters;

import com.organdonation.ordon.fragments.DonatedFragment;
import com.organdonation.ordon.fragments.DonorWaitFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class DonorFragPagerAdapter extends FragmentPagerAdapter {
        private int numOfTabs;
    public DonorFragPagerAdapter(FragmentManager fm, int numOfTabs) {
            super(fm);
            this.numOfTabs = numOfTabs;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new DonatedFragment();
                case 1:
                    return new DonorWaitFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return numOfTabs;
        }
    }
