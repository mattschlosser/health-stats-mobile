package rocks.ruggmatt.healthstats

import android.content.Context
import android.util.Log
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.records.HeartRate
import androidx.health.connect.client.records.HeartRateSeries
import androidx.health.connect.client.records.Record
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

private const val TAG = "HealthClient";

class HealthClient(
    val context: Context
) {
    private var client: HealthConnectClient? = null;
    init {
        if (HealthConnectClient.isAvailable(context)) {
            Log.i(TAG, "Client is available");
            client = HealthConnectClient.getOrCreate(context);
        } else {
            Log.d(TAG, "Health connect client is not installed")
        }
    }

    fun recordSteps(steps: Int) {
//        if (client) {
//            val record =
//        }
    }

    suspend fun recordCurrentHeartRate(hr: Long) = withContext(Dispatchers.IO) {
        Log.i(TAG, "Logging heart rate of $hr")
        val instant = Date().toInstant()
        val records = listOf(
            HeartRateSeries(
                instant,
                null,
                instant,
                null,
                listOf(HeartRate(
                    instant,
                    hr
                ))
            )
        )
        client?.insertRecords(records);
    }


}