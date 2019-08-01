package com.binc.workermanager.workers;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class NetworkWorker extends Worker {
    private static final String TAG = NetworkWorker.class.getSimpleName();

    public NetworkWorker (@NonNull Context appContext,
                          @NonNull WorkerParameters workerParams) {
        super(appContext, workerParams);
    }
    @NonNull
    @Override
    public Result doWork() {

        return null;
    }
}
