package com.android.gpstest.oldchart;

import android.graphics.Color;
import android.location.GpsSatellite;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.android.gpstest.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ValueFormatter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 柱状图类，先放在这里
 * Created by jin on 2016/8/16 0016.
 */

public class BarChart3s {
    public BarChart3s(BarChart chart) {
        // 数据描述
        chart.setDescription("");
        //背景
        chart.setBackgroundColor(0x00151515);
        //定义数据描述得位置
        // chart.setDescriptionPosition(2,100);
        // 设置描述文字的颜色
        // chart.setDescriptionColor(0xffededed);
        // 动画
//        chart.animateY(1000);
        //设置阴影
        //chart.setDrawBarShadow(true);
        //设置无边框
        chart.setDrawBorders(true);
        // 设置是否可以触摸
        chart.setTouchEnabled(false);
        // 是否可以拖拽
        chart.setDragEnabled(true);
        // 是否可以缩放
        chart.setScaleEnabled(false);
        //设置网格背景
        chart.setGridBackgroundColor(0x00151515);
        //设置边线宽度
        chart.setBorderWidth(0);
        //设置边线颜色
        chart.setBorderColor(0x00151515);
        // 集双指缩放
        chart.setPinchZoom(false);
        // 隐藏右边的坐标轴
        chart.getAxisRight().setEnabled(false);
        // 隐藏左边的左边轴
        chart.getAxisLeft().setEnabled(true);
        chart.getLegend().setEnabled(false);



        //设置X轴位置
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        // 前面xAxis.setEnabled(false);则下面绘制的Grid不会有"竖的线"（与X轴有关）
        // 上面第一行代码设置了false,所以下面第一行即使设置为true也不会绘制AxisLine
        //设置轴线得颜色
        xAxis.setAxisLineColor(0x00151515);
        xAxis.setTextColor(0xffffffff);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);
        xAxis.setSpaceBetweenLabels(-1);

        //设置Y轴
        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setTextColor(0xffffffff);
        //Y轴颜色
        leftAxis.setAxisLineColor(0x00151515);
        //Y轴参照线颜色
        leftAxis.setGridColor(0x00151515);
        //参照线长度
        leftAxis.setAxisLineWidth(5f);
        // 顶部居最大值站距离占比
        leftAxis.setSpaceTop(20f);
        chart.invalidate();
    }


    public ArrayList<BarDataSet> getGpsDataSet(Iterator<GpsSatellite> satellites, ArrayList<String> num) {
        ArrayList<BarDataSet> dataSets = null;
        ArrayList<BarEntry> valueSet1 = new ArrayList<BarEntry>();
        int index = 0;
        int[] colors = new int[num.size()];

        while (satellites.hasNext()) {
            GpsSatellite satellite = satellites.next();
            if (num.get(index).equals(satellite.getPrn() + "")) {
                BarEntry barEntry = new BarEntry(satellite.getSnr(), index); // Jan
                valueSet1.add(barEntry);
                if (satellite.getSnr() >= 40.0f) {
                    colors[index] =  Color.parseColor("#d50000");
                } else {
                    colors[index] = Color.parseColor("#009d00");
                }
            }
            index++;
        }
        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "");

        barDataSet1.setColors(colors);

//        barDataSet1.setBarShadowColor(Color.parseColor("#DEAD26"));

        dataSets = new ArrayList<BarDataSet>();
        barDataSet1.setValueTextColor(0xffffffff);
        barDataSet1.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.valueOf((int) value);
            }
        });
        dataSets.add(barDataSet1);
        return dataSets;
    }


    public ArrayList<BarDataSet> getGpsDataSetTest(int count) {
        ArrayList<BarDataSet> dataSets = null;
        ArrayList<BarEntry> valueSet1 = new ArrayList<BarEntry>();

//        int index = 0;
//        while (satellites.hasNext()) {
//            GpsSatellite satellite = satellites.next();
//            if (satellite.getSnr() != 0.0f) {
//                BarEntry barEntry = new BarEntry(satellite.getSnr(), index); // Jan
//                valueSet1.add(barEntry);
//                index++;
//            }
//        }

        for (int i = 0; i < 12; i++) {
            BarEntry barEntry = new BarEntry((1 + i) * 10 + count * i, i); // Jan
            valueSet1.add(barEntry);
        }


        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "");
        barDataSet1.setColor(Color.parseColor("#009d00"));
