package com.nb.kotlinui.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import com.nb.kotlinui.inter.IHttpFunction
import com.nb.kotlinui.inter.IUiFunction
import com.zhy.autolayout.AutoFrameLayout
import com.zhy.autolayout.AutoLinearLayout
import com.zhy.autolayout.AutoRelativeLayout
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.io.Serializable


/**
 * Created by NieBin on 25,六月,2018
 * Github: https://github.com/nb312
 * Email: niebin312@gmail.com
 */
open class NBaseActivity : AppCompatActivity() {
    companion object {
        const val KEY_DATA_INTENT = "key_data_intent"
    }

    private var disposables = CompositeDisposable()

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        var view: View? = null
        if (name == "FrameLayout" || name == "com.zhy.autolayout.AutoFrameLayout") {
            view = AutoFrameLayout(context, attrs)
        }

        if (name == "LinearLayout" || name == "com.zhy.autolayout.AutoLinearLayout") {
            view = AutoLinearLayout(context, attrs)
        }

        if (name == "RelativeLayout" || name == "com.zhy.autolayout.AutoRelativeLayout") {
            view = AutoRelativeLayout(context, attrs)
        }
        return view ?: super.onCreateView(name, context, attrs)
    }


    protected open fun onBeforeInit() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        onBeforeInit()

        if (this is IUiFunction) {
            this.initView()
        }
        if (this is IHttpFunction) {
            this.initController()
        }
        if (this is IUiFunction) {
            this.initData()
        }
    }

    fun addComDisposable(dp: CompositeDisposable?) {
        disposables.addAll(dp)
    }

    fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!disposables.isDisposed) {
            disposables.dispose()
        }
    }
}