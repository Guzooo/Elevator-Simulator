package pl.guzooo.elevatorsimulator.fullscreen;

import android.view.View;

public class AllInsetsAgent implements InsetsAgent{

    @Override
    public void useInsets(View view, int left, int top, int right, int bottom) {
        view.setPadding(left, top, right, bottom);
    }
}
