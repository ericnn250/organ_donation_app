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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.organdonation.ordon.models.Donation;
import com.organdonation.ordon.models.User;

import java.util.ArrayList;

public class TestSeach extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView recyclerViewAnimals;
    private TestingAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<User> list=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_seach);
        toolbar=(Toolbar)findViewById(R.id.too);
        toolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(toolbar);

        recyclerViewAnimals=(RecyclerView) findViewById(R.id.testsearch);
        recyclerViewAnimals.setHasFixedSize(true);
                 initRecyclerView();
    }
    ArrayList<User> listcopy;
    private void initRecyclerView() {
        //initAnimalsList();
        getUserAccountData();
        mLayoutManager = new LinearLayoutManager(TestSeach.this);
        ArrayList<User> listcopy=(ArrayList<User>)list.clone();
        mAdapter = new TestingAdapter(TestSeach.this,listcopy);
        recyclerViewAnimals.setLayoutManager(mLayoutManager);
        recyclerViewAnimals.setAdapter(mAdapter);
       // initAnimalsList();

    }

    private void initAnimalsList() {
        list.add(new User("Eric","0786647565"));
        list.add(new User("Bosco","073458858"));
        list.add(new User("Sarah","34567890"));
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
                mAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);


}
    private void getUserAccountData(){
        Log.d("TAG", "getUserAccountData: getting the user's account information");

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
                    Log.d("TAG", "onDataChange: (QUERY METHOD 1) found user: "
                            + singleSnapshot.getValue(Donation.class).toString());
                    String status = singleSnapshot.getValue(Donation.class).getStatus();
                    if( status.equals("DONE")){
                        Log.d("TAG", "onDataChange: donor is done.");
                        Donation donationwait = singleSnapshot.getValue(Donation.class);
                        list.add(new User(donationwait.getNames(),donationwait.getPhone()));
                    }

                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }
}