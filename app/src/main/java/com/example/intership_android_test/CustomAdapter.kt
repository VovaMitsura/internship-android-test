package com.example.intership_android_test

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView

internal class CustomAdapter(
    private var userList: MutableList<User>, private val onItemClick: ((Int) -> Unit)? = null
) : RecyclerView.Adapter<CustomAdapter.MyViewHolder>() {

    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var itemNameView: TextView = view.findViewById(R.id.itemNameView)
        var itemAgeView: TextView = view.findViewById(R.id.itemAgeView)
        var itemIsStudentSwitch: Switch = view.findViewById(R.id.itemIsStudentView)

        init {
            itemView.setOnLongClickListener {
                showDeleteDialog(userList[position], view)
                true
            }
        }

        private fun showDeleteDialog(user: User, view: View) {
            val alertDialogBuilder = AlertDialog.Builder(itemAgeView.context)
            alertDialogBuilder.setTitle("You want to delete this User")
            alertDialogBuilder.setMessage("Are you sure you want to delete ${user.name}?")

            alertDialogBuilder.setPositiveButton("Yes") { _, _ ->
                deleteUser(user, view)
            }

            alertDialogBuilder.setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            alertDialogBuilder.show()
        }

        private fun deleteUser(user: User, view: View) {
            (view.context as? MainActivity)?.deleteUser(user)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentUser = userList[position]
        holder.itemNameView.text = "Name: \n" + currentUser.name
        holder.itemAgeView.text = "Age: \n" + currentUser.age.toString()

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(position)
        }

        holder.itemIsStudentSwitch.setOnCheckedChangeListener { _, isChecked ->
            currentUser.isStudent = isChecked
        }

        holder.itemIsStudentSwitch.isChecked = currentUser.isStudent
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}