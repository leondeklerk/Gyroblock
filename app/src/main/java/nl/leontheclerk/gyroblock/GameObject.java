package nl.leontheclerk.gyroblock;

import android.graphics.Canvas;

public interface GameObject {
    void draw(Canvas canvas);
    void update();
}
