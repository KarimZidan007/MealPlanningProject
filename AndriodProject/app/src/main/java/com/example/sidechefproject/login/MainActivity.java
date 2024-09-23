package com.example.sidechefproject.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sidechefproject.R;

public class MainActivity extends AppCompatActivity {
Button signUp;
Button signIn;
TextView signUpClickable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        signUp = findViewById(R.id.button7);
        signIn = findViewById(R.id.button8);
        //signUpClickable = findViewById(R.id.SignInTextView);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createAccountOut = new Intent(MainActivity.this ,createAccount.class);
                startActivity(createAccountOut);
            }
        });
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = new Intent(MainActivity.this ,SignIn.class);
                startActivity(signInIntent);
            }
        });

    }
}