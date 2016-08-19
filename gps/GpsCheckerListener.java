package tools.gps;

/**
 * Created by Dexter on 2016/8/18.
 */
public interface GpsCheckerListener {
    void onGpsOff();
    void onGpsOn();
    void sendLocation(Double longitude, Double latitude);
    void noLocation();//無法定位座標
}
