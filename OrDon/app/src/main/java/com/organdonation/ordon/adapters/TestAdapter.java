package com.organdonation.ordon.adapters;


import android.content.Context;
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

import androidx.recyclerview.widget.RecyclerView;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.RAdapterViewHolder> implements Filterable {

    Context mCntx;
    public ArrayList<OrganRequests> arrayList;
    public ArrayList<OrganRequests> arrayListFiltered;

    public TestAdapter(Context mCntx, ArrayList<OrganRequests> arrayList)
    {
        this.mCntx = mCntx;
        this.arrayList = arrayList;
        this.arrayListFiltered = new ArrayList<>(arrayList);
    }

    @Override
    public int getItemCount()
    {
        return arrayList.size();
    }

    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public RAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recieved_one_row, parent, false);

        RAdapterViewHolder viewHolder = new RAdapterViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RAdapterViewHolder holder, final int position)
    {
        final OrganRequests currentItem = arrayList.get(position);

        holder.mTextView1_name.setText(currentItem.getNames());
        holder.mTextView2_date.setText(currentItem.getRequest_date());
        holder.mTextView2_organ.setText(currentItem.getOrgantype());
        holder.mTextView2_status.setText(currentItem.getStatus());

    }


    public class RAdapterViewHolder extends RecyclerView.ViewHolder
    {
        public TextView mTextView1_name, mTextView2_organ,mTextView2_date,mTextView2_status;

        public RAdapterViewHolder(View itemView) {
            super(itemView);
            mTextView1_name = itemView.findViewById(R.id.text_recieved_name);
            mTextView2_organ = itemView.findViewById(R.id.text_received_organ);
            mTextView2_date = itemView.findViewById(R.id.text_received_date);
            mTextView2_status = itemView.findViewById(R.id.textView_received);
        }
    }

    public Filter getFilter()
    {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<OrganRequests> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(arrayListFiltered);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (OrganRequests item : arrayListFiltered) {
                    if (item.getOrgantype().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {
            arrayList.clear();
            arrayList.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };
}
