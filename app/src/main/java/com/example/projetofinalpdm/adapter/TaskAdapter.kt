package com.example.projetofinalpdm.adapter

import android.widget.CheckBox
import com.example.projetofinalpdm.model.Task


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.projetofinalpdm.R

class TaskAdapter(
    private val onToggleTask: (Task) -> Unit
) : ListAdapter<Task, TaskAdapter.TaskViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Task>() {
            override fun areItemsTheSame(oldItem: Task, newItem: Task) = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Task, newItem: Task) = oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.project_list, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = getItem(position)
        holder.bind(task)
    }

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.fieldTaskTitle)
        private val priority: TextView = itemView.findViewById(R.id.fieldPriority)
        private var checkbox: CheckBox = itemView.findViewById(R.id.checkTaskCompleted)
        fun bind(task: Task) {

            title.text = task.title
            priority.text =task.priority.toString()
            checkbox.isChecked = task.status

            checkbox.setOnClickListener { onToggleTask(task) }
        }
    }
}