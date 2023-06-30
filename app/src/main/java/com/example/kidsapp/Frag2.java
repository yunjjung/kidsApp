package com.example.kidsapp;

//import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

public class Frag2 extends Fragment {
    private FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser user = mFirebaseAuth.getCurrentUser();
//    private TextView gyro;
//    private TextView heart;
    private Intent intent;//intent 선언
    private TextView avg_heart;
    private TextView avg_level;
    private TextView totalStepCount;
    private View view;
    private LineChart chart_gyro;
    private LineChart chart_heart;
    float todayHeart=0;
    float averageLevel=0;
    //평균 계산을 위한 count
    int l4 = 0;
    int l1=0;
    ArrayList<Entry> heartArr = new ArrayList<>();//데이터를 담을 리스트

    ArrayList<Entry> accArr = new ArrayList<>();//데이터를 담을 리스트
    private LineChart chart_stepCount;
    ArrayList<Entry> stepCountArr = new ArrayList<>();//데이터를 담을 리스트
    int todayStepCount = 0;
    private DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference("kidsApp");
    int i = 0;
    Collection<Float> h; //해쉬에 있는 센서값 받기 위해 필요한 변수
    //Locale은 지역대 설정. 두번째 인자로는 Locale이 올 수 있다.
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
    DateTimeFormatter form = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.KOREA);
    LocalDate today = LocalDate.now(); //오늘의 날짜 저장
    LocalDateTime t = LocalDateTime.now();
    String time = LocalDateTime.now().format(formatter);
    ZonedDateTime Ktime = LocalDateTime.now().atZone(ZoneId.of("Asia/Seoul"));
    String s = Ktime.format(formatter);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag2, container, false);

        return view;
    }

    @Nullable
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        System.out.println("뭐가 문제");
        // heart = (TextView) getActivity().findViewById(R.id.read_heart);
        avg_heart = (TextView) getActivity().findViewById(R.id.avg_heart);
        //gyro = (TextView) getActivity().findViewById(R.id.read_gyro);


        Log.w("시간", Ktime + "시");
        Log.w("s시간", s + "시");
        Log.w("넣는 시간", today.format(form) + "시");

        SensorData s1 = new SensorData(120, LocalDateTime.now());
        SensorData s2 = new SensorData(140, LocalDateTime.now());


        //전에 기록되어있던 날짜 읽어오기

        //mDatabaseRef.child("UserAccount").child(user.getUid()).child("SensorData").child("gyro").child(today.format(form)).child("sensor").push().setValue(s1);//setValue("x: -0.01, y: -0.01, z: 0.05");
        //mDatabaseRef.child("UserAccount").child(user.getUid()).child("SensorData").child("gyro").child(today.format(form)).child("sensor").push().setValue(s2);//setValue("x: -0.01, y: -0.01, z: 0.05");
        //mDatabaseRef.child("UserAccount").child(user.getUid()).child("SensorData").child("accelerometer").child(today.format(form)).child("data").push().setValue(new ThSensorData(0,2,4));
        mDatabaseRef.child("UserAccount").child(user.getUid()).child("SensorData").child("accelerometer").child(today.format(form)).child("data").push().setValue(new ThSensorData(0,2,0));
        mDatabaseRef.child("UserAccount").child(user.getUid()).child("SensorData").child("accelerometer").child(today.format(form)).child("data").push().setValue(new ThSensorData(0,3,4));
        mDatabaseRef.child("UserAccount").child(user.getUid()).child("SensorData").child("accelerometer").child(today.format(form)).child("data").push().setValue(new ThSensorData(0,6,3));
        mDatabaseRef.child("UserAccount").child(user.getUid()).child("SensorData").child("accelerometer").child(today.format(form)).child("data").push().setValue(new ThSensorData(0,6,3));
        mDatabaseRef.child("UserAccount").child(user.getUid()).child("SensorData").child("accelerometer").child(today.format(form)).child("data").push().setValue(new ThSensorData(0,6,2));
        //mDatabaseRef.child("UserAccount").child(user.getUid()).child("SensorData").child("accelerometer").child(today.format(form)).child("data").push().setValue(new ThSensorData(6,4,1));
        //mDatabaseRef.child("UserAccount").child(user.getUid()).child("SensorData").child("accelerometer").child(today.format(form)).child("data").push().setValue(new ThSensorData(6,5,2));

        mDatabaseRef.child("UserAccount").child(user.getUid()).child("SensorData").child("heart").child(today.format(form)).child("data").push().setValue(new SensorData(80, t));
        mDatabaseRef.child("UserAccount").child(user.getUid()).child("SensorData").child("heart").child(today.format(form)).child("data").push().setValue(new SensorData(102, t));
        //mDatabaseRef.child("UserAccount").child(user.getUid()).child("SensorData").child("heart").child(time).push().setValue(new SensorData(120, t));
        //mDatabaseRef.child("UserAccount").child(user.getUid()).child("SensorData").child("heart").child(time).push().setValue(new SensorData(123,t));

        mDatabaseRef.child("UserAccount").child(user.getUid()).child("SensorData").child("stepCount").child(today.format(form)).child("data").push().setValue(new SensorData(2, t));
        mDatabaseRef.child("UserAccount").child(user.getUid()).child("SensorData").child("stepCount").child(today.format(form)).child("data").push().setValue(new SensorData(1, t));
        mDatabaseRef.child("UserAccount").child(user.getUid()).child("SensorData").child("stepCount").child(today.format(form)).child("data").push().setValue(new SensorData(2, t));
        mDatabaseRef.child("UserAccount").child(user.getUid()).child("SensorData").child("stepCount").child(today.format(form)).child("data").push().setValue(new SensorData(1, t));
        //데이터 읽기
