package rocks.ruggmatt.healthstats


import android.companion.CompanionDeviceManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContract
import androidx.health.connect.client.permission.HealthDataRequestPermissions
import androidx.health.connect.client.permission.Permission
import androidx.health.connect.client.records.HeartRateSeries
import androidx.health.connect.client.records.Steps
import androidx.health.connect.client.records.Weight
import androidx.health.platform.client.HealthDataService

private const val TAG = "Main";

class MainActivity : AppCompatActivity() {
    private val deviceManager: CompanionDeviceManager by lazy {
        getSystemService(Context.COMPANION_DEVICE_SERVICE) as CompanionDeviceManager
    }

    private val requestPermissions = registerForActivityResult(HealthDataRequestPermissions()) {
        permissionCallback(it)
    }

    private val permissions = setOf(
        Permission.createReadPermission(Steps::class),
        Permission.createWritePermission(Steps::class),
        Permission.createReadPermission(HeartRateSeries::class),
        Permission.createWritePermission(HeartRateSeries::class)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

            // Find only devices that match this request filter.
         Log.i(TAG, "Creating notification service");
         val intent = Intent(this, NotificationService::class.java)
         startForegroundService(intent);
        Log.i(TAG, "Requesting permissions")
        findViewById<Button>(R.id.permissionButton).setOnClickListener {
            askForPermissionToHealthData()
        }

        findViewById<Button>(R.id.logHeartRateButton).setOnClickListener {
            val modalBottomSheet = HeartDialogFragment();
            modalBottomSheet.show(supportFragmentManager, HeartDialogFragment.TAG);
//            val intent = Intent(this, AddHeartRateActivity::class.java);
//            startActivity(intent);
        }
    }

    private fun askForPermissionToHealthData() {
        Log.i(TAG, "Launching request for Permissions")
        requestPermissions.launch(permissions);
    }


    private fun permissionCallback(permissions: Set<Permission>) {
        // check which permissions were granted
        Log.i(TAG, permissions.toString())
    }

}