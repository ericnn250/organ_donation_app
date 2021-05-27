package com.organdonation.ordon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.organdonation.ordon.models.User;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {


    private Button login,register;
    private Group inter,wait;
    private TextView noInternet;
    private static final String TAG="MAINACTIVITY";

    // Firebase
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupFirebaseAuth();
        setContentView(R.layout.activity_main);
        login=(Button) findViewById(R.id.buttonLogin);
        register=(Button) findViewById(R.id.buttonRegister);
        inter=(Group) findViewById(R.id.group2);
        wait=(Group) findViewById(R.id.group);
        noInternet=(TextView) findViewById(R.id.no_internet);
        if (isNetworkAvailable()) {

            inter.setVisibility(View.VISIBLE);
            wait.setVisibility(View.INVISIBLE);
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ///Toast.makeText(MainActivity.this,"login clicked",Toast.LENGTH_LONG).show();

                    //IS ALERADY LOGGED
                    // setupFirebaseAuth();

//                    if (!FirebaseAuth.getInstance().getCurrentUser().getUid().equals("")) {
//                        isAdmin();
//                    }
//                    else{
                    Intent intent = new Intent(MainActivity.this, WaitActivity.class);
                    startActivity(intent);
//                    }


                }
            });
            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentregister = new Intent(MainActivity.this, Register.class);
                    startActivity(intentregister);
                }
            });
        }
        else{
            wait.setVisibility(View.INVISIBLE);
            noInternet.setVisibility(View.VISIBLE);


        }
    }

    public boolean isNetworkAvailable(){
        ConnectivityManager connectivityManager
                =(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
    //    public boolean hasInternetConnection(Context context){
//        //if(isNetworkAvailable()){
//            try {
//                HttpURLConnection httpURLConnection = (HttpURLConnection) (new URL("https://www.google.com").openConnection());
//                httpURLConnection.setRequestProperty("User-Agent", "Test");
//                httpURLConnection.setRequestProperty("Connection", "close");
//                httpURLConnection.setConnectTimeout(1500);
//                httpURLConnection.connect();
//                return (httpURLConnection.getResponseCode() == 200);
//            }catch (IOException ex){
//                ex.printStackTrace();
//            }
//       // }
//        return false;
//    }
//
    private void setupFirebaseAuth(){
        Log.d(TAG, "setupFirebaseAuth: started.");
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {

                    //check if email is verified
//                if(user.isEmailVerified()){
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    //Toast.makeText(Login.this, "Authenticated with: " + user.getEmail(), Toast.LENGTH_SHORT).show();
                    //is admin
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
                                Intent intentadmin = new Intent(MainActivity.this, home.class);
                                intentadmin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intentadmin);
                                finish();
                            }
                            else {
                                Intent intentuser = new Intent(MainActivity.this, Userhome.class);
                                intentuser.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intentuser);
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
//                        if(isAdmin()){
//
//                        }else{
//
//                        }


//                }else{
//                    //Toast.makeText(Login.this, "Email is not Verified\nCheck your Inbox", Toast.LENGTH_SHORT).show();
//                    new TMessage(MainActivity.this,"Email is not Verified\nCheck your Inbox").show();
//                    FirebaseAuth.getInstance().signOut();
//                }

                } else {
                    Intent intent = new Intent(MainActivity.this, Login.class);
                    startActivity(intent);
                }
                // ...
            }
        };
    }
    private void isAdmin(){
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
                    Intent intentadmin = new Intent(MainActivity.this, home.class);
                    intentadmin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intentadmin);
                    finish();
                }
                else {
                    Intent intentuser = new Intent(MainActivity.this, Userhome.class);
                    intentuser.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intentuser);
                    finish();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        sendOnChannel1();
    }
    public void sendOnChannel1() {
        String title = "Organ Donation";
        String message = "Reminder";
        //for alram
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,00);
        calendar.set(Calendar.MINUTE,00);
        calendar.set(Calendar.SECOND,0);


        // if(Calendar.getInstance().getTimeInMillis()<= calendar.getTimeInMillis()){




        Intent broadcastIntent = new Intent(this, NotificationReceiver.class);

        broadcastIntent.putExtra("toastMessage", message);
        broadcastIntent.putExtra("BigMessage","Messages ");
        PendingIntent actionIntent = PendingIntent.getBroadcast(this,
                0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        //for alarm

        AlarmManager alarmManager= (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),3000,actionIntent);

        //  }


    }
}

