package com.alberovalley.primeracongps;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;
import android.widget.Toast;

public class PrimeraConGPSActivity extends Activity implements LocationListener {
	private TextView enlace;
	private LocationManager locationManager;
	private String provider;
	private static final String gMapsLink = "https://maps.google.com/maps?z=15&q=";
	private Location location;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        enlace = (TextView)findViewById(R.id.enlace);      

     // obtener el location manager
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		// Definir los criterios para elegir el location provider -> usamos
		// por defecto
		Criteria criteria = new Criteria();
		provider = locationManager.getBestProvider(criteria, false);
		 location = locationManager.getLastKnownLocation(provider);

     // Inicializamos los valores de latitud y longitud
		obtenerLocalizacionYcrearEnlace();
    }

    private void obtenerLocalizacionYcrearEnlace() {
    	if (location != null) {
			System.out.println("Provider " + provider + " has been selected.");
			double lat =  (location.getLatitude());
			double lng =  (location.getLongitude());
			enlace.setText(gMapsLink + lat + "," + lng);
		} else {
			enlace.setText("Provider no disponible");	
		}
	}

	/* Solicita las actualizaciones en el arranque */
	@Override
	protected void onResume() {
		super.onResume();
		locationManager.requestLocationUpdates(provider, 400, 1, this);
	}

	/* Elimina las actualizaciones del locationlistener cuando la Actividad está pausada*/
	@Override
	protected void onPause() {
		super.onPause();
		locationManager.removeUpdates(this);
	}

	@Override//si se cambia de ubicación
	public void onLocationChanged(Location location) {
		obtenerLocalizacionYcrearEnlace();
	}

	@Override//si se cambia de estado 
	public void onStatusChanged(String provider, int status, Bundle extras) {
		Toast.makeText(this, "Cambio de status: " ,
				Toast.LENGTH_SHORT).show();

	}

	@Override//si se habilita un provider
	public void onProviderEnabled(String provider) {
		Toast.makeText(this, "Habilitado nuevo provider " + provider,
				Toast.LENGTH_SHORT).show();

	}

	@Override//si el provider no está habilitado
	public void onProviderDisabled(String provider) {
		Toast.makeText(this, "Deshabilitado el provider: " + provider,
				Toast.LENGTH_SHORT).show();
	}
}