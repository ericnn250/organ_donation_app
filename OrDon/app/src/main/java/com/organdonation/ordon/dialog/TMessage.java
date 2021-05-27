package com.organdonation.ordon.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.organdonation.ordon.R;

import androidx.annotation.NonNull;

public class TMessage  extends Dialog {
    //variable
    private String  message;
    private Context context;
    //widget
    private TextView message_textview;
    private Button ok_btn;
    public TMessage(@NonNull Context context, String message) {
        super(context);
        this.context=context;
        this.message=message;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.messages_dialog);
        message_textview=(TextView)findViewById(R.id.messages_Textview);
        ok_btn=(Button) findViewById(R.id.ok_btn) ;
        message_textview.setText(message);
        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
