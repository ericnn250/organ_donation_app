package com.organdonation.ordon.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.organdonation.ordon.R;
import com.organdonation.ordon.adapters.RecieverWaitAdapter;
import com.organdonation.ordon.dialog.BeforeAssignDialog;
import com.organdonation.ordon.models.OrganRequests;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecieverWaitFragment extends Fragment {



    private static final String  TAG="RECEIVERWAITFRAGMENT";
    //wigdet
    private RecyclerView mreceiverwaitListView;
    private ProgressBar progressBar;
    //  varable
    private ArrayList<OrganRequests> receiverwaitList;
    private RecieverWaitAdapter mReceiverwaitAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public RecieverWaitFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_reciever_wait, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mreceiverwaitListView=(RecyclerView) view.findViewById(R.id.recyclerview_recieved_wait);
        receiverwaitList=new ArrayList<>();
        mreceiverwaitListView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(view.getContext());
        mReceiverwaitAdapter = new RecieverWaitAdapter(receiverwaitList);
        progressBar= view.findViewById(R.id.progressBar_receiver_wait);

        mreceiverwaitListView.setLayoutManager(mLayoutManager);
        mreceiverwaitListView.setAdapter(mReceiverwaitAdapter);
        getUserAccountData();
        mReceiverwaitAdapter.setOnItemClickListener(new RecieverWaitAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                viewUser(position);
            }
        });


    }

    private void viewUser(int position) {
        OrganRequests user=receiverwaitList.get(position);
        BeforeAssignDialog dialog=new BeforeAssignDialog(getContext(),user);
        dialog.show();
    }


    private void getUserAccountData() {
        showDialog();
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
                    String status = singleSnapshot.getValue(OrganRequests.class).getStatus();
                    if( status .equals(getContext().getString(R.string.status_pending))){

                        OrganRequests requestswait = singleSnapshot.getValue(OrganRequests.class);
                        Log.d(TAG, "onDataChange: Request is waiting."+requestswait.toString());
                        receiverwaitList.add(requestswait);
                    }

                }
                mReceiverwaitAdapter.notifyDataSetChanged();
                hideDialog();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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
    private void showDialog() {
        progressBar.setVisibility(View.VISIBLE);
    }
    private void hideDialog() {
        if(progressBar.getVisibility() == View.VISIBLE){
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.receiverwairmenu, menu);
        MenuItem searchItem = menu.findItem(R.id.app_bar_search_receiverwait);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
               // Toast.makeText(getContext(),newText,Toast.LENGTH_SHORT).show();

                mReceiverwaitAdapter.getFilter().filter(newText);
                //mReceiverwaitAdapter.notifyDataSetChanged();
                return false;
            }
        });
    }
}
