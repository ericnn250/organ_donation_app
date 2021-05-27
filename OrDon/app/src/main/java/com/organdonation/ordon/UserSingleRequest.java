package com.organdonation.ordon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.organdonation.ordon.dialog.TMessage;
import com.organdonation.ordon.models.Donation;
import com.organdonation.ordon.models.OrganRequests;

public class UserSingleRequest extends AppCompatActivity {
    private TextView rmNames,rmPhone,rmaddress,rmdob,rmcomment,bloodtype,organtype,status,requestedDate,doctor,receiver,email;
    private Button btn_cancel;
    private static final String STATUS = "PENDING";
    private RelativeLayout progressBar;
    private RelativeLayout relativeLayout;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_single_request);
        Intent user=getIntent();
        final String id=user.getStringExtra("req_id");
        bloodtype=(TextView) findViewById(R.id.dialog_blodtypesbd);
        organtype=(TextView)   findViewById(R.id.dialog_organsbd);
        status=(TextView) findViewById(R.id.dialog_statussbd) ;
        requestedDate=(TextView) findViewById(R.id.dialog_datesbd) ;
        rmNames=(TextView)  findViewById(R.id.dialog_namesbd);
        rmPhone=(TextView)  findViewById(R.id.dialog_contactsbd);
        rmaddress=(TextView)  findViewById(R.id.dialog_adressbd);
        rmdob=(TextView)  findViewById(R.id.dialog_bdsbd);
        rmcomment=(TextView)  findViewById(R.id.dialog_commentsbd);
        btn_cancel=(Button)findViewById(R.id.assignsbd);
        doctor=(TextView) findViewById(R.id.user_donation_s_doctor_reald);
        receiver=(TextView)findViewById(R.id.user_donation_s_receiver_reald);
        progressBar=(RelativeLayout) findViewById(R.id.progMySrequest);
        email=(TextView) findViewById(R.id.dialog_emailsbd);
        relativeLayout=(RelativeLayout) findViewById(R.id.result_sectiond) ;
        toolbar=(Toolbar) findViewById(R.id.toolbar_sbd) ;
        toolbar.setTitle(getResources().getString(R.string.app_name));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getPDonation(id);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDonation(id);
            }
        });
    }

    private void deleteDonation(String doctor_id) {
        if(doctor_id != null){

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
            reference.child(getString(R.string.dbnode_organrequest))
                    .child(doctor_id)
                    .removeValue();
            new TMessage(UserSingleRequest.this,"Deleted").show();
            startActivity(new Intent(UserSingleRequest.this,PrevioiusRequestActivity.class));
            this.finish();

        }
    }

    private void getPDonation(String id) throws NullPointerException{
        showDialog();
        Log.d("MYSingleDonation", "getPrevoiusDonationList: getting a list of all Donation");
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        Query query = reference.child(getString(R.string.dbnode_organrequest)).orderByChild("requestid").equalTo(id);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    OrganRequests mydonations = snapshot.getValue(OrganRequests.class);
                    rmNames.setText(mydonations.getNames());
                    rmPhone.setText(mydonations.getPhone());
                    email.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                    requestedDate.setText(mydonations.getRequest_date());
                    rmaddress.setText(mydonations.getAddress());
                    rmdob.setText(mydonations.getDob());
                    rmcomment.setText(mydonations.getNote());
                    status.setText(mydonations.getStatus());
                    bloodtype.setText(mydonations.getBloodtype());
                    organtype.setText(mydonations.getOrgantype());
                    doctor.setText(mydonations.getDoctorname());
                    receiver.setText(mydonations.getDonornames());
                    if (mydonations.getStatus().equals(STATUS)){

                    }
                    else{
                        relativeLayout.setVisibility(View.VISIBLE);
                        btn_cancel.setVisibility(View.GONE);
                    }
                }
                hideDialog();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void showDialog() {
        progressBar.setVisibility(View.VISIBLE);
    }
    private void hideDialog() {
        if(progressBar.getVisibility() == View.VISIBLE){
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
}