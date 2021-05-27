package com.organdonation.ordon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

public class UserSingleDonation extends AppCompatActivity {

    private TextView rmNames,rmPhone,rmaddress,rmdob,rmcomment,bloodtype,organtype,status,requestedDate,doctor,receiver,email;
    private Button btn_cancel;
    private static final String STATUS = "PENDING";
    private RelativeLayout progressBar;
    private RelativeLayout relativeLayout;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_single_donation);
        Intent user=getIntent();
        final String id=user.getStringExtra("donation_id");
        bloodtype=(TextView) findViewById(R.id.dialog_blodtypesb);
        organtype=(TextView)   findViewById(R.id.dialog_organsb);
        status=(TextView) findViewById(R.id.dialog_statussb) ;
        requestedDate=(TextView) findViewById(R.id.dialog_datesb) ;
        rmNames=(TextView)  findViewById(R.id.dialog_namesb);
        rmPhone=(TextView)  findViewById(R.id.dialog_contactsb);
        rmaddress=(TextView)  findViewById(R.id.dialog_adressb);
        email=(TextView) findViewById(R.id.dialog_emailsb);
        rmdob=(TextView)  findViewById(R.id.dialog_bdsb);
        rmcomment=(TextView)  findViewById(R.id.dialog_commentsb);
        btn_cancel=(Button)findViewById(R.id.assignsb);
        doctor=(TextView) findViewById(R.id.user_donation_s_doctor_real);
        receiver=(TextView)findViewById(R.id.user_donation_s_receiver_real);
        progressBar=(RelativeLayout) findViewById(R.id.progMySdonation);
        relativeLayout=(RelativeLayout) findViewById(R.id.result_section) ;
        toolbar=(Toolbar) findViewById(R.id.toolbar_sb) ;
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
            reference.child(getString(R.string.dbnode_organdonation))
                    .child(doctor_id)
                    .removeValue();
            new TMessage(UserSingleDonation.this,"Deleted").show();
            startActivity(new Intent(UserSingleDonation.this,PreviousDonationActivity.class));
            this.finish();

        }
    }

    private void getPDonation(String id) throws NullPointerException{
        showDialog();
        Log.d("MYSingleDonation", "getPrevoiusDonationList: getting a list of all Donation");
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        Query query = reference.child(getString(R.string.dbnode_organdonation)).orderByChild("donationid").equalTo(id);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Donation mydonations = snapshot.getValue(Donation.class);
                    rmNames.setText(mydonations.getNames());
                    rmPhone.setText(mydonations.getPhone());
                    email.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                    requestedDate.setText(mydonations.getDonation_date());
                    rmaddress.setText(mydonations.getAddress());
                    rmdob.setText(mydonations.getDob());
                    rmcomment.setText(mydonations.getNote());
                    status.setText(mydonations.getStatus());
                    bloodtype.setText(mydonations.getBloodtype());
                    organtype.setText(mydonations.getOrgantype());
                    doctor.setText(mydonations.getDoctorname());
                    receiver.setText(mydonations.getRecipientnames());
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