package com.example.intership_android_test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class CustomFragment() : Fragment() {
    companion object {
        private const val ARG_USER = "user"

        fun newInstance(user: User): CustomFragment {
            val fragment = CustomFragment()
            val args = Bundle()
            args.putParcelable(ARG_USER, user)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.item_fragment, container, false)

        val user = arguments?.getParcelable<User>(ARG_USER)
        user?.let {
            view.findViewById<TextView>(R.id.nameTextView).text = "Name: " + it.name
            view.findViewById<TextView>(R.id.ageTextView).text = "Age: " + it.age.toString()
            view.findViewById<TextView>(R.id.isStudentTextView).text =
                if (it.isStudent) "Student" else "Not a Student"
            view.findViewById<TextView>(R.id.descriptionTextView).text =
                "Description: " + if (it.description.isNullOrBlank()) {
                    "Simple User"
                } else it.description

        }
        return view
    }

}