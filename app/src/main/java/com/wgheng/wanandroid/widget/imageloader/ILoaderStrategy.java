package com.wgheng.wanandroid.widget.imageloader;

/**
 * Created by wgheng on 2018/7/4.
 */

public interface ILoaderStrategy {

	void loadImage(ImageLoader.LoaderOptions options);

	/**
	 * 清理内存缓存
	 */
	void clearMemoryCache();

	/**
	 * 清理磁盘缓存
	 */
	void clearDiskCache();

}
