package com.organdonation.ordon.fragments;

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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.organdonation.ordon.AssignmentManager;
import com.organdonation.ordon.R;
import com.organdonation.ordon.ViewUser;
import com.organdonation.ordon.adapters.UserReqAdapter;
import com.organdonation.ordon.models.Doctor;
import com.organdonation.ordon.models.Donation;
import com.organdonation.ordon.models.OrganRequests;

import java.util.ArrayList;

public class UserRequestsFragment extends Fragment {
    private RecyclerView mreceivedListView;
    private ArrayList<OrganRequests> receivedList;
    private UserReqAdapter userReqAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public  static final  String TAG="UserRequestFrg";
    public String id;
    public String dnames,dononames;
    public UserRequestsFragment() {
        // Required empty public constructor
    }

//    public interface FragmentUserRListener {
//        void onInputUserRSent(CharSequence input);
//    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
              //    id=this.getArguments().getString("uid_req");
//        ((ViewUser) getActivity()).passVal(new UserIdProvider() {
//            @Override
//            public void onGetUserId(String input) {
//                id=input;
//            }
//
//        });
        return inflater.inflate(R.layout.fragment_user_requests, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        AssignmentManager am=new AssignmentManager(view.getContext());
//        if (!am.getUserId().equals("")){
           // id= am.getUserId();
//        }else{
//        }

        mreceivedListView=(RecyclerView) view.findViewById(R.id.recyclerView_userrequest_frg);
        receivedList=new ArrayList<>();
        mreceivedListView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(view.getContext());
        userReqAdapter = new UserReqAdapter(receivedList);

        mreceivedListView.setLayoutManager(mLayoutManager);
        mreceivedListView.setAdapter(userReqAdapter);
        Toast.makeText(view.getContext(),"From Request fragemnt "+ id, Toast.LENGTH_SHORT).show();
        getUserAccountData();

    }

    public void setId(String id) {
        this.id = id;
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
                    String userid = singleSnapshot.getValue(OrganRequests.class).getUserid();
                    if(userid.equals(id)){
                        //Log.d(TAG, "onDataChange: Request is received.");
                        OrganRequests requestswait = singleSnapshot.getValue(OrganRequests.class);
//                        String dn=getDoctorName(requestswait.getDoctorid());
//                        String don=getDonorName(requestswait.getDonorid());
//                        requestswait.setDoctorid(dn);
//                        requestswait.setDonorid(don);
                        Log.d(TAG, "onDataChange: ." +requestswait.toString());
                        receivedList.add(requestswait);

                    }

                }
                userReqAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    private String getDonorName(String donorid) {
        Log.d(TAG, "getUserAccountData: getting the user's account information");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        /*
            ---------- QUERY Method 1 ----------
         */
        Query query1 = reference.child(getString(R.string.dbnode_organdonation))
                .orderByKey()
                .equalTo(donorid);
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //this loop will return a single result
                for(DataSnapshot singleSnapshot: dataSnapshot.getChildren()){
                    // Log.d(TAG, "onDataChange: (QUERY METHOD 1) found user: "
                    //      + singleSnapshot.getValue(Donation.class).toString());
                    dononames= singleSnapshot.getValue(Donation.class).getNames();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Log.d(TAG, "onDataChange: found donor: "
                + dononames);
        return dononames;
    }


    private String getDoctorName(String doctorid) {
        Log.d(TAG, "getUserAccountData: getting the user's account information");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        /*
            ---------- QUERY Method 1 ----------
         */
        Query query1 = reference.child(getString(R.string.dbnode_doctors))
                .orderByKey()
                .equalTo(doctorid);
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //this loop will return a single result
                for(DataSnapshot singleSnapshot: dataSnapshot.getChildren()){
                    Log.d(TAG, "onDataChange: (QUERY METHOD 1) found user: "
                            + singleSnapshot.getValue(Doctor.class).toString());
                    dnames = singleSnapshot.getValue(Doctor.class).getNames();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Log.d(TAG, "onDataChange:  found doctor: "
                + dnames);
        return dnames;
    }

}

