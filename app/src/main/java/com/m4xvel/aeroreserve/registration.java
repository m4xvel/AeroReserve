package com.m4xvel.aeroreserve;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class registration extends AppCompatActivity {

    private EditText email_reg;
    private EditText login_reg;
    private EditText password_reg;
    private Button button_reg;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO); // Светлая тема по умолчанию

        setContentView(R.layout.registration);

        TextView tv = (TextView)findViewById(R.id.textView3);
        String s = (String) tv.getText();
        SpannableString ss = new SpannableString(s);
        ss.setSpan(new UnderlineSpan(), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(ss);

        TextView textView = findViewById(R.id.textView3);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(registration.this, authorization.class);
                startActivity(intent);
                finish();
            }
        });

        email_reg = findViewById(R.id.email_reg);
        password_reg = findViewById(R.id.password_reg);
        button_reg = findViewById(R.id.button_reg);


        mAuth = FirebaseAuth.getInstance();

        button_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (email_reg.getText().toString().isEmpty() ||  password_reg.getText().toString().isEmpty()) {
                    Toast.makeText(registration.this, "Введены некорректные данные", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.createUserWithEmailAndPassword(email_reg.getText().toString(), password_reg.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(registration.this, bottom_navigation_bar.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(registration.this, "Введены некорректные данные", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}