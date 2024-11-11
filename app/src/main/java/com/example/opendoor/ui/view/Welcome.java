package com.example.opendoor.ui.view;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.opendoor.R;

public class Welcome extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);

        // Find each button by ID
        Button buttonRentOut = findViewById(R.id.buttonRentOut);
        Button buttonForRent = findViewById(R.id.buttonForRent);
        Button buttonForSale = findViewById(R.id.buttonForSale);
        Button buttonAuctions = findViewById(R.id.buttonAuctions);

        // Find TextView by ID for animations
        TextView multicolorTextView = findViewById(R.id.multicolorTextView);

        // Apply animations to the TextView
        //applyFadeAnimation(multicolorTextView);       // Fade in/out animation
        //applySlideAnimation(multicolorTextView);      // Slide animation
        //applyZoomAnimation(multicolorTextView);       // Zoom animation
        applyLeftToRightGradientAnimation(multicolorTextView); // Left-to-right gradient animation

        // Set up click listeners
        buttonRentOut.setOnClickListener(v -> startActivity(new Intent(Welcome.this, LoginRntr.class)));
        buttonForRent.setOnClickListener(v -> startActivity(new Intent(Welcome.this, LoginRnt.class)));
        buttonForSale.setOnClickListener(v -> startActivity(new Intent(Welcome.this, Loginselr.class)));
        buttonAuctions.setOnClickListener(v -> startActivity(new Intent(Welcome.this, Loginbuyer.class)));
    }

    // Method for fade animation
    private void applyFadeAnimation(TextView textView) {
        Animation fadeInOutAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in_out);
        textView.startAnimation(fadeInOutAnimation);
    }

    // Method for slide animation
    private void applySlideAnimation(TextView textView) {
        Animation slideAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_left_right);
        textView.startAnimation(slideAnimation);
    }

    // Method for zoom animation
    private void applyZoomAnimation(TextView textView) {
        Animation zoomAnimation = AnimationUtils.loadAnimation(this, R.anim.zoom_in_out);
        textView.startAnimation(zoomAnimation);
    }

    // Method for left-to-right color gradient animation
    private void applyLeftToRightGradientAnimation(TextView textView) {
        final int textWidth = (int) textView.getPaint().measureText(textView.getText().toString());
        final LinearGradient gradient = new LinearGradient(
                -textWidth, 0, 0, 0,
                new int[]{0xFFFF6347, 0xFFFFC107, 0xFF3F51B5}, // Gradient colors: pink to yellow to blue
                null,
                Shader.TileMode.CLAMP);

        textView.getPaint().setShader(gradient);

        // Animate the gradient from left to right
        ValueAnimator animator = ValueAnimator.ofInt(0, textWidth * 2);
        animator.setDuration(4000); // Duration for a full transition
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.RESTART);

        animator.addUpdateListener(animation -> {
            int translateX = (int) animation.getAnimatedValue();
            LinearGradient animatedGradient = new LinearGradient(
                    -textWidth + translateX, 0, translateX, 0,
                    new int[]{0xFFFF6347, 0xFFFFC107, 0xFF3F51B5}, // Gradient colors
                    null,
                    Shader.TileMode.CLAMP);
            textView.getPaint().setShader(animatedGradient);
            textView.invalidate();
        });

        animator.start();
    }
}
