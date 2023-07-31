package com.example.intership_android_test

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.intership_android_test.databinding.FragmentCounterBinding

class CounterFragment : Fragment() {

    private lateinit var clickButton: Button
    private lateinit var backButton: ImageButton
    private lateinit var clickCountTextView: TextView
    private lateinit var viewBinding: FragmentCounterBinding

    private var clickCount = 0
    private var counterListener: OnCounterClickListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        counterListener = requireActivity() as? OnCounterClickListener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentCounterBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        clickButton = view.findViewById(R.id.click_button)
        clickCountTextView = view.findViewById(R.id.click_counter_text_view)
        backButton = view.findViewById<ImageButton>(R.id.back_button)

        // Restore the counter value from the saved state (if any)
        if (savedInstanceState != null) {
            clickCount = savedInstanceState.getInt("clickCount", 0)
        }
        updateClickCountTextView()

        // Set a click listener for the button in the Fragment
        clickButton.setOnClickListener {
            // Increase the click count and update the TextView
            clickCount++
            updateClickCountTextView()
        }

        backButton.setOnClickListener {
            requireActivity().supportFragmentManager.commit {
                remove(this@CounterFragment)
            }

            val activity = requireActivity() as? OnCounterClickListener

            activity?.onCounterClick(clickCount)

            requireActivity().supportFragmentManager.popBackStack()
        }

    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        // Restore the click count from the saved state (if any)
        savedInstanceState?.let {
            clickCount = it.getInt("clickCount", clickCount)
            updateClickCountTextView()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Save the click count to the bundle
        outState.putInt("clickCount", clickCount)
    }

    private fun updateClickCountTextView() {
        clickCountTextView.text = "Counter: $clickCount"
    }

}