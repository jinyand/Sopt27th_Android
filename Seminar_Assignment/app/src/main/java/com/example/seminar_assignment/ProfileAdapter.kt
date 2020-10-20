package com.example.seminar_assignment

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

class ProfileAdapter (private var context : Context) : RecyclerView.Adapter<ProfileViewHolder>() {

    var data = mutableListOf<ProfileData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_home_recycler, parent, false)
        return ProfileViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        holder.onBind(data[position])

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra("title", data[position].title)
            intent.putExtra("subtitle", data[position].subTitle)
            intent.putExtra("content", data[position].content)
            startActivity(holder.itemView.context, intent, null)

        }
    }

    override fun getItemCount(): Int = data.size

}