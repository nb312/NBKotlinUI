package com.nb.kotlinui.extension

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.nb.kotlinui.KotlinUi
import com.nb.kotlinui.adapter.NBItemLine


/**
 * Created by NieBin on 2018-07-31
 * Github: https://github.com/nb312
 * Email: niebin312@gmail.com
 * RecyclerView 的扩展
 */

private const val HEIGHT_SCALE = 1
private const val WIDTH_SCALE = 1

fun RecyclerView.verticalLine(height: Int, isStack: Boolean = true) {
    var linear = LinearLayoutManager(KotlinUi.context, LinearLayoutManager.VERTICAL, false)
    linear.stackFromEnd = isStack
    this.layoutManager = linear

    var line = NBItemLine()
    line.verticalSize = height.times(HEIGHT_SCALE)
    this.addItemDecoration(line)
}

fun RecyclerView.horizontalLine(width: Int) {
    this.layoutManager = LinearLayoutManager(KotlinUi.context, LinearLayoutManager.HORIZONTAL, false)
    var line = NBItemLine()
    line.orientation = NBItemLine.DIVIDER_HORIZONTAL
    line.horizontalSize = width * WIDTH_SCALE
    this.addItemDecoration(line)
}

fun RecyclerView.verticalLine(height: Int, num: Int) {
    this.layoutManager = GridLayoutManager(KotlinUi.context, num, LinearLayoutManager.VERTICAL, false)
    var line = NBItemLine()
    line.orientation = NBItemLine.DIVIDER_VERTICAL
    line.verticalSize = height * HEIGHT_SCALE
    this.addItemDecoration(line)
}

fun RecyclerView.scroll2Top() {
    scrollToPosition(0)
    if (layoutManager is LinearLayoutManager) {
        (layoutManager as LinearLayoutManager).scrollToPositionWithOffset(0, 0)
    }
}