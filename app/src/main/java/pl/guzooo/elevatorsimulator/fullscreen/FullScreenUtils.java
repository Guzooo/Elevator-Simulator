package pl.guzooo.elevatorsimulator.fullscreen;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

public class FullScreenUtils {
    public static void setInsets(AppCompatActivity a, View v, InsetsAgent insetsAgent){
        WindowCompat.setDecorFitsSystemWindows(a.getWindow(), false);
        ViewCompat.setOnApplyWindowInsetsListener(v, (view, windowInsetsCompat) -> {
            Insets insets = windowInsetsCompat.getInsets(WindowInsetsCompat.Type.systemBars());
            insetsAgent.useInsets(view, insets.left, insets.top, insets.right, insets.bottom);
            return WindowInsetsCompat.CONSUMED;
        });
    }
}
