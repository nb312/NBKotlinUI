package com.nb.kotlinui.inter

/**
 * Created by NieBin on 2018-08-06
 * Github: https://github.com/nb312
 * Email: niebin312@gmail.com
 */
interface IUiFunction {
    /**初始化 视图*/
    fun initView()

    /**初始化数据*/
    fun initData()
}

interface IHttpFunction {
    fun initController()
}

/**初始化activity title*/
interface ITitleFunc {
    /**初始化activity title*/
    fun initTitle()
}

/**初始化 click 事件*/
interface IClickFunc {
    /**初始化 click 事件*/
    fun initClick()
}

/**初始化 recycler view*/
interface IRecyclerFunc {
    /**初始化 recycler view*/
    fun initRecycler()
}

/**检测是否为空*/
interface ICheckFunc {
    /**检测是否为空*/
    fun checkEmpty()
}
