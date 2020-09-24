package com.example.netlibrary;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.netlibrary.okhttps.OkhttpsStrategy;
import com.example.netlibrary.volley.VolleyStrategy;

/**
 * Created by txl on 2019/1/8.
 */

public class BaseViewModel extends AndroidViewModel {

    public  String mRequestTag;

    protected RequestStrategy mBaseModel;


    protected MutableLiveData<String> toastMessage = new MutableLiveData<>();

    public BaseViewModel(@NonNull Application application) {
        super(application);
        mRequestTag = getClass().getName() + System.currentTimeMillis();
        mBaseModel = BPRequest.getInstance().mStrategy;
    }

    public LiveData<String> getToastMessage() {
        return toastMessage;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mBaseModel.cancelRequests(mRequestTag);
    }

}
