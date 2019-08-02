package com.binc.workermanager.workers;

import android.content.Context;
import android.os.Environment;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.binc.workermanager.util.Constants;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class PreProcessFileWorker extends Worker {
    private static String TAG = PreProcessFileWorker.class.getSimpleName();

    public PreProcessFileWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Data.Builder resultDataBuilder = new Data.Builder();
        String content = getInputData().getString(Constants.CONTENT_KEY);
        // direct content is being used
        //step 1: identify keywords
        String[] keywords = identifyKeywords(content);
        resultDataBuilder.putStringArray(Constants.KEYWORDS_KEY, keywords);
        //step 2: write file
        String filename = null;
        try {
            filename = writeToFile(content);
            resultDataBuilder.putString(Constants.FILE_NAME, filename);
            return Result.success(resultDataBuilder.build());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.failure();
    }

    private String[] identifyKeywords(String content) {
        ArrayList<String> keywords = new ArrayList<>();
        String[] words = content.split(" ");
        int wordCount = 0;
        for (String word : words) {
            if (word.length() > 4) {
                keywords.add(word);
                wordCount++;
            }
        }
        String[] keyWordsArray = new String[wordCount];
        keywords.toArray(keyWordsArray);
        return keyWordsArray;
    }

    private String writeToFile(String content) throws IOException {
        String fileName = System.currentTimeMillis() + ".txt";
        String fileDir = Environment.getExternalStorageDirectory() + "/" + Constants.BASE_DIR;
        File file = new File(fileDir);
        if (!file.exists()) {
            file.mkdir();
        }
        BufferedWriter out = new BufferedWriter(
                new FileWriter(fileDir + "/" + fileName));
        out.write(content);
        out.newLine();
        out.newLine();
        out.write(Constants.END_OF_TEXT);
        out.close();
        return fileName;
    }
}
