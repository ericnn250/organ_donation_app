package com.organdonation.ordon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import android.view.View;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.organdonation.ordon.adapters.DonorFragPagerAdapter;

public class DonorActivity extends AppCompatActivity {
    private Toolbar toolbar;

    private  TabLayout tabLayout;
    private  ViewPager viewPager;
    private DonorFragPagerAdapter pageAdapter;
    private  TabItem tabDonated;
    private  TabItem tabdonorwait;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor);
        toolbar=(Toolbar)  findViewById(R.id.toolbar_donor) ;
        toolbar.setTitle(getResources().getString(R.string.donorslist));


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tabLayout = findViewById(R.id.tabLayout2donor);
        tabDonated = findViewById(R.id.tabdonated);
        tabdonorwait = findViewById(R.id.tabwaitdoner);
        viewPager = findViewById(R.id.viewpagerdonor);

        pageAdapter = new DonorFragPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pageAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
//                if (tab.getPosition() == 1) {
//                    toolbar.setBackgroundColor(ContextCompat.getColor(SignedInActivity.this,
//                            R.color.blue0));
////                    tabLayout.setBackgroundColor(ContextCompat.getColor(SignedInActivity.this,
////                            R.color.colorAccent));
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                        getWindow().setStatusBarColor(ContextCompat.getColor(SignedInActivity.this,
//                                R.color.blue0));
//                    }
//                } else if (tab.getPosition() == 2) {
//                    toolbar.setBackgroundColor(ContextCompat.getColor(SignedInActivity.this,
//                            R.color.blue0));
////                    tabLayout.setBackgroundColor(ContextCompat.getColor(SignedInActivity.this,
////                            android.R.color.darker_gray));
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                        getWindow().setStatusBarColor(ContextCompat.getColor(SignedInActivity.this,
//                                R.color.blue0));
//                    }
//                } else {
//                    toolbar.setBackgroundColor(ContextCompat.getColor(SignedInActivity.this,
//                            R.color.blue0));
////                    tabLayout.setBackgroundColor(ContextCompat.getColor(SignedInActivity.this,
////                            R.color.colorPrimary));
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                        getWindow().setStatusBarColor(ContextCompat.getColor(SignedInActivity.this,
//                                R.color.blue0));
//                    }
//                }
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
