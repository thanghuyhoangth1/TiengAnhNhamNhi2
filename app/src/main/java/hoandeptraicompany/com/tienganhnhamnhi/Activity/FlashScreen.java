package hoandeptraicompany.com.tienganhnhamnhi.Activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.daimajia.androidanimations.library.zooming_entrances.ZoomInUpAnimator;

import hoandeptraicompany.com.tienganhnhamnhi.R;

public class FlashScreen extends AppCompatActivity {
    private Button btnBatDau;
    private Button btnExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_screen);

        btnBatDau = (Button) findViewById(R.id.btnStart);
        btnExit = (Button) findViewById(R.id.btnClose);
        setFontForButton(btnBatDau);
        setFontForButton(btnExit);

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnBatDau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FlashScreen.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void setFontForButton(Button btn) {
        Typeface font = Typeface.createFromAsset(getAssets(), "AstounderSquaredBB.otf");
        btn.setTypeface(font);

    }
}
