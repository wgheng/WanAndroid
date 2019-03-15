package com.wgheng.wanandroid.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wgheng on 2017/8/18.
 * <p>
 * 动态权限申请工具类
 */

public class PermissionUtils {

    private static final String TAG = PermissionUtils.class.getName();

    /***
     * 获取需要授权中尚未被授权的权限集合
     */
    private static List<String> getDeniedPermissions(Context context, @NonNull String... perms) {


        List<String> deniedPermissions = new ArrayList<>();

        // Always return true for SDK < M, let the system deal with the permissions
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return deniedPermissions;
        }

        // Null context may be passed if we have detected Low API (less than M) so getting
        // to this point with a null context should not be possible.
        if (context == null) {
            throw new IllegalArgumentException("Can't check permissions for null context");
        }

        for (String perm : perms) {
            if (ContextCompat.checkSelfPermission(context, perm)
                    != PackageManager.PERMISSION_GRANTED) {
                deniedPermissions.add(perm);
            }
        }
        return deniedPermissions;
    }

    public static void requestPermissions(@NonNull Activity activity, int requestCode, @NonNull String[] perms, PermissionUtils.PermissionCallbacks listener) {

        List<String> deniedPermissions = getDeniedPermissions(activity, perms);

        if (deniedPermissions.size() == 0) {
            listener.onAllPermissionsGranted();
        } else {
            String[] deniedPermissionsArray = new String[deniedPermissions.size()];
            deniedPermissions.toArray(deniedPermissionsArray);
            ActivityCompat.requestPermissions(activity, deniedPermissionsArray, requestCode);
        }
    }

    public static void onRequestPermissionsResult(Activity activity,
                                                  int requestCode,
                                                  @NonNull String[] permissions,
                                                  @NonNull int[] grantResults,
                                                  @NonNull PermissionCallbacks callbacks) {

        List<String> denied = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            String perm = permissions[i];
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                denied.add(perm);
            }
        }
        // Report denied permissions, if any.
        if (!denied.isEmpty()) {
            //  如果用户点击了 不再提示 那就跳转到设置去吧
            ArrayList<String> hasRefuseList = new ArrayList<>();
            for (String deniedPermission : denied) {
                boolean hasRefused = !ActivityCompat.shouldShowRequestPermissionRationale(activity, deniedPermission);
                if (hasRefused) {
                    hasRefuseList.add(deniedPermission);
                }
            }
            if (hasRefuseList.size() > 0) {
                //点击了不再提示时
                callbacks.onPermissionsCantRequest(hasRefuseList);
            } else {
                callbacks.onPermissionsDenied(denied);
            }
        } else {
            callbacks.onAllPermissionsGranted();
        }
    }

    public interface PermissionCallbacks {

        void onAllPermissionsGranted();

        void onPermissionsDenied(List<String> perms);

        /***
         * 当用户点击了「不再提示」 后续申请这个权限就不会弹窗了
         * 需要引导用户去设置
         */
        void onPermissionsCantRequest(List<String> perms);

    }
}
