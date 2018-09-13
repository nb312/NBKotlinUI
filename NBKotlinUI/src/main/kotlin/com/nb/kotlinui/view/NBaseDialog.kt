package com.nb.kotlinui.view

import android.app.Dialog
import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.util.DisplayMetrics
import com.nb.kotlinui.R


/**
 * Created by NieBin on 2018-07-25
 * Github: https://github.com/nb312
 * Email: niebin312@gmail.com
 */

abstract class NBaseDialog<DataBinding : ViewDataBinding>(context: Context) : Dialog(context, R.style.custom_dialog) {
    abstract var layoutId: Int
    var isEnableCancel = true

    private fun initView() {
        var mBinding: DataBinding = DataBindingUtil.inflate(layoutInflater, layoutId, null, false)
        var view = mBinding.root
        setContentView(view)
        val window = window
        val displayMetrics = DisplayMetrics()
        val layoutParams = window!!.attributes
        window.windowManager.defaultDisplay.getMetrics(displayMetrics)
        layoutParams.width = displayMetrics.widthPixels
        layoutParams.height = displayMetrics.heightPixels
        window.attributes = layoutParams
        setCancelable(isEnableCancel)
        setCanceledOnTouchOutside(isEnableCancel)
        mBinding.initVew()
    }

    abstract fun DataBinding.initVew()

    override fun show() {
        if (!isShowing) {
            initView()
            super.show()
        }
    }

}

