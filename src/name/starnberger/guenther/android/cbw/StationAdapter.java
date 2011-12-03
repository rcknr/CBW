package name.starnberger.guenther.android.cbw;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import name.starnberger.guenther.android.cbw.R;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

class StationAdapter extends ArrayAdapter<Station> {
	private ListStations listStations;
	private ArrayList<Station> items;
	
	public StationAdapter(ListStations listStations, int textViewResourceId,
			ArrayList<Station> items) {
		super(listStations, textViewResourceId, items);
		this.listStations = listStations;
		this.items = items;
	}

	private String roundLoc(float loc) {
		NumberFormat numberFormat = new DecimalFormat("0.0");
		return numberFormat.format(loc);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) listStations
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.row, null);
		}
		Station o = items.get(position);
		if (o != null) {
			TextView station_title = (TextView) v
					.findViewById(R.id.station_title);
			if (station_title != null) {
				station_title.setText(o.getStationName());
			}
//  Description field
//			TextView station_description = (TextView) v
//					.findViewById(R.id.station_description);
//			if (station_description != null) {
//				station_description.setText(o.getStationDescription());
//			}

			TextView bikes_available_num = (TextView) v
					.findViewById(R.id.bikes_available_num);
			if (bikes_available_num != null) {
				String bikes_available_str = o.getBikesAvailable();
				int bikes_available = Integer.parseInt(bikes_available_str);
				bikes_available_num.setText(bikes_available_str);
				if (bikes_available == 0) {
					bikes_available_num.setTextColor(Color.RED);
				} else if (bikes_available <= 3) {
					bikes_available_num.setTextColor(Color.YELLOW);
				} else {
					bikes_available_num.setTextColor(Color.WHITE);
				}
			}

			TextView boxes_available_num = (TextView) v
					.findViewById(R.id.boxes_available_num);
			if (boxes_available_num != null) {
				String boxes_available_str = o.getBoxesAvailable();
				int boxes_available = Integer.parseInt(boxes_available_str);
				boxes_available_num.setText(boxes_available_str);
				if (boxes_available == 0) {
					boxes_available_num.setTextColor(Color.RED);
				} else if (boxes_available <= 3) {
					boxes_available_num.setTextColor(Color.YELLOW);
				} else {
					boxes_available_num.setTextColor(Color.WHITE);
				}
			}

			//LinearLayout distance_and_active_row = (LinearLayout) v.findViewById(R.id.distance_and_active_row);
			TextView distance = (TextView) v.findViewById(R.id.distance);
			TextView distance_num = (TextView) v.findViewById(R.id.distance_num);
			TextView distance_km = (TextView) v.findViewById(R.id.distance_km);
			
			Location loc = listStations.getLocationHelper().getCachedLocation();
			
			float dist = 0;
			if(loc != null) {
				dist = loc.distanceTo(o.getLocation()) / 1000;
			}
			
			if (distance_num != null) {			
				if(loc == null) {
					distance.setVisibility(View.INVISIBLE);
					distance_num.setVisibility(View.INVISIBLE);
					distance_km.setVisibility(View.INVISIBLE);
				} else {
					distance.setVisibility(View.VISIBLE);
					distance_num.setVisibility(View.VISIBLE);
					
					if (dist < 100) {
						distance_num.setText(roundLoc(dist));
						distance_km.setVisibility(View.VISIBLE);
					} else {
						distance_num.setText("?");
						distance_km.setVisibility(View.INVISIBLE);
					}
				}
			}

    // Active station flag display
	//		TextView active_view = (TextView) v
	//				.findViewById(R.id.active);
	//		boolean active_val = o.isActive();
	//		if (active_view != null) {
	//			if(active_val) {
	//				active_view.setVisibility(View.INVISIBLE);
	//			} else {
	//				active_view.setVisibility(View.VISIBLE);
	//			}
	//		}
			
	//		if(active_val && (loc == null)) {
				//distance_and_active_row.setVisibility(View.GONE);
	//		} else {
				//distance_and_active_row.setVisibility(View.VISIBLE);
	//		}

		}
		return v;
	}
}
