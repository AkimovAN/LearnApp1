package kaczmarek.learnapp1.ui.splash.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import kaczmarek.learnapp1.R;
import kaczmarek.learnapp1.ui.login.activities.LoginActivity;

public class MainActivity extends AppCompatActivity {
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();

        TextView mTextView = findViewById(R.id.informationTextView);
        Animation mAnimation = AnimationUtils.loadAnimation(this, R.anim.text_anim);
        mTextView.startAnimation(mAnimation);

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        },5000);
    }
}
