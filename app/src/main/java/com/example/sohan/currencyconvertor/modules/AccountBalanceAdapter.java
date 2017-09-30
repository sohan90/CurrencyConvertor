package com.example.sohan.currencyconvertor.modules;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sohan.currencyconvertor.R;
import com.example.sohan.currencyconvertor.model.CountryInfo;

import java.util.List;

/**
 * Created by sohan on 30/9/17.
 */

public class AccountBalanceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<CountryInfo> mList;

    public AccountBalanceAdapter(List<CountryInfo> list) {
        mList = list;
    }

    public void setData(List<CountryInfo> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    public static class AdapterViewHolder extends RecyclerView.ViewHolder {
        public AdapterViewHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.balance_item, parent, false);
        AdapterViewHolder viewHolder = new AdapterViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }
}