//        barDataSet1.setBarShadowColor(Color.parseColor("#DEAD26"));

        dataSets = new ArrayList<BarDataSet>();
        barDataSet1.setValueTextColor(0xffffffff);
        barDataSet1.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.valueOf((int) value);
            }
        });
        dataSets.add(barDataSet1);
        return dataSets;
    }


    //写死数据直接添加
    public ArrayList<BarDataSet> getDataSet() {
        ArrayList<BarDataSet> dataSets = null;
        ArrayList<BarEntry> valueSet1 = new ArrayList<BarEntry>();
        BarEntry v1e1 = new BarEntry(0.000f, 0); // Jan
        valueSet1.add(v1e1);
        BarEntry v1e2 = new BarEntry(0.000f, 1); // Feb
        valueSet1.add(v1e2);
        BarEntry v1e3 = new BarEntry(0.000f, 2); // Mar
        valueSet1.add(v1e3);
        BarEntry v1e4 = new BarEntry(0.000f, 3); // Apr
        valueSet1.add(v1e4);
        BarEntry v1e5 = new BarEntry(0.000f, 4); // May
        valueSet1.add(v1e5);
        BarEntry v1e6 = new BarEntry(0.000f, 5); // Jun
        valueSet1.add(v1e6);
        BarEntry v1e7 = new BarEntry(0.000f, 6); // Jan
        valueSet1.add(v1e7);
        BarEntry v1e8 = new BarEntry(0.000f, 7); // Jan
        valueSet1.add(v1e8);
        BarEntry v1e9 = new BarEntry(0.000f, 8); // Jan
        valueSet1.add(v1e9);
        BarEntry v1e10 = new BarEntry(0.000f, 9); // Jan
        valueSet1.add(v1e10);
        BarEntry v1e11 = new BarEntry(0.000f, 10); // Jan
        valueSet1.add(v1e11);
        BarEntry v1e12 = new BarEntry(0.000f, 11); // Jan
        valueSet1.add(v1e12);


        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "");
        barDataSet1.setColor(Color.parseColor("#009d00"));  //#d50000
//        barDataSet1.setBarShadowColor(Color.parseColor("#DEAD26"));


        dataSets = new ArrayList<BarDataSet>();
        barDataSet1.setValueTextColor(0xffffffff);
        barDataSet1.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.valueOf((int) value);
            }
        });
        dataSets.add(barDataSet1);
        return dataSets;
    }

    //写死X轴数据
    public ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<String>();
        xAxis.add("12");
        xAxis.add("34");
        xAxis.add("30");
        xAxis.add("22");
        xAxis.add("33");
        xAxis.add("56");
        xAxis.add("87");
        xAxis.add("23");
        xAxis.add("79");
        xAxis.add("80");
        xAxis.add("67");
        xAxis.add("25");
        return xAxis;
    }

    //动态添加封装方法
    public ArrayList<BarDataSet> getDataSet(List<BarEntry> valueSet1, List<BarEntry> valueSet2,
                                            String title1, String title2, String color1, String color2) {
        ArrayList<BarDataSet> dataSets = null;
        dataSets = new ArrayList<>();
        BarDataSet barDataSet1 = new BarDataSet(valueSet1, title1);
        barDataSet1.setColor(Color.parseColor(color1));
        barDataSet1.setBarShadowColor(Color.parseColor("#01000000"));
        dataSets.add(barDataSet1);

        if (valueSet2 != null) {
            BarDataSet barDataSet2 = new BarDataSet(valueSet2, title2);
            barDataSet2.setColor(Color.parseColor(color2));
            barDataSet2.setBarShadowColor(Color.parseColor("#01000000"));
            dataSets.add(barDataSet2);
        }
        return dataSets;
    }
}
