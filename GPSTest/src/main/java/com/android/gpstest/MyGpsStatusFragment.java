package com.android.gpstest;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.PointF;
import android.location.GpsStatus;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.gpstest.view.GpsSkyView;


/**
 * Created by Administrator on 2018/6/22/022.
 */

public class MyGpsStatusFragment extends Fragment implements LocationInterface {


    private GpsSkyView gpsSkyView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gps_status, container, false);
        gpsSkyView = view.findViewById(R.id.gps_map_fragment);
        return view;
    }


    public void updateGpsSkyView(GpsStatus gpsStatus) {
        if (gpsSkyView!=null){
            gpsSkyView.setSats(gpsStatus);
        }
        //gpsSkyView.gpsStart();
    }


    public void gpsStart() {
        gpsSkyView.gpsStart();
    }

    public void gpsStop() {
        gpsSkyView.gpsStop();
    }

    @Override
    public void updateLocation(Location location) {

    }
}
