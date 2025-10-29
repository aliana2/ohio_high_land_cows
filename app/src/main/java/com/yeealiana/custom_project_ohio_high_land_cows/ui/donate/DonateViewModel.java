package com.yeealiana.custom_project_ohio_high_land_cows.ui.donate;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DonateViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public DonateViewModel() {
        mText = new MutableLiveData<>();
        //mText.setValue("This is donate fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}