package com.passin.pmvp.util;

/**
 * <pre>
 * @author : passin
 * Contact me : https://github.com/passin95
 * Date : 2018/3/31 23:32
 * </pre>
 */

public class PermissionUtil {
    public static final String TAG = "Permission";

//    private PermissionUtil() {
//        throw new IllegalStateException("you can't instantiate me!");
//    }
//
//    public interface RequestPermission {
//        /**
//         * 权限请求成功
//         */
//        void onRequestPermissionSuccess();
//
//        /**
//         * 用户拒绝了权限请求, 权限请求失败, 但还可以继续请求该权限
//         *
//         * @param permissions 请求失败的权限名
//         */
//        void onRequestPermissionFailure(List<String> permissions);
//
//        /**
//         * 用户拒绝了权限请求并且用户选择了以后不再询问, 权限请求失败, 这时将不能继续请求该权限, 需要提示用户进入设置页面打开该权限
//         *
//         * @param permissions 请求失败的权限名
//         */
//        void onRequestPermissionFailureWithAskNeverAgain(List<String> permissions);
//    }
//
//
//    @SuppressLint("RxLeakedSubscription")
//    public static void requestPermission(final RequestPermission requestPermission, RxPermissions rxPermissions , String... permissions) {
//        if (permissions == null || permissions.length == 0) return;
//
//        List<String> needRequest = new ArrayList<>();
//        for (String permission : permissions) { //过滤调已经申请过的权限
//            if (!rxPermissions.isGranted(permission)) {
//                needRequest.add(permission);
//            }
//        }
//
//        if (needRequest.isEmpty()) {//全部权限都已经申请过，直接执行操作
//            requestPermission.onRequestPermissionSuccess();
//        } else {//没有申请过,则开始申请
//            rxPermissions
//                    .requestEach(needRequest.toArray(new String[needRequest.size()]))
//                    .buffer(permissions.length)
//                    .subscribe(new Consumer<List<Permission>>() {
//                        @Override
//                        public void accept(List<Permission> permissions) throws Exception {
//                            for (Permission p : permissions) {
//                                if (!p.granted) {
//                                    if (p.shouldShowRequestPermissionRationale) {
//                                        Timber.tag(TAG).d("Request permissions failure");
//                                        requestPermission.onRequestPermissionFailure(Arrays.asList(p.name));
//                                        return;
//                                    } else {
//                                        Timber.tag(TAG).d("Request permissions failure with ask never again");
//                                        requestPermission.onRequestPermissionFailureWithAskNeverAgain(Arrays.asList(p.name));
//                                        return;
//                                    }
//                                }
//                            }
//                            Timber.tag(TAG).d("Request permissions success");
//                            requestPermission.onRequestPermissionSuccess();
//                        }
//                    }, new Consumer<Throwable>() {
//                        @Override
//                        public void accept(Throwable throwable) throws Exception {
//
//                        }
//                    });
//        }
//
//    }
//
//
//    /**
//     * 请求摄像头权限
//     */
//    public static void launchCamera(RequestPermission requestPermission, RxPermissions rxPermissions  ) {
//        requestPermission(requestPermission, rxPermissions, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA);
//    }
//
//
//    /**
//     * 请求外部存储的权限
//     */
//    public static void externalStorage(RequestPermission requestPermission, RxPermissions rxPermissions  ) {
//        requestPermission(requestPermission, rxPermissions, Manifest.permission.WRITE_EXTERNAL_STORAGE);
//    }
//
//
//    /**
//     * 请求发送短信权限
//     */
//    public static void sendSms(RequestPermission requestPermission, RxPermissions rxPermissions ) {
//        requestPermission(requestPermission, rxPermissions, Manifest.permission.SEND_SMS);
//    }
//
//
//    /**
//     * 请求打电话权限
//     */
//    public static void callPhone(RequestPermission requestPermission, RxPermissions rxPermissions) {
//        requestPermission(requestPermission, rxPermissions, Manifest.permission.CALL_PHONE);
//    }
//
//
//    /**
//     * 请求获取手机状态的权限
//     */
//    public static void readPhonestate(RequestPermission requestPermission, RxPermissions rxPermissions) {
//        requestPermission(requestPermission, rxPermissions, Manifest.permission.READ_PHONE_STATE);
//    }

}
