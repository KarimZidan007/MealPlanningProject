package com.example.sidechefproject.login.Login;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sidechefproject.R;

import Feed.NavActivity;

public class SignIn extends AppCompatActivity {
    EditText passwordEditText;
    ImageView eyeImageView;
    TextView clickableSignUp ;
    Button signIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_in);
        eyeImageView = findViewById(R.id.showpassSignup);
        passwordEditText = findViewById(R.id.passSignin);
        signIn = findViewById(R.id.signUpBtn);
        eyeImageView.setOnClickListener(new View.OnClickListener() {
            boolean isPasswordVisible = false;
            @Override
            public void onClick(View v) {
                if (isPasswordVisible) {
                    // Hide password
                    passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passwordEditText.setSelection(passwordEditText.getText().length());
                    eyeImageView.setImageResource(R.drawable.ic_eye);
                } else {
                    // Show password
                    passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    passwordEditText.setSelection(passwordEditText.getText().length());
                    eyeImageView.setImageResource(R.drawable.ic_eye_open);
                }
                isPasswordVisible = !isPasswordVisible;
            }
        });
        clickableSignUp = findViewById(R.id.SignUpTextView);
        clickableSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createAccountOut = new Intent(SignIn.this ,createAccount.class);
                startActivity(createAccountOut);
                finish();
            }
        });
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Start = new Intent(SignIn.this , NavActivity.class);
                startActivity(Start);
                finish();
            }
        });

    }
}