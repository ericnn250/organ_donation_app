package com.organdonation.ordon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.organdonation.ordon.adapters.DoctorAssignFragAdapter;
import com.organdonation.ordon.dialog.TMessage;

public class AssignDoctor extends AppCompatActivity {
    private Toolbar toolbar;
    private static final String TAG="Assigndoctoractivity";
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private DoctorAssignFragAdapter pageAdapter;
    private TabItem tabDoctor;
    private  TabItem tabdonor;
    private TextView name,doctor,donor;
    private ImageView saveAssign;
    private  String donorid;
    private String doctorid;
    private String request_names;
    private String otype;
    private String donatedtype;

    //variable
    private String request_id;
    private String donoruid;
    private String rec_uid;
    private String doctorname;
    private String donorname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_doctor);
        toolbar=(Toolbar)  findViewById(R.id.toolbar4) ;
        toolbar.setTitle(getResources().getString(R.string.app_name));
        name=(TextView) findViewById(R.id.name_to_assign);
        doctor=(TextView) findViewById(R.id.assigned_Doctor) ;
        donor=(TextView) findViewById(R.id.assigned_donor);
        saveAssign=(ImageView) findViewById(R.id.save_assignement);
        saveAssign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ///
                if (rec_uid.equals(donoruid)){
                    Toast.makeText(AssignDoctor.this,"something wrong may be Donor and Receiver is one Person",Toast.LENGTH_SHORT).show();
                }
                else if (!otype.equals(donatedtype)){
                    Toast.makeText(AssignDoctor.this,"something wrong may be Your assign organ which is not maching",Toast.LENGTH_SHORT).show();
                }
                else{
                    updateReceiver(request_id,doctorid,donorid,donorname,doctorname);
                    updateDonor(donorid,doctorid,request_id,request_names,doctorname);
                    notifyUsers(request_id,donorid);
                    new TMessage(AssignDoctor.this,"Doctor and Donor Assigned Successfully").show();
                }


            }
        });
        Intent intent = getIntent();
        request_id= intent.getStringExtra("id");
        rec_uid= intent.getStringExtra("ruid");
        request_names= intent.getStringExtra("names");
        otype=intent.getStringExtra("type");
        name.setText(request_names);

        /// getUserAccountData(request_id);



        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tabLayout = findViewById(R.id.tabLayout_doc_assign);
        tabDoctor = findViewById(R.id.tabdoctor);
        tabdonor = findViewById(R.id.tabdonor);
        viewPager = findViewById(R.id.viewpagerdonor_assign);

        pageAdapter = new DoctorAssignFragAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pageAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void notifyUsers(String request_id, String donorid) {
    }

    private void updateDonor(String donation_id,String doctorid,String recipientid,String recipientname,String doctorname) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                /*
                ------ Change status -----
                 */
        if(!donation_id.equals("")){
            reference.child(getString(R.string.dbnode_organdonation))
                    .child(donation_id)
                    .child(getString(R.string.field_status))
                    .setValue(getString(R.string.status_done));
             /*
                ------ Change doctor who did it -----
                 */
            reference.child(getString(R.string.dbnode_organdonation))
                    .child(donation_id)
                    .child("doctorid")
                    .setValue(doctorid);
                         /*
                ------ Change doctor  name who did it -----
                 */
            reference.child(getString(R.string.dbnode_organdonation))
                    .child(donation_id)
                    .child("doctorname")
                    .setValue(doctorname);
             /*
                ------ Change recipient -----
                 */
            reference.child(getString(R.string.dbnode_organdonation))
                    .child(donation_id)
                    .child("recipientid")
                    .setValue(recipientid);
                    /*
                ------ Change recipient -----
                 */
            reference.child(getString(R.string.dbnode_organdonation))
                    .child(donation_id)
                    .child("recipientnames")
                    .setValue(recipientname);

        }
    }

    private void updateReceiver(String request_id,String doctorid,String  donorid,String donorname,String doctorname) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                /*
                ------ Change status -----
                 */
        if(!request_id.equals("")){
            reference.child(getString(R.string.dbnode_organrequest))
                    .child(request_id)
                    .child(getString(R.string.field_status))
                    .setValue(getString(R.string.status_done));
            /*
                ------ Change doctor who did  it -----
                 */
            reference.child(getString(R.string.dbnode_organrequest))
                    .child(request_id)
                    .child("doctorid")
                    .setValue(doctorid);
              /*
                          /*
                ------ Change doctor name who did  it -----
                 */
            reference.child(getString(R.string.dbnode_organrequest))
                    .child(request_id)
                    .child("doctorname")
                    .setValue(doctorname);
              /*
                ------ Change donor id -----
                 */
            reference.child(getString(R.string.dbnode_organrequest))
                    .child(request_id)
                    .child("donorid")
                    .setValue(donorid);
        }
                  /*
                ------ Change donor name  -----
                 */
        reference.child(getString(R.string.dbnode_organrequest))
                .child(request_id)
                .child("donornames")
                .setValue(donorname);

    }

    public  void assigndoctor(String text_doctor,String doctorid){
        doctor.setText(text_doctor);
        this.doctorid=doctorid;
        this.doctorname=text_doctor;

    }
    public  void assigndonor(String text_donor,String donorid, String uid,String donatedtype){
        donor.setText(text_donor);
        this.donorid=donorid;
        this.donoruid=uid;
        this.donorname=text_donor;
        this.donatedtype=donatedtype;


    }


}
