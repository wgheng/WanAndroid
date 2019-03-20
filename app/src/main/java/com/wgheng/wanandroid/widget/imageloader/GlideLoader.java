package com.wgheng.wanandroid.widget.imageloader;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.wgheng.wanandroid.app.App;

/**
 * Created by wgheng on 2018/7/9.
 * Description :
 */
public class GlideLoader implements ILoaderStrategy {

    @Override
    public void loadImage(final ImageLoader.LoaderOptions options) {

        Object context = options.context;

        RequestManager requestManager = null;
        if (context instanceof View) {
            requestManager = Glide.with((View) context);
        } else if (context instanceof Fragment) {
            requestManager = Glide.with((Fragment) context);
        } else if (context instanceof android.app.Fragment) {
            requestManager = Glide.with((android.app.Fragment) context);
        } else if (context instanceof FragmentActivity) {
            requestManager = Glide.with((FragmentActivity) context);
        } else if (context instanceof Activity) {
            requestManager = Glide.with((Activity) context);
        } else if (context instanceof Context) {
            requestManager = Glide.with((Context) context);
        }

        if (requestManager == null) {
            throw new NullPointerException("context must not be null");
        }

        Object source = null;
        if (options.url != null) {
            source = options.url;
        } else if (options.file != null) {
            source = options.file;
        } else if (options.drawableResId != 0) {
            source = options.drawableResId;
        } else if (options.uri != null) {
            source = options.uri;
        }

        // 直接获取图片信息而不是加载图片到视图
        if (options.resourceInfoCallback != null) {
            requestManager.asBitmap().load(source).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                    options.resourceInfoCallback.onResourceReady(resource);
                }
            });
            return;
        }

        RequestBuilder<Drawable> builder = requestManager.load(source);
        RequestOptions requestOptions = new RequestOptions();
        if (options.targetHeight > 0 && options.targetWidth > 0) {
            requestOptions = requestOptions.override(options.targetWidth, options.targetHeight);
        }
        if (options.isCenterInside) {
            requestOptions = requestOptions.centerInside();
        } else if (options.isCenterCrop) {
            requestOptions = requestOptions.centerCrop();
        }
        if (options.config != null) {
            if (options.config == Bitmap.Config.ARGB_8888) {
                requestOptions = requestOptions.format(DecodeFormat.PREFER_ARGB_8888);
            } else {
                requestOptions = requestOptions.format(DecodeFormat.PREFER_RGB_565);
            }
        }
        if (options.errorResId != 0) {
            requestOptions = requestOptions.error(options.errorResId);
        }
        if (options.placeholderResId != 0) {
            requestOptions = requestOptions.placeholder(options.placeholderResId);
        }

        if (options.skipDiskCache) {
            requestOptions = requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
        }

        requestOptions = requestOptions.skipMemoryCache(options.skipMemoryCache);

        if (options.isCenterCrop) {
            requestOptions = requestOptions.centerCrop();
        }
        if (options.isCenterInside) {
            requestOptions = requestOptions.centerInside();
        }

        if (options.isCircleCrop) {
            requestOptions = requestOptions.circleCrop();
        }

        //主动调用transform需要使用clone的requestOptions对象，否则图片处理效果不能叠加
        if (options.corner != 0) {
            requestOptions = requestOptions.clone().transform(new RoundedCorners(options.corner));
        }

        if (options.degrees != 0) {
            requestOptions = requestOptions.clone().transform(new RotateTransformation(options.degrees));
        }


        if (options.callBack != null) {
            builder = builder.listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    options.callBack.onLoadFailed(e);
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    options.callBack.onLoadComplete();
                    return false;
                }
            });
        }

        builder = builder.apply(requestOptions);

        if (options.targetView instanceof ImageView) {
            builder.into(((ImageView) options.targetView));
        } else {
            throw new RuntimeException("Only can load into ImageView");
        }

    }

    @Override
    public void clearMemoryCache() {
        Glide.get(App.context).clearMemory();
    }

    @Override
    public void clearDiskCache() {
        Glide.get(App.context).clearDiskCache();
    }

}
