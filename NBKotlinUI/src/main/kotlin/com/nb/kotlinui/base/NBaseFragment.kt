package com.nb.kotlinui.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nb.kotlinui.inter.IHttpFunction
import com.nb.kotlinui.inter.IUiFunction
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


/**
 * Created by NieBin on 25,六月,2018
 * Github: https://github.com/nb312
 * Email: niebin312@gmail.com
 */
open abstract class NBaseFragment : Fragment() {

    private var disposables = CompositeDisposable()
    abstract var layoutId: Int
    protected var contentView: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        container?.setBackgroundColor(android.R.color.transparent.toColor)
        contentView = LayoutInflater.from(context)
                .inflate(layoutId, container, false)
        return contentView
    }

    protected open fun onBeforeInit() {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

    private val Int.toColor: Int
        get() = resources.getColor(this)
}