package com.organdonation.ordon;


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.organdonation.ordon.dialog.DatePickerFragment;
import com.organdonation.ordon.dialog.TMessage;
import com.organdonation.ordon.models.Donation;
import com.organdonation.ordon.models.User;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;

public class DonateOrganActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener {
    private Toolbar toolbar;
    private static final String TAG = "DonateOrganActivity";
    private static final String STATUS = "PENDING";
    private EditText dmNames,dmPhone,dmaddress,dmdob,dmcomment;
    private int age;
    private Spinner bloodtype,organtype;
    private Button submitOrgnaDonation;
    private ImageView button_chooseDate;
    private RelativeLayout mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate_organ);
        toolbar =  findViewById(R.id.toolbardonate);
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
        mProgressBar=(RelativeLayout) findViewById(R.id.progressBarDonate) ;
        bloodtype=(Spinner) findViewById(R.id.spinner_blood_donate);
        organtype=(Spinner)   findViewById(R.id.spinner2_organdonate);
        dmNames=(EditText)  findViewById(R.id.editTextRnamedonate);
        dmPhone=(EditText)  findViewById(R.id.editText5_teldonate);
        dmaddress=(EditText)  findViewById(R.id.editTextdonateaddress);
        dmdob=(EditText)  findViewById(R.id.editText4dob_donate);
        dmcomment=(EditText)  findViewById(R.id.editText7Donationcomment);
        submitOrgnaDonation=(Button) findViewById(R.id.buttonsubmitDonate);
        button_chooseDate=(ImageView) findViewById(R.id.button_choosedate_donate) ;
        hideSoftKeyboard();
        getUserAccountData();
        submitOrgnaDonation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validation()){
                    if (age<21){
                        Toast.makeText(DonateOrganActivity.this, "The Person donating organ is too young he /she must be atleast 21 years old", Toast.LENGTH_SHORT).show();
                    }else{
                    sendDonation(dmNames.getText().toString(),
                            dmPhone.getText().toString(),
                            dmaddress.getText().toString(),
                            dmdob.getText().toString(),
                            bloodtype.getSelectedItem().toString(),
                            organtype.getSelectedItem().toString(),
                            dmcomment.getText().toString(),
                            STATUS
                    );}
                }else{
                    Toast.makeText(DonateOrganActivity.this,"All field required",Toast.LENGTH_LONG).show();
                }
            }
        });
        button_chooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.blood_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bloodtype.setAdapter(adapter);
        bloodtype.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.organ_type, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        organtype.setAdapter(adapter2);
        organtype.setOnItemSelectedListener(this);
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        // Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    private boolean validation() {
        if (dmNames.getText().toString().equals("")||
                dmPhone.getText().toString().equals("") ||
                dmaddress.getText().toString().equals("")||
                dmdob.getText().toString().equals("")||
                bloodtype.getSelectedItem().toString().equals("")||
                organtype.getSelectedItem().toString().equals("")||
                dmcomment.getText().toString().equals("")){
            return false;
        }
        return true;
    }
    private void sendDonation(String names,
                              String phone,
                              String address,
                              String dob,
                              String bloodtype,
                              String organtype,
                              String note,
                              String status) {
        showDialog();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        //get the new chatroom unique id
        String donationId = reference
                .child(getString(R.string.dbnode_organdonation))
                .push().getKey();

        Donation organRequests=new Donation();
        organRequests.setNames(names);
        organRequests.setAddress(address);
        organRequests.setDob(dob);
        organRequests.setPhone(phone);
        organRequests.setBloodtype(bloodtype);
        organRequests.setOrgantype(organtype);
        organRequests.setNote(note);
        organRequests.setStatus(status);
        organRequests.setDoctorid("");
        organRequests.setDoctorname("");
        organRequests.setRecipientid("");
        organRequests.setRecipientnames("");
        organRequests.setDonationid(donationId);
        organRequests.setDonation_date(getTimestamp());
        organRequests.setUserid(FirebaseAuth.getInstance().getCurrentUser().getUid());
        FirebaseDatabase.getInstance().getReference()
                .child(getString(R.string.dbnode_organdonation))
                .child(donationId)
                .setValue(organRequests)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //FirebaseAuth.getInstance().signOut();
                        hideDialog();
                        new TMessage(DonateOrganActivity.this, "Donation  Succeed Wait For Response.").show();
                        //redirect the user to the login screen
                        //redirectLoginScreen();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(DonateOrganActivity.this, "something went wrong Donation Failed.", Toast.LENGTH_SHORT).show();
                //FirebaseAuth.getInstance().signOut();

                //redirect the user to the login screen
                //redirectLoginScreen();
            }
        });




    }
    private String getTimestamp(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("Africa/Kigali"));
        return sdf.format(new Date());
    }
    private void getUserAccountData(){
        showDialog();
        Log.d(TAG, "getUserAccountData: getting the user's account information");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        /*
            ---------- QUERY Method 1 ----------
         */
        Query query1 = reference.child(getString(R.string.dbnode_users))
                .orderByKey()
                .equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //this loop will return a single result
                for(DataSnapshot singleSnapshot: dataSnapshot.getChildren()){
                    Log.d(TAG, "onDataChange: (QUERY METHOD 1) found user: "
                            + singleSnapshot.getValue(User.class).toString());
                    User user = singleSnapshot.getValue(User.class);
                    dmNames.setText(user.getName());
                    dmPhone.setText(user.getPhone());
                    dmaddress.setText(user.getAddress());
                    dmdob.setText(user.getDob());
                    hideDialog();
                    //ImageLoader.getInstance().displayImage(user.getProfile_image(), mProfileImage);
                }
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
                            + singleSnapshot.getValue(User.class).toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //  mEmail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        age=getAge(year,month,dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        dmdob.setText(currentDateString);
    }
    private void hideSoftKeyboard(){
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
    private void hideDialog(){
        if(mProgressBar.getVisibility() == View.VISIBLE){
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }
    private void showDialog(){
        mProgressBar.setVisibility(View.VISIBLE);

    }
    public int getAge(int year, int month, int day){
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }

        Integer ageInt = new Integer(age);

        return ageInt;
    }



}

