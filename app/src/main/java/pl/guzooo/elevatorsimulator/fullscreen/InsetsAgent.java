package pl.guzooo.elevatorsimulator.fullscreen;

import android.view.View;

public interface InsetsAgent {
    void useInsets(View view, int left, int top, int right, int bottom);
}
