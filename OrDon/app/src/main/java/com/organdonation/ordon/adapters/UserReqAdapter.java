package com.organdonation.ordon.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.organdonation.ordon.R;
import com.organdonation.ordon.models.OrganRequests;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UserReqAdapter extends RecyclerView.Adapter<UserReqAdapter.UserReqViewHolder> implements Filterable {

    private ArrayList<OrganRequests> mReceivedtList;
    private java.util.ArrayList<OrganRequests> mReceivedListfull;


    public UserReqAdapter(ArrayList<OrganRequests> mReceivedtList) {
        this.mReceivedtList = mReceivedtList;
        this.mReceivedListfull = new ArrayList<>(mReceivedtList);

    }
    private UserReqAdapter.OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(UserReqAdapter.OnItemClickListener listener) {
        mListener = listener;
    }
    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<OrganRequests> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(mReceivedListfull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (OrganRequests item : mReceivedListfull) {
                    if (item.getNames().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mReceivedtList.clear();
            mReceivedtList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    @NonNull
    @Override
    public UserReqViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_request_one_row, parent, false);
        UserReqViewHolder evh = new UserReqViewHolder(v,mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull UserReqViewHolder holder, int position) {
        OrganRequests currentItem=mReceivedtList.get(position);
        holder.mTextView1_no.setText(String.valueOf(position +1));
        holder.mTextView2_date.setText(currentItem.getRequest_date());
        holder.mTextView2_organ.setText(currentItem.getOrgantype());
        holder.mTextView2_doctor.setText(currentItem.getDoctorname());
        holder.mTextView1_donor.setText(currentItem.getDonornames());
        holder.mTextView1_status.setText(currentItem.getStatus());


    }

    @Override
    public int getItemCount() {
        return mReceivedtList.size();
    }

    public class UserReqViewHolder extends RecyclerView.ViewHolder{
        public TextView mTextView1_no, mTextView2_organ,mTextView2_date,mTextView2_doctor,mTextView1_donor,mTextView1_status;
        public UserReqViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            mTextView1_no = itemView.findViewById(R.id.userR_no);
            mTextView2_organ = itemView.findViewById(R.id.userR_organe);
            mTextView2_date = itemView.findViewById(R.id.userR_date);
            mTextView2_doctor = itemView.findViewById(R.id.userR_doctor);
            mTextView1_donor = itemView.findViewById(R.id.userR_donor);
            mTextView1_status = itemView.findViewById(R.id.user_request_tatus);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}