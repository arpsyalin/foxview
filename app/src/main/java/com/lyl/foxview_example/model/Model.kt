package com.lyl.foxview_example.model

import com.lyl.foxview_example.adpter.ItemType

class Model {
    var type: Int = ItemType.TITLE
    var context: String? = "测试数据"

    constructor(type: Int) {
        this.type = type
    }
}