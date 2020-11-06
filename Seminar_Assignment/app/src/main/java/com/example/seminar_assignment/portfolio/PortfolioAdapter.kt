package com.example.seminar_assignment.portfolio

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.seminar_assignment.R
import com.example.seminar_assignment.activity.DetailActivity
import java.util.*

class PortfolioAdapter (private var context : Context) : RecyclerView.Adapter<PortfolioViewHolder>() {

    var data = mutableListOf<PortfolioData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PortfolioViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_home_recycler, parent, false)
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

    // 순서를 변경하는 함수
    fun onItemDragMove(beforePosition : Int, afterPosition : Int){
        if(beforePosition < afterPosition){
            for (i in beforePosition until afterPosition) {
                Collections.swap(data, i, i + 1)
            }
        } else {
            for (i in beforePosition downTo afterPosition + 1) {
                Collections.swap(data, i, i - 1)
            }
        }

        notifyItemMoved(beforePosition, afterPosition)
        notifyDataSetChanged()
    }

    // 아이템을 삭제하는 함수
    fun onItemRemoved(position: Int) {
        data.removeAt(position)
        notifyItemRemoved(position)
        notifyDataSetChanged()
    }

}