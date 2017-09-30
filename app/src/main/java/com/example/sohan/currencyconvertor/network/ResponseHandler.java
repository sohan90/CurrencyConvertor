package com.example.sohan.currencyconvertor.network;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Wrapper class for retrofit response callback
 * @param <T>
 */
public class ResponseHandler<T> implements Callback<T> {

    private ResponseCallBack mResponseCallBack;

    public ResponseHandler(ResponseCallBack callBack) {
        mResponseCallBack = callBack;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.isSuccessful()) {
            mResponseCallBack.onSuccess(response.body());
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        mResponseCallBack.onFailure("error");

    }

    public interface ResponseCallBack<T> {
        void onSuccess(T t);

        void onFailure(String error);
    }

}
