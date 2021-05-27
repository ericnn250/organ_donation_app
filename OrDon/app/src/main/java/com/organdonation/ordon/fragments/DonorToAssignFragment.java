package com.organdonation.ordon.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.organdonation.ordon.AssignDoctor;
import com.organdonation.ordon.R;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.organdonation.ordon.adapters.DonorWaitAdapter;
import com.organdonation.ordon.models.Donation;

import java.util.ArrayList;

public class DonorToAssignFragment extends Fragment {
    private static final String TAG = "DonorWaitActivity";
    //wigdet
    private RecyclerView DonorWaitdoctorListView;
    //  varable
    private ArrayList<Donation> donorswaitList;
    private DonorWaitAdapter mDonorWaitAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public DonorToAssignFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_donor_to_assign, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DonorWaitdoctorListView=(RecyclerView) view.findViewById(R.id.recyclerViewAssigndonor);
        donorswaitList=new ArrayList<>();
        DonorWaitdoctorListView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(view.getContext());
        mDonorWaitAdapter = new DonorWaitAdapter(donorswaitList);

        DonorWaitdoctorListView.setLayoutManager(mLayoutManager);
        DonorWaitdoctorListView.setAdapter(mDonorWaitAdapter);
        getUserAccountData();
        mDonorWaitAdapter.setOnItemClickListener(new DonorWaitAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(view.getContext(),donorswaitList.get(position).getNames(),Toast.LENGTH_LONG).show();
                AssignDoctor assignDoctor=(AssignDoctor) getActivity();
                assignDoctor.assigndonor(donorswaitList.get(position).getNames(),donorswaitList.get(position).getDonationid(),donorswaitList.get(position).getUserid(),donorswaitList.get(position).getOrgantype());


            }
        });
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
                    if( status.equals(getContext().getString(R.string.status_pending))){
                        Log.d(TAG, "onDataChange: donor is waiting.");
                        Donation donationwait = singleSnapshot.getValue(Donation.class);
                        donorswaitList.add(donationwait);
                    }

                }
                mDonorWaitAdapter.notifyDataSetChanged();
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
}

