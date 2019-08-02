package com.binc.workermanager.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;
import androidx.databinding.Observable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkContinuation;
import androidx.work.WorkManager;

import com.binc.workermanager.util.Constants;
import com.binc.workermanager.workers.NetworkWorker;
import com.binc.workermanager.workers.PostProcessFileWorker;
import com.binc.workermanager.workers.PreProcessFileWorker;

public class MainViewModel extends AndroidViewModel implements Observable {
    private static String TAG = MainViewModel.class.getSimpleName();
    public String fileText;
    public MutableLiveData<String> _fileText = new MutableLiveData<>();
    private WorkManager mWorkManager;

    public MainViewModel(@NonNull Application application) {
        super(application);
        _fileText.setValue("");
        mWorkManager = WorkManager.getInstance(application);
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
        //create worker input data
        Data inputData = generateInputData();
        //worker job
        OneTimeWorkRequest preProcessWorker = new OneTimeWorkRequest.Builder(PreProcessFileWorker.class)
                .setInputData(inputData)
                .build();

        OneTimeWorkRequest networkWorker = new OneTimeWorkRequest.Builder(NetworkWorker.class)
                .build();

        OneTimeWorkRequest postProcessWorker = new OneTimeWorkRequest.Builder(PostProcessFileWorker.class)
                .setConstraints(new Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build())
                .build();

        WorkContinuation continuation = mWorkManager.beginWith(preProcessWorker)
                .then(networkWorker)
                .then(postProcessWorker);

        continuation.enqueue();
    }

    private Data generateInputData() {
        Data.Builder builder = new Data.Builder();
        builder.putString(Constants.CONTENT_KEY, fileText);
        return builder.build();
    }
}
