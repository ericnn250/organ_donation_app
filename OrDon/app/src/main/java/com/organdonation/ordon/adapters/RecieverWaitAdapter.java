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

public class RecieverWaitAdapter extends RecyclerView.Adapter<RecieverWaitAdapter.RecievedViewHolder> implements Filterable {
    private ArrayList<OrganRequests> mReceivedtList;
    private ArrayList<OrganRequests> mReceivedListfull;

    public RecieverWaitAdapter(ArrayList<OrganRequests> requestsList) {
        this.mReceivedtList = requestsList;
        this.mReceivedListfull=new ArrayList<>(requestsList);
    }
    private RecieverWaitAdapter.OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(RecieverWaitAdapter.OnItemClickListener listener) {
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
            mReceivedtList.clear();
            mReceivedtList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    @NonNull
    @Override
    public RecievedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.reciever_wait_one_row, parent, false);
        RecievedViewHolder evh = new RecievedViewHolder(v,mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecievedViewHolder holder, int position) {
        OrganRequests currentItem=mReceivedtList.get(position);
        holder.mTextView1_name.setText(currentItem.getNames());
        holder.mTextView2_date.setText(currentItem.getRequest_date());
        holder.mTextView2_organ.setText(currentItem.getOrgantype());
        holder.mTextView2_status.setText(currentItem.getStatus());

    }

    @Override
    public int getItemCount() {
        return mReceivedtList.size();
    }

    public class RecievedViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView1_name, mTextView2_organ,mTextView2_date,mTextView2_status;
        public RecievedViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            mTextView1_name = itemView.findViewById(R.id.text_reciever_wait_name);
            mTextView2_organ = itemView.findViewById(R.id.text_receive_wait_organ);
            mTextView2_date = itemView.findViewById(R.id.text_receiver_wait_date);
            mTextView2_status = itemView.findViewById(R.id.textView_receiver_wait);
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
