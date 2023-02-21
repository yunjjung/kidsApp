package com.example.kidsapp;

//import android.app.Fragment;
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

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;

public class Frag2 extends Fragment {
    private FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser user = mFirebaseAuth.getCurrentUser();
    private TextView gyro;
    private TextView heart;
    private TextView stepCount;
    private View view;
    private LineChart lineChart;
    private LineChart chart_heart;
    private LineChart chart_stepCount;
    private DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference("kidsApp");

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
        heart = (TextView)getActivity().findViewById(R.id.read_heart);
        gyro = (TextView) getActivity().findViewById(R.id.read_gyro);

        SensorData sensor = new SensorData();
        sensor.setGyro("x: -0.01, y: -0.01, z: 0.05");
        sensor.setHeart(128);
        sensor.setStepCount(73);


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String time = LocalDateTime.now().format(formatter);
        Log.w("시간", LocalDateTime.now() + "시");


        mDatabaseRef.child("UserAccount").child(user.getUid()).child("SensorData").child("gyro").child(time).setValue("x: -0.01, y: -0.01, z: 0.05");
        mDatabaseRef.child("UserAccount").child(user.getUid()).child("SensorData").child("gyro").child(time).setValue("x: -0.05, y: -0.04, z: 0.03");

        mDatabaseRef.child("UserAccount").child(user.getUid()).child("SensorData").child("heart").child(time).setValue("120");
        mDatabaseRef.child("UserAccount").child(user.getUid()).child("SensorData").child("heart").child(time).setValue("123");
        // mDatabaseRef.child("UserAccount").child(user.getUid()).child("SensorData").child("gyro").child(time).setValue(sensor);
        //데이터 읽기
        mDatabaseRef.child("SensorDatas").child("test22").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                SensorData str = snapshot.getValue(SensorData.class);
                gyro.setText(str.getGyro());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //심박수 센서
        chart_heart = (LineChart) getActivity().findViewById(R.id.chart_heart);

        ArrayList<Entry> heart = new ArrayList<>();//데이터를 담을 리스트

        for (int i = 0; i < 10; i++) {
//            mDatabaseRef.child("UserAccount").child(user.getUid()).child("SensorData").child("heart").addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    float data = snapshot.getValue(float.class);
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });
            float val = (float) (Math.random() * 100);
            heart.add(new Entry(i, val));//values에 데이터를 담는다.
        }
        LineDataSet set1;
        set1 = new LineDataSet(heart, "DataSet 1");//데이터가 담긴 리스트를 LineDataSet으로 변환.

        ArrayList<LineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        LineData data = new LineData(); //차트에 담길 데이터

        data.addDataSet(set1);

        chart_heart.setData(data);

        chart_heart.invalidate();//차트 업데이트
        chart_heart.setTouchEnabled(false); //차트 터치 disable


        set1.setColor(Color.BLACK);
        set1.setCircleColor(Color.BLACK);


        //자이로 센서
        lineChart = (LineChart) getActivity().findViewById(R.id.chart_gyro);

        ArrayList<Entry> value = new ArrayList<>();//데이터를 담을 리스트

        for (int i = 0; i < 10; i++) {
            float val = (float) (Math.random() * 10);
            value.add(new Entry(i, val));//values에 데이터를 담는다.
        }
        LineDataSet set2;
        set2 = new LineDataSet(value, "DataSet 2");//데이터가 담긴 리스트를 LineDataSet으로 변환.

        ArrayList<LineDataSet> dataSet = new ArrayList<>();
        dataSet.add(set2);

        LineData data2 = new LineData(); //차트에 담길 데이터

        data2.addDataSet(set2);

        lineChart.setData(data2);

        lineChart.invalidate();//차트 업데이트
        lineChart.setTouchEnabled(false); //차트 터치 disable


        set1.setColor(Color.BLACK);
        set1.setCircleColor(Color.BLACK);

        //걸음수 센서
        chart_stepCount = (LineChart) getActivity().findViewById(R.id.chart_stepCount);

        ArrayList<Entry> value3 = new ArrayList<>();//데이터를 담을 리스트

        //추후에 int로
        float s = 0;
        for (int i = 0; i < 10; i++) {
            s = s + (float) (Math.random());
            value3.add(new Entry(i, s));//values에 데이터를 담는다.
        }
        LineDataSet set3;
        set3 = new LineDataSet(value3, "DataSet 3");//데이터가 담긴 리스트를 LineDataSet으로 변환.

        ArrayList<LineDataSet> dataSet3 = new ArrayList<>();
        dataSet3.add(set3);

        LineData data3 = new LineData(); //차트에 담길 데이터

        data3.addDataSet(set3);

        chart_stepCount.setData(data3);

        chart_stepCount.invalidate();//차트 업데이트
        chart_stepCount.setTouchEnabled(false); //차트 터치 disable


        set1.setColor(Color.BLACK);
        set1.setCircleColor(Color.BLACK);






    }

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
    public float readHeart() {
        final float[] data = {-1};
        mDatabaseRef.child("UserAccount").child(user.getUid()).child("heart").addValueEventListener(new ValueEventListener() {
            @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        data[0] = snapshot.getValue(float.class);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        return data[0];
    }

}
