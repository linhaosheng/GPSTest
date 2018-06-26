package com.android.gpstest;


import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.gpstest.oldchart.BarChart3s;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Administrator on 2018/6/22/022.
 */

public class MyGpsSkyFragment extends Fragment {

    private BarChart3s mBarChart3s;
    private ArrayList<String> nums;
    private BarChart barChart;
    private ArrayList<BarDataSet> dataSets = null;
    private int count;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gps_sky, container, false);
        barChart = (BarChart) view.findViewById(R.id.barChart);
        nums = new ArrayList<>();
        nums.add("02");
        nums.add("05");
        nums.add("06");
        nums.add("09");
        nums.add("12");
        nums.add("13");
        nums.add("17");
        nums.add("19");
        nums.add("25");
        nums.add("66");
        nums.add("67");
        nums.add("68");

        mBarChart3s = new BarChart3s(barChart);
        dataSets = new ArrayList<>();

        BarData data = new BarData(nums, mBarChart3s.getDataSet());

        // 设置数据
        barChart.setData(data);
        barChart.notifyDataSetChanged();

        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void updateChatView(GpsStatus status) {
        count++;
        nums.clear();
        Iterator<GpsSatellite> iterator = status.getSatellites().iterator();
        while (iterator.hasNext()) {
            GpsSatellite gpsSatellite = iterator.next();
            nums.add(gpsSatellite.getPrn() + "");
        }
        if (nums.size() == 0) {
            return;
        }
        BarData data = new BarData(nums, mBarChart3s.getGpsDataSet(status.getSatellites().iterator(), nums));
        // 设置数据
        barChart.setData(data);
        barChart.notifyDataSetChanged();
        barChart.invalidate();
    }
}
