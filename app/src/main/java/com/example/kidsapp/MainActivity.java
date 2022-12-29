package com.example.kidsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;

    private BottomNavigationView bottomNavigationView; //하단탭
    private FragmentManager fm;
    private FragmentTransaction ft;
    private Frag1 frag1;
    private Frag2 frag2;
    private Frag3 frag3;
    private Frag4 frag4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomMenu);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_home:
                        setFrag(0);
                        break;

                    case R.id.action_anal:
                        setFrag(1);
                        break;

                    case R.id.action_mypage:
                        setFrag(2);
                        break;

                    case R.id.action_setting:
                        setFrag(3);
                        break;
                }
                return true;
            }
        });
        frag1 = new Frag1();
        frag2 = new Frag2();
        frag3 = new Frag3();
        frag4 = new Frag4();
        setFrag(0);//처음은 홈 화면


    }

    //프래그먼트 교체가 일어나는 실행문
    private void setFrag(int n) {
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        switch (n) {
            case 0:
                ft.replace(R.id.main, frag1);
                ft.commit(); //저장
                break;
            case 1:
                ft.replace(R.id.main, frag2);
                ft.commit(); //저장
                break;
            case 2:
                ft.replace(R.id.main, frag3);
                ft.commit(); //저장
                break;
            case 3:
                ft.replace(R.id.main, frag4);
                ft.commit(); //저장
                break;

        }

//        mFirebaseAuth = FirebaseAuth.getInstance(); //빼먹지 말기!
//        Button btnLogout = findViewById(R.id.btn_logout);
//        btnLogout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //로그아웃
//                mFirebaseAuth.signOut();
//
//                Intent LogoutIntent = new Intent(MainActivity.this, LoginActivity.class);
//                startActivity(LogoutIntent);
//                //finish();
//            }
//        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        mFirebaseAuth = FirebaseAuth.getInstance();
        switch (item.getItemId()) {
            case R.id.btn_logout:
                mFirebaseAuth.signOut();

                Intent LogoutIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(LogoutIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}