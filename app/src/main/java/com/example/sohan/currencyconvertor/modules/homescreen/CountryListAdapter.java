package com.example.sohan.currencyconvertor.modules.homescreen;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sohan.currencyconvertor.R;
import com.example.sohan.currencyconvertor.models.CountryInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by SOHAN on 10/2/2017.
 */

public class CountryListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final CallBack mListener;
    private List<CountryInfo> mList;

    public CountryListAdapter(List<CountryInfo> list, CallBack callBack) {
        this.mList = list;
        this.mListener = callBack;
    }

    /**
     * updating the adapter with the new list
     * @param list
     */
    public void setData(List<CountryInfo> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    public class CountryListViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.country)
        TextView mCountryTxt;

        public CountryListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        CountryInfo countryInfo = mList.get(getAdapterPosition());
                        mListener.onClick(countryInfo);
                    }
                }
            });
        }

        public void bind(CountryInfo countryInfo) {
            mCountryTxt.setText(countryInfo.getCountryName());
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.coutry_item, parent, false);
        return new CountryListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CountryListViewHolder viewHolder = (CountryListViewHolder) holder;
        viewHolder.bind(mList.get(position));

    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    public interface CallBack {
        void onClick(CountryInfo countryInfo);
    }
}
