package com.example.intership_android_test

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddFragment() : Fragment() {

    private lateinit var button: Button
    private lateinit var nameField: EditText
    private lateinit var ageField: EditText
    private lateinit var descriptionField: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.add_fragment, container, false)

        button = view.findViewById(R.id.add_user_button)
        nameField = view.findViewById(R.id.nameEditText)
        ageField = view.findViewById(R.id.ageEditText)
        descriptionField = view.findViewById(R.id.descriptionEditText)

        button.setOnClickListener {

            saveData(nameField.text.toString(), parseDate(), descriptionField.text.toString())
            activity?.supportFragmentManager?.beginTransaction()?.remove(this)?.commit()
        }

        ageField.setOnClickListener {
            showDatePickerDialog()
        }

        return view
    }

    private fun saveData(name: String, age: Int, description: String) {

        if (name.isNotBlank() && age > 0 && description.isNotBlank()) {

            val user = User(name, age, false, description)

            (activity as? MainActivity)?.saveUser(user)
            Toast.makeText(context, "User added successfully", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(
                context,
                "Please enter valid name, and age and description",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun showDatePickerDialog() {
        // Get the current date to set as the default in the date picker
        val calendar = Calendar.getInstance()
        val defaultYear = calendar.get(Calendar.YEAR)
        val defaultMonth = calendar.get(Calendar.MONTH)
        val defaultDay = calendar.get(Calendar.DAY_OF_MONTH)

        // Create a date picker dialog and show it to the user
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _: DatePicker, year: Int, month: Int, day: Int ->
                // The user has selected a date
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, month, day)

                // Format the selected date and display it in the datePickerEditText
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                ageField.setText(dateFormat.format(selectedDate.time))
            },
            defaultYear,
            defaultMonth,
            defaultDay
        )

        datePickerDialog.show()
    }

    private fun parseDate(): Int {
        // Parse the date of birth from the date picker and set it to the user
        val dobString = ageField.text.toString()
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val dateOfBirth = dateFormat.parse(dobString)
        val calendarBirth = Calendar.getInstance()
        calendarBirth.time = dateOfBirth
        return Calendar.getInstance().get(Calendar.YEAR) - calendarBirth.get(Calendar.YEAR)
    }
}