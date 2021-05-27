package com.organdonation.ordon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.organdonation.ordon.adapters.MyDonationAdapter;
import com.organdonation.ordon.models.Donation;

import java.util.ArrayList;

public class PreviousDonationActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private static final String TAG = "PreviousR_O_A";
    private RecyclerView recyclerViewDonation;
    //  varable
    private ArrayList<Donation> myDonationList;
    private MyDonationAdapter mMyDonationrAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_donation);
        toolbar =  findViewById(R.id.toolbar6);
        recyclerViewDonation=(RecyclerView) findViewById(R.id.recyclerViewDonation) ;
        progressBar=(ProgressBar) findViewById(R.id.progressBar_donation) ;
        toolbar.setTitle("Recently Donated Organ");


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //  setupPDonationList();
        showDialog();
        myDonationList=new ArrayList<>();
        recyclerViewDonation.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mMyDonationrAdapter = new MyDonationAdapter(myDonationList);

        recyclerViewDonation.setLayoutManager(mLayoutManager);
        recyclerViewDonation.setAdapter(mMyDonationrAdapter);
        getPDonationList();
        mMyDonationrAdapter.setOnItemClickListener(new MyDonationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent=new Intent(PreviousDonationActivity.this,UserSingleDonation.class);
                intent.putExtra("donation_id",myDonationList.get(position).getDonationid());
                startActivity(intent);

            }
        });
    }
    /**
     * Get a list of all Donations
     * @throws NullPointerException
     */
    private void getPDonationList() throws NullPointerException{
        Log.d(TAG, "getPrevoiusDonationList: getting a list of all Donation");
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        Query query = reference.child(getString(R.string.dbnode_organdonation));

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Donation mydonations = snapshot.getValue(Donation.class);
                    Log.d(TAG, "getPrevoiusDonationList: found donation" +mydonations.toString());
                    if (mydonations.getUserid().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                        myDonationList.add(mydonations);
                    }
                }
                mMyDonationrAdapter.notifyDataSetChanged();
                hideDialog();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /**
     * Setup the list of employees
     */
    private void setupPDonationList(){
        myDonationList=new ArrayList<>();
        mMyDonationrAdapter = new MyDonationAdapter(myDonationList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewDonation.setLayoutManager(layoutManager);
        recyclerViewDonation.setAdapter(mMyDonationrAdapter);
    }
    private void getUserAccountData(){
        Log.d(TAG, "getUserAccountData: getting the user's account information");
        myDonationList=new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        /*
            ---------- QUERY Method 1 ----------
         */
        Query query1 = reference.child(getString(R.string.dbnode_organdonation))
                .orderByKey()
                .equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());;
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //this loop will return a single result
                for(DataSnapshot singleSnapshot: dataSnapshot.getChildren()){
                    Log.d(TAG, "onDataChange: (QUERY METHOD 1) found user: "
                            + singleSnapshot.getValue(Donation.class).toString());
                    Donation mydonations = singleSnapshot.getValue(Donation.class);
                    myDonationList.add(mydonations);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        /*
            ---------- QUERY Method 2 ----------
         */
        Query query2 = reference.child(getString(R.string.dbnode_users))
                .orderByChild(getString(R.string.field_user_id))
                .equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());
        query2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //this loop will return a single result
                for(DataSnapshot singleSnapshot: dataSnapshot.getChildren()){
                    Log.d(TAG, "onDataChange: (QUERY METHOD 2) found user: "
                            + singleSnapshot.getValue(Donation.class).toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //  mEmail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
    }
    private void showDialog() {
        progressBar.setVisibility(View.VISIBLE);
    }
    private void hideDialog() {
        if(progressBar.getVisibility() == View.VISIBLE){
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
}

