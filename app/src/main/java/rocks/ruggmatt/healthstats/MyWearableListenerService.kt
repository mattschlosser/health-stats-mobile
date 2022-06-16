package rocks.ruggmatt.healthstats

import android.content.Intent
import android.util.Log
import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.WearableListenerService

private const val TAG = "Service";

class MyWearableListenerService: WearableListenerService() {
    override fun onMessageReceived(messageEvent: MessageEvent) {
        val steps = String(messageEvent.data);
        Log.i(TAG, "The user's total steps are $steps")
        val intent = Intent(this, NotificationService::class.java)
        intent.action = UPDATE_TOTAL_STEPS;
        intent.putExtra(TOTAL_STEPS, steps);
        startForegroundService(intent)
    }
}