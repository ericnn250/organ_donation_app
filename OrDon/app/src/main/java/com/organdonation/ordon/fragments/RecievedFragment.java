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
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.organdonation.ordon.R;
import com.organdonation.ordon.adapters.RecievedAdapter;
import com.organdonation.ordon.adapters.TestAdapter;
import com.organdonation.ordon.adapters.UserAdapter;
import com.organdonation.ordon.models.OrganRequests;
import com.organdonation.ordon.models.User;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecievedFragment extends Fragment {


    private static final String  TAG="RECEIVEDFRAGMENT";
    //wigdet
    private RecyclerView mreceivedListView;
    //  varable
    private ArrayList<OrganRequests> receivedList;
    private ArrayList<User> testAdapter=new ArrayList<>();
    private TestAdapter mReceivedAdapter;
    private UserAdapter usadpter;
    private RecyclerView.LayoutManager mLayoutManager;

    public RecievedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_recieved, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mreceivedListView=(RecyclerView) view.findViewById(R.id.recyclerview_recieved);
        receivedList=new ArrayList<>();
        mreceivedListView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(view.getContext());
       // mReceivedAdapter = new TestAdapter(getContext(),receivedList);
        usadpter=new UserAdapter(testAdapter);

        mreceivedListView.setLayoutManager(mLayoutManager);
        mreceivedListView.setAdapter(usadpter);
        //getUserAccountData();
        tesfunc();

    }
    private void tesfunc(){

        testAdapter.add(new User("Eric","0786647565"));
        testAdapter.add(new User("Bosco","073458858"));
        testAdapter.add(new User("Sarah","34567890"));
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
                    String status = singleSnapshot.getValue(OrganRequests.class).getStatus();
                    if(!status.equals("PENDING")){
                        Log.d(TAG, "onDataChange: Request is received.");
                        OrganRequests requestswait = singleSnapshot.getValue(OrganRequests.class);
                        receivedList.add(requestswait);

                    }

                }
//                mReceivedAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.receivermenu, menu);
        MenuItem searchItem = menu.findItem(R.id.app_bar_search_reciever);
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
                //mReceivedAdapter.getFilter().filter(newText);
                usadpter.getFilter().filter(newText);
               // mReceivedAdapter.notifyDataSetChanged();
                return false;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
