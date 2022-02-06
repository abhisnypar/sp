package com.spidugu.nycshools.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.spidugu.nycshools.R
import com.spidugu.nycshools.repository.SchoolInfo

/**
 * Adapter to take care of the list view, for populating items, and other behaviours.
 */
class NYCListRecyclerAdapter(private val onClickListener: (SchoolInfo) -> Unit) :
    RecyclerView.Adapter<NYCListViewHolder>() {

    private val schoolsInfoList = arrayListOf<SchoolInfo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NYCListViewHolder {
        return NYCListViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_view_layout, parent, false) as ConstraintLayout,
            onClickListener
        )
    }

    override fun onBindViewHolder(holder: NYCListViewHolder, position: Int) {
        holder.bind(schoolsInfoList[position])
    }

    override fun getItemCount(): Int = schoolsInfoList.size

    fun updateData(it: List<SchoolInfo>?) {
        schoolsInfoList.apply {
            clear()
            it?.let { it1 -> addAll(it1) }
        }
        notifyDataSetChanged()
    }
}