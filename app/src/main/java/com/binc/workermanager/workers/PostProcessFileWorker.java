package com.binc.workermanager.workers;

import android.content.Context;
import android.os.Environment;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.binc.workermanager.util.Constants;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Map;

public class PostProcessFileWorker extends Worker {
    public PostProcessFileWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Map<String, Object> wordMeaningPair = getInputData().getKeyValueMap();
        String fileName = getInputData().getString(Constants.FILE_NAME);
        String filePath = Environment.getExternalStorageDirectory() + "/" + Constants.BASE_DIR + "/" + fileName;
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(filePath, true);
            PrintStream printStream = new PrintStream(fileOutputStream);
            printStream.println();
            printStream.println();
            for(Map.Entry<String, Object> entry : wordMeaningPair.entrySet()) {
                String word = entry.getKey();
                if(Constants.FILE_NAME.equals(word))
                    continue;
                String meaning = (String) entry.getValue();
                printStream.print(word + ": " + meaning + "\n");
            }
            printStream.close();
            fileOutputStream.close();
            return Result.success();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.failure();
    }
}
