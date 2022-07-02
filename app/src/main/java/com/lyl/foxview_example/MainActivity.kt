package com.lyl.foxview_example

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.RecyclerView
import com.lyl.foxview.DrawBackgroundItemDecoration
import com.lyl.foxview_example.adpter.BaseAdapter
import com.lyl.foxview_example.adpter.ItemType
import com.lyl.foxview_example.model.Model

class MainActivity : AppCompatActivity() {
    private lateinit var baseAdapter: BaseAdapter<Model>
    private lateinit var data: java.util.ArrayList<Model>
    lateinit var rv_list: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        data = ArrayList<Model>()
        data.add(Model(ItemType.TITLE))
        data.add(Model(ItemType.CONTENT))
        data.add(Model(ItemType.CONTENT))
        data.add(Model(ItemType.CONTENT))
        data.add(Model(ItemType.CONTENT))
        data.add(Model(ItemType.CONTENT))
        data.add(Model(ItemType.CONTENT))
        data.add(Model(ItemType.TITLE))
        data.add(Model(ItemType.CONTENT))
        data.add(Model(ItemType.CONTENT))
        data.add(Model(ItemType.CONTENT))
        data.add(Model(ItemType.CONTENT))
        data.add(Model(ItemType.TITLE))
        data.add(Model(ItemType.TITLE))
        data.add(Model(ItemType.CONTENT))
        data.add(Model(ItemType.CONTENT))
        baseAdapter = BaseAdapter<Model>(data)
        rv_list = findViewById(R.id.rv_list)
        var gridLayoutManager = GridLayoutManager(getContext(), 4)
        gridLayoutManager.spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                var model = baseAdapter.mList[position]
                if (model.type == ItemType.CONTENT) return 1
                return 4
            }
        }
        rv_list.layoutManager = gridLayoutManager
        rv_list.addItemDecoration(DrawBackgroundItemDecoration())
        rv_list.adapter = baseAdapter
    }


    private fun getContext(): Activity {
        return this
    }
}