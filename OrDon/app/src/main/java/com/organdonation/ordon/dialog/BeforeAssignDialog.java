package com.organdonation.ordon.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.organdonation.ordon.AssignDoctor;
import com.organdonation.ordon.R;
import com.organdonation.ordon.models.OrganRequests;

public class BeforeAssignDialog extends Dialog implements
        android.view.View.OnClickListener{
    private OrganRequests organRequests;
    private Activity activity;
    private Context context;
    private TextView rmNames,rmPhone,rmaddress,rmdob,rmcomment,bloodtype,organtype,status,requestedDate,ref;
    private Button btn_assign,btn_cancel;
    private ImageView close;

    public BeforeAssignDialog(Context a, OrganRequests user) {
        super(a);
        this.activity=(Activity)a;
        this.context=a;
        this.organRequests=user;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.before_assign_dialog);
        bloodtype=(TextView) findViewById(R.id.dialog_blodtype);
        organtype=(TextView)   findViewById(R.id.dialog_organ);
        status=(TextView) findViewById(R.id.dialog_status) ;
        requestedDate=(TextView) findViewById(R.id.dialog_date) ;
        rmNames=(TextView)  findViewById(R.id.dialog_name);
        rmPhone=(TextView)  findViewById(R.id.dialog_contact);
        rmaddress=(TextView)  findViewById(R.id.dialog_adres);
        rmdob=(TextView)  findViewById(R.id.dialog_bd);
        rmcomment=(TextView)  findViewById(R.id.dialog_comment);
        btn_assign=(Button)   findViewById(R.id.assign);
        btn_cancel=(Button)findViewById(R.id.cancel_assign);
        close=(ImageView) findViewById(R.id.close);
        ref=(TextView) findViewById(R.id.dialog_email) ;

        rmNames.setText(organRequests.getNames());
        rmPhone.setText(organRequests.getPhone());
        requestedDate.setText(organRequests.getRequest_date());
        rmaddress.setText(organRequests.getAddress());
        rmdob.setText(organRequests.getDob());
        rmcomment.setText(organRequests.getNote());
        status.setText(organRequests.getStatus());
        bloodtype.setText(organRequests.getBloodtype());
        organtype.setText(organRequests.getOrgantype());
        ref.setText(organRequests.getRef_number());
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        btn_assign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(context, AssignDoctor.class);
                intent.putExtra("id",organRequests.getRequestid());
                intent.putExtra("ruid",organRequests.getUserid());
                intent.putExtra("names",organRequests.getNames());
                intent.putExtra("type",organRequests.getOrgantype());
                context.startActivity(intent);
                dismiss();
            }
        });


    }

    @Override
    public void onClick(View v) {

    }
}
