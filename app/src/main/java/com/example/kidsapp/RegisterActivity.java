package com.example.kidsapp;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {
    //파이어베이스를 위한 전역변수 선언
    private FirebaseAuth mFirebaseAuth; //파이어베이스 인증
    private DatabaseReference mDatabaseRef; // 실시간 데이터베이스
    private EditText etEmail, etPwd, etNickname; // 회원가입 입력 필드
    private Button btnRegister; //회원가입 버튼
    private Button btnNickName; //닉네임 중복 확인 버튼
    int flag = 0; //닉네임 중복 확인 (1:중복, 2 : 통과, 0: 중복 확인 안함)


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //초기화
        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("kidsApp");

        etEmail = findViewById(R.id.et_email);
        etPwd = findViewById(R.id.et_pwd);
        etNickname = findViewById(R.id.et_nickname);
        btnRegister = findViewById(R.id.btn_register);
//        btnNickName = findViewById(R.id.btn_nickName);

//        btnNickName.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
                //닉네임 중복 검사
                mDatabaseRef.child("UserAccount").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Log.w("tag", "닉네임 중복 드루와 " + flag);
                        for (DataSnapshot data : snapshot.getChildren()) {
                            UserAccount person = data.getValue(UserAccount.class);
                            if (etNickname.getText().toString().equals(person.getNickname())) {
                                flag = 1;
                                Toast.makeText(RegisterActivity.this, "닉네임 중복입니다.", Toast.LENGTH_SHORT).show();
                                flag = 0;
                                Log.w("tag", "닉네임 중복 찾았다" + flag);
                                break;
                            }
                        }
                        if(flag != 1 && flag !=0 ) flag = 2;
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

//            }
//        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //회원가입 처리 시작
                String nickName = etNickname.getText().toString();
                String strEmail = etEmail.getText().toString(); //회원가입 버튼 눌렸을 때 etEmail에 있는 필드를 가져옴
                String strPwd = etPwd.getText().toString();


                if(flag==2) {
                    //FirebaseAuth 진행
                    mFirebaseAuth.createUserWithEmailAndPassword(strEmail, strPwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.w("회원가입 전 플래그", "ㅁㅁ" + flag);
                                if (task.isSuccessful()) {
                                    Log.w("tag", "ㅁㅁ" + flag);
                                    FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
                                    UserAccount account = new UserAccount();
                                    account.setIdToken(firebaseUser.getUid());
                                    account.setNickname(nickName);
                                    account.setEmailId(firebaseUser.getEmail());
                                    account.setPassword(strPwd);

                                    mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).setValue(account);

                                    Toast.makeText(RegisterActivity.this, "회원가입 성공!", Toast.LENGTH_SHORT).show();
                                    flag = 0;
                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Log.e("태그", task.getException().getMessage());

                                    Toast.makeText(RegisterActivity.this, "회원가입 실패", Toast.LENGTH_SHORT).show();
                                }
//                        switch (flag) {
//                            case 1:
//                                Toast.makeText(RegisterActivity.this, "닉네임 중복입니다.", Toast.LENGTH_SHORT).show();
//                                flag = 0;
//                                break;
//                            case 0:
//                                Toast.makeText(RegisterActivity.this, "중복을 확인해주세요.", Toast.LENGTH_SHORT).show();
//                                break;
//                        }
                        }
                    });
                }
                if (flag == 0) {
                    Toast.makeText(RegisterActivity.this, "중복을 확인해주세요.", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
}