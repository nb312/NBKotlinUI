package com.nb.kotlinui.view

import android.os.CountDownTimer
import android.widget.TextView
import com.nb.kotlinui.R
import com.nb.kotlinui.extension.id2Color

/**
 * Created by NieBin  on 2017/8/23.
 * <h1>Github地址</h1><br></br>
 * <a>https://github.com/nb312</a>
 */

/**
 * @time the count time with unit of seconds.default 60 seconds
 * */
class TViewCountDown(var textView: TextView?,
                     time: Int = 120,
                     private var obtainStr: String = "Send",
                     private var againStr: String = "S",
                     private var obtainColorId: Int = R.color.nb_blue,
                     private var againColorId: Int = R.color.nb_text_black) : CountDownTimer((time * 1000).toLong(), 1000) {

    override fun onFinish() {
        textView?.text = obtainStr
        textView?.setTextColor(obtainColorId.id2Color)
        textView?.isEnabled = true
        onFinishFuc()
    }

    override fun onTick(p0: Long) {
        if ((p0 / 1000).toInt() == 0) {

        } else {
            textView?.text = "${(p0 / 1000).toInt()}$againStr"
            textView?.setTextColor(againColorId.id2Color)
            textView?.isEnabled = false
        }
    }

    var onFinishFuc: () -> Unit = {}

}

object CountDownUtil {
    private var countMap = HashMap<String, TViewCountDown>()
    fun initEmailAgain(tag: String, textView: TextView,
                       time: Int = 120,
                       obtainId: String = "Send",
                       againId: String = "S") {
        var countDown: TViewCountDown?
        if (tag in countMap.keys) {
            countDown = countMap[tag]
            countDown?.textView = textView
        } else {
            countDown = TViewCountDown(textView, time = time, obtainStr = obtainId, againStr = againId)
            countMap[tag] = countDown
        }
        countDown?.onFinishFuc = {
            countMap.remove(tag)
        }
    }

    fun hasTag(tag: String): Boolean {
        return tag in countMap
    }

    fun start(tag: String) {
        if (tag in countMap.keys) {
            countMap[tag]?.start()
        }
    }

    fun stop(tag: String) {
        if (tag in countMap) {
            var countDown: TViewCountDown? = countMap[tag]
            countDown?.cancel()
            countMap.remove(tag)
        }
    }
}