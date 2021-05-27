package com.organdonation.ordon.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.organdonation.ordon.R;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.organdonation.ordon.adapters.DonatedtAdapter;
import com.organdonation.ordon.models.Donation;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class DonatedFragment extends Fragment {

    private static final String TAG = "DonorWaitActivity";
    //wigdet
    private RecyclerView mDonatedListView;
    //  varable
    private ArrayList<Donation> donatedList;
    private DonatedtAdapter mDonatedAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public DonatedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_donated, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDonatedListView=(RecyclerView) view.findViewById(R.id.recyclerview_donated);
        donatedList=new ArrayList<>();
        mDonatedListView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(view.getContext());
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.donormenu, menu);
        MenuItem searchItem = menu.findItem(R.id.app_bar_search_donor);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Toast.makeText(getContext(),newText,Toast.LENGTH_SHORT).show();
                mDonatedAdapter.getFilter().filter(newText);
                mDonatedAdapter.notifyDataSetChanged();
                return false;
            }
        });

    }


}
