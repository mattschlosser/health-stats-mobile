package rocks.ruggmatt.healthstats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kotlinx.coroutines.*

private val TAG = "AddHeartRateActivity";

class AddHeartRateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_heart_rate)
        val hc = HealthClient(this);
        val button = findViewById<Button>(R.id.addHeartRateButton)

        button.setOnClickListener {
            it.isEnabled = false;
            val heartRate = findViewById<EditText>(R.id.heartRate);
            val hr =  heartRate.text.toString().toLongOrNull();
            if (hr != null) {
                Log.i(TAG, "Heart rate is $hr");
                GlobalScope.launch {
                    hc.recordCurrentHeartRate(hr)
                    withContext(Dispatchers.Main) {
                        finish()
                    }
                };
            } else {
                Log.i(TAG, "Heart rate not found")
            }
        }
    }


}