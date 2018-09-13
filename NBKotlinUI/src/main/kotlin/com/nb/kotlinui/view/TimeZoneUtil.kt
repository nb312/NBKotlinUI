package com.nb.kotlinui.view


import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by NieBin on 21,五月,2018
 * Github: https://github.com/nb312
 * Email: niebin312@gmail.com
 * This util is about changing time with the time zone.
 */
const val TIME_FORMAT_CHINA = "yyyy-MM-dd HH:mm:ss"
const val TIME_FORMAT = "MM-dd-yyyy HH:mm:ss"
const val TIME_FORMAT_SHORT = "HH:mm MM-dd"
private const val SERVER_TIME_ZONE = "UTC"

private fun changeToDate(time: String, curTZ: TimeZone, goalTZ: TimeZone, curFormat: DateFormat, goalFormat: DateFormat): String {
    curFormat.timeZone = curTZ
    var parsed: Date? = null // => Date is in UTC now
    try {
        parsed = curFormat.parse(time)
    } catch (e: ParseException) {
        e.printStackTrace()
    }

    goalFormat.timeZone = goalTZ
    return goalFormat.format(parsed)
}

/**日期格式转换为日期格式string*/
fun String.dateUtc2Local(curFmt: String = TIME_FORMAT_CHINA, goalFmt: String = TIME_FORMAT_CHINA): String {
    var curFormat = SimpleDateFormat(curFmt)
    val curTZ = TimeZone.getTimeZone(SERVER_TIME_ZONE)
    val poseTZ = TimeZone.getDefault()
    return changeToDate(this, curTZ, poseTZ, curFormat, SimpleDateFormat(goalFmt))
}

fun String.timestampUnit2Local(format: String = TIME_FORMAT_CHINA): String {
    return this.toLong().timestampUnit2Local(format)
}

/**时间戳转换为日期格式string*/
fun Long.timestampUnit2Local(format: String = TIME_FORMAT_CHINA): String {
    var timestamp = this
    val sourceFormat = SimpleDateFormat(format)
//    val offset = TimeZone.getDefault().rawOffset.toLong()
//    timestamp += offset //现在不需要调用
    val date = Date(timestamp)
    // format of the date
    sourceFormat.timeZone = TimeZone.getDefault()
    return sourceFormat.format(date)
}


