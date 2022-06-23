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
import rocks.ruggmatt.healthstats.utils.PermissionsManager

private const val TAG = "Main";

class MainActivity : AppCompatActivity() {

    private val requestPermissions = registerForActivityResult(HealthDataRequestPermissions()) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startNotificationService()
        setupButtons()
    }

    private fun startNotificationService() {
        Log.i(TAG, "Creating notification service");
        val intent = Intent(this, NotificationService::class.java)
        startForegroundService(intent);
    }

    private fun setupButtons() {
        setupPermissionButton()
        setupHeartRateButton()
    }

    private fun setupHeartRateButton() {
        findViewById<Button>(R.id.logHeartRateButton).setOnClickListener {
            openHeartRateSheet()
        }
    }

    private fun setupPermissionButton() {
        findViewById<Button>(R.id.permissionButton).setOnClickListener {
            askForPermissionToHealthData()
        }
    }

    private fun openHeartRateSheet() {
        val modalBottomSheet = HeartDialogFragment();
        modalBottomSheet.show(supportFragmentManager, HeartDialogFragment.TAG);
    }

    private fun askForPermissionToHealthData() {
        Log.i(TAG, "Launching request for Permissions")
        requestPermissions.launch(PermissionsManager.permissions);
    }


}