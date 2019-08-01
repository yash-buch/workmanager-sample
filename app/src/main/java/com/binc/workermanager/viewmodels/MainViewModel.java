package com.binc.workermanager.viewmodels;

import android.util.Log;

import androidx.databinding.Bindable;
import androidx.databinding.Observable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel implements Observable {
    private static String TAG = MainViewModel.class.getSimpleName();
    public String fileText;
    public MutableLiveData<String> _fileText = new MutableLiveData<>();

    public MainViewModel() {
        _fileText.setValue("");
    }

    @Bindable
    public String getFileText() {
        return fileText;
    }

    public void setFileText(String val) {
        fileText = val;
        _fileText.setValue(val);
        Log.i(TAG, fileText);
    }

    @Override
    public void addOnPropertyChangedCallback(OnPropertyChangedCallback callback) {

    }

    @Override
    public void removeOnPropertyChangedCallback(OnPropertyChangedCallback callback) {

    }

    public void processFile() {
        Log.i(TAG, _fileText.getValue());
        //worker job
    }
}
