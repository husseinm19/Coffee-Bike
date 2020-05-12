package husseinm19.github.com.coffee_bike.Activities;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Window;
import android.widget.ProgressBar;
import java.util.Timer;
import java.util.TimerTask;
import androidx.appcompat.app.AppCompatActivity;
import husseinm19.github.com.coffee_bike.R;

/**
 * Created by Hussein on 24-03-2020
 */

public class Splash extends AppCompatActivity {

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }
    /** Called when the activity is first created. */
//    Thread splashTread;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ProgressBar progressBar= (ProgressBar) findViewById(R.id.intro_progress);
        progressBar.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.colorWhite), android.graphics.PorterDuff.Mode.SRC_IN);
        //getSupportActionBar().hide();
        Timer myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                // If you want to modify a view in your Activity
                Splash.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(Splash.this,Login.class));
                        finish();

                    }
                });
            }
        }, 3000);

    }
}
