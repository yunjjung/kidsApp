package com.example.kidsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.VectorEnabledTintResources;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance(); //파이어베이스 인증
    private DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference("kidsApp");; // 실시간 데이터베이스

    EditText etEmail, etPwd; //로그인 입력 필드
    Button btnRegister, btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.et_email);
        etPwd = findViewById(R.id.et_pwd);

        btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //로그인 요청
                String strEmail = etEmail.getText().toString();
                String strPwd = etPwd.getText().toString();

                mFirebaseAuth.signInWithEmailAndPassword(strEmail, strPwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //로그인 성공
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();//현재 액티비티 파괴
                        }
                        else
                        {
                            Toast.makeText(LoginActivity.this, "로그인 실패", Toast.LENGTH_SHORT).show();
                            Log.w("error",task.getException());
                        }
                    }
                });
            }
        });

        btnRegister = findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //회원가입 화면으로 이동(현재 자신의 위치, 이동할 위치)
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }
}