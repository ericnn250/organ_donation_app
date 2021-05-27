package com.organdonation.ordon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class home extends AppCompatActivity {

    private static final String TAG = "SignedInActivity";

    //Firebase
    private FirebaseAuth.AuthStateListener mAuthListener;


    // widgets
    private RelativeLayout relative_donor,relative_recipient,relative_doctor,relativeLayout_user;
    private Toolbar toolbar;
    //vars
    public static boolean isActivityRunning;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Log.d(TAG, "onCreate: started.");
        toolbar =  findViewById(R.id.toolbar2);
        toolbar.setTitle(getResources().getString(R.string.app_name));


        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
        relative_doctor=(RelativeLayout) findViewById(R.id.doctor);
        relative_donor=(RelativeLayout) findViewById(R.id.donor);
        relative_recipient=(RelativeLayout)findViewById(R.id.recipient);
        relativeLayout_user=(RelativeLayout) findViewById(R.id.user) ;
        relativeLayout_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_user=new Intent(home.this,UserActivity.class);
                startActivity(intent_user);
            }
        });
        relative_recipient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_recipient=new Intent(home.this, RecipentActivity.class);
                startActivity(intent_recipient);
            }
        });
        relative_donor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_donor=new Intent(home.this,DonorActivity.class);
                startActivity(intent_donor);
            }
        });
        relative_doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_doctor=new Intent(home.this,DoctorActivity.class);
                startActivity(intent_doctor);
            }
        });
        //setupFirebaseAuth();

    }



    @Override
    protected void onResume() {
        super.onResume();
        //checkAuthenticationState();
    }



//
//    private void checkAuthenticationState(){
//        Log.d(TAG, "checkAuthenticationState: checking authentication state.");
//
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//
//        if(user == null){
//            Log.d(TAG, "checkAuthenticationState: user is null, navigating back to login screen.");
//
//            Intent intent = new Intent(home.this, Login.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(intent);
//            finish();
//        }else{
//            Log.d(TAG, "checkAuthenticationState: user is authenticated.");
//        }
//    }
//


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menuadmin, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()){
            case R.id.optionSignOutad:
                signOut();
                return true;
            case R.id.optionAccountSettingsad:
                intent = new Intent(home.this, TestSeach.class);
                startActivity(intent);
                return true;
            case R.id.optionfeedback:
                intent = new Intent(home.this, FeedBack.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void signOut(){
        Toast.makeText(home.this,"Signed out", Toast.LENGTH_LONG).show();
        Log.d("USERhomeActivity", "signOut: signing out");
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(home.this,Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    /*
            ----------------------------- Firebase setup ---------------------------------
         */
//    private void setupFirebaseAuth(){
//        Log.d(TAG, "setupFirebaseAuth: started.");
//
//        mAuthListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser user = firebaseAuth.getCurrentUser();
//                if (user != null) {
//
//                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
//
//                } else {
//                    Log.d(TAG, "onAuthStateChanged:signed_out");
//                    Intent intent = new Intent(home.this, home.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    startActivity(intent);
//                    finish();
//                }
//                // ...
//            }
//        };
//    }

    @Override
    public void onStart() {
        super.onStart();
        //FirebaseAuth.getInstance().addAuthStateListener(mAuthListener);
        isActivityRunning = true;
    }

    @Override
    public void onStop() {
        super.onStop();
//        if (mAuthListener != null) {
//            FirebaseAuth.getInstance().removeAuthStateListener(mAuthListener);
//        }
        isActivityRunning = false;
    }


}
