package route.training;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.view.View;
import android.widget.GridView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import basic.TagFragment;
import dexter.studio.upro.R;
import objects.BottomMessageBar;
import objects.Tag;
import objects.gps_map.gps.Gps;
import objects.gps_map.gps.GpsCheckerListener;
import objects.gps_map.map.TrackLine;
import objects.gps_map.marker.LatLngInterpolator;
import objects.gps_map.marker.MarkerAnimation;
import tools.MLog;
import tools.Settings;
import tools.Tools;

/**
 * 訓練主頁
 * Created by Dexter on 2016/8/14.
 */
public class SampleFragment extends TagFragment implements OnMapReadyCallback {
    GoogleMap googleMap;



    @Override
    public void onResume() {
        super.onResume();
        frameTag.setSingleTagTitle("2016/6/1 12:00 訓練");
        frameTag.setTagStyle(Tag.TagStyle.SINGLE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                        .findFragmentById(R.id.map);
                mapFragment.getMapAsync(TrainingMainFragment.this);

            }
        }, 2000);


    }

    Gps gps;

    private void checkGps() {
        gps = new Gps(activity);
        gps.ready(new GpsCheckerListener() {
            @Override
            public void onGpsOff() {
                bottomMessageBar.showMessage(true, getString(R.string.pls_turn_on_gps));
                activity.showToast(getString(R.string.pls_turn_on_gps));
            }

            @Override
            public void onGpsOn() {
                activity.showToast(getString(R.string.gps_turn_on));
                bottomMessageBar.showMessage(false, null);
            }

            @Override
            public void sendLocation(Double longitude, Double latitude) {
                MLog.i(this, "dexter longitude=" + longitude + "/latitude=" + latitude);
                bottomMessageBar.showMessage(true, longitude + ", " + latitude);
                LatLng latLng = new LatLng(latitude, longitude);
                setLocation(latLng);
//                if (demoTrainingRoute == null) {
//                    demoTrainingRoute = new DemoTrainingRoute() {
//                        @Override
//                        protected void toLocation(LatLng latLng) {
//                            setLocation(latLng);
//                        }
//                    };
//                    demoTrainingRoute.start();
//                }
            }

            @Override
            public void noLocation() {

            }
        });
        gps.getLocation();
    }

    @Override
    public void onPause() {
        super.onPause();
        gps.onPause();
        if (demoTrainingRoute != null) demoTrainingRoute.close();
        if (markerAnimation != null) markerAnimation.stop = true;
    }

    DemoTrainingRoute demoTrainingRoute;

    @Override
    protected void initLayout(View view) {
        bottomMessageBar = (BottomMessageBar) view.findViewById(R.id.bottom_bar);

        gridView = (GridView) view.findViewById(R.id.grid_view);
        List<TrainingMainAdapterBundle> list = new ArrayList<TrainingMainAdapterBundle>();

        list.add(new TrainingMainAdapterBundle("完成距離", "0 km"));
        list.add(new TrainingMainAdapterBundle("均速", "0' 00\""));
        list.add(new TrainingMainAdapterBundle("心律", "0 bmp"));
        list.add(new TrainingMainAdapterBundle("消耗卡路里", "0 cal"));
        list.add(new TrainingMainAdapterBundle("經過時間", "17:00.00"));
        gridView.setAdapter(new TrainingMainAdapter(activity, list));
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        checkGps();
        // Add a marker in Sydney and move the camera
//        LatLng latLng = new LatLng(25.0477498, 121.5170497);

    }


    private void setLocation(LatLng latLng) {
        bottomMessageBar.showMessage(true, latLng.longitude + ", " + latLng.latitude);
        if (googleMap == null) {
            MLog.d(this, "dexter google map is null");
            return;
        }
        try {
            CameraPosition cp = googleMap.getCameraPosition();
            if (cp != null) zoom = googleMap.getCameraPosition().zoom;
        } catch (Exception e) {
        }
        MLog.i(this, "dexter get zoom=" + zoom);
//        LatLng latLng = new LatLng(25.0477498, 121.5170497);
        if (marker == null) {
            //初始化 marker
            MarkerOptions marker = new MarkerOptions().position(latLng);
            Bitmap bitmap = BitmapFactory.decodeResource(activity.getResources(), R.drawable.dot_map_orange);
            int pixels = new Tools().getPixelsFromDp(activity, 12);
            bitmap = bitmap.createScaledBitmap(bitmap, pixels, pixels, false);
            marker.icon(BitmapDescriptorFactory.fromBitmap(bitmap));
            this.marker = googleMap.addMarker(marker);
            this.marker.setAnchor(0.5f, 0.5f);
        } else {

            //移動 marker
            LatLngInterpolator.Spherical spherical = new LatLngInterpolator.Spherical();
            spherical.interpolate(0.5f, marker.getPosition(), latLng);

            if (markerAnimation != null) {
                markerAnimation.stop = true;
            }
            markerAnimation = new MarkerAnimation();
            markerAnimation.animateMarkerToGB(marker, latLng, spherical);

            //晝出路徑
            if (routeLineList == null) routeLineList = new ArrayList<>();
            if (routeLineList.size() > 2) routeLineList.remove(0);
            routeLineList.add(latLng);
            TrackLine.trackLine(googleMap, routeLineList, getResources().getColor(R.color.orange), 8);
        }
        if (!isSettingZoom) {
            isSettingZoom = true;
            zoom = Settings.GOOGLE_MAP_ZOOM_DEFAULT;
        }
        //移動 camera
        final CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(zoom).bearing(0).build(); //replace with your current lat long and try
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    MarkerAnimation markerAnimation;
    List<LatLng> routeLineList;

    float zoom;
    boolean isSettingZoom;
    Marker marker;
}
