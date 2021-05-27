package com.organdonation.ordon.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.organdonation.ordon.R;
import com.organdonation.ordon.models.Contact;
import com.organdonation.ordon.models.User;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedViewHolder> implements Filterable {

    private ArrayList<Contact> mUserList;
    private ArrayList<Contact> mUserListfull;


    public FeedAdapter(ArrayList<Contact> UserList) {
        mUserList = UserList;
        mUserListfull=new ArrayList<>(UserList);
    }
    private FeedAdapter.OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(FeedAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    public static class FeedViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextname;
        public TextView mTextmessage;
        public TextView mTexttimehappen;
        public FeedViewHolder(View itemView, final FeedAdapter.OnItemClickListener listener) {

            super(itemView);
            mTextname = itemView.findViewById(R.id.textView_feed_name);
            mTextmessage= itemView.findViewById(R.id.textView_feed_message);
            mTexttimehappen= itemView.findViewById(R.id.textView_feed_time);

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
    public FeedAdapter.FeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_feed, parent, false);
        FeedAdapter.FeedViewHolder evh = new FeedAdapter.FeedViewHolder(v,mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(FeedAdapter.FeedViewHolder holder, int position) {
        Contact currentItem = mUserList.get(position);

        holder.mTextname.setText(currentItem.getUsername());
        holder.mTextmessage.setText(currentItem.getMessage());
        holder.mTexttimehappen.setText(currentItem.getDatedone());
    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }

    @Override
    public Filter getFilter() {
        return userFilter;
    }

    private Filter userFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Contact> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(mUserListfull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Contact item : mUserListfull) {
                    if (item.getUsername().toLowerCase().contains(filterPattern)) {
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
            mUserList.clear();
            mUserList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}

