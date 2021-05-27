package com.organdonation.ordon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.organdonation.ordon.dialog.PasswordResetDialog;
import com.organdonation.ordon.dialog.TMessage;
import com.organdonation.ordon.models.User;

public class Login extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    //Firebase
    private FirebaseAuth.AuthStateListener mAuthListener;
    // widgets
    private EditText mEmail, mPassword;
    private RelativeLayout mProgressBar;
  //  public static ResendVerificationDialog dialog;
    public static boolean isActivityRunning;
    //private boolean mIsAdmin = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail = (EditText) findViewById(R.id.email);
        mPassword = (EditText) findViewById(R.id.password);
        mProgressBar = (RelativeLayout) findViewById(R.id.progressBar);
        hideSoftKeyboard();
        setupFirebaseAuth();

        Button signIn = (Button) findViewById(R.id.email_sign_in_button);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //check if the fields are filled out
                if(!isEmpty(mEmail.getText().toString())
                        && !isEmpty(mPassword.getText().toString())){
                    Log.d(TAG, "onClick: attempting to authenticate.");

                    showDialog();

                    FirebaseAuth.getInstance().signInWithEmailAndPassword(mEmail.getText().toString(),
                            mPassword.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

//                                    Intent intent = new Intent(Login.this, WaitActivity.class);
//                                    startActivity(intent);
//                                    hideDialog();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Login.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                            hideDialog();
                        }
                    });
                }else{
                    Toast.makeText(Login.this, "You didn't fill in all the fields.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        TextView register = (TextView) findViewById(R.id.link_register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });

        TextView resetPassword = (TextView) findViewById(R.id.forgot_password);
        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PasswordResetDialog dialog = new PasswordResetDialog();
                dialog.show(getSupportFragmentManager(), "dialog_password_reset");
            }
        });

//        TextView resendEmailVerification = (TextView) findViewById(R.id.resend_verification_email);
//        resendEmailVerification.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog = new ResendVerificationDialog();
//                dialog.show(getSupportFragmentManager(), "dialog_resend_email_verification");
 //           }
        //});

        hideSoftKeyboard();

    }

    /**
     * Return true if the @param is null
     * @param string
     * @return
     */
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

    /*
        ----------------------------- Firebase setup ---------------------------------
     */
    private void setupFirebaseAuth(){
        Log.d(TAG, "setupFirebaseAuth: started.");

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    //check if email is verified
                    if(user.isEmailVerified()){
                        Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                        Intent intent = new Intent(Login.this, WaitActivity.class);
                        startActivity(intent);
                        hideDialog();
                        finish();
                        //Toast.makeText(Login.this, "Authenticated with: " + user.getEmail(), Toast.LENGTH_SHORT).show();
                        //is admin
//                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
//                        Query query = reference.child(getString(R.string.dbnode_users))
//                                .orderByChild(getString(R.string.field_user_id))
//                                .equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());
//                        query.addListenerForSingleValueEvent(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(DataSnapshot dataSnapshot) {
//                                Log.d(TAG, "onDataChange: datasnapshot: " + dataSnapshot);
//                                DataSnapshot singleSnapshot = dataSnapshot.getChildren().iterator().next();
//                                int securityLevel = Integer.parseInt(singleSnapshot.getValue(User.class).getSecurity_level());
//                                if( securityLevel == 10){
//                                    Log.d(TAG, "onDataChange: user is an admin.");
//                                    Intent intentadmin = new Intent(Login.this, home.class);
//                                    intentadmin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                    startActivity(intentadmin);
//                                    finish();
//                                }
//                                else {
//                                    Intent intentuser = new Intent(Login.this, Userhome.class);
//                                    intentuser.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                    startActivity(intentuser);
//                                    finish();
//                                }
//                            }
//
//                            @Override
//                            public void onCancelled(DatabaseError databaseError) {
//
//                            }
//                        });
//                        if(isAdmin()){
//
//                        }else{
//
//                        }

/////
                    }else{
                        //Toast.makeText(Login.this, "Email is not Verified\nCheck your Inbox", Toast.LENGTH_SHORT).show();
                        new TMessage(Login.this,"Email is not Verified\nCheck your Inbox").show();
                        FirebaseAuth.getInstance().signOut();
                        hideDialog();
                    }

                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(mAuthListener);
        isActivityRunning = true;
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            FirebaseAuth.getInstance().removeAuthStateListener(mAuthListener);
            isActivityRunning = false;
        }
    }
    private boolean isAdmin(){
        final boolean[] mIsAdmin = {false};
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child(getString(R.string.dbnode_users))
                .orderByChild(getString(R.string.field_user_id))
                .equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange: datasnapshot: " + dataSnapshot);
                DataSnapshot singleSnapshot = dataSnapshot.getChildren().iterator().next();
                int securityLevel = Integer.parseInt(singleSnapshot.getValue(User.class).getSecurity_level());
                if( securityLevel == 10){
                    Log.d(TAG, "onDataChange: user is an admin.");
                    mIsAdmin[0] = true;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return mIsAdmin[0];
    }
}