//        mDatabaseRef.child("SensorDatas").child("test22").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                SensorData str = snapshot.getValue(SensorData.class);
////                gyro.setText(str.getGyro());
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

        //심박수 센서

        //초기화
        chart_heart = (LineChart) getActivity().findViewById(R.id.chart_heart);

        Log.w("tag", "통과");

//            float val = (float) (Math.random() * 100);
//            heart.add(new Entry(i, val));//values에 데이터를 담는다.


//        //Map.Entry 인터페이스로 Map 객체의 각 요소를 접근
//        Map<Float, Float> map = new HashMap<>();
//        mDatabaseRef.child("UserAccount").child(user.getUid()).child("SensorData").child("heart").child(time).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Log.w("data create", "들어는 왔다 for문 문제임");
//
//                Log.w("child 값", snapshot.getChildren() + "이거 맞아?");
//                for (DataSnapshot sensorData : snapshot.getChildren()) {
//
//                    Log.w("data create", "생성");
//                    float data = (float) sensorData.child("sensor").getValue(Integer.class);
//                    i++;
//                    heartArr.add(new Entry(i, data));
//                    todayHeart = todayHeart + data;
//                    Log.w("data create 얌", heartArr + "생성");
//                }
//                LineDataSet heartSet; //데이터셋에 데이터 넣기
//                heartSet = new LineDataSet(heartArr, "Heart");//데이터가 담긴 리스트를 LineDataSet으로 변환.
//
//                //리스트에 데이터셋 추가
//                ArrayList<LineDataSet> heartdataSets = new ArrayList<>();
//                heartdataSets.add(heartSet);
//
//                //LineData에 list추가
//                LineData data = new LineData(); //차트에 담길 데이터
//                data.addDataSet(heartSet);
//
//                //차트에 데이터 추가
//                chart_heart.setData(data);
//
//                chart_heart.invalidate();//차트 업데이트
//                chart_heart.setTouchEnabled(false); //차트 터치 disable
//
//
//                heartSet.setColor(Color.BLACK);
//                heartSet.setCircleColor(Color.BLACK);
//
//
//                //심박수 평균 구하기
//                todayHeart = todayHeart / snapshot.getChildrenCount();
//                Log.w("child", snapshot.getChildrenCount() + "di" + todayHeart);
//                avg_heart.setText(String.valueOf(todayHeart));
//
//
//            }
//
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.e("heart data read fail", error.toException() + " 힝");
//
//            }
//        });

        //데이터 생성
//        for (i = 0; i < 10; i++) {
//            mDatabaseRef.child("UserAccount").child(user.getUid()).child("SensorData").child("heart").addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                     //HashMap data = snapshot.getValue(HashMap.class);
//                     Log.w("key",snapshot.getKey()+"key");
//                    //float val = (float) (Math.random() * 100);
////                    h = data.values();//heart에 데이터를 담는다.
////                    heart.add(new Entry(i, h));
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });

//            float val = (float) (Math.random() * 100);
//            heart.add(new Entry(i, val));//values에 데이터를 담는다.
        // }
        //==================기존의 mung====================
