package com.kimia.tomer.expirationdatereminders;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kimia.tomer.expirationdatereminders.models.Expiration;


public class ExpirationListAdapter extends RecyclerView.Adapter<ExpirationListAdapter.ExpirationViewHolder> {
    private Expiration[] listData;

    public ExpirationListAdapter() {}

    @Override
    public void onBindViewHolder(ExpirationViewHolder holder, int position) {
        Expiration item = listData[position];
        holder.mItemName.setText(item.getName());
        holder.mItemDate.setText(Long.toString(item.getDaysLeft()));
        holder.itemView.setTag(item.getId());
    }

    @Override
    public ExpirationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.expiration_list_item, parent, false);
        return new ExpirationViewHolder(listItem);
    }

    @Override
    public int getItemCount() {
        return (listData == null) ? 0 : listData.length;
    }

    public class ExpirationViewHolder extends RecyclerView.ViewHolder {
        public TextView mItemName;
        public TextView mItemDate;

        public ExpirationViewHolder(View v) {
            super(v);
            mItemDate = (TextView) v.findViewById(R.id.textview_expiration_list_item_date);
            mItemName = (TextView) v.findViewById(R.id.textview_expiration_list_item_name);
        }
    }

    public void setListData(Expiration[] listData) {
        this.listData = listData;
        notifyDataSetChanged();
    }
}
