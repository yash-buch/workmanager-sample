package com.binc.workermanager.workers;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;
import com.binc.workermanager.beans.JsonPayload;
import com.binc.workermanager.util.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class NetworkWorker extends Worker {
    private static final String TAG = NetworkWorker.class.getSimpleName();
    private RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

    public NetworkWorker(@NonNull Context appContext,
                         @NonNull WorkerParameters workerParams) {
        super(appContext, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        String[] keywords = getInputData().getStringArray(Constants.KEYWORDS_KEY);
        String fileName = getInputData().getString(Constants.FILE_NAME);
        Map<String, Object> wordMeaningPair = new HashMap<>();
        Data.Builder resultDataBuilder = new Data.Builder();
        for (String word : keywords) {
            Log.i(TAG, word);
            String definition = makeDictionaryRequest(word);
            if (definition != null) {
                wordMeaningPair.put(word, definition);
            }
        }

        resultDataBuilder.putAll(wordMeaningPair);
        resultDataBuilder.putString(Constants.FILE_NAME, fileName);

        return Result.success(resultDataBuilder.build());
    }

    private String makeDictionaryRequest(String word) {
        String url = Constants.GOOGLE_DICT_API + word;
        RequestFuture<JSONArray> future = RequestFuture.newFuture();
        JsonArrayRequest jsonArrayRequest =
                new JsonArrayRequest(url, future, future);
        requestQueue.add(jsonArrayRequest);
        try {
            JSONObject jsonObject = future.get().getJSONObject(0);
            String jsonString = jsonObject.toString();
            Gson gson = new GsonBuilder().create();
            JsonPayload payload = gson.fromJson(jsonString, JsonPayload.class);
            String definition = null;
            if (payload.getMeaning().getNoun() != null) {
                definition = payload.getMeaning().getNoun()[0].getDefinition();
            } else if (payload.getMeaning().getAdjective() != null) {
                definition = payload.getMeaning().getAdjective()[0].getDefinition();
            } else if (payload.getMeaning().getVerb() != null) {
                definition = payload.getMeaning().getVerb()[0].getDefinition();
            } else if (payload.getMeaning().getAdverb() != null) {
                definition = payload.getMeaning().getAdverb()[0].getDefinition();
            } else if (payload.getMeaning().getConjunction() != null) {
                definition = payload.getMeaning().getConjunction()[0].getDefinition();
            } else if (payload.getMeaning().getInterjection() != null) {
                definition = payload.getMeaning().getInterjection()[0].getDefinition();
            } else if (payload.getMeaning().getPreposition() != null) {
                definition = payload.getMeaning().getPreposition()[0].getDefinition();
            } else if (payload.getMeaning().getPronoun() != null) {
                definition = payload.getMeaning().getPronoun()[0].getDefinition();
            }
            Log.i(TAG, definition);
            return definition;
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return "could not find its meaning :(";
    }
}
