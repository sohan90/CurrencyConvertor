package com.example.sohan.currencyconvertor.modules.homescreen;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sohan.currencyconvertor.R;
import com.example.sohan.currencyconvertor.models.CountryInfo;
import com.example.sohan.currencyconvertor.utils.LogUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * AccountBalance Adapter class for showing current balance amount in homescreen activity
 */

public class AccountBalanceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = AccountBalanceAdapter.class.getSimpleName();
    private final OnItemClickListener mListener;
    private List<CountryInfo> mList;
    private static final String EXTENSION = ".png";

    public AccountBalanceAdapter(List<CountryInfo> list, OnItemClickListener listener) {
        this.mList = list;
        this.mListener = listener;

    }

    public void setData(List<CountryInfo> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    public class AdapterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.currency)
        TextView mCurrency;
        @BindView(R.id.amount)
        TextView mCurrentBalance;
        @BindView(R.id.sell)
        Button mSellBtn;
        @BindView(R.id.flag)
        ImageView mImageView;

        public AdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            if(mSellBtn != null) {
                mSellBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CountryInfo countryInfo = mList.get(getAdapterPosition());
                        mListener.OnSellButtonClick(countryInfo);
                    }
                });
            }
        }

        public void bind(CountryInfo countryInfo) {
            mCurrency.setText(countryInfo.getCurrency());
            String amount = countryInfo.getmCurrentBalance();
            String currentBalance = amount + " " + countryInfo.getSymbol();
            mCurrentBalance.setText(currentBalance);
            String url = getFlagUrl(countryInfo);
            Glide.with(itemView.getContext()).load(url).into(mImageView);
        }

        /**
         * Since we are not getting country's flag url from country info api call, So  we are trying
         * get it from other api
         * @param countryInfo
         * @return
         */
        private String getFlagUrl(CountryInfo countryInfo) {
            String isoCode3 = countryInfo.getIsoCode();
            String isoCode2 = isoCode3.substring(0, 2);
            LogUtils.LOGD(TAG, "isoCode2 string " + isoCode2);
            String url = itemView.getContext().getString(R.string.country_flag_url) + isoCode2 + EXTENSION;
            return url;
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
        CountryInfo countryInfo = mList.get(position);
        AdapterViewHolder viewHolder = (AdapterViewHolder) holder;
        viewHolder.bind(countryInfo);

    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    public interface OnItemClickListener {
        void OnSellButtonClick(CountryInfo countryInfo);
    }
}
