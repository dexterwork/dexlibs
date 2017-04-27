package dexter.studio.dexlib.permission;

import android.app.Activity;
import android.os.Build;

/**
 * Created by Dexter on 2017/4/27.
 */

public abstract class Permission {

    Activity activity;





    //sample------write this in activity------------------------------------------------------------------------------
//    private boolean isPassPermission() {
//        String[] permissions = new String[]{android.Manifest.permission.READ_PHONE_STATE,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE};
//        Permission per = new Permission(this, permissions) {
//            @Override
//            protected void onPass(boolean isPass) {
//                MLog.v("ha", "on pass=" + isPass);
//            }
//        };
//        return per.check();
//    }
    //sample------------------------------------------------------------------------------------

    public Permission(Activity activity, String[] permissions) {
        this.activity = activity;
        this.permissions = permissions;
    }

    String[] permissions;


    public boolean check() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PermissionsChecker checker = new PermissionsChecker(activity);
            if (checker.lacksPermissions(permissions)) {
                PermissionsActivity.permissionListener = new PermissionsActivity.PermissionListener() {
                    @Override
                    public void onPass() {
                        Permission.this.onPass(true);
                    }

                    @Override
                    public void onDeny() {
                        Permission.this.onPass(false);
                    }
                };
                PermissionsActivity.startActivityForResult(activity, 0, permissions);
                return false;
            } else {
                return true;
            }
        }
        return true;
    }


    protected abstract void onPass(boolean isPass);

}
