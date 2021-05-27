package com.organdonation.ordon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.organdonation.ordon.dialog.TMessage;
import com.organdonation.ordon.models.Doctor;

public class EditDoctor extends AppCompatActivity {

    private Toolbar toolbar;
    private Button save;
    private EditText doctorNames,doctorEmail,contact,hospital,adress,province,district;
    private String id;
    private static final String TAG="EditDoctor Activity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_doctor);
        toolbar=(Toolbar) findViewById(R.id.toolbar4edit_doctor);
        toolbar.setTitle(getResources().getString(R.string.app_name));

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent user=getIntent();
        id=user.getStringExtra("did");
        save=(Button) findViewById(R.id.btn_doctor_save);
        doctorNames=(EditText)findViewById(R.id.input_doctornameedit);
        doctorEmail=(EditText)findViewById(R.id.input_doctor_emailedit);
        contact=(EditText)findViewById(R.id.input_doctor_contactedit);
        hospital=(EditText)findViewById(R.id.input_doctor_hospitaledit);
        adress=(EditText)findViewById(R.id.input_doctor_hospital_adresedit);
        province=(EditText)findViewById(R.id.input_doctor_provinceedit);
        district=(EditText)findViewById(R.id.input_districtedit);
        getUserAccountData();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                /*
                ------ Change Name -----
                 */
                if(!doctorNames.getText().toString().equals("")){
                    reference.child(getString(R.string.dbnode_doctors))
                            .child(id)
                            .child(getString(R.string.field_name_doctor))
                            .setValue(doctorNames.getText().toString());
                }


                /*
                ------ Change Phone Number -----
                 */
                if(!contact.getText().toString().equals("")){
                    reference.child(getString(R.string.dbnode_doctors))
                            .child(id)
                            .child(getString(R.string.field_phone_doctor))
                            .setValue(contact.getText().toString());
                }
                  /*
                ------ Change email -----
                 */
                if(!doctorEmail.getText().toString().equals("")){
                    reference.child(getString(R.string.dbnode_doctors))
                            .child(id)
                            .child(getString(R.string.field_email_doctor))
                            .setValue(doctorEmail.getText().toString());
                }


                /*
                ------ Change Hospital -----
                 */
                if(!hospital.getText().toString().equals("")){
                    reference.child(getString(R.string.dbnode_doctors))
                            .child(id)
                            .child(getString(R.string.field_hospital_doctor))
                            .setValue(hospital.getText().toString());
                }
                 /*
                ------ Change Province -----
                 */
                if(!province.getText().toString().equals("")){
                    reference.child(getString(R.string.dbnode_doctors))
                            .child(id)
                            .child(getString(R.string.field_province_doctor))
                            .setValue(province.getText().toString());
                }


                /*
                ------ Change address -----
                 */
                if(!adress.getText().toString().equals("")){
                    reference.child(getString(R.string.dbnode_doctors))
                            .child(id)
                            .child(getString(R.string.field_adress_doctor))
                            .setValue(adress.getText().toString());
                }
                  /*
                ------ Change district -----
                 */
                if(!district.getText().toString().equals("")){
                    reference.child(getString(R.string.dbnode_doctors))
                            .child(id)
                            .child(getString(R.string.field_district_doctor))
                            .setValue(district.getText().toString());
                }

                new TMessage(EditDoctor.this,"Change saved!").show();
                startActivity(new Intent(EditDoctor.this,ViewDoctor.class));
            }

        });
    }

    private void getUserAccountData(){
        Log.d(TAG, "getUserAccountData: getting the user's account information");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        /*
            ---------- QUERY Method 1 ----------
         */
        Log.d(TAG, "getUserAccountData: doctor id"+id);
        Query query1 = reference.child(getString(R.string.dbnode_doctors))
                .orderByKey()
                .equalTo(id);
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //this loop will return a single result
                for(DataSnapshot singleSnapshot: dataSnapshot.getChildren()){
                    Log.d(TAG, "onDataChange: (QUERY METHOD 1) found user: "
                            + singleSnapshot.getValue(Doctor.class).toString());
                    Doctor user = singleSnapshot.getValue(Doctor.class);
                    doctorNames.setText(user.getNames());
                    contact.setText(user.getContact());
                    doctorEmail.setText(user.getEmail());
                    adress.setText(user.getAdress());
                    hospital.setText(user.getHospital());
                    province.setText(user.getProvince());
                    district.setText(user.getDisctrict());

                    // ImageLoader.getInstance().displayImage(user.getProfile_image(), mProfileImage);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }
}
