package com.example.kidsapp;

import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

//화면과 데이터를 연결해주는 클래스
public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>{
    ArrayList<Date> dayList;

    //OnItemListener onItemListener;//인터페이스 선언

   public CalendarAdapter(ArrayList<Date> dayList){
        this.dayList = dayList;
        //this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_cell, parent, false);
        return new CalendarViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
        //날짜 변수에 담기
        Date monthDay = dayList.get(position);

        //달력 초기화
        Calendar dateCalendar = Calendar.getInstance();
        dateCalendar.setTime(monthDay);

        //현재 년 월
        int currentDay = CalendarUtil.selectedDate.get(Calendar.DAY_OF_MONTH);
        int currentMonth = CalendarUtil.selectedDate.get(Calendar.MONTH)+1;
        int currentYear = CalendarUtil.selectedDate.get(Calendar.YEAR);

        //넘어온 데이터
        int displayDay = dateCalendar.get(Calendar.DAY_OF_MONTH);
        int displayMonth = dateCalendar.get(Calendar.MONTH)+1;
        int displayYear = dateCalendar.get(Calendar.YEAR);


        //비교해서 년, 월 같으면 진한색 아니면 연한색 변경
        if(displayMonth==currentMonth && displayYear == currentYear){
            holder.parentView.setBackgroundColor((Color.parseColor("#FAF4C0")));
            //날짜까지 맞으면 색상 표시
            holder.itemView.setBackgroundColor(Color.parseColor("#FAF4C0"));
            //현재 날짜에 색상 표시
            if(displayDay == currentDay){
                holder.parentView.setBackgroundColor((Color.parseColor("#FAED7D")));
                holder.itemView.setBackgroundColor((Color.parseColor("#FAED7D")));
            }
        }
        else{
            holder.parentView.setBackgroundColor(Color.parseColor("#F6F6F6"));
        }

        //날짜 변수에 담기
        int dayNo = dateCalendar.get(Calendar.DAY_OF_MONTH);
        holder.dayText.setText(String.valueOf(dayNo));


//        if(day == null){
//            holder.dayText.setText("");
//        }
//        else{
//            holder.dayText.setText(String.valueOf(day.getDayOfMonth()));
//
//            //현재 날짜 색상 칠하기
//            if(day.equals(CalendarUtil.selectedDate)){
//                holder.parentView.setBackgroundColor(Color.LTGRAY);
//            }
//        }

        //텍스트 색상 지정
        //토요일 : 파랑
        if ((position + 1) % 7 == 0) {
            holder.dayText.setTextColor(Color.BLUE);
        } else if (position == 0 || position % 7 == 0) {
            //일요일은 빨강
            holder.dayText.setTextColor(Color.RED);
        }
        //날짜 클릭 이벤트
        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String day = holder.dayText.getText().toString();
                System.out.println(day);
//                int iYear = day.getYear();
//                int iMonth = day.getMonthValue();
//                int iDay = day.getDayOfMonth();
//                //인터페이스를 통해 날짜 넘겨줌.
//                //onItemListener.onItemClick(day);
//
//                String yearMonDay = iYear + "년 " + iMonth + "월 " + iDay + "일";
//                Toast.makeText(holder.itemView.getContext(), yearMonDay, Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public int getItemCount(){
        return dayList.size();
    }

    class CalendarViewHolder extends RecyclerView.ViewHolder{
        //초기화
        TextView dayText;
        View parentView;

        public CalendarViewHolder(@NonNull View itemView){
            super(itemView);

            dayText = itemView.findViewById(R.id.dayText);
            parentView = itemView.findViewById(R.id.parentView);
        }
    }

}
