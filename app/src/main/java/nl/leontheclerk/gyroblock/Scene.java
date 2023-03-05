package nl.leontheclerk.gyroblock;

import android.graphics.Canvas;
import android.view.MotionEvent;

public interface Scene {
    void update();
    void draw(Canvas canvas);
    void receiveTouch(MotionEvent event);
}
