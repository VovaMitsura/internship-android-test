package com.example.intership_android_test

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), OnCounterClickListener {

    private lateinit var clickCountTextView: TextView
    private lateinit var navToFragment: Button

    private var fragmentClickCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        clickCountTextView = findViewById(R.id.click_counter_text_view)
        navToFragment = findViewById<Button>(R.id.nav_to_fragment_button)

        navToFragment.setOnClickListener {
            openCounterFragment()
        }

        val fragment = supportFragmentManager.findFragmentByTag("Fragment")

        if (fragment != null) {
            navToFragment.visibility = View.GONE
        } else {
            if (savedInstanceState != null) {
                if (savedInstanceState.containsKey("clickCount")) {
                    fragmentClickCount = savedInstanceState.getInt("clickCount", 0)
                }
            }
            clickCountTextView.text = "Counter in Fragment: $fragmentClickCount"
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt("clickCount", fragmentClickCount)
    }

    private fun openCounterFragment() {
        navToFragment.visibility = View.GONE
        supportFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .replace(R.id.fragment_container_view, CounterFragment(), "Fragment")
            .addToBackStack(null)
            .commit()
    }

    override fun onCounterClick(counter: Int) {
        navToFragment.visibility = View.VISIBLE

        fragmentClickCount = counter
        clickCountTextView.text = "Counter in Fragment: $fragmentClickCount"
    }

}