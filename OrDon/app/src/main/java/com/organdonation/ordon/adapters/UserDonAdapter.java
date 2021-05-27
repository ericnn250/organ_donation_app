package com.organdonation.ordon.adapters;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.organdonation.ordon.R;
import com.organdonation.ordon.models.Donation;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public  class UserDonAdapter extends RecyclerView.Adapter<UserDonAdapter.UserDonViewHolder> implements Filterable {

    private ArrayList<Donation> mReceivedtList;
    private java.util.ArrayList<Donation> mReceivedListfull;


    public UserDonAdapter(ArrayList<Donation> mReceivedtList) {
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
            List<Donation> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(mReceivedListfull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Donation item : mReceivedListfull) {
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
    public UserDonAdapter.UserDonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_donation_one_row, parent, false);
        UserDonAdapter.UserDonViewHolder evh = new UserDonAdapter.UserDonViewHolder(v,mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull UserDonAdapter.UserDonViewHolder holder, int position) {
        Donation currentItem=mReceivedtList.get(position);
        holder.mTextView1_no.setText(String.valueOf(position +1));
        holder.mTextView2_date.setText(currentItem.getDonation_date());
        holder.mTextView2_organ.setText(currentItem.getOrgantype());
        holder.mTextView2_doctor.setText(currentItem.getDoctorname());
        holder.mTextView1_donor.setText(currentItem.getRecipientnames());
        holder.mTextView1_status.setText(currentItem.getStatus());


    }

    @Override
    public int getItemCount() {
        return mReceivedtList.size();
    }

    public class UserDonViewHolder extends RecyclerView.ViewHolder{
        public TextView mTextView1_no, mTextView2_organ,mTextView2_date,mTextView2_doctor,mTextView1_donor,mTextView1_status;
        public UserDonViewHolder(@NonNull View itemView, final UserReqAdapter.OnItemClickListener listener) {
            super(itemView);
            mTextView1_no = itemView.findViewById(R.id.userd_no);
            mTextView2_organ = itemView.findViewById(R.id.userD_organe);
            mTextView2_date = itemView.findViewById(R.id.userD_date);
            mTextView2_doctor = itemView.findViewById(R.id.userD_doctor);
            mTextView1_donor = itemView.findViewById(R.id.userD_recipient);
            mTextView1_status = itemView.findViewById(R.id.user_donation_status);
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
