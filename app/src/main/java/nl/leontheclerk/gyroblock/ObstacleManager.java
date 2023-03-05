package nl.leontheclerk.gyroblock;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.preference.PreferenceManager;

import java.util.ArrayList;

public class ObstacleManager {
    private final ArrayList<Obstacle> obstacles;
    private final int playerGap;
    private final int obstacleGap;
    private final int obstacleHeight;
    private final int color;

    private long startTime;
    private final long initTime;
    private int score = 0;

    private final SharedPreferences preferences;

    public ObstacleManager(int playerGap, int obstacleGap, int obstacleHeight, int color, Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);

        this.playerGap = playerGap;
        this.obstacleGap = obstacleGap;
        this.obstacleHeight = obstacleHeight;
        this.color = color;

        startTime = initTime = System.currentTimeMillis();

        obstacles = new ArrayList<>();

        populateObstacles();
    }

    public boolean playerCollide(RectPlayer player) {
        for (Obstacle ob : obstacles) {
            if (ob.playerCollide(player)) {
                return true;
            }
        }
        return false;
    }

    private void populateObstacles() {
        int currY = -5 * Constants.SCREEN_HEIGHT / 4;
        while (currY < 0) {
            int xStart = (int) (Math.random() * (Constants.SCREEN_WIDTH - playerGap));
            obstacles.add(new Obstacle(obstacleHeight, color, xStart, currY, playerGap));
            currY += obstacleHeight + obstacleGap;
        }
    }

    public void update() {
        if (startTime < Constants.INIT_TIME) {
            startTime = Constants.INIT_TIME;
        }
        int elapsedTime = (int) (System.currentTimeMillis() - startTime);
        startTime = System.currentTimeMillis();
        float speed = (float) (Math.sqrt(1 + (startTime - initTime) / 3000f)) * Constants.SCREEN_HEIGHT / 10000.0f;
        for (Obstacle ob : obstacles) {
            ob.addY(speed * elapsedTime);
        }
        if (obstacles.get(obstacles.size() - 1).getRectangle().top >= Constants.SCREEN_HEIGHT) {
            int xStart = (int) (Math.random() * (Constants.SCREEN_WIDTH - playerGap));
            obstacles.add(0, new Obstacle(obstacleHeight, color, xStart, obstacles.get(0).getRectangle().top - obstacleHeight - obstacleGap, playerGap));
            obstacles.remove(obstacles.size() - 1);
        }

        score();
        int curTopScore = Integer.parseInt(preferences.getString("high_score", "0"));
        if (score > curTopScore) {
            preferences.edit().putString("high_score", String.valueOf(score)).apply();
        }
    }

    public void score() {
        RectPlayer player = GameplayScene.player;
        for (Obstacle ob : obstacles) {
            if (Rect.intersects(ob.getScoreRect(), player.getRectangle()) && !ob.playerCollide(player)) {
                score++;
                ob.deleteScoreRect();
            }

        }
    }

    public void draw(Canvas canvas) {
        for (Obstacle ob : obstacles)
            ob.draw(canvas);

        Paint paint = new Paint();
        paint.setTextSize(Constants.SCREEN_WIDTH / 10f);
        paint.setColor(Color.WHITE);
        canvas.drawText("" + score, 50, 50 + paint.descent() - paint.ascent(), paint);
    }

}
