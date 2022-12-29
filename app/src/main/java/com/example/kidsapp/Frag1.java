package com.example.kidsapp;

//import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Frag1 extends Fragment {
    //메인화면
    private View view;
    TextView nickName;
    String nick;
    FirebaseUser user;
    FirebaseDatabase mDatabase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag1, container,false);

        return view;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//
//        nickName = (TextView) getActivity().findViewById(R.id.tv_ment);
//        String ment =  user.getDisplayName() + "님 안녕하세요~!";
//        nickName.setText(ment);
    }
}
