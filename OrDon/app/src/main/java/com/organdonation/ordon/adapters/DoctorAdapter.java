package com.organdonation.ordon.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.organdonation.ordon.R;
import com.organdonation.ordon.models.Doctor;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class DoctorAdapter  extends RecyclerView.Adapter<DoctorAdapter.DoctorViewHolder> implements Filterable {

    private ArrayList<Doctor> mDoctorList;
    private ArrayList<Doctor> mDoctorListfull;

    public DoctorAdapter(ArrayList<Doctor> doctorList) {
        mDoctorList = doctorList;
        mDoctorListfull=new ArrayList<>(doctorList);
    }
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class DoctorViewHolder extends RecyclerView.ViewHolder {
        //        public ImageView mImageView;
        public TextView mTextView_name;
        public TextView mTextView_hospital;

        public DoctorViewHolder(View itemView, final OnItemClickListener listener) {

            super(itemView);
//            mImageView = itemView.findViewById(R.id.imageView);
            mTextView_name= itemView.findViewById(R.id.textView_doc_name);
            mTextView_hospital = itemView.findViewById(R.id.textView_doc_hospital);
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
    public DoctorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_one_row, parent, false);
        DoctorViewHolder evh = new DoctorViewHolder(v,mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(DoctorViewHolder holder, int position) {
        Doctor currentItem = mDoctorList.get(position);

        //holder.mImageView.setImageResource(currentItem.getImageResource());
        holder. mTextView_name.setText(currentItem.getNames());
        holder.mTextView_hospital.setText(currentItem.getHospital());
    }

    @Override
    public int getItemCount() {
        return mDoctorList.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Doctor> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(mDoctorListfull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Doctor item : mDoctorListfull) {
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
            mDoctorList.clear();
            mDoctorList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
