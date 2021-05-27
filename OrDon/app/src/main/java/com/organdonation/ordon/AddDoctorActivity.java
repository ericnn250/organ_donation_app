package com.organdonation.ordon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.organdonation.ordon.dialog.TMessage;
import com.organdonation.ordon.models.Doctor;

public class AddDoctorActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Button add,cancel;
    private EditText doctorNames,doctorEmail,mcontact,mhospital,madress,mprovince,mdistrict;
    private RelativeLayout mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_doctor);
        toolbar=(Toolbar) findViewById(R.id.toolbar4add_doctor);
        toolbar.setTitle(getResources().getString(R.string.app_name));

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        add=(Button) findViewById(R.id.btn_doctor_add);
        doctorNames=(EditText)findViewById(R.id.input_doctorname);
        doctorEmail=(EditText)findViewById(R.id.input_doctor_email);
        mcontact=(EditText)findViewById(R.id.input_doctor_contact);
        mhospital=(EditText)findViewById(R.id.input_doctor_hospital);
        madress=(EditText)findViewById(R.id.input_doctor_hospital_adres);
        mprovince=(EditText)findViewById(R.id.input_doctor_province);
        mdistrict=(EditText)findViewById(R.id.input_district);
        cancel=(Button)  findViewById(R.id.btn_doctor_add_cancel);
        mProgressBar = (RelativeLayout) findViewById(R.id.progressBarAddnewd);
        hideSoftKeyboard();
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddDoctorActivity.this,DoctorActivity.class));
                finish();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendDoctorInfo(doctorNames.getText().toString(),
                        doctorEmail.getText().toString(),
                        mcontact.getText().toString(),
                        mhospital.getText().toString(),
                        madress.getText().toString(),
                        mprovince.getText().toString(),
                        mdistrict.getText().toString()

                );
            }

        });
    }
    private void sendDoctorInfo(String names,
                                String email,
                                String contact,
                                String hospital,
                                String adress,
                                String province,
                                String district){
        if (names.equals("")||email.equals("")||contact.equals("")||hospital.equals("")|| adress.equals("")||province.equals("")||district.equals("")){
            Toast.makeText(getApplicationContext(),"All Field Required please",Toast.LENGTH_LONG).show();
            return;
        }
        showDialog();
        Doctor doctor=new Doctor();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        //get the new chatroom unique id
        String doctor_id = reference
                .child(getString(R.string.dbnode_doctors))
                .push().getKey();
        //String doctor_id=FirebaseAuth.getInstance().getCurrentUser().getUid()+String.valueOf(new Random().nextInt(100));
        doctor.setNames(names);
        doctor.setEmail(email);
        doctor.setDoctor_id(doctor_id);
        doctor.setContact(contact);
        doctor.setAdress(adress);
        doctor.setHospital(hospital);
        doctor.setProvince(province);
        doctor.setDisctrict(district);
        FirebaseDatabase.getInstance().getReference()
                .child(getString(R.string.dbnode_doctors))
                .child(doctor_id)
                .setValue(doctor)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            //Toast.makeText(AddDoctorActivity.this, "Registration succeed.", Toast.LENGTH_SHORT).show();
                            doctorNames.setText("");
                                    doctorEmail.setText("");
                                    mcontact.setText("");
                                    mhospital.setText("");
                                    madress.setText("");
                                    mprovince.setText("");
                                    mdistrict.setText("");
                                    hideDialog();
                            new TMessage(AddDoctorActivity.this, "Registration succeed.").show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Toast.makeText(Register.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                // Toast.makeText(AddDoctorActivity.this, "something went wrong.", Toast.LENGTH_SHORT).show();
                new TMessage(AddDoctorActivity.this, "something went wrong.").show();


                //redirect the user to the login screen

            }
        });
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
