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

import androidx.recyclerview.widget.RecyclerView;

public class MyRequestAdapter  extends RecyclerView.Adapter<MyRequestAdapter.RequestViewHolder> implements Filterable {

    private ArrayList<OrganRequests> mRequestList;
    private ArrayList<OrganRequests> mRequestListfull;

    public MyRequestAdapter(ArrayList<OrganRequests> requestsList) {
        mRequestList = requestsList;
        mRequestListfull=new ArrayList<>(requestsList);
    }
    private MyRequestAdapter.OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(MyRequestAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    public static class RequestViewHolder extends RecyclerView.ViewHolder {
        //        public ImageView mImageView;
        public TextView mTextView1RDate;
        public TextView mTextView2ROrgan;

        public RequestViewHolder(View itemView, final MyRequestAdapter.OnItemClickListener listener) {

            super(itemView);
//            mImageView = itemView.findViewById(R.id.imageView);
            mTextView1RDate = itemView.findViewById(R.id.textView12requestDate);
            mTextView2ROrgan = itemView.findViewById(R.id.textView12req_organ);
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
    public MyRequestAdapter.RequestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_one_row, parent, false);
        MyRequestAdapter.RequestViewHolder evh = new MyRequestAdapter.RequestViewHolder(v,mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(MyRequestAdapter.RequestViewHolder holder, int position) {
        OrganRequests currentItem = mRequestList.get(position);

//        holder.mImageView.setImageResource(currentItem.getImageResource());
        holder.mTextView1RDate.setText(currentItem.getRequest_date());
        holder.mTextView2ROrgan.setText(currentItem.getOrgantype());
    }

    @Override
    public int getItemCount() {
        return mRequestList.size();
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
                filteredList.addAll(mRequestListfull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (OrganRequests item : mRequestListfull) {
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
            mRequestList.clear();
            mRequestList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