//        Log.w("data create before", "생성");
//        mDatabaseRef.child("UserAccount").child(user.getUid()).child("SensorData").child("heart").child(today.format(form)).child("data").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Log.w("data create", "들어는 왔다 for문 문제임");
//
//                Log.w("child 값", snapshot.getChildren() + "이거 맞아?");
//                for (DataSnapshot sensorData : snapshot.getChildren()) {
//                    Log.w("data create", "생성");
//                    float data = (float) sensorData.child("sensor").getValue(Integer.class);
//                    i++;
//                    heartArr.add(new Entry(i, data));
//                    todayHeart = todayHeart + data;
//                    Log.w("data create 얌", heartArr + "생성");
//                }
//                LineDataSet heartSet; //데이터셋에 데이터 넣기
//                heartSet = new LineDataSet(heartArr, "Heart");//데이터가 담긴 리스트를 LineDataSet으로 변환.
//
//                //리스트에 데이터셋 추가
//                ArrayList<LineDataSet> heartdataSets = new ArrayList<>();
//                heartdataSets.add(heartSet);
//
//                //LineData에 list추가
//                LineData data = new LineData(); //차트에 담길 데이터
//                data.addDataSet(heartSet);
//
//                //차트에 데이터 추가
//                chart_heart.setData(data);
//
//                chart_heart.invalidate();//차트 업데이트
//                chart_heart.setTouchEnabled(false); //차트 터치 disable
//
//
//                heartSet.setColor(Color.BLACK);
//                heartSet.setCircleColor(Color.BLACK);
//
//
//                //심박수 평균 구하기
//                todayHeart = todayHeart / snapshot.getChildrenCount();
//                Log.w("child", snapshot.getChildrenCount() + "di" + todayHeart);
//                avg_heart.setText(String.valueOf(todayHeart));
//
//                //그날의 심박수 평균 저장 (.push빼서 덮어쓰기 가능하게!)
//               mDatabaseRef.child("UserAccount").child(user.getUid()).child("SensorData").child("heart").child(today.format(form)).child("averageHeart").setValue(todayHeart);
////
//
//
//
//            }
        //================기존의 mung===========================


        Log.w("data create before", "생성");
        System.out.println(today.format(form));
        mDatabaseRef.child("UserAccount").child(user.getUid()).child("SensorData").child("heart").child(today.format(form)).child("data").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.w("data create", "들어는 왔다 for문 문제임");

                Log.w("child 값", snapshot.getChildren() + "이거 맞아?");
                for (DataSnapshot sensorData : snapshot.getChildren()) {
                    Log.w("data create", "생성");
                    Log.w("hh", sensorData.child("sensor") + "hu");
                    Log.w("htht", sensorData.child("sensor").getValue() + "h");
                    if (sensorData.child("sensor").getValue() == null) break;
                    float data = Float.parseFloat(sensorData.child("sensor").getValue().toString());
                    //if (data == 0.0) break;
                    i++;
                    heartArr.add(new Entry(i, data));
                    todayHeart = todayHeart + data;
                    Log.w("data create 얌", heartArr + "생성");
                }
                Log.w("heart average", todayHeart + "d");
                LineDataSet heartSet; //데이터셋에 데이터 넣기
                heartSet = new LineDataSet(heartArr, "Heart");//데이터가 담긴 리스트를 LineDataSet으로 변환.

                //리스트에 데이터셋 추가
                ArrayList<LineDataSet> heartdataSets = new ArrayList<>();
                heartdataSets.add(heartSet);

                //LineData에 list추가
                LineData data = new LineData(); //차트에 담길 데이터
                data.addDataSet(heartSet);

                //차트에 데이터 추가
                chart_heart.setData(data);

                chart_heart.invalidate();//차트 업데이트
                chart_heart.setTouchEnabled(false); //차트 터치 disable


                heartSet.setColor(Color.BLACK);
                heartSet.setCircleColor(Color.BLACK);


                Log.w("심박수", todayHeart + "나누기 전");
                //심박수 평균 구하기
                if (todayHeart == 0.0) {
                    avg_heart.setText("NaN");
                } else {
                    todayHeart = todayHeart / snapshot.getChildrenCount();
                    Log.w("child", snapshot.getChildrenCount() + "di" + todayHeart);
                    avg_heart.setText(String.valueOf(todayHeart));
                }

                //그날의 심박수 평균 저장 (.push빼서 덮어쓰기 가능하게!)
                mDatabaseRef.child("UserAccount").child(user.getUid()).child("SensorData").child("heart").child(today.format(form)).child("averageHeart").setValue(todayHeart);
