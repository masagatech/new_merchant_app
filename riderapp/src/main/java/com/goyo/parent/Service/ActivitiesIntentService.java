package com.goyo.parent.Service;

import android.app.IntentService;
import android.content.Intent;

import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;
import com.goyo.parent.utils.Constants;

import java.util.ArrayList;

/**
 * Created by mis on 31-Aug-17.
 */

public class ActivitiesIntentService extends IntentService {

    public static ArrayList<DetectedActivity> detectedActivities;

    private static final String TAG = "ActivitiesIntentService";

    public ActivitiesIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(intent);
        Intent i = new Intent(Constants.STRING_ACTION);

        detectedActivities = (ArrayList) result.getProbableActivities();

        i.putExtra(Constants.STRING_EXTRA, detectedActivities);
//        LocalBroadcastManager.getInstance(this).sendBroadcast(i);
    }
}
