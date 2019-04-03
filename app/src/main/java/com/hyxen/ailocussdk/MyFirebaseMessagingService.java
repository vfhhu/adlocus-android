package com.hyxen.ailocussdk;

import android.text.TextUtils;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.hyxen.ailocus.AdLocus;
import com.hyxen.ailocus.constants.Constants;
import com.hyxen.ailocus.repository.data.response.GetFCMDataResponse;
import com.hyxen.ailocus.util.Log;

import java.util.Map;

/**
 * Created by leo3x on 2019/4/1.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
//        Logger.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d("onMessageReceived");

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Map<String, String> data = remoteMessage.getData();
//            Logger.d(TAG, "Message data payload: " + remoteMessage.getData());
            if (TextUtils.equals(data.get(GetFCMDataResponse.TAG_TARGET), Constants.TAG_FCM_TARGET))
                AdLocus.getInstance().sendFCMMessage(this,data);
            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
//                scheduleJob();
            } else {
                // Handle message within 10 seconds
//                handleNow();
            }
        }
    }
}
