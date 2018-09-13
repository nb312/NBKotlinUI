package com.nb.kotlinui.adapter

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View

import com.zhy.autolayout.utils.AutoUtils

class NBItemLine : RecyclerView.ItemDecoration() {

    var divider: Drawable? = null
    var orientation = DIVIDER_VERTICAL
        set(orientation) {
            if (DIVIDER_VERTICAL != orientation && DIVIDER_HORIZONTAL != orientation && DIVIDER_BOTH != orientation) {
                throw IllegalArgumentException("invalid orientation")
            }
            field = orientation
        }
    var verticalSize: Int = 0
    var horizontalSize: Int = 0

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        if (null != divider) {
            when (orientation) {
                DIVIDER_VERTICAL -> drawVertical(canvas, parent)
                DIVIDER_HORIZONTAL -> drawHorizontal(canvas, parent)
                DIVIDER_BOTH -> drawBoth(canvas, parent)
                else -> {
                }
            }
        }
    }

    private fun drawVertical(canvas: Canvas, parent: RecyclerView) {
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight
        val childCount = parent.childCount - 1
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val top = child.bottom + params.bottomMargin
            val bottom = top + divider!!.intrinsicHeight
            divider!!.setBounds(left, top, right, bottom)
            divider!!.draw(canvas)
        }
    }

    private fun drawHorizontal(canvas: Canvas, parent: RecyclerView) {
        val top = parent.paddingTop
        val bottom = parent.height - parent.paddingBottom
        val childCount = parent.childCount - 1
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val left = child.right + params.rightMargin
            val right = left + divider!!.intrinsicWidth
            divider!!.setBounds(left, top, right, bottom)
            divider!!.draw(canvas)
        }

    }

    private fun drawBoth(canvas: Canvas, parent: RecyclerView) {
        val leftVertical = parent.paddingLeft
        val rightVertical = parent.width - parent.paddingRight
        val topHorizontal = parent.paddingTop
        val bottomHorizontal = parent.height - parent.paddingBottom
        val childCount = parent.childCount
        var spanCount = 0
        val layoutManager = parent.layoutManager
        if (layoutManager is GridLayoutManager) {
            spanCount = (parent.layoutManager as GridLayoutManager).spanCount
        }
        if (layoutManager is StaggeredGridLayoutManager) {
            spanCount = (parent.layoutManager as StaggeredGridLayoutManager).spanCount
        }
        val lineCount = if (0 == childCount % spanCount) childCount / spanCount else childCount / spanCount + 1
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val spanCountNow = (i + 1) % spanCount
            val lineCountNow = if (0 == (i + 1) % spanCount) (i + 1) / spanCount else (i + 1) / spanCount + 1
            if (lineCountNow != lineCount) {
                val topVertical = child.bottom + params.bottomMargin
                val bottomVertical = topVertical + divider!!.intrinsicHeight
                divider!!.setBounds(leftVertical, topVertical, rightVertical, bottomVertical)
                divider!!.draw(canvas)
            }
            if (spanCountNow != 0) {
                val leftHorizontal = child.right + params.rightMargin
                val rightHorizontal = leftHorizontal + divider!!.intrinsicWidth
                divider!!.setBounds(leftHorizontal, topHorizontal, rightHorizontal, bottomHorizontal)
                divider!!.draw(canvas)
            }
        }
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        when (orientation) {
            DIVIDER_VERTICAL -> outRect.set(0, 0, 0, (verticalSize * AutoUtils.getPercentHeight1px()).toInt())
            DIVIDER_HORIZONTAL -> outRect.set(0, 0, (horizontalSize * AutoUtils.getPercentWidth1px()).toInt(), 0)
            DIVIDER_BOTH -> outRect.set(0, 0, (horizontalSize * AutoUtils.getPercentWidth1px()).toInt(), (verticalSize * AutoUtils.getPercentWidth1px()).toInt())
            else -> {
            }
        }
    }

    companion object {

        val DIVIDER_VERTICAL = 0
        val DIVIDER_HORIZONTAL = 1
        val DIVIDER_BOTH = 2
    }
}
