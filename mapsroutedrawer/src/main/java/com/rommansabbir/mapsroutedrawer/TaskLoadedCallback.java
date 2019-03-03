package com.rommansabbir.mapsroutedrawer;


import com.google.android.gms.maps.model.PolylineOptions;

import java.util.HashMap;
import java.util.List;

public interface TaskLoadedCallback {
    void onTaskDone(PolylineOptions polylineOptions);
    void onManeuverListReceived(List<HashMap<String, String>> maneuverList);
}
