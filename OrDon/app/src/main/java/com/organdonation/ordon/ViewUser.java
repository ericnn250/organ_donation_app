package com.organdonation.ordon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.organdonation.ordon.adapters.UserDetailsFragAdapter;
import com.organdonation.ordon.dialog.TMessage;
import com.organdonation.ordon.fragments.UserDonationFragment;
import com.organdonation.ordon.fragments.UserIdProvider;
import com.organdonation.ordon.fragments.UserRequestsFragment;
import com.organdonation.ordon.models.User;

public class ViewUser extends AppCompatActivity  {
///implements UserDonationFragment.FragmentUserDListener  ,UserRequestsFragment.FragmentUserRListener
    private static final String TAG="ViewUserActivity";
    private static final String NOT_ACTIVE = "no";
    private String userid;
    private TextView names,email,phone,adres,dob;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabItem tabrequest;
    private  TabItem tabdonation;
    private UserDetailsFragAdapter pageAdapter;
    private Toolbar toolbar;
    private UserIdProvider provider;
//    public interface UserIdProvider {
//        void onGetUserId(String input);
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user);
        toolbar=(Toolbar) findViewById(R.id.toolbar9_view_user);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent user=getIntent();
        userid=user.getStringExtra("userid");
//        AssignmentManager assignmentManager=new AssignmentManager(ViewUser.this);
//        assignmentManager.assignUser(userid);

        names=(TextView) findViewById(R.id.textView_username);
        email=(TextView) findViewById(R.id.textVieweuseremail);
        phone=(TextView) findViewById(R.id.textVieweusercontact);
        adres=(TextView) findViewById(R.id.textVieweuseradres);
        dob=(TextView) findViewById(R.id.textVieweuserdob);

        tabrequest = findViewById(R.id.tabreq);
        tabdonation = findViewById(R.id.tabdon);
        tabLayout=(TabLayout)findViewById(R.id.tabLayout_user_datails);
        viewPager=(ViewPager)findViewById(R.id.viewpager_userdetails);
        //Toast.makeText(this,"From activity"+userid,Toast.LENGTH_LONG).show();
        ///
//        Bundle bundle_req = new Bundle();
//        bundle_req .putString("uid_req", userid);
//        Bundle bundle_don = new Bundle();
//        bundle_don.putString("uid_don", userid);
//        UserRequestsFragment userRequestsFragment=new UserRequestsFragment();
//        userRequestsFragment.setArguments(bundle_req );
//        UserDonationFragment userDonationFragment=new UserDonationFragment();
//        userDonationFragment.setArguments(bundle_don);

        ///
//        context=ViewUser.this;
//        try {
//            provider= (UserIdProvider) context;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(ViewUser.this.toString() +
//                    "must implement Interfaces UserIdProvider");
//        }
        getUserAccountData();
        pageAdapter = new UserDetailsFragAdapter(getSupportFragmentManager(), tabLayout.getTabCount(),userid);
        viewPager.setAdapter(pageAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
//     provider=(UserIdProvider) this;
//       provider.onGetUserId(userid);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
    public void passVal(UserIdProvider fragmentCommunicator) {
        this.provider = fragmentCommunicator;

    }
    private Menu menudelete;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflator=getMenuInflater();
        inflator.inflate(R.menu.viewusermenu,menu);
        this.menudelete=menu;
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.deletemenu:
                deleteUser();
                return true;
            case R.id.makehima_admin:
                makeAdmin();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void makeAdmin() {
        if(userid != null){
            Log.d(TAG, "onClick: deleting Doctor: " + userid);

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
            reference.child(getString(R.string.dbnode_users))
                    .child(userid)
                    .child(getString(R.string.field_security_level))
                    .setValue("10");
            new TMessage(ViewUser.this,"Now he is admin").show();
            startActivity(new Intent(ViewUser.this,UserActivity.class));
            this.finish();

        }
    }

    private void deleteUser() {
        if(userid != null){
            Log.d(TAG, "onClick: deleting Doctor: " + userid);

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
            reference.child(getString(R.string.dbnode_users))
                    .child(userid)
                    .child(getString(R.string.field_role))
                    .setValue(NOT_ACTIVE);
            new TMessage(ViewUser.this,"Deleted").show();
            startActivity(new Intent(ViewUser.this,UserActivity.class));
            this.finish();

        }
    }

    private void getUserAccountData(){
        Log.d(TAG, "getUserAccountData: getting the user's account information");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        /*
            ---------- QUERY Method 1 ----------
         */
        Query query1 = reference.child(getString(R.string.dbnode_users))
                .orderByKey()
                .equalTo(userid);
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //this loop will return a single result
                for(DataSnapshot singleSnapshot: dataSnapshot.getChildren()){
                    Log.d(TAG, "onDataChange: (QUERY METHOD 1) found user: "
                            + singleSnapshot.getValue(User.class).toString());
                    User user = singleSnapshot.getValue(User.class);
                    names.setText(user.getName());
                    phone.setText(user.getPhone());
                    email.setText(user.getEmail());
                    adres.setText(user.getAddress());
                    dob.setText(user.getDob());

                    toolbar.setTitle(user.getName());
//                    if (user.getRole().equals(NOT_ACTIVE)){
//                        menudelete.getItem(0).setIcon(ContextCompat.getDrawable(ViewUser., R.drawable.delete_forever));
//                    }

                    // ImageLoader.getInstance().displayImage(user.getProfile_image(), mProfileImage);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }


}

