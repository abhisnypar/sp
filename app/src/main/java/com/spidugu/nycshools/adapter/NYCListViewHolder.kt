package com.spidugu.nycshools.adapter

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.spidugu.nycshools.R
import com.spidugu.nycshools.databinding.ItemViewLayoutBinding
import com.spidugu.nycshools.repository.SchoolInfo

class NYCListViewHolder(
    itemView: ConstraintLayout,
    private val onClickListener: (SchoolInfo) -> Unit
) : RecyclerView.ViewHolder(itemView) {

    private val binding = ItemViewLayoutBinding.bind(itemView)

    fun bind(schoolInfo: SchoolInfo) {
        binding.title.text = itemView.context.getString(R.string.title_value, schoolInfo.schoolName)
        binding.num.text = schoolInfo.dbn

        itemView.setOnClickListener {
            onClickListener.invoke(schoolInfo)
        }
    }
}