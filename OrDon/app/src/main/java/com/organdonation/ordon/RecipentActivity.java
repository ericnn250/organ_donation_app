package com.organdonation.ordon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.organdonation.ordon.adapters.RecipientFragPagerAdapter;

public class RecipentActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private RecipientFragPagerAdapter pageAdapter;
    private TabItem tabreceived;
    private  TabItem tabrecipientwait;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipent);
        toolbar=(Toolbar)  findViewById(R.id.toolbar_recipients) ;
        toolbar.setTitle(getResources().getString(R.string.recipientslist));


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tabLayout = findViewById(R.id.tabLayoutrecipient);
        tabreceived = findViewById(R.id.recipientfinished);
        tabrecipientwait = findViewById(R.id.recipientwait);
        viewPager = findViewById(R.id.viewpager_recipient);

        pageAdapter = new RecipientFragPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
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
