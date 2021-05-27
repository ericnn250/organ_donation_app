package com.organdonation.ordon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
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
import com.organdonation.ordon.models.OrganRequests;
import com.organdonation.ordon.models.User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;

public class RequestOrganActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener {
    private Toolbar toolbar;
    private static final String TAG = "RequestOrganActivity";
    private EditText  rmNames,rmPhone,rmaddress,rmdob,rmcomment,ref;
    private Spinner bloodtype,organtype;
    private Button  submitOrgnaRequest;
    private ImageView button_chooseDate;
    private RelativeLayout mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_organ);
        toolbar =  findViewById(R.id.toolbar4requestorgan);
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

        bloodtype=(Spinner) findViewById(R.id.spinnerbloodtype);
        organtype=(Spinner)   findViewById(R.id.spinnerorgantype);
        rmNames=(EditText)  findViewById(R.id.editTextRname);
        rmPhone=(EditText)  findViewById(R.id.editTextRphone);
        rmaddress=(EditText)  findViewById(R.id.editTextRaddress);
        rmdob=(EditText)  findViewById(R.id.editTextrrRdob);
        ref=(EditText)  findViewById(R.id.ref);
        rmcomment=(EditText)  findViewById(R.id.organrequestnote);
        submitOrgnaRequest=(Button) findViewById(R.id.buttonorganrequest);
        button_chooseDate=(ImageView) findViewById(R.id.button_choosedate_request);

        mProgressBar = (RelativeLayout) findViewById(R.id.progressBarRequestOrgan);
        hideSoftKeyboard();
        getUserAccountData();
        submitOrgnaRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validation()){
                    sendRequest(rmNames.getText().toString(),
                            rmPhone.getText().toString(),
                            rmaddress.getText().toString(),
                            rmdob.getText().toString(),
                            bloodtype.getSelectedItem().toString(),
                            organtype.getSelectedItem().toString(),
                            rmcomment.getText().toString(),
                            getString(R.string.status_pending),
                            ref.getText().toString()
                    );
                }else{
                    Toast.makeText(RequestOrganActivity.this,"all field required",Toast.LENGTH_LONG).show();

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
        if (rmNames.getText().toString().equals("")||
                rmPhone.getText().toString().equals("") ||
                rmaddress.getText().toString().equals("")||
                rmdob.getText().toString().equals("")||
                bloodtype.getSelectedItem().toString().equals("")||
                organtype.getSelectedItem().toString().equals("")||
                rmcomment.getText().toString().equals("")||
                 ref.getText().toString().equals("")){
            Toast.makeText(RequestOrganActivity.this, "All Field Required please", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void sendRequest(String names,
                             String phone,
                             String address,
                             String dob,
                             String bloodtype,
                             String organtype,
                             String note,
                             String status,String refcode) {
        showDialog();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        //get the new chatroom unique id
        String requestId = reference
                .child(getString(R.string.dbnode_organrequest))
                .push().getKey();
        OrganRequests organRequests=new OrganRequests();
        organRequests.setNames(names);
        organRequests.setAddress(address);
        organRequests.setDob(dob);
        organRequests.setRef_number(refcode);
        organRequests.setPhone(phone);
        organRequests.setBloodtype(bloodtype);
        organRequests.setOrgantype(organtype);
        organRequests.setNote(note);
        organRequests.setStatus(status);
        organRequests.setUserid(FirebaseAuth.getInstance().getCurrentUser().getUid());
        organRequests.setDoctorid("");
        organRequests.setDoctorname("");
        organRequests.setDonorid("");
        organRequests.setDonornames("");
        organRequests.setRequestid(requestId);
        organRequests.setRequest_date(getTimestamp());
        FirebaseDatabase.getInstance().getReference()
                .child(getString(R.string.dbnode_organrequest))
                .child(requestId)
                .setValue(organRequests)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //FirebaseAuth.getInstance().signOut();
                        //Toast.makeText(RequestOrganActivity.this, "Request  Succeed Wait For Response.", Toast.LENGTH_SHORT).show();
                        hideDialog();
                        rmNames.setText("");
                                rmPhone.setText("");
                                rmaddress.setText("");
                                rmcomment.setText("");
                        new TMessage(RequestOrganActivity.this, "Request  Succeed Wait For Response.").show();
                        //redirect the user to the login screen
                        //redirectLoginScreen();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Toast.makeText(RequestOrganActivity.this, "something went wrong Request Failed.", Toast.LENGTH_SHORT).show();
                hideDialog();
                new TMessage(RequestOrganActivity.this, "something went wrong Request Failed.").show();
                //FirebaseAuth.getInstance().signOut();

                //redirect the user to the login screen
                //redirectLoginScreen();
            }
        });




    }

    private void getUserAccountData(){
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
                    rmNames.setText(user.getName());
                    rmPhone.setText(user.getPhone());
                    rmaddress.setText(user.getAddress());
                    rmdob.setText(user.getDob());
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
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        rmdob.setText(currentDateString);
    }
    private String getTimestamp(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("Africa/Kigali"));
        return sdf.format(new Date());
    }

    private boolean isEmpty(String string){
        return string.equals("");
    }
    private void hideSoftKeyboard(){
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
    private void showDialog(){
        mProgressBar.setVisibility(View.VISIBLE);

    }

    private void hideDialog(){
        if(mProgressBar.getVisibility() == View.VISIBLE){
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }


}

