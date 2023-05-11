package pl.guzooo.elevatorsimulator.utils;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;

public class ColorUtils {

    public static int getColorFromAttr(int attr, Context context){
        Resources.Theme theme = context.getTheme();
        TypedArray a = theme.obtainStyledAttributes(new int[]{attr});
        return a.getColor(0, 0);
    }
}
