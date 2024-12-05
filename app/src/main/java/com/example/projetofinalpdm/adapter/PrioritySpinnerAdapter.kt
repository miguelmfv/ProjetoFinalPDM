package com.example.projetofinalpdm.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class PrioritySpinnerAdapter(
    context: Context,
    private val priorities: List<String>,
    private val colors: List<Int>
) : ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, priorities) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent) as TextView
        view.setTextColor(colors[position])
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getDropDownView(position, convertView, parent) as TextView
        view.setTextColor(colors[position])
        return view
    }
}