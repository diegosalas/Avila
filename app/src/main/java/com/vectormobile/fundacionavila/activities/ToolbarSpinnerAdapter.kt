package com.vectormobile.fundacionavila.activities

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import com.vectormobile.fundacionavila.R

class ToolbarSpinnerAdapter(context: Context, internal var toolbarIcons: Array<Int>) :
    ArrayAdapter<Int>(context,
        R.layout.spinner_row,
        R.id.spinnerImage, toolbarIcons) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, null)
    }

    private fun initView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var convertView = convertView
        val customer = getItem(position)
        if (convertView == null) {
            convertView = if (parent == null)
                LayoutInflater.from(context).inflate(R.layout.spinner_row, null)
            else
                LayoutInflater.from(context).inflate(R.layout.spinner_row, parent, false)
        }
        val ivCustomerImage = convertView!!.findViewById<View>(R.id.spinnerImage) as ImageView

        ivCustomerImage.setImageResource(customer!!)

        return convertView
    }
}
