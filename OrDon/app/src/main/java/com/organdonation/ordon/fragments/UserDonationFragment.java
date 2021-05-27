package com.organdonation.ordon.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.organdonation.ordon.AssignmentManager;
import com.organdonation.ordon.R;

import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.organdonation.ordon.ViewUser;
import com.organdonation.ordon.adapters.UserDonAdapter;
import com.organdonation.ordon.models.Doctor;
import com.organdonation.ordon.models.Donation;
import com.organdonation.ordon.models.OrganRequests;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class UserDonationFragment extends Fragment     {
    private RecyclerView mDonationListView;
    private ArrayList<Donation> userDonationList;
    private UserDonAdapter userDonAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public  static final  String TAG="UserRequestFrg";
    public String id;
    public String dnames,dononames;
    public UserDonationFragment() {
        // Required empty public constructor
    }

//    public interface FragmentUserDListener {
//        void onInputUserDSent(CharSequence input);
//    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

       // id=this.getArguments().getString("uid_don");
//        ((ViewUser) getActivity()).passVal(new UserIdProvider() {
//            @Override
//            public void onGetUserId(String input) {
//                id=input;
//            }
//
//        });
        return inflater.inflate(R.layout.fragment_user_donation, container, false);
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        AssignmentManager am=new AssignmentManager(view.getContext());
//        if (!am.getUserId().equals("")){
//            id= am.getUserId();
//        }else{
//        }

        mDonationListView=(RecyclerView) view.findViewById(R.id.userdonationfrg_recyclerview);
        userDonationList=new ArrayList<>();
        mDonationListView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(view.getContext());
        userDonAdapter = new UserDonAdapter(userDonationList);

        mDonationListView.setLayoutManager(mLayoutManager);
        mDonationListView.setAdapter(userDonAdapter);

        Toast.makeText(view.getContext(),"From Donation fragemnt "+ id, Toast.LENGTH_SHORT).show();
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
                    String userid = singleSnapshot.getValue(Donation.class).getUserid();
                    if(userid.equals(id)){
                        //Log.d(TAG, "onDataChange: Request is received.");
                        Donation requestswait = singleSnapshot.getValue(Donation.class);
//                      getDoctorName(requestswait.getDoctorid());
//                       getRecipientName(requestswait.getRecipientid());
//                        requestswait.setDoctorid(dnames);
//                        requestswait.setRecipientid(dononames);
                        Log.d(TAG, "onDataChanges: ." +requestswait.toString());
                        userDonationList.add(requestswait);

                    }

                }
                userDonAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    private void getRecipientName(String donorid) {
        Log.d(TAG, "getUserAccountData: getting the user's account information");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        /*
            ---------- QUERY Method 1 ----------
         */
        Query query1 = reference.child(getString(R.string.dbnode_organrequest))
                .orderByKey()
                .equalTo(donorid);
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //this loop will return a single result
                for(DataSnapshot singleSnapshot: dataSnapshot.getChildren()){
                    // Log.d(TAG, "onDataChange: (QUERY METHOD 1) found user: "
                    //      + singleSnapshot.getValue(Donation.class).toString());
                    dononames= singleSnapshot.getValue(OrganRequests.class).getNames();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Log.d(TAG, "onDataChange: found donor: "
                + dononames);

    }
    private void getDoctorName(final String doctorid) {
        Log.d(TAG, "getUserAccountData: getting the user's account information");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        /*
            ---------- QUERY Method 1 ----------
         */
        Query query1 = reference.child(getString(R.string.dbnode_doctors));
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //this loop will return a single result
                for(DataSnapshot singleSnapshot: dataSnapshot.getChildren()){
                    Log.d(TAG, "onDataChange: (QUERY METHOD 1) found user: "
                            + singleSnapshot.getValue(Doctor.class).toString());
                    Doctor doctor =singleSnapshot.getValue(Doctor.class);
                    if (doctor.getDoctor_id().equals(doctorid)){

                        dnames = doctor.getNames();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Log.d(TAG, "onDataChange:  found doctor: "
                + dnames);
       // return dnames;
    }

//    @Override
//    public void onGetUserId(String input) {
//        id=input;
//    }
}

