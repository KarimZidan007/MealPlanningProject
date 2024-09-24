package com.example.sidechefproject.login.Login;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sidechefproject.R;

public class createAccount extends AppCompatActivity {
    EditText passwordEditText;
    ImageView eyeImageView;
    TextView clickableSignIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_account);
         passwordEditText = findViewById(R.id.passSignin);
         eyeImageView = findViewById(R.id.showpassSignup);
        //handle the password state (visible or not)
        eyeImageView.setOnClickListener(new View.OnClickListener() {
            boolean isPasswordVisible = false;


            @Override
            public void onClick(View v) {
                if (isPasswordVisible) {
                    // Hide password
                    passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passwordEditText.setSelection(passwordEditText.getText().length());
                    eyeImageView.setImageResource(R.drawable.ic_eye); // Set image to closed eye icon
                } else {
                    // Show password
                    passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    passwordEditText.setSelection(passwordEditText.getText().length());
                    eyeImageView.setImageResource(R.drawable.ic_eye_open);
                }
                isPasswordVisible = !isPasswordVisible;
            }
        });
        clickableSignIn = findViewById(R.id.SignInTextView);
        clickableSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = new Intent(createAccount.this ,SignIn.class);
                startActivity(signInIntent);
            }
        });
    }
}