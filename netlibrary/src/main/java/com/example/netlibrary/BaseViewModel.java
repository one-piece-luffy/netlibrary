package com.example.netlibrary;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

/**
 * Created by txl on 2019/1/8.
 */

public class BaseViewModel extends AndroidViewModel {

    protected BaseRequestModel mBaseModel;


    protected MutableLiveData<String> toastMessage = new MutableLiveData<>();

    public BaseViewModel(@NonNull Application application) {
        super(application);
        mBaseModel = new BaseRequestModel(getClass().getName() + System.currentTimeMillis());
    }

    public LiveData<String> getToastMessage() {
        return toastMessage;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mBaseModel.cancelRequests();
    }

}
