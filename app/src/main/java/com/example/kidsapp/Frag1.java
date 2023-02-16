package com.example.kidsapp;

//import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Tag;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Frag1 extends Fragment {
    FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    //메인화면
    private View view;
    long mNow;
    Date mDate;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd");

    TextView mTextView;

    private String getTime(){
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        return mFormat.format(mDate);
    }
    TextView userName;
    String nick;
    private DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference("kidsApp");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag1, container,false);

        return view;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userName = (TextView) getActivity().findViewById(R.id.main_user);
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
        mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserAccount user = snapshot.getValue(UserAccount.class);
                Log.w("FirebaseData",user.toString());
                if(user == null){
                    Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
                }
                userName.setText(user.getNickname()+"님 환영합니다");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
               //실패
                userName.setText("null");
            }
        });


        mTextView = (TextView)getActivity().findViewById(R.id.main_date);
        mTextView.setText(getTime());
    }
}
