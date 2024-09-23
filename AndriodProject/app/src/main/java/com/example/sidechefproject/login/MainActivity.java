package com.example.sidechefproject.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sidechefproject.R;

public class MainActivity extends AppCompatActivity {
Button createAccount;
Button signIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        Intent createAccountOut = new Intent(this ,createAccount.class);
        startActivity(createAccountOut);
    }
}