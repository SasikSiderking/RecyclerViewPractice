package com.example.recyclerviewpractice

import android.view.View
import androidx.recyclerview.widget.RecyclerView

fun interface OnViewHolderClickListener<VH : RecyclerView.ViewHolder> {

    fun onViewHolderClick(view: View, holder: VH)
}