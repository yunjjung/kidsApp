package com.example.kidsapp;

//import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.Map;

public class Frag2 extends Fragment {

    private View view;
    private LineChart lineChart;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag2, container,false);

        return view;
    }

    @Nullable
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lineChart = (LineChart)getActivity().findViewById(R.id.linechart);

        ArrayList<Entry> values = new ArrayList<>();//데이터를 담을 리스트

        for(int i = 0; i < 10; i++){
            float val = (float)(Math.random() * 10);
            values.add(new Entry(i, val));//values에 데이터를 담는다.
        }
        LineDataSet set1;
        set1 = new LineDataSet(values, "DataSet 1");//데이터가 담긴 리스트를 LineDataSet으로 변환.

        ArrayList<LineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        LineData data = new LineData(); //차트에 담길 데이터

        data.addDataSet(set1);

        lineChart.setData(data);

        lineChart.invalidate();//차트 업데이트
        lineChart.setTouchEnabled(false); //차트 터치 disable


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


}
