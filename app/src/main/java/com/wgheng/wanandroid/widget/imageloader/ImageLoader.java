package com.wgheng.wanandroid.widget.imageloader;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.view.View;

import java.io.File;

/**
 * 图片加载类
 * 策略或者静态代理模式，开发者只需要关心ImageLoader + LoaderOptions
 * Created by wgheng on 2018/7/4.
 */

public class ImageLoader {
    private static ILoaderStrategy sLoader;
    private static volatile ImageLoader sInstance;

    private ImageLoader() {
    }

    //单例模式
    public static ImageLoader getInstance() {
        if (sInstance == null) {
            synchronized (ImageLoader.class) {
                if (sInstance == null) {
                    sInstance = new ImageLoader();
                }
            }
        }
        return sInstance;
    }

    /**
     * 提供全局替换图片加载框架的接口，若切换其它框架，可以实现一键全局替换
     */
    public void setGlobalImageLoader(ILoaderStrategy loader) {
        sLoader = loader;
    }

    public LoaderOptions load(String url) {
        return new LoaderOptions(url);
    }

    public LoaderOptions load(int drawable) {
        return new LoaderOptions(drawable);
    }

    public LoaderOptions load(File file) {
        return new LoaderOptions(file);
    }

    public LoaderOptions load(Uri uri) {
        return new LoaderOptions(uri);
    }

    /**
     * 优先使用实时设置的图片loader，其次使用全局设置的图片loader
     *
     * @param options
     */
    public void loadOptions(LoaderOptions options) {
        if (options.loader != null) {
            options.loader.loadImage(options);
        } else {
            checkNotNull();
            sLoader.loadImage(options);
        }
    }

    public void clearMemoryCache() {
        checkNotNull();
        sLoader.clearMemoryCache();
    }

    public void clearDiskCache() {
        checkNotNull();
        sLoader.clearDiskCache();
    }

    private void checkNotNull() {
        if (sLoader == null) {
            throw new NullPointerException("you must be set your imageLoader at first!");
        }
    }

    public static class LoaderOptions {
        int placeholderResId;
        int errorResId;
        boolean isCircleCrop;//圆形图片
        boolean isCenterCrop;
        boolean isCenterInside;
        boolean skipDiskCache; //是否缓存到本地
        boolean skipMemoryCache; //是否内存缓存
        Bitmap.Config config = Bitmap.Config.RGB_565;
        int targetWidth;
        int targetHeight;
        int corner; //圆角角度
        int degrees; //旋转角度.注意:picasso针对三星等本地图片，默认旋转回0度，即正常位置。此时不需要自己rotate
        Drawable placeholder;
        View targetView;//targetView展示图片
        Callback callBack;//图片加载的状态回调
        ResourceInfoCallback resourceInfoCallback;//获取图片信息回调
        String url;
        File file;
        int drawableResId;
        Uri uri;
        ILoaderStrategy loader;//实时切换图片加载库
        Object context;//加载图片时的上下文

        public LoaderOptions(String url) {
            this.url = url;
        }

        public LoaderOptions(File file) {
            this.file = file;
        }

        public LoaderOptions(int drawableResId) {
            this.drawableResId = drawableResId;
        }

        public LoaderOptions(Uri uri) {
            this.uri = uri;
        }

        public LoaderOptions with(Object context) {
            this.context = context;
            return this;
        }

        public void into(View targetView) {
            this.targetView = targetView;
            ImageLoader.getInstance().loadOptions(this);
        }

        public LoaderOptions callback(Callback callBack) {
            this.callBack = callBack;
            return this;
        }

        public LoaderOptions loader(ILoaderStrategy imageLoader) {
            this.loader = imageLoader;
            return this;
        }

        public LoaderOptions placeholder(@DrawableRes int placeholderResId) {
            this.placeholderResId = placeholderResId;
            return this;
        }

        public LoaderOptions placeholder(Drawable placeholder) {
            this.placeholder = placeholder;
            return this;
        }

        public LoaderOptions error(@DrawableRes int errorResId) {
            this.errorResId = errorResId;
            return this;
        }

        public LoaderOptions circleCrop() {
            isCircleCrop = true;
            return this;
        }

        public LoaderOptions centerCrop() {
            isCenterCrop = true;
            return this;
        }

        public LoaderOptions centerInside() {
            isCenterInside = true;
            return this;
        }

        public LoaderOptions config(Bitmap.Config config) {
            this.config = config;
            return this;
        }

        public LoaderOptions resize(int targetWidth, int targetHeight) {
            this.targetWidth = targetWidth;
            this.targetHeight = targetHeight;
            return this;
        }

        /**
         * 圆角
         *
         * @param corner 度数
         */
        public LoaderOptions roundCorner(int corner) {
            this.corner = corner;
            return this;
        }

        public LoaderOptions skipDiskCache(boolean skipDiskCache) {
            this.skipDiskCache = skipDiskCache;
            return this;
        }

        public LoaderOptions skipMemoryCache(boolean skipMemoryCache) {
            this.skipMemoryCache = skipMemoryCache;
            return this;
        }

        public LoaderOptions rotate(int degrees) {
            this.degrees = degrees;
            return this;
        }

        /**
         * 直接获取图片信息
         */
        public void getResource(ResourceInfoCallback resourceInfoCallback) {
            this.resourceInfoCallback = resourceInfoCallback;
            ImageLoader.getInstance().loadOptions(this);
        }

    }

    public interface Callback {
        void onLoadComplete();

        void onLoadFailed(Exception e);
    }

    public interface ResourceInfoCallback {
        void onResourceReady(Bitmap bitmap);
    }

}