package com.organdonation.ordon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
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
import com.organdonation.ordon.dialog.TMessage;
import com.organdonation.ordon.models.Contact;
import com.organdonation.ordon.models.OrganRequests;
import com.organdonation.ordon.models.User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Contactus extends AppCompatActivity {
    private ProgressBar mProgressBar;
    private Toolbar toolbar;
    private EditText mMessage;
    private Button btnsend;
    private String names;

    private static final String TAG = "ContactUsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactus);
        hideSoftKeyboard();
        toolbar=(Toolbar) findViewById(R.id.toolbarcontactus);
        toolbar.setTitle(getResources().getString(R.string.app_name));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnsend=(Button) findViewById(R.id.buttoncontactus);
        mProgressBar=(ProgressBar) findViewById(R.id.progressBarcontactus);
        mMessage=(EditText) findViewById(R.id.message);
        getUserAccountData();
        btnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEmpty(mMessage.getText().toString())){
                    Toast.makeText(Contactus.this,"Invalid message",Toast.LENGTH_SHORT).show();
                }
                else{
                    sendMessage(mMessage.getText().toString());
                }
            }
        });

    }
    private void sendMessage(String message) {
        showDialog();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        //get the new chatroom unique id
        String contactId = reference
                .child(getString(R.string.dbnode_contactus))
                .push().getKey();
        Contact contact=new Contact();
        contact.setUsername(names);
        contact.setEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        contact.setUid(FirebaseAuth.getInstance().getCurrentUser().getUid());
        contact.setMessage(message);
        contact.setId(contactId);
        contact.setDatedone(getTimestamp());
        FirebaseDatabase.getInstance().getReference()
                .child(getString(R.string.dbnode_contactus))
                .child(contactId)
                .setValue(contact)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //FirebaseAuth.getInstance().signOut();
                        //Toast.makeText(RequestOrganActivity.this, "Request  Succeed Wait For Response.", Toast.LENGTH_SHORT).show();
                        hideDialog();
                        mMessage.setText("");
                        new TMessage(Contactus.this, "Thank you for your feedback.").show();
                        //redirect the user to the login screen
                        //redirectLoginScreen();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Toast.makeText(RequestOrganActivity.this, "something went wrong Request Failed.", Toast.LENGTH_SHORT).show();
                hideDialog();
                new TMessage(Contactus.this, "something went wrong .").show();
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
                    names=user.getName();
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

    private String getTimestamp(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("Africa/Kigali"));
        return sdf.format(new Date());
    }
    private boolean isEmpty(String string){
        return string.equals("");
    }


    private void showDialog(){
        mProgressBar.setVisibility(View.VISIBLE);

    }

    private void hideDialog(){
        if(mProgressBar.getVisibility() == View.VISIBLE){
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }

    private void hideSoftKeyboard(){
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

}
