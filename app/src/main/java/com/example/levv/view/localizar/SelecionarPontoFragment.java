package com.example.levv.view.localizar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.levv.R;
import com.example.levv.support.AppUtil;
import com.example.levv.support.Localizar;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.common.collect.MapMaker;
import com.google.firebase.firestore.GeoPoint;


public class SelecionarPontoFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;


    Button btnSelecionarPontoNoMapa;
    OnFragmentInteractionListenerMapa mListenerMapa;
    private static final String LATITUDE = "latitude";
    private static final String LONGITUDE = "longitude";
    private double mLatitude;
    private double mLongitude;

    private static final String NOME_DO_TIPO_DO_FRAGMENTO = "nomeDoFragmento";

    private GeoPoint geoPointEnd;
    private MarkerOptions markerOptions;
    String descricaoDoPonto;
    String ponto = "";


    public SelecionarPontoFragment() {
        // Required empty public constructor
    }


    public interface OnFragmentInteractionListenerMapa {
        void selecionarGeoLocalizacao(GeoPoint geoPoint, String nomeDoTipoDeFramento);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);


        if (context instanceof OnFragmentInteractionListenerMapa) {
            mListenerMapa = (OnFragmentInteractionListenerMapa) context;
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_selecionar_ponto, container, false);

        btnSelecionarPontoNoMapa = view.findViewById(R.id.btnSelecionarPontoNoMapa);


        btnSelecionarPontoNoMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (geoPointEnd != null) {


                    //geoPointEnd = new GeoPoint(Localizar.LATITUDE_TUBARAO, Localizar.LONGITUDE_TUBARAO);

                    //Log.d("MAKER", "geopoint null - LAT " + geoPointEnd.getLatitude() + "LOG " + geoPointEnd.getLongitude());


                    mListenerMapa.selecionarGeoLocalizacao(geoPointEnd, getArguments().getString(NOME_DO_TIPO_DO_FRAGMENTO));

                    Log.d("MAKER", "LAT " + geoPointEnd.getLatitude() + "LOG " + geoPointEnd.getLongitude());
                } else {
                    Toast.makeText(getContext(), "Selecione um ponto válido no mapa!", Toast.LENGTH_LONG).show();
                }

            }
        });


        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        if (getArguments().containsKey(LATITUDE)) {


            if (mLongitude != 0 && mLongitude != 0) {

                mLatitude = getArguments().getDouble(LATITUDE);
                mLongitude = getArguments().getDouble(LONGITUDE);

                //setar localizacao já escolhida anteriormente
            } else {
                //setar localizacao atual
            }
        }


        return view;
    }

    public static SelecionarPontoFragment newInstance(double latirude, double longitude, String nomeDoFragmento) {

        Bundle args = new Bundle();

        args.putDouble(LATITUDE, latirude);
        args.putDouble(LONGITUDE, longitude);

        args.putString(NOME_DO_TIPO_DO_FRAGMENTO, nomeDoFragmento);

        SelecionarPontoFragment fragment = new SelecionarPontoFragment();
        fragment.setArguments(args);

        return fragment;
    }


    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        Localizar localizar = new Localizar(getActivity());

        LatLng latLngAtual = localizar.localizarLatLng();


        descricaoDoPonto = getArguments().getString(NOME_DO_TIPO_DO_FRAGMENTO);

        verificarNomeDoMarcador();

        markerOptions = new MarkerOptions().position(latLngAtual).title(ponto).draggable(true);

        mMap.addMarker(markerOptions);

        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDrag(@NonNull Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(@NonNull Marker marker) {

                markerOptions = new MarkerOptions().position(marker.getPosition()).title(ponto).draggable(true);

                geoPointEnd = new GeoPoint(marker.getPosition().latitude, marker.getPosition().longitude);

                moverMarcador();
            }

            @Override
            public void onMarkerDragStart(@NonNull Marker marker) {

            }
        });


        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                markerOptions = new MarkerOptions().position(latLng).title(ponto).draggable(true);

                geoPointEnd = new GeoPoint(latLng.latitude, latLng.longitude);

                moverMarcador();
            }
        });

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(@NonNull LatLng latLng) {
                markerOptions = new MarkerOptions().position(latLng).title(ponto).draggable(true);

                geoPointEnd = new GeoPoint(latLng.latitude, latLng.longitude);

                moverMarcador();
            }
        });


        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngAtual, 15));

        //zoom
        mMap.getUiSettings().setZoomControlsEnabled(true);

        //sua localização em tempo real
        mMap.setMyLocationEnabled(true);

        //zoom até o local atual
        mMap.getUiSettings().setZoomGesturesEnabled(true);
    }

    private void verificarNomeDoMarcador() {
        if (descricaoDoPonto.equalsIgnoreCase("fragmentoColeta")) {
            ponto = "Ponto de coleta";
        } else if (descricaoDoPonto.equalsIgnoreCase("fragmentoEntrega")) {
            ponto = "Ponto de entrega";
        }
    }

    private void moverMarcador() {
        mMap.clear();
        mMap.addMarker(markerOptions);
        mMap.animateCamera(CameraUpdateFactory.newLatLng(markerOptions.getPosition()));
       // mMap.moveCamera(CameraUpdateFactory.newLatLng(markerOptions.getPosition()));
    }



}