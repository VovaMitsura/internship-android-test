package com.example.intership_android_test

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var customAdapter: CustomAdapter

    private var itemsList = ArrayList<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "RecyclerView"

        customAdapter = CustomAdapter(itemsList) { position ->
            openCustomFragment(position)
        }

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = customAdapter

        prepareItems()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_add_user -> {
                openAddFragment()
                return true
            }

            R.id.action_sort -> {
                showSortDialog()
                return true
            }

            else -> return super.onOptionsItemSelected(item)

        }
    }


    private fun showSortDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_sort, null)
        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .show()

        val sortByName = dialogView.findViewById<TextView>(R.id.sortByName)
        val sortByAge = dialogView.findViewById<TextView>(R.id.sortByAge)
        val sortByStudentStatus = dialogView.findViewById<TextView>(R.id.sortByStudentStatus)
        val sortByDescription = dialogView.findViewById<TextView>(R.id.sortByDescription)

        sortByName.setOnClickListener {
            itemsList.sortBy { it.name }
            SharedPreferenceHelper.saveUsers(this, itemsList)
            prepareItems()
            dialog.dismiss()
        }

        sortByAge.setOnClickListener {
            itemsList.sortBy { it.age }
            SharedPreferenceHelper.saveUsers(this, itemsList)
            prepareItems()
            dialog.dismiss()
        }

        sortByStudentStatus.setOnClickListener {
            itemsList.sortWith(compareBy({ !it.isStudent }, { it.name }))
            SharedPreferenceHelper.saveUsers(this, itemsList)
            prepareItems()
            dialog.dismiss()
        }

        sortByDescription.setOnClickListener {
            itemsList.sortBy { it.description.length }
            SharedPreferenceHelper.saveUsers(this, itemsList)
            prepareItems()
            dialog.dismiss()
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        SharedPreferenceHelper.saveUsers(this, itemsList)

    }

    fun saveUser(user: User) {
        itemsList.add(user)
        customAdapter.notifyDataSetChanged()
        SharedPreferenceHelper.saveUsers(this, itemsList)
    }

    fun deleteUser(user: User) {
        itemsList.remove(user)
        customAdapter.notifyDataSetChanged()
        SharedPreferenceHelper.saveUsers(this, itemsList)
    }

    private fun prepareItems() {
        val users = SharedPreferenceHelper.getUsers(this)
        itemsList.clear()

        for (user in users) {
            itemsList.add(user)
        }

        customAdapter.notifyDataSetChanged()
    }

    private fun openCustomFragment(position: Int) {
        val clickUser = itemsList[position]

        val fragment = CustomFragment.newInstance(clickUser)

        supportFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .replace(R.id.fragment_container_view, fragment, Tags.ADD_FRAGMENT_TAG.name)
            .addToBackStack(null)
            .commit()
    }

    private fun openAddFragment() {
        val fragment = AddFragment()

        supportFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .replace(R.id.fragment_container_view, fragment, Tags.CUSTOM_FRAGMENT_TAG.name)
            .addToBackStack(null)
            .commit()
    }
}