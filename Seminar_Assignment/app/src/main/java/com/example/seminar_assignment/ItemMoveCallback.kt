package com.example.seminar_assignment

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class ItemMoveCallback constructor(val profileAdapter: ProfileAdapter) : ItemTouchHelper.Callback(){

    private var isMoved = false    //무빙 이벤트에 대한 boolean값

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

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        super.onSelectedChanged(viewHolder, actionState)
        if(isMoved){
            isMoved = false
            profileAdapter.changeMoveEvent()
        }
    }

    override fun onMoved(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        fromPos: Int,
        target: RecyclerView.ViewHolder,
        toPos: Int,
        x: Int,
        y: Int
    ) {
        super.onMoved(recyclerView, viewHolder, fromPos, target, toPos, x, y)
        isMoved = true
    }
}
