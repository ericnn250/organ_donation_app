package com.organdonation.ordon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.organdonation.ordon.adapters.FeedAdapter;
import com.organdonation.ordon.adapters.UserAdapter;
import com.organdonation.ordon.models.Contact;
import com.organdonation.ordon.models.User;

import java.util.ArrayList;

public class FeedBack extends AppCompatActivity {
    private ProgressBar mProgressBar;
    private Toolbar toolbar;
    private RecyclerView feedListView;
    private ArrayList<Contact> feedList;
    private FeedAdapter mFeedAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private static final String TAG = "FeedBackActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        toolbar =  findViewById(R.id.toolbar_feedback);
        feedListView=(RecyclerView)findViewById(R.id.recyclerview_feedback) ;
        mProgressBar=(ProgressBar) findViewById(R.id.progressBar_feedback);
        toolbar.setTitle(getResources().getString(R.string.app_name));


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
        feedList=new ArrayList<>();
        feedListView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mFeedAdapter = new FeedAdapter(feedList);

        feedListView.setLayoutManager(mLayoutManager);
        feedListView.setAdapter(mFeedAdapter);
        getFeedData();
    }
    private void showDialog(){
        mProgressBar.setVisibility(View.VISIBLE);

    }

    private void hideDialog(){
        if(mProgressBar.getVisibility() == View.VISIBLE){
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }
    private void getFeedData(){
        Log.d(TAG, "getUserAccountData: getting the user's account information");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        /*
            ---------- QUERY Method 1 ----------
         */
        Query query1 = reference.child(getString(R.string.dbnode_contactus));

        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //this loop will return a single result
                for(DataSnapshot singleSnapshot: dataSnapshot.getChildren()){
                    Log.d(TAG, "onDataChange: (QUERY METHOD 1) found user: "
                            + singleSnapshot.getValue(Contact.class).toString());
                    Contact contact = singleSnapshot.getValue(Contact.class);
                    feedList.add(contact);

                }
                mFeedAdapter.notifyDataSetChanged();
                hideDialog();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        //  mEmail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
    }
}