package com.nb.kotlinui.extension

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.provider.MediaStore
import android.widget.ImageView
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder
import com.nb.kotlinui.KotlinUi
import com.nb.kotlinui.R
import com.zhy.autolayout.utils.ScreenUtils
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by NieBin on 2018-08-03
 * Github: https://github.com/nb312
 * Email: niebin312@gmail.com
 */

/**将string 转换为二维码 显示到 image view 中*/
fun String?.showImage(img: ImageView, front_color_id: Int = R.color.nb_text_black, center_image_id: Int = 0) {
    if (this.isNullOrEmpty()) return
    var (w, h) = ScreenUtils.getScreenSize(KotlinUi.context, true)
    this?.toQrBitmap(w = w, h = h, front_color_id = front_color_id, center_id = center_image_id) {
        //        img.setImageBitmap(it)
        img.background = BitmapDrawable(KotlinUi.context.resources, it)
    }
}

private fun String.toQrBitmap(w: Int = 800, h: Int = 800, front_color_id: Int = R.color.nb_text_black, center_id: Int = 0, call: (bit: Bitmap) -> Unit) {
    Observable.just(this)
            .subscribeOn(Schedulers.computation())
            .flatMap {
                var size = w
                if (w < h) size = h
                var bitmap = if (center_id != 0) {
                    val logoBitmap: Bitmap = BitmapFactory.decodeResource(KotlinUi.context.resources, center_id)
                    QRCodeEncoder.syncEncodeQRCode("$it", size / 4, front_color_id.id2Color, logoBitmap)
                } else {
                    QRCodeEncoder.syncEncodeQRCode("$it", size / 4, front_color_id.id2Color)
                }
                Observable.just(bitmap)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                call(it)
            }
}

fun String?.saveQR2file(success: () -> Unit) {
    if (this.isNullOrEmpty()) return
    this?.toQrBitmap {
        MediaStore.Images.Media.insertImage(KotlinUi.context.contentResolver, it, "double qr ${System.currentTimeMillis()}", "this is discretion for the qr")
        success()
    }
}