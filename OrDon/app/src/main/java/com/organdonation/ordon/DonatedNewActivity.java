package com.organdonation.ordon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.organdonation.ordon.adapters.DonatedtAdapter;
import com.organdonation.ordon.models.Donation;

import java.util.ArrayList;

public class DonatedNewActivity extends AppCompatActivity {
    private static final String TAG = "DonorWaitActivity";
    //wigdet
    private Toolbar toolbar;
    private RecyclerView mDonatedListView;
    //  varable
    private ArrayList<Donation> donatedList=new ArrayList<>();
    private DonatedtAdapter mDonatedAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donated_new);

        toolbar=(Toolbar)findViewById(R.id.toolbarnewdonated);
        toolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(toolbar);
        mDonatedListView=(RecyclerView) findViewById(R.id.testsearchdonated);
        //donatedList=new ArrayList<>();
        mDonatedListView.setHasFixedSize(true);
        initRecyclerView();
    }

    private void initRecyclerView() {
        mLayoutManager = new LinearLayoutManager(DonatedNewActivity.this);
        mDonatedAdapter = new DonatedtAdapter(donatedList);

        mDonatedListView.setLayoutManager(mLayoutManager);
        mDonatedListView.setAdapter(mDonatedAdapter );
        getUserAccountData();
    }
    private void getUserAccountData(){
        Log.d(TAG, "getUserAccountData: getting the user's account information");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        /*
            ---------- QUERY Method 1 ----------
         */
        Query query1 = reference.child(getString(R.string.dbnode_organdonation));
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //this loop will return a single result
                for(DataSnapshot singleSnapshot: dataSnapshot.getChildren()){
                    Log.d(TAG, "onDataChange: (QUERY METHOD 1) found user: "
                            + singleSnapshot.getValue(Donation.class).toString());
                    String status = singleSnapshot.getValue(Donation.class).getStatus();
                    if( status.equals("DONE")){
                        Log.d(TAG, "onDataChange: donor is done.");
                        Donation donationwait = singleSnapshot.getValue(Donation.class);
                        donatedList.add(donationwait);
                    }

                }
                mDonatedAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.doctormenu, menu);
        MenuItem searchItem = menu.findItem(R.id.app_bar_search_doctor);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mDonatedAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);


    }
}