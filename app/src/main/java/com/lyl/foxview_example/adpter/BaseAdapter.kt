package com.lyl.foxview_example.adpter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.lyl.foxview_example.R
import com.lyl.foxview_example.model.Model

open class BaseAdapter<T> constructor(data: Collection<T>) :
    RecyclerView.Adapter<SmartViewHolder>() {
    var mList: List<T>

    init {
        mList = ArrayList(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SmartViewHolder {
        return SmartViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(getLayoutIdByViewType(viewType), parent, false),
            getItemClickByViewType(viewType)
        )
    }

    open fun getItemClickByViewType(viewType: Int): AdapterView.OnItemClickListener? {
        return null
    }


    open fun getLayoutIdByViewType(viewType: Int): Int {
        return 0
    }

    override fun onBindViewHolder(holder: SmartViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return mList.size
    }
}