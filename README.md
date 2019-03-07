# MapsRouteDrawer-Android

## Documentation

### Installation
---
Step 1. Add the JitPack repository to your build file 

```gradle
	allprojects {
		repositories {
			maven { url 'https://jitpack.io' }
		}
	}
```

Step 2. Add the dependency

```gradle
	dependencies {
	        implementation 'com.github.rommansabbir:MapsRouteDrawer-Android:Tag'
	}
```

---

### Version available

| Releases        
| ------------- |
| v1.0.2        |
| v1.0.1        |
| v1.0          |


# Usages

### For Java: 

```java
public class MainActivity extends AppCompatActivity implements
        OnMapReadyCallback, TaskLoadedCallback {
    ..................................
    ..................................
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        .......................................
        .......................................

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        ..........................................
        ..........................................

        /**
         * Now fetch the polyline for your current location to destination location
         * Pass the context of the parent activity
         * Pass the direction mode ex: driving, walking or something else which accepted by google
         * Pass the value for your polyline width
         * Pass value for direction color like if driving then BLACK, if walking then GREEN
         * Now pass the current marker & destination marker to fetch the polyline for this two position
         * @param context
         * @param directionMode
         * @param lineOptionWidth
         * @param drivingColor
         * @param walkingColor
         * @param apiKey
         * @param origin
         * @param dest
         */
        new FetchURL(
                MainActivity.this,
                "driving",
                10,
                Color.BLACK,
                Color.GREEN).
                execute(new GetURL(this).
                        getUrl(
                                R.string.google_maps_key,
                                mMarker.getPosition(),
                                dMarker.getPosition(),
                                "driving"),
                        "driving");
                        
        ..........................................
        ..........................................

    }

    @Override
    public void onTaskDone(PolylineOptions polylineOptions) {
        //TODO Implement your logic here
    }

    @Override
    public void onManeuverListReceived(List<HashMap<String, String>> maneuverList) {
        //TODO Implement your logic here
    }
}
```

### Contact me
[Portfolio](https://www.rommansabbir.com/) | [LinkedIn](https://www.linkedin.com/in/rommansabbir/) | [Twitter](https://www.twitter.com/itzrommansabbir/) | [Facebook](https://www.facebook.com/itzrommansabbir/)
