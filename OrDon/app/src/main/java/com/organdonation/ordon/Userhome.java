package com.organdonation.ordon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.firebase.auth.FirebaseAuth;

public class Userhome extends AppCompatActivity {

    private RelativeLayout requestOrgan,donateOrgan;
    private ImageView newRequest,newDonation;
    private RelativeLayout contactus;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userhome);
        toolbar =  findViewById(R.id.toolbar3);
        toolbar.setTitle(getResources().getString(R.string.app_name));


        setSupportActionBar(toolbar);
        requestOrgan=(RelativeLayout) findViewById(R.id.request_organ);
        donateOrgan=(RelativeLayout) findViewById(R.id.donate_organ);
        contactus=(RelativeLayout) findViewById(R.id.contact_us);
        newDonation=(ImageView) findViewById(R.id.new_donation);
        newRequest=(ImageView) findViewById(R.id.new_request);
        newRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_request=new Intent(Userhome.this,RequestOrganActivity.class);
                startActivity(intent_request);
            }
        });
        newDonation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_donate=new Intent(Userhome.this,DonateOrganActivity.class);
                startActivity(intent_donate);
            }
        });
        contactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent contact_intent=new Intent(Userhome.this,Contactus.class);
                startActivity(contact_intent);
            }
        });
        requestOrgan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent p_request=new Intent(Userhome.this,PrevioiusRequestActivity.class);
                startActivity(p_request);
            }
        });
        donateOrgan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent p_donate=new Intent(Userhome.this,PreviousDonationActivity.class);
                startActivity(p_donate);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()){
            case R.id.optionSignOut:
                signOut();
                return true;
            case R.id.optionAccountSettings:
                intent = new Intent(Userhome.this, SettingsActivity.class);
                startActivity(intent);
                return true;
//            case R.id.optionChat:
//                intent = new Intent(Userhome.this, ChatActivity.class);
//                startActivity(intent);
//                return true;
//            case R.id.optionAdmin:
//                if(mIsAdmin){
//                    intent = new Intent(Userhome.this, AdminActivity.class);
//                    startActivity(intent);
//                }else{
//                    Toast.makeText(this, "You're not an Admin", Toast.LENGTH_SHORT).show();
//                }
//
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void signOut() {
        Log.d("USERhomeActivity", "signOut: signing out");
        FirebaseAuth.getInstance().signOut();
        Intent signout_intent=new Intent(Userhome.this,Login.class);
        startActivity(signout_intent);
        finish();
    }

}
