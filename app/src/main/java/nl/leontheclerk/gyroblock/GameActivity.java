package nl.leontheclerk.gyroblock;

import android.app.Activity;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

public class GameActivity extends Activity {
    MediaPlayer backgroundMusic;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        Constants.SCREEN_WIDTH = dm.widthPixels;
        Constants.SCREEN_HEIGHT = dm.heightPixels;

        setContentView(new GamePanel(this));

        if(preferences.getBoolean("music_switch", true)){
            backgroundMusic = MediaPlayer.create(this, R.raw.backgroundmusic);
            backgroundMusic.setLooping(true);
            backgroundMusic.start();
        }

    }

    @Override
    protected void onStop(){
        super.onStop();
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if(preferences.getBoolean("music_switch", true)){
            backgroundMusic.release();
        }
        finish();
    }
}
