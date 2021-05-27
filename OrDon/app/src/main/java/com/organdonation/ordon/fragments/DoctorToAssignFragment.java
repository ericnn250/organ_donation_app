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
import com.organdonation.ordon.adapters.DoctorAdapter;
import com.organdonation.ordon.models.Doctor;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class DoctorToAssignFragment extends Fragment {
    private  static final String TAG="DoctorAssign";
    private ArrayList<Doctor> doctorsList;
    private DoctorAdapter mDoctorAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private RecyclerView  doctorListView;

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        doctorListView=(RecyclerView) view.findViewById(R.id.recyclerView_doctor_assign);
        doctorsList=new ArrayList<>();
        doctorListView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(view.getContext());
        mDoctorAdapter = new DoctorAdapter(doctorsList);

        doctorListView.setLayoutManager(mLayoutManager);
        doctorListView.setAdapter(mDoctorAdapter);
        mDoctorAdapter.setOnItemClickListener(new DoctorAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(view.getContext(),doctorsList.get(position).getNames(),Toast.LENGTH_LONG).show();
                AssignDoctor assignDoctor=(AssignDoctor) getActivity();
                assignDoctor.assigndoctor(doctorsList.get(position).getNames(),doctorsList.get(position).getDoctor_id());
            }
        });
        getUserAccountData();
    }
    private void getUserAccountData(){
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
                    Doctor doctor = singleSnapshot.getValue(Doctor.class);
                    doctorsList.add(doctor);
                }
                mDoctorAdapter.notifyDataSetChanged();
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
                            + singleSnapshot.getValue(Doctor.class).toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //  mEmail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
    }

    public DoctorToAssignFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doctor_to_assign, container, false);
    }

}
