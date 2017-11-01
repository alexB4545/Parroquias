package com.example.sistemas.parroquias;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.example.sistemas.parroquias.Api.Datos;
import com.example.sistemas.parroquias.Models.Parroquias;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Retrofit retrofit;
    public final static String TAG="Datos Parroquia";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        retrofit=new Retrofit.Builder().baseUrl("https://www.datos.gov.co/resource/").addConverterFactory(GsonConverterFactory.create()).build();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Datos service = retrofit.create(Datos.class);
        final Call<List<Parroquias>> terminalCall = service.obtenerListaParroquia();

        terminalCall.enqueue(new Callback<List<Parroquias>>() {
            @Override
            public void onResponse(Call<List<Parroquias>> call, Response<List<Parroquias>> response) {

                if (response.isSuccessful()) {
                    List terminal = response.body();
                    for (int i = 0; i < terminal.size(); i++) {
                        Parroquias te = (Parroquias) terminal.get(i);


                        // Add a marker in Sydney and move the camera
                        LatLng sydney = new LatLng(te.getLatitud(),te.getLongitud());
                        mMap.addMarker(new MarkerOptions().position(sydney).title(te.getParroquia()+
                                " " + te.getTelefono()+ " "+ " " + te.getDireccion()));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                        mMap.getUiSettings().setZoomControlsEnabled(true);

                    }
                } else {

                    //Toast notificacion = Toast.makeText(MainActivity.this,"error",Toast.LENGTH_LONG);
                    //notificacion.show();
                }
            }

            @Override
            public void onFailure(Call<List<Parroquias>> call, Throwable t) {

                //Toast notificacion = Toast.makeText(MainActivity.this,"error fallido",Toast.LENGTH_LONG);
                //notificacion.show();
            }
        });
    }
}
