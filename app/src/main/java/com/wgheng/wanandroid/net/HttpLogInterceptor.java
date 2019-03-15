package com.wgheng.wanandroid.net;


import android.support.annotation.NonNull;
import com.wgheng.wanandroid.BuildConfig;
import com.orhanobut.logger.Logger;
import okhttp3.*;
import okio.Buffer;
import okio.BufferedSource;
import okio.GzipSource;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Created by wgheng on 2018/7/10.
 * Description : 网络请求日志拦截器
 */
public class HttpLogInterceptor implements Interceptor {

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        if (BuildConfig.DEBUG) {
            printRequestInfo(request);
        }
        Response response = chain.proceed(request);
        if (BuildConfig.DEBUG) {
            printResponseBody(request.url().toString(), response);
        }
        return response;
    }

    private void printRequestInfo(Request request) {
        Logger.i("RequestUrl:" + request.url().toString());
    }

    /**
     * 打印响应体
     */
    private void printResponseBody(String url, Response response) throws IOException {
        Headers headers = response.headers();
        ResponseBody responseBody = response.body();

        if (responseBody != null) {
            long contentLength = responseBody.contentLength();
            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.buffer();

            Long gzippedLength = null;
            if ("gzip".equalsIgnoreCase(headers.get("Content-Encoding"))) {
                gzippedLength = buffer.size();
                GzipSource gzippedResponseBody = null;
                try {
                    gzippedResponseBody = new GzipSource(buffer.clone());
                    buffer = new Buffer();
                    buffer.writeAll(gzippedResponseBody);
                } finally {
                    if (gzippedResponseBody != null) {
                        gzippedResponseBody.close();
                    }
                }
            }

            Charset charset = Charset.forName("UTF-8");
            MediaType contentType = responseBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(Charset.forName("UTF-8"));
            }

            if (contentLength != 0) {
                Logger.i("url:" + url + "\nResponseBody:" + buffer.clone().readString(charset));//打印响应的json
            }

            if (gzippedLength != null) {
                Logger.i("url:" + url + "\n<-- END HTTP (" + buffer.size() + "-byte, "
                        + gzippedLength + "-gzipped-byte body)");
            } else {
                Logger.i("url:" + url + "\n<-- END HTTP (" + buffer.size() + "-byte body)");
            }
        }
    }

}
