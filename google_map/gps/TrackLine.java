package objects.gps_map.map;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

/**
 * 在 google map 上畫線
 * Created by Dexter on 2016/8/20.
 */
public class TrackLine {


    public static void trackLine(GoogleMap googleMap, List<LatLng> latLngList, int lineColor, int lineWidth) {
        if (latLngList.size() <= 1) return;
        PolylineOptions polylineOpt = new PolylineOptions();
        for (LatLng latlng : latLngList) {
            polylineOpt.add(latlng);
        }
        polylineOpt.color(lineColor);
        Polyline line = googleMap.addPolyline(polylineOpt);
        line.setWidth(lineWidth);
    }

}
