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

public class DonatedtAdapter extends RecyclerView.Adapter<DonatedtAdapter.DonatedAdapterViewHolder> implements Filterable {

    private ArrayList<Donation>  mDonatedList;
    private ArrayList<Donation>  mDonatedListfull;

    public DonatedtAdapter(ArrayList<Donation> mDonatedList) {
        this.mDonatedList = mDonatedList;
        this.mDonatedListfull = new ArrayList<>(mDonatedList);
    }

    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class DonatedAdapterViewHolder extends RecyclerView.ViewHolder {
        //        public ImageView mImageView;
        public TextView mTextView_donator_name;
        public TextView mTextView_donated_organ;
        public TextView mTextView_donated_date;

        public DonatedAdapterViewHolder(View itemView, final OnItemClickListener listener) {

            super(itemView);
            mTextView_donator_name= itemView.findViewById(R.id.textView12donated_name);
            mTextView_donated_organ= itemView.findViewById(R.id.textView12donated_organ);
            mTextView_donated_date = itemView.findViewById(R.id.textView12donated_Date);
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
    public  DonatedAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.donated_one_row, parent, false);
        DonatedAdapterViewHolder evh = new  DonatedAdapterViewHolder(v,mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder( DonatedAdapterViewHolder holder, int position) {
        Donation currentItem =  mDonatedList.get(position);

        holder.mTextView_donator_name.setText(currentItem.getNames());
        holder.mTextView_donated_organ.setText(currentItem.getOrgantype());
        holder.mTextView_donated_date.setText(currentItem.getDonation_date());
    }

    @Override
    public int getItemCount() {
        return mDonatedList.size();
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
                filteredList.addAll(mDonatedListfull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Donation item : mDonatedListfull) {
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
            mDonatedList.clear();
            mDonatedList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
