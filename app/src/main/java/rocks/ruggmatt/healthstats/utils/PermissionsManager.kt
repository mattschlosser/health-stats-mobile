package rocks.ruggmatt.healthstats.utils

import android.content.Context
import androidx.health.connect.client.permission.Permission
import androidx.health.connect.client.records.HeartRateSeries
import androidx.health.connect.client.records.Steps
import androidx.health.connect.client.records.Weight

class PermissionsManager(private val context: Context) {


    companion object {
        val permissions = setOf(
            Permission.createReadPermission(Steps::class),
            Permission.createWritePermission(Steps::class),
            Permission.createReadPermission(HeartRateSeries::class),
            Permission.createWritePermission(HeartRateSeries::class),
            Permission.createReadPermission(Weight::class),
            Permission.createWritePermission(Weight::class)
        )
    }

}