package com.nb.kotlinui.extension

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import com.nb.kotlinui.R
import com.sunfusheng.marqueeview.MarqueeView
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import com.youth.banner.loader.ImageLoader

/**
 * Created by NieBin on 18-9-13
 * Github: https://github.com/nb312
 * Email: niebin312@gmail.com
 */

fun MarqueeView.changeData(stringList: MutableList<String>?) {
    if (null == stringList || stringList.isEmpty()) {
        return
    }
    try {
        startWithList(stringList)
        for (i in 0 until childCount) {
            if (getChildAt(i) is TextView) {
                (getChildAt(i) as TextView).ellipsize = android.text.TextUtils.TruncateAt.END
            }
        }
        if (1 < stringList.size) {
            startFlipping()
        } else {
            stopFlipping()
        }
    } catch (e: Exception) {
    }

}

fun Banner.initParam(color: Int = R.color.nb_background.id2Color) {
    val imageWidth = com.zhy.autolayout.config.AutoLayoutConifg.getInstance().screenWidth
    val imageHeight = imageWidth * 186 / 414
    setBackgroundColor(color)
    setImageLoader(object : ImageLoader() {
        override fun displayImage(context: Context, path: Any, imageView: ImageView) {
            //FIXME need you change.
        }
    })

    //        设置banner动画效果
    setBannerAnimation(Transformer.Stack)
    //        设置标题集合（当banner样式有显示title时）
    //        List<String> titles=new ArrayList<>();
    //        titles.add("這个标题不错哦");
    //        titles.add("什么情况，这也是标题");
    //        mainBanner.setBannerTitles(titles);
    //        mainBanner.setBannerStyle(BannerConfig.CENTER);
    //        设置自动轮播，默认为true
    isAutoPlay(true)
    //设置轮播时间
    setDelayTime(4000)
    //设置指示器位置（当banner模式中有指示器时）
    setIndicatorGravity(BannerConfig.CENTER)
}
