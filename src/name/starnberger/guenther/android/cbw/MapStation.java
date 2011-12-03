package name.starnberger.guenther.android.cbw;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ZoomButtonsController;
import android.widget.ZoomControls;
import com.google.android.maps.*;
import android.view.MotionEvent;
import android.view.GestureDetector.OnGestureListener;
import android.view.GestureDetector.OnDoubleTapListener;
import com.google.android.maps.MapView;

public class MapStation extends MapActivity implements OnGestureListener, OnDoubleTapListener
{
    private MapView mapView;
    private MapController mapController;
    GeoPoint viennaLocation = new GeoPoint(48209206, 16372778);
    private MyLocationOverlay myLocationOverlay;
    private LocationManager locationManager;
    private Location currentLocation;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);

        mapView = (MapView) findViewById(R.id.mapView);
        mapController = mapView.getController();

        mapView.setBuiltInZoomControls(true);
        mapView.setSatellite(false);

        // Setting position of zoom control
        ZoomButtonsController zbc = mapView.getZoomButtonsController();
        ViewGroup container = zbc.getContainer();
        for (int i = 0; i < container.getChildCount(); i++) {
            View child = container.getChildAt(i);
            if (child instanceof ZoomControls) {
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) child.getLayoutParams();
        lp.gravity = Gravity.RIGHT | Gravity.BOTTOM;
        child.requestLayout();
        break;
        }
}

        //GeoPoint startPoint = new GeoPoint((int)(40.7575 * 1E6), (int)(-73.9785 * 1E6));
        //mapController.setCenter(startPoint);

        //mapController.setZoom(8);
        myLocationOverlay = new MyLocationOverlay(this, mapView);
        myLocationOverlay.enableCompass();
        myLocationOverlay.enableMyLocation();
        myLocationOverlay.runOnFirstFix(new Runnable() {
            public void run() {
                mapView.getController().animateTo(myLocationOverlay.getMyLocation());
                //mapView.getController().animateTo(viennaLocation);
                mapView.getController().setZoom(17);
            }
        });
        mapView.getOverlays().add(myLocationOverlay);
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                if (LocationHelperOld.isBetterLocation(location, currentLocation)) {
                    currentLocation = location;
                }
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };

//        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

    }

    @Override
    protected boolean isRouteDisplayed()
    {
        return false;
    }

/**
   * Methods required by OnDoubleTapListener
   **/
  @Override
  public boolean onDoubleTap(MotionEvent e) {
    //GeoPoint p = mapView.getProjection().fromPixels((int)e.getX(), (int)e.getY());
    mapView.getController().zoomInFixing((int) e.getX(), (int) e.getY());
    //AlertDialog.Builder dialog = new AlertDialog.Builder(this);
    //dialog.setTitle("Double Tap");
    //dialog.setMessage("Location: " + p.getLatitudeE6() + ", " + p.getLongitudeE6());
    // dialog.setMessage("Location: " + (int) e.getX() + ", " + (int) e.getY());
    //dialog.show();

    return true;
  }

  @Override
  public boolean onDoubleTapEvent(MotionEvent e) {
    return false;
  }

  @Override
  public boolean onSingleTapConfirmed(MotionEvent e) {
    return false;
  }

  /**
   * Methods required by OnGestureListener
   **/
  @Override
  public boolean onDown(MotionEvent e) {
    return false;
  }

  @Override
  public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
    return false;
  }

  @Override
  public void onLongPress(MotionEvent e) {
  }

  @Override
  public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
    return false;
  }

  @Override
  public void onShowPress(MotionEvent e) {
  }

  @Override
  public boolean onSingleTapUp(MotionEvent e) {
    return false;
  }
}