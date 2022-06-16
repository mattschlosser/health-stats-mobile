package rocks.ruggmatt.healthstats

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class StartupReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            Intent.ACTION_BOOT_COMPLETED -> {
                val intent = Intent(context, NotificationService::class.java)
                context.startForegroundService(intent)
            }
        }
    }

}