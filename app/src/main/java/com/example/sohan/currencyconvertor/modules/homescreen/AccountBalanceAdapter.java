package com.example.sohan.currencyconvertor.modules.homescreen;

import android.os.Handler;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * AccountBalance Adapter class for showing current balance amount in homescreen activity
 */

public class AccountBalanceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = AccountBalanceAdapter.class.getSimpleName();
    private final OnItemClickListener mListener;
    private final Handler mHandler;
    private List<CountryInfo> mList;
    private List<AdapterViewHolder> lstHolders;
    private static final String EXTENSION = ".png";

    public AccountBalanceAdapter(List<CountryInfo> list, OnItemClickListener listener) {
        this.mList = list;
        this.mListener = listener;
        mHandler = new Handler();
        lstHolders = new ArrayList<>();
        startUpdateTimer();

    }

    public void setData(List<CountryInfo> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    private Runnable updateRemainingTimeRunnable = new Runnable() {
        @Override
        public void run() {
            synchronized (lstHolders) {
                for (AdapterViewHolder lstHolder : lstHolders) {
                    animateDotView(lstHolder);
                }
            }
        }
    };

    private void startUpdateTimer() {
        Timer tmr = new Timer();
        tmr.schedule(new TimerTask() {
            @Override
            public void run() {
                mHandler.post(updateRemainingTimeRunnable);
            }
        }, 1000, 1000);
    }

    @SuppressWarnings("unused")
    public class AdapterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.currency)
        TextView mCurrency;
        @BindView(R.id.amount)
        TextView mCurrentBalance;
        @BindView(R.id.sell)
        Button mSellBtn;
        @BindView(R.id.flag)
        ImageView mImageView;
        @BindView(R.id.dots)
        TextView mDotsTextView;
        private int mTotalDots = 4;
        private int dotsCount = 0;
        CountryInfo mCountryInfo;

        public AdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            if (mSellBtn != null) {
                mSellBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CountryInfo countryInfo = mList.get(getAdapterPosition());
                        mListener.OnSellButtonClick(countryInfo);
                    }
                });
            }
        }


        public void bind(final CountryInfo countryInfo) {
            mCountryInfo = countryInfo;
            mCurrency.setText(countryInfo.getCurrency());
            String amount = countryInfo.getmCurrentBalance();
            String currentBalance = amount + " " + countryInfo.getSymbol();
            mCurrentBalance.setText(currentBalance);
            String url = getFlagUrl(countryInfo);
            Glide.with(itemView.getContext()).load(url).into(mImageView);
           /* mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    synchronized (this) {
                        animateDotView(AdapterViewHolder.this);
                    }
                    mHandler.postDelayed(this, 500);

                }
            }, 500);*/
        }


        /**
         * Since we are not getting country's flag url from country info api call, So  we are trying
         * get it from other api
         *
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

    private  void animateDotView(AdapterViewHolder holder) {
        CountryInfo countryInfo = holder.mCountryInfo;
        StringBuilder builder = new StringBuilder();
        //int dotsCount = countryInfo.getDotsCount();
        holder.dotsCount++;
        holder.dotsCount = holder.dotsCount % holder.mTotalDots;
        for (int i = 0; i < holder.dotsCount; i++) {
            builder.append(". ");
        }
        holder.mDotsTextView.setText(builder.toString());
        //countryInfo.setDotsCount(dotsCount);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.balance_item, parent, false);
        AdapterViewHolder viewHolder = new AdapterViewHolder(view);
        synchronized (lstHolders) {
            lstHolders.add(viewHolder);
        }
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
