package com.example.sidechefproject.login.Login;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sidechefproject.R;

import Feed.NavActivity;

public class MainActivity extends AppCompatActivity {
Button signUp;
Button signIn;
TextView signUpClickable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        if (isNetworkAvailable(MainActivity.this.getApplicationContext())) {
            setContentView(R.layout.activity_main);
            signUp = findViewById(R.id.button7);
            signIn = findViewById(R.id.button8);
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
        else {
            // Inflate guest mode layout
            setContentView(R.layout.guest_mode_welcome);  // Replace with your actual guest mode layout name
            Button continueAsGuest = findViewById(R.id.btnGst); // Adjust the ID as per your layout
            continueAsGuest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, "Continuing as guest", Toast.LENGTH_SHORT).show();
                    Intent Start = new Intent(MainActivity.this, NavActivity.class);
                    startActivity(Start);
                    finish();
                }
            });
        }
    }
    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}

