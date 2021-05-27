package com.organdonation.ordon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.organdonation.ordon.models.User;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class TestingAdapter extends RecyclerView.Adapter<TestingAdapter.AnimalViewHolder> implements Filterable {

    private ArrayList<User> mExampleList;
    private ArrayList<User> mExampleListfull;
    private Context context;

    public TestingAdapter(Context context, ArrayList<User> exampleList) {
        this.context=context;
        mExampleList = exampleList;
        mExampleListfull=new ArrayList<>(exampleList);
    }
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class AnimalViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextname;
        public TextView mTextcontact,user_role;
        // public TextView mTextView2;

        public AnimalViewHolder(View itemView, final OnItemClickListener listener) {

            super(itemView);
            mTextname = itemView.findViewById(R.id.textView_name_row_u);
            mTextcontact= itemView.findViewById(R.id.textView_row_u_email);
            user_role=itemView.findViewById(R.id.user_role);
            //  mTextView2 = itemView.findViewById(R.id.textView2);
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
    public AnimalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_one_row, parent, false);
        AnimalViewHolder evh = new AnimalViewHolder(v,mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(AnimalViewHolder holder, int position) {
       User currentItem = mExampleList.get(position);

        String userole="";
        holder.mTextname.setText(currentItem.getName());
        holder.mTextcontact.setText(currentItem.getPhone());
        holder.user_role.setText(userole);
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<User> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(mExampleListfull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (User item : mExampleListfull) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
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
            mExampleList.clear();
            mExampleList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

}
