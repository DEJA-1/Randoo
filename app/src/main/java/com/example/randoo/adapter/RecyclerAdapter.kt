package com.example.randoo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.randoo.R
import com.example.randoo.fragments.MainFragmentDirections
import com.example.randoo.model.Task

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    private var taskList = emptyList<Task>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.task_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = taskList[position]
        holder.task.text = item.task_text
        item.status = holder.task.isChecked

        //setting up update option
        holder.row.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToUpdateFragment(item)
            holder.itemView.findNavController().navigate(action)
        }
    }
    override fun getItemCount() = taskList.size

    fun setTasks(task: List<Task>) {
        this.taskList = task
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val task : CheckBox = itemView.findViewById(R.id.checkbox_task)
        val row : View = itemView.findViewById(R.id.rowLayout)
    }

}