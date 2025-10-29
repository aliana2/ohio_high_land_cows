package com.yeealiana.custom_project_ohio_high_land_cows.ui.sweettreat;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SweettreatViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public SweettreatViewModel() {
        mText = new MutableLiveData<>();
        //mText.setValue("This is sweettreat fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}