//

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("heart data read fail", error.toException() + " 힝");

            }
        });

        Log.w("heart arr후", heartArr + "?");
        Log.w("heart data read after", " 힝");
        LineDataSet heartSet; //데이터셋에 데이터 넣기
        heartSet = new LineDataSet(heartArr, "Heart");//데이터가 담긴 리스트를 LineDataSet으로 변환.

        //리스트에 데이터셋 추가
        ArrayList<LineDataSet> heartdataSets = new ArrayList<>();
        heartdataSets.add(heartSet);

        //LineData에 list추가
        LineData data = new LineData(); //차트에 담길 데이터
        data.addDataSet(heartSet);

        //차트에 데이터 추가
        chart_heart.setData(data);

        chart_heart.invalidate();//차트 업데이트
        chart_heart.setTouchEnabled(false); //차트 터치 disable


        heartSet.setColor(Color.BLACK);
        heartSet.setCircleColor(Color.BLACK);


        ///가속도 y가 4이하면 level 1로 표시
        //TODO 계속 돌아가는 이유 찾기
        chart_gyro = (LineChart) getActivity().findViewById(R.id.chart_gyro);

        mDatabaseRef.child("UserAccount").child(user.getUid()).child("SensorData").child("accelerometer").child(today.format(form)).child("data").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot sensorData : snapshot.getChildren()) {
//                    Log.w("data create", "생성");
//                    Log.w("자", sensorData.child("x") + "hu");
//                    Log.w("이", sensorData.child("y") + "hu");
//                    Log.w("로", sensorData.child("z").getValue() + "h");
                    if (sensorData.child("y").getValue() == null) break;
                    float data = Float.parseFloat(sensorData.child("y").getValue().toString());
                    if (data < 4) {
                        data = 1;
                        l1++;
                    } else {
                        data = 4;
                        l4++;
                    }
                    i++;
                    Log.w("level", data + "why");
                    accArr.add(new Entry(i, data));
                    if (l4 > l1) {
                        averageLevel = 4;
                    } else {
                        averageLevel = 1;
                    }

                    Log.w("data create 얌", accArr + "생성");
                }
                LineDataSet accSet;
                accSet = new LineDataSet(accArr, "Level");//데이터가 담긴 리스트를 LineDataSet으로 변환.

                ArrayList<LineDataSet> accDataSets = new ArrayList<>();
                accDataSets.add(accSet);

                LineData acc = new LineData(); //차트에 담길 데이터

                acc.addDataSet(accSet);

                chart_gyro.setData(acc);

                chart_gyro.invalidate();//차트 업데이트
                chart_gyro.setTouchEnabled(false); //차트 터치 disable

                accSet.setColor(Color.BLACK);
                accSet.setCircleColor(Color.BLACK);

                //평균 레벨 구하기(최다 빈도로)
                avg_level = (TextView) getActivity().findViewById(R.id.avg_level);
                //averageLevel = averageLevel / snapshot.getChildrenCount();
                avg_level.setText(String.valueOf(averageLevel));
                mDatabaseRef.child("UserAccount").child(user.getUid()).child("SensorData").child("accelerometer").child(today.format(form)).child("avgLevel").setValue(0);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("heart data read fail", error.toException() + " 힝");

            }
        });



