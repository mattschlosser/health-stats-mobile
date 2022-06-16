package rocks.ruggmatt.healthstats

import android.app.Activity
import android.os.Bundle
import rocks.ruggmatt.healthstats.databinding.ActivityServiceBinding

class Service : Activity() {

    private lateinit var binding: ActivityServiceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}