package com.binc.workermanager.workers;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.binc.workermanager.util.Constants;

public class PreProcessFileWorker extends Worker {
    public PreProcessFileWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        String fileUri = getInputData().getString(Constants.FILE_URI);
        String content = getInputData().getString(Constants.CONTENT);
        if(fileUri == null) {
            // direct content is being used
        } else {
            // file path input is being given
        }
        return null;
    }
}
