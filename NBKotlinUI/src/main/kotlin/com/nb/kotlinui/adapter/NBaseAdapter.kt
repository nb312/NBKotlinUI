package com.nb.kotlinui.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import java.io.Serializable

/**
 * Created by NieBin on 2018-07-19
 * Github: https://github.com/nb312
 * Email: niebin312@gmail.com
 */
abstract class NBaseAdapter<DataItem : NBaseDataItemInter, DataBindView : ViewDataBinding>(var context: Context?, private var dataList: MutableList<DataItem> = mutableListOf()) : RecyclerView.Adapter<NBaseVHolder<DataBindView>>() {
    private var mDataList: MutableList<DataItem> = mutableListOf()
    /**this must be init by user, if you do not init it.*/
    abstract var layoutId: Int
    var hasClick = true
    var clickListener: (item: DataItem) -> Unit = {}

    init {
        mDataList.clear()
        mDataList.addAll(dataList)
        mDataList = mDataList.filterNot {
            it == null
        }.toMutableList()

    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): NBaseVHolder<DataBindView> {
        var bindView: DataBindView = DataBindingUtil.inflate(LayoutInflater.from(context), layoutId, parent, false)
        return NBaseVHolder(bindView)
    }

    override fun getItemCount(): Int = mDataList?.size
    override fun onBindViewHolder(vHolder: NBaseVHolder<DataBindView>, pos: Int) {
        var item = getPosItem(pos)
        if (item != null && hasClick) {
            vHolder.mBindView.root.setOnClickListener {
                clickListener(item)
            }
        }
        if (item != null) {
            vHolder.mBindView.onBindView(item)
        }
    }

    private fun getPosItem(pos: Int): DataItem? {
        return if (mDataList.size == 0) {
            null
        } else {
            var p = pos % mDataList.size
            mDataList[p]
        }
    }

    abstract fun DataBindView.onBindView(dataItem: DataItem)

    /**append the data to its trail.*/
    fun changeAppendPart(list: MutableList<DataItem>) {
        if (list.size == 0) return
        var pos = mDataList.size
        mDataList.addAll(list)
        notifyItemRangeChanged(pos, list.size + pos - 1)
    }

    /** clear the data then add all to it.*/
    open fun changeAll(list: MutableList<DataItem>?) {
        mDataList.clear()
        mDataList.addAll(list ?: mutableListOf())
        notifyDataSetChanged()
    }

}

class NBaseVHolder<DataBindView : ViewDataBinding>(var mBindView: DataBindView) : RecyclerView.ViewHolder(mBindView.root)

interface NBaseDataItemInter : Serializable