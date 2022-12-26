package com.example.kidsapp;

//import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Frag3 extends Fragment {
    //달력 부분
    TextView yearMonthText = (TextView)getView().findViewById(R.id.text_yearMonth) ;
    LocalDate selectedDate; //년월 변수
    RecyclerView recyclerView; //객체 생성

    private View view;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag3, container,false);

        ImageButton preBtn = (ImageButton)getView().findViewById(R.id.btn_pre);
        ImageButton nextBtn = (ImageButton)getView().findViewById(R.id.btn_next);
        recyclerView = (RecyclerView) getView().findViewById(R.id.recyclerView);

        //current date
        CalendarUtil.selectedDate = LocalDate.now();

        //화면 설정
        setMonthView();

        //이전달 버튼
        preBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //현재 월 -1 변수에 담기
                CalendarUtil.selectedDate = CalendarUtil.selectedDate.minusMonths(1);
                setMonthView();
            }
        });

        //다음달 버튼
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //현재 월+1 변수에 담기
                CalendarUtil.selectedDate = CalendarUtil.selectedDate.plusMonths(1);
            }
        });
        return view;
    }

    //날짜 타입 설정
   @RequiresApi(api = Build.VERSION_CODES.O)
    private String monthYearFromDate(LocalDate date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM월");
        return date.format(formatter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String yearMonthFromDate(LocalDate date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월");
        return date.format(formatter);
    }

    //화면 설정
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setMonthView(){
        //년월 텍스트뷰 셋팅
        yearMonthText.setText(monthYearFromDate(CalendarUtil.selectedDate));
        //해당 월 날짜 가져오기
        ArrayList<LocalDate> dayList = daysInMonthArray(CalendarUtil.selectedDate);
        //어뎁터 데이터 적용
        CalendarAdapter adapter = new CalendarAdapter(dayList);
        //레이아웃 설정(열 7개)
        RecyclerView.LayoutManager manager = new GridLayoutManager(getActivity().getApplicationContext(), 7);
        recyclerView.setLayoutManager(manager);

        //어뎁터 적용.
        recyclerView.setAdapter(adapter);
    }
//날짜 생성
    @RequiresApi(api = Build.VERSION_CODES.O)
    private ArrayList<LocalDate> daysInMonthArray(LocalDate date){
        ArrayList<LocalDate> dayList = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);

        //해당 월 마지막 날짜 가져오기
        int lastDay = yearMonth.lengthOfMonth();

        //해당 월의 첫번째 날 가져오기
        LocalDate firstDay = CalendarUtil.selectedDate.withDayOfMonth(1);

        //첫번째 날 요일 가져오기(월:1, 일:7)
        int dayOfWeek = firstDay.getDayOfWeek().getValue();

        //날짜 생성
        for(int i = 1; i < 42; i++){
            if(i <= dayOfWeek || i > lastDay + dayOfWeek){
                dayList.add(null);
            }
            else{
                //dayList.add(String.valueOf(i - dayOfWeek));
                dayList.add(LocalDate.of(CalendarUtil.selectedDate.getYear(), CalendarUtil.selectedDate.getMonth(), i- dayOfWeek));
            }
        }
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
