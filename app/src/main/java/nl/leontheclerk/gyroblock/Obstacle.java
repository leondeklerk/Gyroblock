package nl.leontheclerk.gyroblock;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Obstacle implements GameObject {
    private Rect rectangle;
    private int color;
    private Rect rectangle2;
    private Rect rectangle3;

    public Rect getRectangle(){
        return rectangle;
    }

    public void addY(float y){
        rectangle.top += y;
        rectangle.bottom += y;
        rectangle2.top += y;
        rectangle2.bottom += y;
        rectangle3.top += y;
        rectangle3.bottom += y;
    }
    public Obstacle(int rectHeight, int color, int startX, int startY, int playerGap){
        this.color = color;
        rectangle = new Rect(0, startY, startX, startY + rectHeight);
        rectangle2 = new Rect(startX + playerGap, startY, Constants.SCREEN_WIDTH, startY + rectHeight);
        rectangle3 = new Rect(startX, startY, startX + playerGap, startY + rectHeight);
    }

    public boolean playerCollide(RectPlayer player){
        return Rect.intersects(player.getRectangle(), rectangle) || Rect.intersects(player.getRectangle(), rectangle2);
    }

    public Rect getScoreRect(){
        return  rectangle3;
    }

    public void deleteScoreRect(){
        rectangle3 = new Rect(-100, 0, 0,0);
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(rectangle, paint);
        canvas.drawRect(rectangle2, paint);
    }

    @Override
    public void update() {

    }
}
