package com.example.projetofinalpdm.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.projetofinalpdm.model.Project

class ProjectsSpinnerAdapter(
    context: Context,
    private val projects: List<Project>
) : ArrayAdapter<Project>(context, android.R.layout.simple_spinner_item, projects) {

    init {
        setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent) as TextView
        view.text = projects[position].name
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getDropDownView(position, convertView, parent) as TextView
        view.text = projects[position].name
        return view
    }
}
