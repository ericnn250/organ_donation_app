package com.organdonation.ordon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.organdonation.ordon.dialog.DatePickerFragment;
import com.organdonation.ordon.models.User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Register extends AppCompatActivity  implements DatePickerDialog.OnDateSetListener {

    private static final String TAG = "RegisterActivity";

    private static final String DOMAIN_NAME = "gmail.com";
    private static final String ACTIVE = "yes";


    //widgets
    private EditText mNames,mPhone,mAddress,mDob,mEmail, mPassword, mConfirmPassword;
    private Button mRegister;
    private TextView mLogin;
    private ProgressBar mProgressBar;
    public static boolean isActivityRunning;
    private ImageView choosd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mNames = (EditText) findViewById(R.id.input_names_real);
        mEmail = (EditText) findViewById(R.id.input_email_real);
        mPhone = (EditText) findViewById(R.id.input_phone_real);
        mAddress = (EditText) findViewById(R.id.input_address_real);
        mDob = (EditText) findViewById(R.id.inputdob_real);
        mPassword = (EditText) findViewById(R.id.input_password_real);
        mConfirmPassword = (EditText) findViewById(R.id.input_confirm_password_real);
        mRegister = (Button) findViewById(R.id.btn_register);
        mLogin = (TextView) findViewById(R.id.btn_login_real);
        choosd=(ImageView) findViewById(R.id.imagbtnchoosedate);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        choosd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });
        mDob.setEnabled(false);
        mDob.setFocusable(false);
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: attempting to register.");

                //check for null valued EditText fields
                if(!isEmpty(mEmail.getText().toString())
                        && !isEmpty(mPassword.getText().toString())
                        && !isEmpty(mConfirmPassword.getText().toString())){

                    //check if user has a company email address
                    if(isValidDomain(mEmail.getText().toString())){

                        //check if passwords match
                        if(doStringsMatch(mPassword.getText().toString(), mConfirmPassword.getText().toString())){

                            //Initiate registration task
                            registerNewEmail(mNames.getText().toString(),
                                    mEmail.getText().toString(),
                                    mPhone.getText().toString(),
                                    mAddress.getText().toString(),
                                    mDob.getText().toString(),
                                    mPassword.getText().toString());
                        }else{
                            Toast.makeText(Register.this, "Passwords do not Match", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(Register.this, "Please Register with Gmail Email", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(Register.this, "You must fill out all the fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

        hideSoftKeyboard();
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectLoginScreen();
            }
        });

    }


    /**
     * Register a new email and password to Firebase Authentication
     * @param email
     * @param password
     */
    public void registerNewEmail(final String names,
                                 final String email,
                                 final String phone,
                                 final String addres,
                                 final String dob,
                                 final String password){

        showDialog();

        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            FirebaseAuth.getInstance().signOut();
        }
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        if (task.isSuccessful()){
                            Log.d(TAG, "onComplete: AuthState: " + FirebaseAuth.getInstance().getCurrentUser().getUid());

                            //send email verificaiton
                            sendVerificationEmail();
                            //insert some default data
                            final User user = new User();
                            user.setName(names);
                            user.setPhone(phone);
                            user.setAddress(addres);
                            user.setDob(dob);
                            user.setRole(ACTIVE);
                            user.setEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                            user.setSecurity_level("1");
                            user.setUser_id(FirebaseAuth.getInstance().getCurrentUser().getUid());
                            FirebaseDatabase.getInstance().getReference()
                                    .child(getString(R.string.dbnode_users))
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            FirebaseAuth.getInstance().signOut();
                                            Log.i(TAG,user.toString());
                                            if (task.isSuccessful()) {
                                                Toast.makeText(Register.this, "Registration succeed.\n Check your email inbox to verify email",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                            //redirect the user to the login screen
                                            redirectLoginScreen();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    //Toast.makeText(Register.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    Toast.makeText(Register.this, "something went wrong.", Toast.LENGTH_SHORT).show();
                                    FirebaseAuth.getInstance().signOut();

                                    //redirect the user to the login screen
                                    redirectLoginScreen();
                                }
                            });

//                            FirebaseAuth.getInstance().signOut();
//
//                            //redirect the user to the login screen
//                            redirectLoginScreen();
                        }
                        if (!task.isSuccessful()) {
                            Toast.makeText(Register.this, "Unable to Register",
                                    Toast.LENGTH_SHORT).show();
                        }
                        hideDialog();

                        // ...
                    }
                });
    }

    /**
     * sends an email verification link to the user
     */
    private void sendVerificationEmail() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            user.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                //Toast.makeText(Register.this, "Sent Verification Email", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                //Toast.makeText(Register.this, "Couldn't Verification Send Email", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }

    /**
     * Returns True if the user's email contains '@tabian.ca'
     * @param email
     * @return
     */
    private boolean isValidDomain(String email){
        Log.d(TAG, "isValidDomain: verifying email has correct domain: " + email);
        String domain = email.substring(email.indexOf("@") + 1).toLowerCase();
        Log.d(TAG, "isValidDomain: users domain: " + domain);
        return domain.equals(DOMAIN_NAME);
    }

    /**
     * Redirects the user to the login screen
     */
    private void redirectLoginScreen(){
        Log.d(TAG, "redirectLoginScreen: redirecting to login screen.");

        Intent intent = new Intent(Register.this, Login.class);
        startActivity(intent);
        finish();
    }
    /**
     * Return true if @param 's1' matches @param 's2'
     * @param s1
     * @param s2
     * @return
     */
    private boolean doStringsMatch(String s1, String s2){
        return s1.equals(s2);
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
    @Override
    public void onStart() {
        super.onStart();
        isActivityRunning = true;
    }

    @Override
    public void onStop() {
        super.onStop();
        isActivityRunning = false;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        mDob.setText(currentDateString);
    }
    private String getTimestamp(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("Africa/Kigali"));
        return sdf.format(new Date());
    }
}
