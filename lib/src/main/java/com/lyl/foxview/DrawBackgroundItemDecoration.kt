package com.lyl.foxview

import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.GradientDrawable
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

/**
 * * @Description
 * * @Author 刘亚林
 * * @CreateDate 2022/6/1
 * * @Version 1.0
 * * @Remark TODO
 */
class DrawBackgroundItemDecoration : ItemDecoration {
    private var cornerRadius = dp2px(10f).toFloat()
    private var lineTopSize = dp2px(10f)
    private var bgColor = Color.WHITE

    constructor() {}
    constructor(cornerRadius: Float, lineTopSize: Int, bgColor: Int) {
        this.cornerRadius = dp2px(cornerRadius).toFloat()
        this.lineTopSize = dp2px(lineTopSize.toFloat())
        this.bgColor = bgColor
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val gridLayoutManager = parent.layoutManager as GridLayoutManager?
        if (parent.layoutManager is GridLayoutManager) {
            val spanCount = gridLayoutManager!!.spanCount
            val childCount = parent.adapter!!.itemCount
            for (i in 0 until childCount) {
                val child = parent.getChildAt(i)
                if (child == null) {
                    continue
                } else {
                    val drawConfig =
                        calcDrawConfig(parent, child, gridLayoutManager, childCount, spanCount)
                    drawConfig?.gradientDrawable?.draw(c)
                }
            }
        }
        super.onDraw(c, parent, state)
    }

    private fun calcDrawConfig(
        parent: RecyclerView,
        child: View,
        gridLayoutManager: GridLayoutManager?,
        childCount: Int,
        spanCount: Int
    ): DrawConfig? {
        val position = parent.getChildAdapterPosition(child)
        if (position < 0 || position >= childCount) return null
        val spanSizeLookup = gridLayoutManager!!.spanSizeLookup
        val spanSize = spanSizeLookup.getSpanSize(position)
        val spanIndex = spanSizeLookup.getSpanIndex(position, spanCount)
        if (spanIndex != 0) {
            return null
        }
        val hasLastLine = hasLastLine(spanSize, position)
        val hasNextLine = hasNextLine(spanSizeLookup, spanSize, childCount, position, spanCount)
        val drawable = generateDrawable(hasLastLine, hasNextLine)
        val l = 0
        val t = child.top
        val r = parent.right - parent.left
        val b = child.bottom
//        if (hasNextLine) b else b + lineTopSize
        drawable.setBounds(l, t, r, b)
        return DrawConfig(hasLastLine, hasNextLine, drawable, position)
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val gridLayoutManager = parent.layoutManager as GridLayoutManager?
        val spanCount = gridLayoutManager!!.spanCount
        val childCount = parent.adapter!!.itemCount
        val drawConfig = calcDrawConfig(parent, view, gridLayoutManager, childCount, spanCount)
        if (drawConfig != null) {
            if (!drawConfig.isHasLastLine) {
                outRect[0, if (drawConfig.position == 0) lineTopSize else lineTopSize * 2, 0] = 0
            }
        } else {
            outRect[0, 0, 0] = 0
        }
    }

    private fun hasLastLine(spanSize: Int, position: Int): Boolean {
        if (spanSize != 1) {
            return false
        }
        return position > 0 && spanSize == 1
    }

    private fun hasNextLine(
        spanSizeLookup: SpanSizeLookup, spanSize: Int,
        childSize: Int, position: Int, spanCount: Int
    ): Boolean {
        val spanGroupIndex = spanSizeLookup.getSpanGroupIndex(position, spanCount)
        if (spanSize == 1) {
            for (i in 1 until spanCount) {
                //下一个比childSize 😊
                if (position + i < childSize) {
                    //下一个不在同一行了
                    if (spanSizeLookup.getSpanGroupIndex(
                            position + i,
                            spanCount
                        ) != spanGroupIndex
                    ) {
                        return false
                    }
                } else {
                    break
                }
            }
            if (position + spanCount < childSize && spanSizeLookup.getSpanSize(position + spanCount) == 1) {
                return true
            }
        } else {
            if (position + 1 < childSize) {
                if (spanSizeLookup.getSpanSize(position + 1) == 1) {
                    return true
                }
            }
        }
        return false
    }

    var radiusData = floatArrayOf(
        cornerRadius,
        cornerRadius,
        cornerRadius,
        cornerRadius
    )
    var emptyData = floatArrayOf(
        0f,
        0f,
        0f,
        0f
    )
    var gdCornerRadiiData = FloatArray(8)
    private fun copyData(isLast: Boolean, isFullRadius: Boolean) {
        val fullData = if (isFullRadius) radiusData else emptyData
        val fullIndex = if (isLast) 0 else fullData.size
        System.arraycopy(fullData, 0, gdCornerRadiiData, fullIndex, fullData.size)
    }

    private fun generateDrawable(hasLastLine: Boolean, hasNextLine: Boolean): GradientDrawable {
        val gd = GradientDrawable()
        copyData(true, !hasLastLine)
        copyData(false, !hasNextLine)
        gd.cornerRadii = gdCornerRadiiData
        gd.setColor(bgColor)
        return gd
    }

    class DrawConfig(
        var isHasLastLine: Boolean,
        var isHasNextLine: Boolean,
        var gradientDrawable: GradientDrawable,
        var position: Int
    )

    companion object {
        fun dp2px(dpValue: Float): Int {
            val scale = Resources.getSystem().displayMetrics.density
            return (dpValue * scale + 0.5f).toInt()
        }
    }
}