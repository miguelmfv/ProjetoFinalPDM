package com.example.projetofinalpdm.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projetofinalpdm.R
import com.example.projetofinalpdm.model.Project

class ProjectAdapter(private val projects: List<Project>) :
    RecyclerView.Adapter<ProjectAdapter.ProjectViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_project, parent, false)
        return ProjectViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        val project = projects[position]
        holder.tvProjectName.text = project.name
        holder.tvProjectDescription.text = project.description
    }

    override fun getItemCount(): Int = projects.size

    class ProjectViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvProjectName: TextView = itemView.findViewById(R.id.tvProjectName)
        val tvProjectDescription: TextView = itemView.findViewById(R.id.tvProjectDescription)
    }
}