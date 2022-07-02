package com.lyl.foxview_example.adpter

import android.view.View
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView

class SmartViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var onItemClickListener: AdapterView.OnItemClickListener? = null

    constructor(itemView: View, onItemClickListener: AdapterView.OnItemClickListener?) : this(
        itemView
    ) {
        this.onItemClickListener = onItemClickListener
    }
}