package com.organdonation.ordon;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.organdonation.ordon.models.OrganRequests;

import java.util.ArrayList;

public class AssignmentManager {

    private static final String TAG = "ASSIGNEMENTMANAGER";

    static final String ASSIGMENT_CART = "assignement_cart";
    static final String CART_ITEMS = "user_id";
    Context mContext;
    SharedPreferences mSharedPreferences;
    SharedPreferences.Editor mEditor;
    ArrayList<OrganRequests> mOrganRequest;

    public AssignmentManager(Context mContext) {
        this.mContext = mContext;
        this.mSharedPreferences=mContext.getSharedPreferences(ASSIGMENT_CART, 0);
        this.mEditor=mSharedPreferences.edit();
    }
    public void assignUser(String userid){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = preferences.edit();

        //add the new products serial number (if it hasn't already been added)

        editor.putString(CART_ITEMS, userid);
        editor.commit();


    }
    //    public void assignDoctor(Doctor doctor){
//
//        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
//        SharedPreferences.Editor editor = preferences.edit();
//
//        //add the new products serial number (if it hasn't already been added)
//
//        editor.putString("doctor_id", doctor.getDoctor_id());
//        editor.commit();
//
//
//    }
//    public void assignDoctor(Donation donation){
//
//        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
//        SharedPreferences.Editor editor = preferences.edit();
//
//        //add the new products serial number (if it hasn't already been added)
//
//        editor.putString("donator_id", donation.getUserid());
//        editor.commit();
//
//
//    }
    public String  getUserId(){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        String user_ids = preferences.getString(CART_ITEMS, "");
        return  user_ids;
    }
}


