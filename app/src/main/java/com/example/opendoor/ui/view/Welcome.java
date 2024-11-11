package com.example.opendoor.ui.view;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.view.WindowManager;
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

        // Find each button by ID and set click listeners
        Button buttonRentOut = findViewById(R.id.buttonrenter);
        Button buttonForRent = findViewById(R.id.buttontenant);
        Button buttonBuyer = findViewById(R.id.buttonbuyer);
        Button buttonSeller = findViewById(R.id.buttonseller);

        // Find TextView by ID for gradient animation
        TextView multicolorTextView = findViewById(R.id.multicolorTextView);
        applyLeftToRightGradientAnimation(multicolorTextView);

        // Set up click listeners
        buttonRentOut.setOnClickListener(v -> startActivity(new Intent(Welcome.this, LoginRenter.class)));
        buttonForRent.setOnClickListener(v -> startActivity(new Intent(Welcome.this, LoginTenant.class)));
        buttonSeller.setOnClickListener(v -> startActivity(new Intent(Welcome.this, LoginSeller.class)));
        buttonBuyer.setOnClickListener(v -> startActivity(new Intent(Welcome.this, LoginBuyer.class)));
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
