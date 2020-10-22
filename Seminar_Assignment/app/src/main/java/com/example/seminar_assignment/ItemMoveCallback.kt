package com.example.seminar_assignment

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class ItemMoveCallback constructor(val profileAdapter: ProfileAdapter) : ItemTouchHelper.Callback(){

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        val flagDrag = ItemTouchHelper.UP or ItemTouchHelper.DOWN    //드래그 앤 드롭 움직임 설정
        val flagSwipe = ItemTouchHelper.START or ItemTouchHelper.END // 스와이프 움직임 설정
        return makeMovementFlags(flagDrag, flagSwipe)
    }

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        profileAdapter.onItemDragMove(viewHolder.adapterPosition, target.adapterPosition)
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        profileAdapter.onItemRemoved(viewHolder.adapterPosition)
    }

    override fun isLongPressDragEnabled(): Boolean {
        return true
    }

    override fun isItemViewSwipeEnabled(): Boolean {
        return true
    }

}
