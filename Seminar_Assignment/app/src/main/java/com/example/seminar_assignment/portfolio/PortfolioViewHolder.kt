package com.example.seminar_assignment.portfolio

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.seminar_assignment.R

class PortfolioViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView) {
    private val title : TextView = itemView.findViewById(R.id.tv_title)
    private val subTitle : TextView = itemView.findViewById(R.id.tv_subtitle)

    fun onBind(data : PortfolioData) {
        title.text = data.title
        subTitle.text = data.subTitle
    }
}