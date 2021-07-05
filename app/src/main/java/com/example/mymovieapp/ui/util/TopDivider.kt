package com.example.mymovieapp.ui.util

import android.graphics.Rect
import androidx.recyclerview.widget.RecyclerView

class TopDivider(private val padding: Int): RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, itemPosition: Int, parent: RecyclerView) {
         outRect.top = padding
    }
}