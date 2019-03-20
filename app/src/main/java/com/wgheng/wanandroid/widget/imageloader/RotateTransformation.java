package com.wgheng.wanandroid.widget.imageloader;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.TransformationUtils;
import com.bumptech.glide.util.Util;

import java.nio.ByteBuffer;
import java.security.MessageDigest;

/**
 * Created by wgheng on 2018/7/9.
 * Description :
 */
public class RotateTransformation extends BitmapTransformation {

    private static final String ID = "com.banhunli.customer.widget.imageloader.RotateTransformation";
    private static final byte[] ID_BYTES = ID.getBytes(CHARSET);

    //旋转默认0
    private int degree;

    public RotateTransformation(int degree) {
        this.degree = degree;
    }

    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
//        Matrix matrix = new Matrix();
//        //旋转
//        matrix.postRotate(degree);
//        //生成新的Bitmap
//        return Bitmap.createBitmap(toTransform, 0, 0, toTransform.getWidth(), toTransform.getHeight(), matrix, true);
        return TransformationUtils.rotateImage(toTransform, degree);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof RotateTransformation) {
            RotateTransformation other = (RotateTransformation) o;
            return degree == other.degree;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Util.hashCode(ID.hashCode(),
                Util.hashCode(degree));
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update(ID_BYTES);

        byte[] radiusData = ByteBuffer.allocate(4).putInt(degree).array();
        messageDigest.update(radiusData);
    }
}
