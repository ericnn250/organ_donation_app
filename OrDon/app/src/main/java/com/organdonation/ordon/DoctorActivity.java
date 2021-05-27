package com.organdonation.ordon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.SearchView;
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

public class DoctorActivity extends AppCompatActivity {

    private static final String TAG = "DoctorActivity";
    //wigdet
    private Toolbar toolbar;
    private RecyclerView doctorListView;
    private RelativeLayout addDoctor;
    //  varable
    private ArrayList<Doctor> doctorsList;
    private DoctorAdapter mDoctorAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);
        toolbar =  findViewById(R.id.toolbar8);
        toolbar.setTitle("Doctors");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        addDoctor=(RelativeLayout) findViewById(R.id.add_doctor);
        doctorListView=(RecyclerView) findViewById(R.id.recyclerViewDoctorList);
        addDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addDoctor=new Intent(DoctorActivity.this,AddDoctorActivity.class);
                startActivity(addDoctor);
            }
        });


        doctorsList=new ArrayList<>();
        doctorListView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mDoctorAdapter = new DoctorAdapter(doctorsList);

        doctorListView.setLayoutManager(mLayoutManager);
        doctorListView.setAdapter(mDoctorAdapter);
        mDoctorAdapter.setOnItemClickListener(new DoctorAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                viewDoctor(position);

            }
        });
        getUserAccountData();
    }
    private void viewDoctor(int positiom){
        Intent doc=new Intent(DoctorActivity.this,ViewDoctor.class);
        doc.putExtra("docid",doctorsList.get(positiom).getDoctor_id());
        startActivity(doc);
        Log.d(TAG, "getUserAccountData: doctor id"+doctorsList.get(positiom).getDoctor_id());

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
                mDoctorAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()){
           case R.id.app_bar_search_doctor:
                //Toast.makeText(MainActivity.this,"Make Search",Toast.LENGTH_LONG).show();
                return true;
              case R.id.optionAccountSettingsad:
//                intent = new Intent(home.this, SettingsActivity.class);
//                startActivity(intent);
//                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
