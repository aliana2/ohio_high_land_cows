package com.yeealiana.custom_project_ohio_high_land_cows.ui.about;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AboutViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public AboutViewModel() {
        mText = new MutableLiveData<>();
        //mText.setValue("This is about fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}