package com.don.easy2readyoedge.core.self;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;

/**
 * Created by Administrator on 2016/9/1.
 */
public class SelfPermissionsChecker {
    private static final String TAG = SelfPermissionsChecker.class.getSimpleName();
    private final Context context;


    public interface RequestPermissionExplain {

        /**
         * Show the explanation why your app has to request this permission.
         *
         * This method will be called when {@link android.support.v4.app.ActivityCompat#shouldShowRequestPermissionRationale(Activity, String)}
         * return true
         */
        void explain();

    }

    public SelfPermissionsChecker(Context context) {
        this.context = context;
    }

    public boolean lacksPermissions(String... permissions) {
        if (Build.VERSION.SDK_INT >= 23) {
            for (String permission : permissions) {
                if (lacksPermission(permission)) {
                    return true;
                }
            }
            return false;
        } else { //permission is automatically granted on sdk<23 upon installation
            SelfLog.v(TAG, "Permission is granted");
            return false;
        }
    }

    private boolean lacksPermission(String permission) {
        if (Build.VERSION.SDK_INT >= 23) {
            return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_DENIED;
        } else { //permission is automatically granted on sdk<23 upon installation
            SelfLog.v(TAG, "Permission is granted");
            return false;
        }
    }

}
