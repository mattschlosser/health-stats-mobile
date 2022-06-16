package rocks.ruggmatt.healthstats

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.IBinder
import android.util.Log
import android.widget.RemoteViews

private const val CHANNEL_DEFAULT_IMPORTANCE = "Main";
private const val ONGOING_NOTIFICATION_ID = 1;

private const val TAG = "NotificationService";
const val UPDATE_TOTAL_STEPS = "update_steps";
const val TOTAL_STEPS = "totalsteps";
class NotificationService: Service() {

    private lateinit var remoteView: RemoteViews;
    private var steps: String = "--";

    private fun createNotificationChannelIfNotExists() {
        val channel = NotificationChannel(CHANNEL_DEFAULT_IMPORTANCE, "Heads Up", NotificationManager.IMPORTANCE_HIGH)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel);
    }

    private fun updateNotification() {
        remoteView = RemoteViews(packageName, R.layout.notification)
        remoteView.setTextViewText(R.id.steps, steps)


        createNotificationChannelIfNotExists()
        val notification = Notification.Builder(this, CHANNEL_DEFAULT_IMPORTANCE)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setCustomContentView(remoteView)
            .setCustomBigContentView(remoteView)
            .build()
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager;
        notificationManager.notify(ONGOING_NOTIFICATION_ID, notification)
    }

    override fun onCreate() {
        Log.i(TAG, "Starting notification service");
        remoteView = RemoteViews(packageName, R.layout.notification)
        remoteView.setTextViewText(R.id.textView, steps)


        createNotificationChannelIfNotExists()
        val notification = Notification.Builder(this, CHANNEL_DEFAULT_IMPORTANCE)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setCustomContentView(remoteView)
            .setCustomBigContentView(remoteView)
            .build()

        startForeground(ONGOING_NOTIFICATION_ID, notification)
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent != null) {
            when (intent.action) {
                UPDATE_TOTAL_STEPS -> {
                    val intentSteps = intent.getStringExtra(TOTAL_STEPS);
                    if (intentSteps != null) {
                        steps = intentSteps
                        updateNotification()
                    } else {
                        Log.e(TAG, "Expected a string containing the number of steps, found none")
                    }
                }
            }
        }
        return START_STICKY;
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null;
    }

}