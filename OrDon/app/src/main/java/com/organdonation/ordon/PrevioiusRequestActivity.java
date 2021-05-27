package com.organdonation.ordon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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
import com.organdonation.ordon.adapters.MyRequestAdapter;
import com.organdonation.ordon.models.OrganRequests;

import java.util.ArrayList;

public class PrevioiusRequestActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ProgressBar mProgressBar;
    private RecyclerView recyclerView;
    private static final String TAG = "PreviousR_O_A";
    //  varable
    private ArrayList<OrganRequests> myRequestList;
    private MyRequestAdapter mMyRequestAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previoius_request);
        toolbar =  findViewById(R.id.toolbar5);
        recyclerView=(RecyclerView) findViewById(R.id.recyclerViewRa) ;

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar2);
        toolbar.setTitle("Recently Requested Organ");


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
        showDialog();
        myRequestList=new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mMyRequestAdapter = new MyRequestAdapter(myRequestList);

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mMyRequestAdapter);
        getUserAccountData();
        mMyRequestAdapter.setOnItemClickListener(new MyRequestAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent=new Intent(PrevioiusRequestActivity.this,UserSingleRequest.class);
                intent.putExtra("req_id",myRequestList.get(position).getRequestid());
                startActivity(intent);
            }
        });
    }

    private void showDialog() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void getUserAccountData(){
        Log.d(TAG, "getUserAccountData: getting the user's account information");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        /*
            ---------- QUERY Method 1 ----------
         */
        Query query1 = reference.child(getString(R.string.dbnode_organrequest));
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //this loop will return a single result
                for(DataSnapshot singleSnapshot: dataSnapshot.getChildren()){
                    Log.d(TAG, "onDataChange: (QUERY METHOD 1) found user: "
                            + singleSnapshot.getValue(OrganRequests.class).toString());
                    OrganRequests myrequests = singleSnapshot.getValue(OrganRequests.class);
                    if (myrequests.getUserid().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){

                        myRequestList.add(myrequests);
                        Log.d(TAG, "onDataChange: added user: "
                                + myrequests.toString());
                    }
                }
                mMyRequestAdapter.notifyDataSetChanged();
                hideDialog();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                hideDialog();
            }
        });


        /*
            ---------- QUERY Method 2 ----------
         */
//        Query query2 = reference.child(getString(R.string.dbnode_users))
//                .orderByChild(getString(R.string.field_user_id))
//                .equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());
//        query2.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                //this loop will return a single result
//                for(DataSnapshot singleSnapshot: dataSnapshot.getChildren()){
//                    Log.d(TAG, "onDataChange: (QUERY METHOD 2) found user: "
//                            + singleSnapshot.getValue(OrganRequests.class).toString());
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

        //  mEmail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
    }

    private void hideDialog() {
        if(mProgressBar.getVisibility() == View.VISIBLE){
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }
}
