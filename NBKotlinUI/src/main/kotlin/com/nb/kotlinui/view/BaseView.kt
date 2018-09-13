package com.nb.kotlinui.view

import android.app.Activity
import android.view.View

/**
 * Created by NieBin on 2018-07-23
 * Github: https://github.com/nb312
 * Email: niebin312@gmail.com
 */
abstract class BaseView(act: Activity? = null, view: View? = null) {
    protected var mAct: Activity? = null
    protected var mView: View? = null

    init {
        mAct = act
        mView = view
        initView()
    }

    abstract fun initView()
}