package com.nb.kotlinui.base

import android.content.Intent
import com.jph.takephoto.app.TakePhoto
import com.jph.takephoto.app.TakePhotoImpl
import com.jph.takephoto.compress.CompressConfig
import com.jph.takephoto.model.InvokeParam
import com.jph.takephoto.model.TContextWrap
import com.jph.takephoto.model.TResult
import com.jph.takephoto.permission.InvokeListener
import com.jph.takephoto.permission.PermissionManager
import com.jph.takephoto.permission.TakePhotoInvocationHandler


/**
 * Created by NieBin on 2018-07-05
 * Github: https://github.com/nb312
 * Email: niebin312@gmail.com
 */
open class NBasePhotoActivity : NBaseActivity(), InvokeListener {
    private var takePhoto: TakePhoto? = null
    private var mTakeResultListener: TakePhoto.TakeResultListener? = null
    private var invokeParam: InvokeParam? = null
    private var pictureListener: NBasePictureListener? = null
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        getTakePhoto().onActivityResult(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults)
        PermissionManager.handlePermissionsResult(this, type, invokeParam, mTakeResultListener)
    }

    override fun invoke(invokeParam: InvokeParam): PermissionManager.TPermissionType {
        val type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.method)
        if (PermissionManager.TPermissionType.WAIT == type) {
            this.invokeParam = invokeParam
        }
        return type
    }

    /**
     * 获取TakePhoto实例
     *
     * @return
     */
    private fun getTakePhoto(): TakePhoto {
        if (takePhoto == null) {
            takePhoto = TakePhotoInvocationHandler.of(this).bind(TakePhotoImpl(this, mTakeResultListener)) as TakePhoto
            val builder = CompressConfig.Builder()
            val config = builder.enableQualityCompress(true).create()
            takePhoto?.onEnableCompress(config, false)
        }
        return takePhoto!!
    }

    fun NBasePictureListener?.takePicture() {
        pictureListener = this
        mTakeResultListener = object : TakePhoto.TakeResultListener {
            override fun takeSuccess(result: TResult?) {
                var nbPics = mutableListOf<NBasePicture>()
                for (img in result?.images!!) {
                    var nbP = NBasePicture(img.compressPath, img.originalPath)
                    nbPics.add(nbP)
                }
                pictureListener?.onSuccess(nbPics[0], nbPics)
            }

            override fun takeCancel() {
//                pictureListener?.onFail("cancel")
            }

            override fun takeFail(result: TResult?, msg: String?) {
                pictureListener?.onFail("failed")
            }

        }
        takePhoto = TakePhotoInvocationHandler.of(this@NBasePhotoActivity).bind(TakePhotoImpl(this@NBasePhotoActivity, mTakeResultListener)) as TakePhoto
        val builder = CompressConfig.Builder()
        val config = builder.enableQualityCompress(true).create()
        takePhoto?.onEnableCompress(config, false)
        takePhoto?.onPickFromGallery()
    }


}

interface NBasePictureListener {
    fun onSuccess(first: NBasePicture, imgList: MutableList<NBasePicture>)
    fun onFail(str: String)
}

data class NBasePicture(var compressPath: String = "", var originPath: String = "")