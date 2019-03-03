package com.rommansabbir.mapsroutedrawer;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PointsParser extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {
    private static final String TAG = "PointsParser";
    TaskLoadedCallback taskCallback;
    String directionMode = "driving";
    Float lineOptionWidth;
    Integer drivingColor;
    Integer walkingColor;
    HashMap<String, String> point;
    List<HashMap<String, String>> path;

    /**
     *
     * @param mContext
     * @param directionMode
     * @param lineOptionWidth
     * @param drivingColor
     * @param walkingColor
     */
    public PointsParser(Context mContext, String directionMode, Float lineOptionWidth, Integer drivingColor, Integer walkingColor) {
        this.taskCallback = (TaskLoadedCallback) mContext;
        this.directionMode = directionMode;
        this.lineOptionWidth = lineOptionWidth;
        this.drivingColor = drivingColor;
        this.walkingColor = walkingColor;
    }

    /**
     * Parsing the data in non-ui thread
     * @param jsonData
     * @return
     */
    @Override
    protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

        JSONObject jObject;
        List<List<HashMap<String, String>>> routes = null;

        try {
            jObject = new JSONObject(jsonData[0]);
            DataParser parser = new DataParser();
            /**
             * Starts parsing data
             */
            routes = parser.parse(jObject);

        } catch (Exception e) {
            Log.d(TAG, "doInBackground: "+e.getMessage());
            e.printStackTrace();
        }
        return routes;
    }

    /**
     * Executes in UI thread, after the parsing process
     * @param result
     */
    @Override
    protected void onPostExecute(List<List<HashMap<String, String>>> result) {
        ArrayList<LatLng> points;
        List<HashMap<String, String>> maneuverList = new ArrayList<>();
        PolylineOptions lineOptions = null;
        /**
         * Traversing through all the routes
         */
        for (int i = 0; i < result.size(); i++) {
            points = new ArrayList<>();
            lineOptions = new PolylineOptions();
            /**
             * Fetching i-th route
             */
            path = result.get(i);
            /**
             * Fetching all the points in i-th route
             */
            for (int j = 0; j < path.size(); j++) {
                point = path.get(j);
                double lat = Double.parseDouble(point.get("lat"));
                double lng = Double.parseDouble(point.get("lng"));
                LatLng position = new LatLng(lat, lng);
                points.add(position);

                /**
                 * Fetching all maneuver from result
                 */
                HashMap<String, String> tempManeuver = new HashMap<>();
                tempManeuver.put("lat", String.valueOf(lat));
                tempManeuver.put("lng", String.valueOf(lng));
                tempManeuver.put("maneuver", point.get("maneuver"));
                maneuverList.add(tempManeuver);

            }
            /**
             * Adding all the points in the route to LineOptions
             */
            if(lineOptions != null){
                lineOptions.addAll(points);
                if (directionMode.equalsIgnoreCase("walking")) {
                    lineOptions.width(lineOptionWidth);
                    lineOptions.color(walkingColor);
                } else {
                    lineOptions.width(lineOptionWidth);
                    lineOptions.color(drivingColor);
                }
                Log.d(TAG, "onPostExecute: ");

                if(points.size() == maneuverList.size()){
                    /**
                     * Notify the interface on maneuver list received
                     */
                    taskCallback.onManeuverListReceived(maneuverList);
                }
            }
        }

        /**
         * Drawing polyline in the Google Map for the i-th route
         */
        if (lineOptions != null) {
            /**
             * mMap.addPolyline(lineOptions);
             */
            taskCallback.onTaskDone(lineOptions);

        } else {
            Log.d(TAG, "onPostExecute: "+"PolylineOptions null");
        }
    }
}
