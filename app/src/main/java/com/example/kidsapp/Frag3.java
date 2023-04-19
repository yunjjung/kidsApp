package com.example.kidsapp;

//import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Frag3 extends Fragment implements OnItemClick {
    private FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser user = mFirebaseAuth.getCurrentUser();
    private DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference("kidsApp");

    private TextView averageHeart;
    private TextView totalStepcount;
    private TextView averageLevel;

    FragmentActivity intent = getActivity();
    //달력 부분
    TextView yearMonthText;
    LocalDate selectedDate; //년월 변수
    RecyclerView recyclerView; //객체 생성
    Calendar calendar;

    private View view;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag3, container, false);
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        averageHeart = (TextView)getView().findViewById(R.id.day_heart);
        averageLevel = (TextView)getView().findViewById(R.id.day_level);
        totalStepcount = (TextView)getView().findViewById(R.id.day_stepcount);

        yearMonthText = (TextView) getView().findViewById(R.id.text_yearMonth);
        ImageButton preBtn = (ImageButton) getView().findViewById(R.id.btn_pre);
        ImageButton nextBtn = (ImageButton) getView().findViewById(R.id.btn_next);
        recyclerView = (RecyclerView) getView().findViewById(R.id.recyclerView);

        //current date
        CalendarUtil.selectedDate = Calendar.getInstance();
        //calendar = Calendar.getInstance();

        //화면 설정
        setMonthView();


        //이전달 버튼
        preBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //현재 월 -1 변수에 담기
                CalendarUtil.selectedDate.add(Calendar.MONTH, -1);//-1
                setMonthView();
            }
        });

        //다음달 버튼
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //현재 월+1 변수에 담기
                CalendarUtil.selectedDate.add(Calendar.MONTH, 1);
                setMonthView();
            }
        });
    }


    //날짜 타입 설정
    @RequiresApi(api = Build.VERSION_CODES.O)
    private String monthYearFromDate(Calendar calendar) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM월");
        String monthYear = year + "년 " + month + "월";
        return monthYear;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String yearMonthFromDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월");
        return date.format(formatter);
    }

    //화면 설정
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setMonthView() {
        //년월 텍스트뷰 셋팅
        yearMonthText.setText(monthYearFromDate(CalendarUtil.selectedDate));
        //해당 월 날짜 가져오기
        ArrayList<Date> dayList = daysInMonthArray();
        //어뎁터 데이터 적용
        CalendarAdapter adapter = new CalendarAdapter(dayList, getContext(), this);
        //레이아웃 설정(열 7개)
        RecyclerView.LayoutManager manager = new GridLayoutManager(getActivity().getApplicationContext(), 7);
        recyclerView.setLayoutManager(manager);

        //어뎁터 적용.
        recyclerView.setAdapter(adapter);
        //날짜 클릭 되었을때

    }

    //클릭시 데이터 불러오는 이벤트
    @Override
    public void onClick(String value) {
        String date = "2023-04-" + value;
        System.out.println(date);
        mDatabaseRef.child("UserAccount").child(user.getUid()).child("SensorData").child("heart").child(date).child("averageHeart").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                averageHeart.setText(snapshot.getValue().toString());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        mDatabaseRef.child("UserAccount").child(user.getUid()).child("SensorData").child("stepCount").child(date).child("totalStepCount").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                totalStepcount.setText(snapshot.getValue().toString());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//        mDatabaseRef.child("UserAccount").child(user.getUid()).child("SensorData").child("heart").child(date).child("averageHeart").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                averageHeart.setText(snapshot.toString());
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
        System.out.println(value);
    }

    //날짜 생성
    @RequiresApi(api = Build.VERSION_CODES.O)
    private ArrayList<Date> daysInMonthArray(){
        ArrayList<Date> dayList = new ArrayList<>();
        //날짜 복사해서 변수 생성
        Calendar monthCalendar = (Calendar)CalendarUtil.selectedDate.clone();
        //YearMonth yearMonth = YearMonth.from(date);

        //해당 월 마지막 날짜 가져오기
        //int lastDay = yearMonth.lengthOfMonth();

        //1일로 셋팅
        monthCalendar.set(Calendar.DAY_OF_MONTH,1);
        //요일 가져와서 -1/ 일요일:1, 월요일:2
        int firstDayOfMonth = monthCalendar.get(Calendar.DAY_OF_WEEK)-1;
        //날짜 셋팅
        monthCalendar.add(Calendar.DAY_OF_MONTH, -firstDayOfMonth);
        //42전까지 반복
        while(dayList.size() < 42){
            //리스트에 날짜 등록
            dayList.add(monthCalendar.getTime());

            //1일씩 늘린 날짜로 변경
            monthCalendar.add(Calendar.DAY_OF_MONTH,1);
        }
//        //해당 월의 첫번째 날 가져오기
//        LocalDate firstDay = CalendarUtil.selectedDate.withDayOfMonth(1);
//
//        //첫번째 날 요일 가져오기(월:1, 일:7)
//        int dayOfWeek = firstDay.getDayOfWeek().getValue();
//
//        //날짜 생성
//        for(int i = 1; i < 42; i++){
//            if(i <= dayOfWeek || i > lastDay + dayOfWeek){
//                dayList.add(null);
//            }
//            else{
//                //dayList.add(String.valueOf(i - dayOfWeek));
//                dayList.add(LocalDate.of(CalendarUtil.selectedDate.getYear(), CalendarUtil.selectedDate.getMonth(), i- dayOfWeek));
//            }
//        }
        return dayList;


    }


//
//    //날짜 어뎁터에서 넘긴 데이터를 받는 메서드
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    @Override
//    public void onItemClick(String dayText){
//        String yearMonDay = yearMonthFromDate(selectedDate) + "" + dayText + "일";
//        Toast.makeText(this, yearMonDay, Toast.LENGTH_SHORT).show();
//    }
}
