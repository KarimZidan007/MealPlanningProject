package com.example.sidechefproject.login.Login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.airbnb.lottie.LottieAnimationView;
import com.example.sidechefproject.R;

public class LottieActivity extends AppCompatActivity {

    private static final long ANIMATION_DURATION = 3000; // Duration in milliseconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lottie);

        LottieAnimationView lottieAnimationView = findViewById(R.id.lottie_animation_view);
        lottieAnimationView.setAnimation(R.raw.lottie); // Load from res/raw
        lottieAnimationView.playAnimation();

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(LottieActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }, ANIMATION_DURATION);
    }
}
