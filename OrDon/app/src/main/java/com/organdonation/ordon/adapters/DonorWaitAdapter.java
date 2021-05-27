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

import androidx.recyclerview.widget.RecyclerView;

public class DonorWaitAdapter extends RecyclerView.Adapter<DonorWaitAdapter.DWAdapterViewHolder> implements Filterable {

    private ArrayList<Donation> mDonationwList;
    private ArrayList<Donation> mDonationwListfull;

    public DonorWaitAdapter(ArrayList<Donation> mDonationwList) {
        this.mDonationwList = mDonationwList;
        this.mDonationwListfull=new ArrayList<>(mDonationwList);
    }
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class DWAdapterViewHolder extends RecyclerView.ViewHolder {
        //        public ImageView mImageView;
        public TextView mTextView1_name;
        public TextView mTextView2_organ;
        public TextView mTextView2_date;
        public TextView mTextView2_status;


        public DWAdapterViewHolder(View itemView, final OnItemClickListener listener) {

            super(itemView);
//            mImageView = itemView.findViewById(R.id.imageView);
            mTextView1_name = itemView.findViewById(R.id.text_wait_donate_name);
            mTextView2_organ = itemView.findViewById(R.id.text_wait_donated_organ);
            mTextView2_date = itemView.findViewById(R.id.textView_d_date);
            mTextView2_status = itemView.findViewById(R.id.textView_d_pending);
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


    @Override
    public DWAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.donor_wait_one_row, parent, false);
        DWAdapterViewHolder evh = new DWAdapterViewHolder(v,mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(DWAdapterViewHolder holder, int position) {
        Donation currentItem = mDonationwList.get(position);

//        holder.mImageView.setImageResource(currentItem.getImageResource());
        holder.mTextView1_name.setText(currentItem.getNames());
        holder.mTextView2_organ.setText(currentItem.getOrgantype());
        holder.mTextView2_date.setText(currentItem.getDonation_date());
        holder.mTextView2_status.setText(currentItem.getStatus());
    }

    @Override
    public int getItemCount() {
        return mDonationwList.size();
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
                filteredList.addAll(mDonationwListfull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Donation item : mDonationwListfull) {
                    if (item.getOrgantype().toLowerCase().contains(filterPattern)) {
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
            mDonationwList.clear();
            mDonationwList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}

