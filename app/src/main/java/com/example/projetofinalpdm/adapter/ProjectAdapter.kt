package com.example.projetofinalpdm.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.projetofinalpdm.R
import com.example.projetofinalpdm.model.Project

class ProjectAdapter(context: Context,
                     private val projetos: List<Project>) : ArrayAdapter<Project>(context, 0, projetos)
{

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val listItemView = convertView ?: LayoutInflater.from(context).inflate(R.layout.project_list, parent, false)
        val currentProject = projetos[position]

        val projectName: TextView = listItemView.findViewById(R.id.fieldTitle)
        projectName.text = currentProject.name

        val taskCounter: TextView = listItemView.findViewById(R.id.fieldCounter)
        taskCounter.text = "$${currentProject.taskCounter}"


        return listItemView
    }
}