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

public class MyDonationAdapter extends RecyclerView.Adapter<MyDonationAdapter.DonationViewHolder> implements Filterable {

    private ArrayList<Donation> mDonationList;
    private ArrayList<Donation> mDonationListfull;

    public MyDonationAdapter(ArrayList<Donation> donationList) {
        mDonationList = donationList;
        mDonationListfull=new ArrayList<>(donationList);
    }
    private MyDonationAdapter.OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(MyDonationAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    public static class DonationViewHolder extends RecyclerView.ViewHolder {
        //        public ImageView mImageView;
        public TextView mTextViewDate;
        public TextView mTextViewOrgan;

        public DonationViewHolder(View itemView, final MyDonationAdapter.OnItemClickListener listener) {

            super(itemView);
//            mImageView = itemView.findViewById(R.id.imageView);
            mTextViewDate = itemView.findViewById(R.id.textView12donateDate);
            mTextViewOrgan = itemView.findViewById(R.id.textView12donateorgan);
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
    public MyDonationAdapter.DonationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.donation_one_row, parent, false);
        MyDonationAdapter.DonationViewHolder evh = new MyDonationAdapter.DonationViewHolder(v,mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(MyDonationAdapter.DonationViewHolder holder, int position) {
        Donation currentItem = mDonationList.get(position);

//        holder.mImageView.setImageResource(currentItem.getImageResource());
        holder.mTextViewDate.setText(currentItem.getDonation_date());
        holder.mTextViewOrgan.setText(currentItem.getOrgantype());
    }

    @Override
    public int getItemCount() {
        return mDonationList.size();
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
                filteredList.addAll(mDonationListfull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Donation item : mDonationListfull) {
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
            mDonationList.clear();
            mDonationList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
