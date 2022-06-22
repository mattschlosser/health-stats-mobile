package rocks.ruggmatt.healthstats

import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import rocks.ruggmatt.healthstats.databinding.FragmentHeartDialogBinding

// TODO: Customize parameter argument names
const val ARG_ITEM_COUNT = "item_count"

/**
 *
 * A fragment that shows a list of items as a modal bottom sheet.
 *
 * You can show this modal bottom sheet from your activity like this:
 * <pre>
 *    HeartDialogFragment.newInstance(30).show(supportFragmentManager, "dialog")
 * </pre>
 */
class HeartDialogFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentHeartDialogBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    companion object {
        public const val TAG = "HeartDialogFragment";
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHeartDialogBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        addButtonListener(view)
        watchForChanges()
    }

    private fun watchForChanges() {

         val bottomSheetCallback = object: BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        focusInput()
                    }
                }
            }

             override fun onSlide(bottomSheet: View, slideOffset: Float) {
                 //
             }
        }
        (dialog as BottomSheetDialog).behavior.addBottomSheetCallback(bottomSheetCallback)

    }

    private fun addButtonListener(view: View) {
        val hc = HealthClient(binding.root.context);
        val button = view.findViewById<Button>(R.id.addHeartRateButton)
        button.setOnClickListener {
            it.isEnabled = false;
            val heartRate = view.findViewById<EditText>(R.id.heartRate);
            val hr =  heartRate.text.toString().toLongOrNull();
            if (hr === null) {
                Log.i(TAG, "Heart rate not found")
                heartRate.error = "Invalid heart rate"
                return@setOnClickListener;
            }
            if (hr <= 30 || hr > 240) {
                Log.i(TAG, "Absurd");
                heartRate.error = "Heart rate must be between 30 and 240";
                return@setOnClickListener;
            }
            Log.i(TAG, "Heart rate is $hr");
            GlobalScope.launch {
                hc.recordCurrentHeartRate(hr)
                closeDialog()
            }
        }
    }

    private fun focusInput() {
        binding.root.findViewById<EditText>(R.id.heartRate).apply {
            requestFocus()
        }
    }


    private suspend fun closeDialog() = withContext(Dispatchers.Main) {
        (dialog as BottomSheetDialog).behavior.state = BottomSheetBehavior.STATE_HIDDEN;
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}