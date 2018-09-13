package com.nb.kotlinui.base

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import com.nb.kotlinui.R
import com.nb.kotlinui.extension.id2Color
import com.zhy.autolayout.utils.AutoUtils
import net.lucode.hackware.magicindicator.MagicIndicator
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView
import kotlin.math.roundToInt

/**
 * Created by NieBin on 2018-07-30
 * Github: https://github.com/nb312
 * Email: niebin312@gmail.com
 */
open class BasePagerAdapter(private var frags: MutableList<NBaseFragment>, manager: FragmentManager) : FragmentPagerAdapter(manager) {
    override fun getItem(pos: Int): Fragment {
        return frags[pos]
    }

    override fun getCount(): Int {
        return frags.size
    }
}

fun MagicIndicator.initTitle(context: Context, viewPager: ViewPager, titles: MutableList<String>, colors: MutableList<Int> = mutableListOf(R.color.nb_text_black.id2Color, R.color.nb_blue.id2Color), isMarchParent: Boolean = true) {
    val commonNavigator = CommonNavigator(context)
    commonNavigator.isAdjustMode = true
    commonNavigator.adapter = BaseNavigator(context, viewPager, titles, colors, isMarchParent)
    this.navigator = commonNavigator
    if (!isMarchParent) {
        layoutParams.width = getAllWidth(context, titles).times(AutoUtils.getPercentWidth1px()).roundToInt()
    }
    ViewPagerHelper.bind(this, viewPager)
}

private fun getAllWidth(context: Context, titles: MutableList<String>): Float {
    val titleView = ColorTransitionPagerTitleView(context)
    titleView.textSize = 12f
    var width = 0f
    for (title in titles) {
        width += titleView.paint.measureText(title)
    }
    return width
}

open class BaseNavigator(var context: Context,
                         private var viewPager: ViewPager,
                         private var titles: MutableList<String>,
                         private var colors: MutableList<Int> = mutableListOf(R.color.nb_text_black.id2Color, R.color.nb_blue.id2Color),
                         private var isMarchParent: Boolean = true) : CommonNavigatorAdapter() {
    override fun getTitleView(p0: Context?, index: Int): IPagerTitleView {
        val titleView = ColorTransitionPagerTitleView(context)

        titleView.normalColor = colors[0]
//        titleView.width = ScreenUtils.getScreenSize(context, true)[0] / titles.size
        titleView.selectedColor = colors[1]
        titleView.text = titles[index]
        titleView.textSize = 12f
        AutoUtils.autoTextSize(titleView)
        titleView.setOnClickListener {
            viewPager.currentItem = index
        }
        return titleView
    }


    override fun getCount(): Int {
        return titles.size
    }

    override fun getIndicator(p0: Context?): IPagerIndicator {
        val indicator = LinePagerIndicator(context)
        indicator.mode = if (isMarchParent) LinePagerIndicator.MODE_MATCH_EDGE else LinePagerIndicator.MODE_WRAP_CONTENT
        indicator.setColors(colors[1])
        return indicator
    }
}