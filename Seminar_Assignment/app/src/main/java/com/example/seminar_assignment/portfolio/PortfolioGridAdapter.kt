package com.example.seminar_assignment.portfolio

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.seminar_assignment.R
import com.example.seminar_assignment.activity.DetailActivity

class PortfolioGridAdapter (private var context : Context) : RecyclerView.Adapter<PortfolioViewHolder>() {

    var data = mutableListOf<PortfolioData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PortfolioViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_home_grid_recycler, parent, false)
        return PortfolioViewHolder(view)
    }

    override fun onBindViewHolder(holder: PortfolioViewHolder, position: Int) {
        holder.onBind(data[position])

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra("title", data[position].title)
            intent.putExtra("subtitle", data[position].subTitle)
            intent.putExtra("content", data[position].content)
            intent.putExtra("date", data[position].date)
            startActivity(holder.itemView.context, intent, null)

        }
    }

    override fun getItemCount(): Int = data.size

}