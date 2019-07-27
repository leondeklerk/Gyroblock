package nl.leontheclerk.gyroblock;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button button;
    ImageButton settingsButton, informationButton;
    Intent intentGame, intentSettings;
    TextView highScore;
    SharedPreferences preferences;
    AlertDialog.Builder alertBuilder;
    int alertTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alertTheme = R.style.AlertDialogTheme_dark;
        button = findViewById(R.id.button);
        settingsButton = findViewById(R.id.setting_button);
        informationButton = findViewById(R.id.info_button);

        highScore = findViewById(R.id.high_score);
        highScore.setText(preferences.getString("high_score", "0"));

        button.setOnClickListener(this);
        settingsButton.setOnClickListener(this);
        informationButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == button){
            startActivity(intentGame = new Intent(this, GameActivity.class));
        } else if(v == settingsButton){
            startActivity(intentSettings = new Intent(this, SettingsActivity.class));
        } else if(v == informationButton){
            alertBuilder = new AlertDialog.Builder(this, alertTheme);
            alertBuilder.setMessage(getString(R.string.game_info)).setPositiveButton(R.string.dialog_confirm, new DialogInterface.OnClickListener()  {
                public void onClick(DialogInterface dialog, int id) {
                }
            });
            alertBuilder.show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        highScore.setText(preferences.getString("high_score", "0"));
    }
}
