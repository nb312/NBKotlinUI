package com.icoinbay.app.extension

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.nb.kotlinui.KotlinUi
import com.nb.kotlinui.adapter.NBItemLine
import com.zhy.autolayout.utils.AutoUtils
import kotlin.math.roundToInt


/**
 * Created by NieBin on 2018-07-31
 * Github: https://github.com/nb312
 * Email: niebin312@gmail.com
 * RecyclerView 的扩展
 */

fun RecyclerView.verticalLine(height: Int, isStack: Boolean = true) {
    var linear = LinearLayoutManager(KotlinUi.context, LinearLayoutManager.VERTICAL, false)
    linear.stackFromEnd = isStack
    this.layoutManager = linear

    var line = NBItemLine()
    var h = AutoUtils.getPercentHeight1px()
    line.verticalSize = (height * h).roundToInt()
    this.addItemDecoration(line)
}

fun RecyclerView.horizontalLine(width: Int) {
    this.layoutManager = LinearLayoutManager(KotlinUi.context, LinearLayoutManager.HORIZONTAL, false)
    var line = NBItemLine()
    var h = AutoUtils.getPercentWidth1px()
    line.orientation = NBItemLine.DIVIDER_HORIZONTAL
    line.horizontalSize = (width * h).roundToInt()
    this.addItemDecoration(line)
}

fun RecyclerView.verticalLine(width: Int, num: Int) {
    this.layoutManager = GridLayoutManager(KotlinUi.context, num, LinearLayoutManager.VERTICAL, false)
    var line = NBItemLine()
    var h = AutoUtils.getPercentWidth1px()
    line.orientation = NBItemLine.DIVIDER_VERTICAL
    line.verticalSize = (width * h).roundToInt()
    this.addItemDecoration(line)
}

fun RecyclerView.scroll2Top() {
    scrollToPosition(0)
    if (layoutManager is LinearLayoutManager) {
        (layoutManager as LinearLayoutManager).scrollToPositionWithOffset(0, 0)
    }
}