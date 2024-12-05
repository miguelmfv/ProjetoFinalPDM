package com.example.projetofinalpdm.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.projetofinalpdm.R
import com.example.projetofinalpdm.model.Project

class ProjectAdapter : ListAdapter<Project, ProjectAdapter.ProjectViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Project>() {
            override fun areItemsTheSame(oldItem: Project, newItem: Project) = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Project, newItem: Project) = oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.project_list, parent, false)
        return ProjectViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        val project = getItem(position)
        holder.bind(project)
    }

    inner class ProjectViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name: TextView = itemView.findViewById(R.id.fieldTitle)
        private val count: TextView = itemView.findViewById(R.id.fieldCounter)
        fun bind(project: Project) {

            name.text = project.name
            count.text =project.taskCounter.toString()
        }
    }
}