//        //자이로 센서
//        lineChart = (LineChart) getActivity().findViewById(R.id.chart_gyro);
//
//        ArrayList<Entry> value = new ArrayList<>();//데이터를 담을 리스트
//
//        for (int i = 0; i < 10; i++) {
//            Log.w("자이로 센서 ", "뿅");
//            float val = (float) (Math.random() * 10);
//            value.add(new Entry(i, val));//values에 데이터를 담는다.
//        }
//        LineDataSet set2;
//        set2 = new LineDataSet(value, "DataSet 2");//데이터가 담긴 리스트를 LineDataSet으로 변환.
//
//        ArrayList<LineDataSet> dataSet = new ArrayList<>();
//        dataSet.add(set2);
//
//        LineData data2 = new LineData(); //차트에 담길 데이터
//
//        data2.addDataSet(set2);
//
//        lineChart.setData(data2);
//
//        lineChart.invalidate();//차트 업데이트
//        lineChart.setTouchEnabled(false); //차트 터치 disable
//
//
////        set1.setColor(Color.BLACK);
////        set1.setCircleColor(Color.BLACK);
//
//
         //걸음수 센서
        chart_stepCount = (LineChart) getActivity().findViewById(R.id.chart_stepCount);

        mDatabaseRef.child("UserAccount").child(user.getUid()).child("SensorData").child("stepCount").child(today.format(form)).child("data").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int cnt = 0;
                for (DataSnapshot sensorData : snapshot.getChildren()) {
                    if(cnt==15)break;
                    Log.w("data create", "생성");
                    Log.w("걸음수", sensorData + "hu");
                    Log.w("걸음수", sensorData.child("sensor") + "hu");
                    Log.w("걸음", sensorData.child("sensor").getValue().toString() + "h");
                    if(sensorData.child("sensor").getValue().toString()==null)break;
                    float data = Float.parseFloat(sensorData.child("sensor").getValue().toString());
                    if(data == 0.0) break;
                    i++;
                    cnt++;
                    todayStepCount = todayStepCount + (int)data/100;
                    stepCountArr.add(new Entry(i, todayStepCount));
                    Log.w("data create 얌", stepCountArr + "생성");
                }
                LineDataSet stepCountSet;
                stepCountSet = new LineDataSet(stepCountArr, "stepCount");//데이터가 담긴 리스트를 LineDataSet으로 변환.

                ArrayList<LineDataSet> stepCountDataSets = new ArrayList<>();
                stepCountDataSets.add(stepCountSet);

                LineData data3 = new LineData(); //차트에 담길 데이터

                data3.addDataSet(stepCountSet);

                chart_stepCount.setData(data3);

                chart_stepCount.invalidate();//차트 업데이트
                chart_stepCount.setTouchEnabled(false); //차트 터치 disable

                stepCountSet.setColor(Color.BLACK);
                stepCountSet.setCircleColor(Color.BLACK);

                //총 걸음 수 표시
                totalStepCount = (TextView)getActivity().findViewById(R.id.total_stepCount);
                totalStepCount.setText(String.valueOf(todayStepCount));
                mDatabaseRef.child("UserAccount").child(user.getUid()).child("SensorData").child("stepCount").child(today.format(form)).child("totalStepCount").setValue(todayStepCount);


            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("heart data read fail", error.toException() + " 힝");

            }
        });

        //        ArrayList<Entry> entry_chart1 = new ArrayList<>(); // 데이터를 담을 Arraylist
//        ArrayList<Entry> entry_chart2 = new ArrayList<>();
//
//
//        LineData chartData = new LineData(); // 차트에 담길 데이터
//
//        entry_chart1.add(new Entry(1, 1)); //entry_chart1에 좌표 데이터를 담는다.
//        entry_chart1.add(new Entry(2, 2));
//        entry_chart1.add(new Entry(3, 3));
//        entry_chart1.add(new Entry(4, 4));
//        entry_chart1.add(new Entry(5, 2));
//        entry_chart1.add(new Entry(6, 8));
//
//        entry_chart2.add(new Entry(1, 2)); //entry_chart2에 좌표 데이터를 담는다.
//        entry_chart2.add(new Entry(2, 3));
//        entry_chart2.add(new Entry(3, 1));
//        entry_chart2.add(new Entry(4, 4));
//        entry_chart2.add(new Entry(5, 5));
//        entry_chart2.add(new Entry(6, 7));
//
//
//        LineDataSet lineDataSet1 = new LineDataSet(entry_chart1, "LineGraph1"); // 데이터가 담긴 Arraylist 를 LineDataSet 으로 변환한다.
//        LineDataSet lineDataSet2 = new LineDataSet(entry_chart2, "LineGraph2");
//
//        lineDataSet1.setColor(Color.RED); // 해당 LineDataSet의 색 설정 :: 각 Line 과 관련된 세팅은 여기서 설정한다.
//        lineDataSet2.setColor(Color.BLACK);
//
//        chartData.addDataSet(lineDataSet1); // 해당 LineDataSet 을 적용될 차트에 들어갈 DataSet 에 넣는다.
//        chartData.addDataSet(lineDataSet2);
//
//        lineChart.setData(chartData); // 차트에 위의 DataSet을 넣는다.
//
//        lineChart.invalidate(); // 차트 업데이트
//        lineChart.setTouchEnabled(false); // 차트 터치 disable
//

        }

}
