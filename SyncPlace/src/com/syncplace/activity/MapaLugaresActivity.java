package com.syncplace.activity;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.maps.MapController;
import com.syncplace.ErrorDialogFragment;
import com.syncplace.ListaPuntos;
import com.syncplace.Lugar;
import com.syncplace.R;
import com.syncplace.SensorDB;

public class MapaLugaresActivity extends FragmentActivity implements
GooglePlayServicesClient.ConnectionCallbacks, 
GooglePlayServicesClient.OnConnectionFailedListener{
	
	/****** Utilizado solo para mostrar mi posición ******/
	private SupportMapFragment mapFragment;
	private GoogleMap map;
	private LocationClient mLocationClient;
	
	private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
	
	private LatLng myPos = null;
	/*****************************************************/
	
	Context contexto;
	
	boolean orig = false;
		
	EditText direccion;	
	LinearLayout searchPanel;
    Button searchButton;
    EditText searchText;
    public static final int SEARCH_ID = Menu.FIRST;
    MapController mc;
    Geocoder geoCoder;
    
    ListaPuntos listaGP;
    ListaPuntos listaGPaux; 
	
	final DecimalFormat decf = new DecimalFormat("###.####");
    private boolean isEditMode = false;
	    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        
        mLocationClient = new LocationClient(this, this, this);

        mapFragment = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map));
        map = mapFragment.getMap();

        map.setMyLocationEnabled(true);
        
        //FrameLayout superior Principal
        FrameLayout frame = (FrameLayout) findViewById(R.id.mainFrame);
        frame.setVisibility(View.VISIBLE);
        
        searchPanel = (LinearLayout) findViewById(R.id.searchPanel);
        searchButton = (Button) findViewById(R.id.searchButton);
        searchText = (EditText) findViewById(R.id.searchText);
        contexto = this;
	    
        dibuja_lugares();
	            
        /****Pintamos sobre el mapa a�adiendo una capa con el dibujo****/        
        //Si vengo de servicios o de nuevaruta
        Bundle contenedor = getIntent().getExtras();
        if (contenedor != null){
        	String origen = contenedor.getString("origen");
        	if (origen != null){
        		//Seleccionamos origen
        		if (origen.equals("si"))
        			orig = true;
        	}
        	else{
        		//Marcamos lugares en el mapa
        		orig = false;
            	
        		String clase = contenedor.getString("clase");
        		
    	        listaGP = contenedor.getParcelable("gplist");
    	        if (listaGP == null || listaGP.size() == 0)
    	        	System.out.println("No hay lista de puntos que pintar");
    	        else{
    	        	for (int i=0;i<listaGP.size();i++){
    	        		LatLng pos = new LatLng(listaGP.get(i).getLatitud(),listaGP.get(i).getLongitud());
    	        		
    	        		if (clase.equals("servicios"))
    	        			setMarker(pos, listaGP.get(i).getNombre(), listaGP.get(i).getDescripcion(),BitmapDescriptorFactory.fromResource(R.drawable.tick));
    	        			 
    	        		else //Creo la ruta
    	        			setMarker(pos, listaGP.get(i).getNombre(), listaGP.get(i).getDescripcion(),BitmapDescriptorFactory.fromResource(R.drawable.posicion));
    	        	}
    	        }
        	}
        }
        
        /***************************************************************/
        map.setOnMarkerClickListener(new OnMarkerClickListener() {

			@Override
			public boolean onMarkerClick(Marker marker) {
				// TODO Auto-generated method stub
				Lugar l = null;
				
				if (listaGP == null){
					SensorDB usdbh = new SensorDB(contexto, "DBSensor", null, 1);
					SQLiteDatabase db = usdbh.getWritableDatabase();
					
					l = usdbh.buscaLugar(db, marker.getTitle());
					if (l == null)
						return false;					
				}	
				else{				
					for (int i=0; i< listaGP.size();i++){
						if (listaGP.get(i).getNombre().equals(marker.getTitle())){
							l = listaGP.get(i);
						}
					}
				}
				LayoutInflater inflater = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View v = inflater.inflate(R.layout.dialoglugar, null);
		    	
				EditText etLat = (EditText)v.findViewById(R.id.editTextLat);
				etLat.setText(String.valueOf(l.getLatitud()));
				EditText etLon = (EditText)v.findViewById(R.id.editTextLon);
				etLon.setText(String.valueOf(l.getLongitud()));
				EditText etInfo = (EditText)v.findViewById(R.id.editTextInfo);
				etInfo.setText(l.getDescripcion());
				EditText etNombre = (EditText)v.findViewById(R.id.editTextNombre);
				etNombre.setText(l.getNombre());
				EditText etTipo = (EditText)v.findViewById(R.id.editTextTipo);
				etTipo.setText(l.getTipo());
				
		        ErrorDialogFragment alert = new ErrorDialogFragment();
		        alert.createDialogLugar(contexto, v).show();
				
		        return true;
			}
		});
        
        map.setOnMapClickListener(new OnMapClickListener() {
            public void onMapClick(LatLng point) {
                
                Intent i;
                if (orig == true){
	    			i = new Intent(contexto, Servicios.class);		    		
		            Bundle contenedor=new Bundle();
		            contenedor.putDouble("latitud", point.latitude);
		            contenedor.putDouble("longitud", point.longitude);
		            i.putExtras(contenedor);
					i.putExtra("creareditar",true);//crear
					
					((Activity) contexto).setResult(Activity.RESULT_OK, i );
					((Activity) contexto).finish();
                }
	    		else{
	    			LayoutInflater inflater = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					View v = inflater.inflate(R.layout.dialoglugar, null);
			    	
					EditText etLat = (EditText)v.findViewById(R.id.editTextLat);
					etLat.setText(String.valueOf(point.latitude));
					EditText etLon = (EditText)v.findViewById(R.id.editTextLon);
					etLon.setText(String.valueOf(point.longitude));
					
			        ErrorDialogFragment alert = new ErrorDialogFragment();
			        alert.createDialogLugar(contexto, v).show();
	    		}
            }
        });

        /*map.setOnMapLongClickListener(new OnMapLongClickListener() {
            public void onMapLongClick(LatLng point) {
                Projection proj = map.getProjection();
                Point coord = proj.toScreenLocation(point);
         
                Toast.makeText(
                    MapaLugaresActivity.this,
                    "Click Largo\n" +
                    "Lat: " + point.latitude + "\n" +
                    "Lng: " + point.longitude + "\n" +
                    "X: " + coord.x + " - Y: " + coord.y,
                    Toast.LENGTH_SHORT).show();
            }
        });*/

        /***************************************************************/
        searchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String searchFor = searchText.getText().toString();
                
                geoCoder = new Geocoder(getBaseContext(), Locale.getDefault());

                try {
                    List<Address> addresses =
                           geoCoder.getFromLocationName(searchFor, 5);
                    if (addresses.size() > 0) {
                        
                    	map.clear();
                    	LatLng Pos = new LatLng(addresses.get(0).getLatitude(),addresses.get(0).getLongitude());
                        map.addMarker(new MarkerOptions().position(Pos)
                        								  .title(searchFor)
                        								  .icon(BitmapDescriptorFactory.fromResource(R.drawable.tick)));
                	    map.moveCamera(CameraUpdateFactory.newLatLngZoom(Pos, 15));
                	    map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
	}
	
	public boolean isEditMode(){
		return this.isEditMode;
	}
	
	public GoogleMap getMapView(){
		return this.map;
	}
	
	public void dibuja_lugares(){
		
		SensorDB usdbh = new SensorDB(this, "DBSensor", null, 1);
		SQLiteDatabase db = usdbh.getWritableDatabase();
	    
		String sql = "SELECT * FROM Lugar ";
	    Cursor fila = db.rawQuery(sql, null);
	    
	    //Nos aseguramos de que existe al menos un registro
	    if (fila.moveToFirst()) {
	    	//Recorremos el cursor hasta que no haya mas registros
	    	do {
	    		//System.out.println(usdbh.IsActivatedSensor(db, Integer.parseInt(fila.getString(0))));
	    		Lugar l = new Lugar(fila.getString(1), 
	    							fila.getString(2), 
	    							Double.valueOf(fila.getString(3)).doubleValue(), 
	    							Double.valueOf(fila.getString(4)).doubleValue(), 
	    							fila.getString(5));
	    	
	    		LatLng pos = new LatLng(l.getLatitud(),l.getLongitud());

	    		setMarker(pos, l.getNombre(), l.getDescripcion(), null);
	    			    		
	    	} while(fila.moveToNext());	
	    }
	    else {
	    	System.out.println("No existen lugares en la base de datos");    	
	    }
    	//Cerramos la base de datos
	    db.close();
	    fila.close();
	} 
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
	    // Decide what to do based on the original request code
	    switch (requestCode) {

	        case CONNECTION_FAILURE_RESOLUTION_REQUEST:
	            /*
	             * If the result code is Activity.RESULT_OK, try
	             * to connect again
	             */
	            switch (resultCode) {
	                case Activity.RESULT_OK:
	                    mLocationClient.connect();
	                    break;
	            }
            default:
            	centrarCamara(myPos);
            	dibuja_lugares();
            	break;
	    }
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean result = super.onCreateOptionsMenu(menu);
        menu.add(0, SEARCH_ID, 0, "Search");
        return result;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean result = super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case SEARCH_ID:
                searchPanel.setVisibility(View.VISIBLE);
                break;
        }

        return result;
    }
    public void hideSearchPanel() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(
             searchText.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        searchPanel.setVisibility(View.INVISIBLE);
    }

	private void centrarCamara(LatLng posicion){		
		// Move the camera instantly to Sydney with a zoom of 15.
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(posicion, 15));
		// Zoom in, animating the camera.
		map.animateCamera(CameraUpdateFactory.zoomIn());
		// Zoom out to zoom level 10, animating with a duration of 2 seconds.
		map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);		
	}
	
	private void setMarker(LatLng position, String titulo, String info, BitmapDescriptor icon) {
		// Agregamos marcadores para indicar sitios de inter�ses.
		if (icon == null)
			icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);
		map.addMarker(new MarkerOptions().position(position).title(titulo)  //Agrega un titulo al marcador
				.snippet(info)   //Agrega informaci�n detalle relacionada con el marcador 
				.icon(icon)); //Color del marcador		  
	}
	
	private boolean isGooglePlayServicesAvailable() {
	    // Check that Google Play services is available
	    int resultCode =  GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
	    // If Google Play services is available
	    if (ConnectionResult.SUCCESS == resultCode) {
	        // In debug mode, log the status
	        Log.d("Location Updates", "Google Play services is available.");
	        return true;
	    } else {
	        // Get the error dialog from Google Play services
	        Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog( resultCode,this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
	        // If Google Play services can provide an error dialog
	        if (errorDialog != null) {
	            // Create a new DialogFragment for the error dialog
	            ErrorDialogFragment errorFragment = new ErrorDialogFragment();
	            errorFragment.setDialog(errorDialog);
	            errorFragment.show(getSupportFragmentManager(), "Location Updates");
	        }

	        return false;
	    }
	}

	/*
	 * Called by Location Services when the request to connect the
	 * client finishes successfully. At this point, you can
	 * request the current location or start periodic updates
	 */
	@Override
	public void onConnected(Bundle dataBundle) {
	    // Display the connection status
	    Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
	    Location location = mLocationClient.getLastLocation();
	    if (location != null){
		    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
		    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 17);
		    map.animateCamera(cameraUpdate);
	    }
	}

	/*
	 * Called by Location Services if the connection to the
	 * location client drops because of an error.
	 */
	@Override
	public void onDisconnected() {
	    // Display the connection status
	    Toast.makeText(this, "Disconnected. Please re-connect.", Toast.LENGTH_SHORT).show();
	}

	/*
	 * Called by Location Services if the attempt to
	 * Location Services fails.
	 */
	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
	    /*
	     * Google Play services can resolve some errors it detects.
	     * If the error has a resolution, try sending an Intent to
	     * start a Google Play services activity that can resolve
	     * error.
	     */
	    if (connectionResult.hasResolution()) {
	        try {
	            // Start an Activity that tries to resolve the error
	            connectionResult.startResolutionForResult(
	                    this,
	                    CONNECTION_FAILURE_RESOLUTION_REQUEST);
	            /*
	            * Thrown if Google Play services canceled the original
	            * PendingIntent
	            */
	        } catch (IntentSender.SendIntentException e) {
	            // Log the error
	            e.printStackTrace();
	        }
	    } else {
	       Toast.makeText(getApplicationContext(), "Sorry. Location services not available to you", Toast.LENGTH_LONG).show();
	    }
	}
	
	@Override
	protected void onStart() {
	    super.onStart();
	    // Connect the client.
	    if(isGooglePlayServicesAvailable()){
	        mLocationClient.connect();
	    }
	}
	
	/*
	 * Called when the Activity is no longer visible.
	 */
	@Override
	protected void onStop() {
	    // Disconnecting the client invalidates it.
	    mLocationClient.disconnect();
	    super.onStop();
	}
    
}