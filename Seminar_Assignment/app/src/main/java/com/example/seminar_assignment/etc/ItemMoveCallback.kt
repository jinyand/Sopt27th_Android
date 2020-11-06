package com.example.seminar_assignment.etc

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.seminar_assignment.portfolio.PortfolioAdapter

class ItemMoveCallback constructor(val portfolioAdapter: PortfolioAdapter) : ItemTouchHelper.Callback(){

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        val flagDrag = ItemTouchHelper.UP or ItemTouchHelper.DOWN    //드래그 앤 드롭 움직임 설정
        val flagSwipe = ItemTouchHelper.START or ItemTouchHelper.END // 스와이프 움직임 설정
        return makeMovementFlags(flagDrag, flagSwipe)
    }

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        portfolioAdapter.onItemDragMove(viewHolder.adapterPosition, target.adapterPosition)
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        portfolioAdapter.onItemRemoved(viewHolder.adapterPosition)
    }

    override fun isLongPressDragEnabled(): Boolean {
        return true
    }

    override fun isItemViewSwipeEnabled(): Boolean {
        return true
    }

}
