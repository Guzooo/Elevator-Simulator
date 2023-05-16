package pl.guzooo.elevatorsimulator.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import pl.guzooo.elevatorsimulator.R;

public class TextUtils {

    public static String getVersion(Context context) {
        try {
            String packageName = context.getPackageName();
            PackageInfo info = context.getPackageManager().getPackageInfo(packageName, 0);
            return context.getString(R.string.version, info.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "??";
        }
    }
}
