package com.awant.lion.maps;

import android.app.Activity;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.awant.lion.R;
import com.awant.lion.TabSecondActivityGroupHandeler;
import com.awant.lion.tools.MLog;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapRouterActivity extends Activity {
    GoogleMap mGoogleMap;
    private final int MAP_LINE_WIDTH = 8;
    private final int MAP_LINE_COLOR = Color.BLUE;
    MapFragment mapFragment;

    String storeName;
    String storeAddr;
    double fromLat, fromLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        View viewToLoad = LayoutInflater.from(this.getParent()).inflate(
//                R.layout.activity_maps_router, null);
//        setContentView(viewToLoad);
        setContentView( R.layout.activity_maps_router);
        parserBundle();
        init();

    }

    private void parserBundle() {
        Bundle bundle = this.getIntent().getExtras();
        storeName = bundle.getString("storeName");
        storeAddr = bundle.getString("storeAddr");
        fromLat = bundle.getDouble("fromLat");
        fromLng = bundle.getDouble("fromLng");
        MLog.i(this, "dex store : " + storeName + "/" + storeAddr);
    }

    ArrayList<Marker> points;
    LatLng start, end;

    private void init() {
        mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        points = new ArrayList<Marker>();

        Geocoder geo = new Geocoder(getApplication(), Locale.TRADITIONAL_CHINESE);
        List<Address> addrList = null;
        try {
            addrList = geo.getFromLocationName(storeAddr, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (addrList == null || addrList.isEmpty()) {
            Toast.makeText(getApplication(), getResources().getString(R.string.nonLatLng), Toast.LENGTH_SHORT).show();
            MLog.e(this, "dex address list is empty.");
            return;
        }
        MLog.i(this, "dex Lat:" + addrList.get(0).getLatitude() + " // Lng:" + addrList.get(0).getLongitude());
        end = new LatLng(addrList.get(0).getLatitude(), addrList.get(0).getLongitude());
    }

    private void showMap() {
        mGoogleMap = mapFragment.getMap();
        mGoogleMap.clear();
        points.clear();
        start = new LatLng(fromLat, fromLng);
        points.add(marker(start, "From", null));

        Marker endMarker = marker(end, storeName, storeAddr);
        endMarker.showInfoWindow();//always show information.
        points.add(endMarker);
        moveCamera(points);


        String url = getDirectionsUrl(start, end);
        googleMapRouteTask task = new googleMapRouteTask(url);
        task.execute();


    }

    @Override
    protected void onResume() {
        super.onResume();
        showMap();
    }


    private void moveCamera(ArrayList<Marker> points) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (Marker ll : points) builder.include(ll.getPosition());
        LatLngBounds bounds = builder.build();
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 420, 760, 10));
    }

    private Marker marker(LatLng place, String title, String snippet) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(place);
        if (!TextUtils.isEmpty(title)) markerOptions.title(title);
        if (!TextUtils.isEmpty(snippet)) markerOptions.snippet(snippet);
        return mGoogleMap.addMarker(markerOptions);
    }

    private String getDirectionsUrl(LatLng origin, LatLng dest) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + ","
                + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";

        // Travelling Mode
        String mode = "mode=driving";

        // String waypointLatLng = "waypoints="+"40.036675"+","+"116.32885";


        String parameters = null;
        // Building the parameters to the web service

        parameters = str_origin + "&" + str_dest + "&" + sensor + "&" + mode;
        // parameters = str_origin + "&" + str_dest + "&" + sensor + "&"
        // + mode+"&"+waypoints;

        // Output format
        // String output = "json";
        String output = "xml";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/"
                + output + "?" + parameters;
//        System.out.println("getDerectionsURL--->: " + url);
        return url;
    }


    @Override
    public void onBackPressed() {
        TabSecondActivityGroupHandeler.group.back();
    }


    private class googleMapRouteTask extends
            AsyncTask<String, Void, List<LatLng>> {

        HttpClient client;
        String url;

        List<LatLng> routes = null;

        public googleMapRouteTask(String url) {
            this.url = url;
        }

        @Override
        protected List<LatLng> doInBackground(String... params) {

            HttpGet get = new HttpGet(url);

            try {
                HttpResponse response = client.execute(get);
                int statusecode = response.getStatusLine().getStatusCode();
//                System.out.println("response:" + response + "      statuscode:"
//                        + statusecode);
                if (statusecode == 200) {

                    String responseString = EntityUtils.toString(response
                            .getEntity());

                    int status = responseString.indexOf("<status>OK</status>");
//                    System.out.println("status:" + status);
                    if (-1 != status) {
                        int pos = responseString.indexOf("<overview_polyline>");
                        pos = responseString.indexOf("<points>", pos + 1);
                        int pos2 = responseString.indexOf("</points>", pos);
                        responseString = responseString
                                .substring(pos + 8, pos2);
                        routes = decodePoly(responseString);
                    } else {
                        // ??�N?�A
                        return null;
                    }

                } else {
                    // ?�D��?
                    return null;
                }

            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
//            System.out.println("doInBackground:"+routes);
            return routes;
        }

        @Override
        protected void onPreExecute() {
            client = new DefaultHttpClient();
            client.getParams().setParameter(
                    CoreConnectionPNames.CONNECTION_TIMEOUT, 15000);
            client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
                    15000);
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(List<LatLng> routes) {
            super.onPostExecute(routes);
            if (routes == null) {
//                Toast.makeText(getApplicationContext(),, Toast.LENGTH_LONG).show();
            } else {

                PolylineOptions lineOptions = new PolylineOptions();
                lineOptions.addAll(routes);
                lineOptions.width(MAP_LINE_WIDTH);
                lineOptions.color(MAP_LINE_COLOR);
                mGoogleMap.addPolyline(lineOptions);
//                mGoogleMap.animateCamera(CameraUpdateFactory.newLatLng(routes.get(0)));
            }

        }

    }

    private List<LatLng> decodePoly(String encoded) {
        List<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;
        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;
            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;
            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }
        return poly;
    }
}
