package com.example.levv.support;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.levv.controller.usuario.TransportadorController;
import com.example.levv.model.bo.endereco.Bairro;
import com.example.levv.model.bo.endereco.Cidade;
import com.example.levv.model.bo.endereco.Endereco;
import com.example.levv.model.bo.endereco.Estado;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.firestore.GeoPoint;
import java.util.List;
import java.util.Locale;

public class Localizar extends Permissao implements LocationListener  {

    protected LocationManager locationManager;
    protected GeoPoint geoPointAtual;

    public Localizar(Activity activity) {
        super(activity);
    }

    //2
    public boolean isGpsEnabled() {
        if(locationManager == null){
            locationManager = ((LocationManager) activity.getApplication().getSystemService(Context.LOCATION_SERVICE));
        }
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    //3
    @SuppressLint("MissingPermission")
    private Location startLocation() {
        return locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    }

    @SuppressLint("MissingPermission")
    public GeoPoint localizarGeoPoint() {

        if (isPermissaoParaLocalizar()) {

            if (isGpsEnabled()) {

                Location location = startLocation();

                if (location != null) {

                    return new GeoPoint(location.getLatitude(), location.getLongitude());

                } else {

                    Toast.makeText(activity, "Erro, ao obter Localização nula!", Toast.LENGTH_LONG).show();

                }

            } else {

                Toast.makeText(activity, Tag.GPS_DESATIVADO, Toast.LENGTH_LONG).show();

            }


        } else {

            Toast.makeText(activity, Tag.PERMISSAO_BUSCAR_LOCALIZACAO_NEGADA, Toast.LENGTH_LONG).show();

        }

        return null;
    }

    @SuppressLint("MissingPermission")
    public LatLng localizarLatLng() {

        if (isPermissaoParaLocalizar()) {

            if (isGpsEnabled()) {

                Location location = startLocation();

                if (location != null) {

                    return new LatLng(location.getLatitude(), location.getLongitude());

                } else {

                    Toast.makeText(activity, "Erro, ao obter Localização nula!", Toast.LENGTH_LONG).show();

                }

            } else {

                Toast.makeText(activity, Tag.GPS_DESATIVADO, Toast.LENGTH_LONG).show();

            }


        } else {

            Toast.makeText(activity, Tag.PERMISSAO_BUSCAR_LOCALIZACAO_NEGADA, Toast.LENGTH_LONG).show();

        }

        return null;
    }

    public Endereco buscarEndereco() {

        Geocoder geocoder = new Geocoder(activity.getApplicationContext(), Locale.getDefault());
        List<Address> addresses;
        Endereco endereco = null;
        GeoPoint geoPoint;

        if (isPermissaoParaLocalizar()) {

            if (isGpsEnabled()) {

                Location location = startLocation();

                if (location != null) {

                    geoPoint = new GeoPoint(location.getLatitude(), location.getLongitude());

                    try {

                        addresses = geocoder.getFromLocation(geoPoint.getLatitude(), geoPoint.getLongitude(), 1);

                        String logradouro = addresses.get(0).getThoroughfare();
                        String numero = addresses.get(0).getSubThoroughfare();
                        String complemento = addresses.get(0).getFeatureName();
                        String bairro = addresses.get(0).getSubLocality();
                        String cidade = addresses.get(0).getSubAdminArea();
                        String estado = addresses.get(0).getAdminArea();
                        String cep = addresses.get(0).getPostalCode();

                        endereco = new Endereco.EnderecoBuilder()
                                .setNumero(numero)
                                .setLogradouro(logradouro)
                                .setComplemento(complemento)
                                .setBairro(new Bairro(bairro, new Cidade(cidade, new Estado(estado))))
                                .setCep(cep)
                                .setLatLng(geoPoint)
                                .createEndereco();

                    } catch (Exception e) {

                        Toast.makeText(activity, "Erro ao obter endereço!", Toast.LENGTH_LONG).show();

                    }

                } else {

                    Toast.makeText(activity, "Erro, localização nula!", Toast.LENGTH_LONG).show();

                }

            } else {

                Toast.makeText(activity, Tag.GPS_DESATIVADO, Toast.LENGTH_LONG).show();

            }

        } else {

            Toast.makeText(activity, Tag.PERMISSAO_BUSCAR_LOCALIZACAO_NEGADA, Toast.LENGTH_LONG).show();

        }


        return endereco;
    }

    public String buscarEnderecoCompleto() {

        Geocoder geocoder = new Geocoder(activity.getApplicationContext(), Locale.getDefault());
        List<Address> addresses;
        String enderecoCompleto = "";


        if (isPermissaoParaLocalizar()) {

            if (isGpsEnabled()) {

                Location location = startLocation();

                if (location != null) {

                    try {

                        addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                        enderecoCompleto = addresses.get(0).getAddressLine(0);

                    } catch (Exception e) {

                        Toast.makeText(activity, "Erro ao obter endereço completo!", Toast.LENGTH_LONG).show();

                        Log.i("LOCALIZAR", e.getMessage());

                    }

                } else {

                    Toast.makeText(activity, "Erro, ao obter Localização nula!", Toast.LENGTH_LONG).show();

                }

            } else {

                Toast.makeText(activity, Tag.GPS_DESATIVADO, Toast.LENGTH_LONG).show();

            }


        } else {

            Toast.makeText(activity, Tag.PERMISSAO_BUSCAR_LOCALIZACAO_NEGADA, Toast.LENGTH_LONG).show();

        }

        return enderecoCompleto;
    }

    public GeoPoint converterEnderecoEmGeoPoint(String enderecoCompleto) {

        Geocoder geocoder = new Geocoder(activity.getApplicationContext(), Locale.getDefault());
        List<Address> addresses;
        GeoPoint p1 = null;

        try {

            addresses = geocoder.getFromLocationName(enderecoCompleto, 5);

            if (addresses == null) {
                return null;
            }

            Address location = addresses.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new GeoPoint((double) (location.getLatitude()),
                    (double) (location.getLongitude()));


        } catch (Exception e) {
            Toast.makeText(activity, "Erro ao converter Endereço em uma Geolocalização Válida!", Toast.LENGTH_LONG).show();
        }
        return p1;
    }

    public String converterLatLngEmEnderecoCompleto(GeoPoint latLng) {

        String address = "";

        try {


            Geocoder geocoder = new Geocoder(activity.getApplicationContext(), Locale.getDefault());

            List<Address> addresses = geocoder.getFromLocation(latLng.getLatitude(), latLng.getLongitude(), 1);

            address = addresses.get(0).getAddressLine(0);

        } catch (Exception e) {

            Toast.makeText(activity, "Erro ao converter LatLng em End!", Toast.LENGTH_LONG).show();

        }

        return address;
    }

    @SuppressLint("MissingPermission")
    public void startLocationUpdates(){

        if(isGpsEnabled()){
            if(isPermissaoParaLocalizar()){
                locationManager = (LocationManager) activity.getSystemService(activity.LOCATION_SERVICE);

                if(locationManager != null){
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,0, Localizar.this);


                    Toast.makeText(activity, "Start updates - Localizar - StartLocationUpdates", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    public void stopLocationUpdates(){
        locationManager.removeUpdates(this);
        Toast.makeText(activity, "stop update", Toast.LENGTH_LONG).show();
    }


    @Override
    public void onLocationChanged(@NonNull Location location) {

        geoPointAtual = new GeoPoint(location.getLatitude(), location.getLongitude());

        Toast.makeText(activity, "Lat " + geoPointAtual.getLatitude() + "Long " + geoPointAtual.getLongitude(), Toast.LENGTH_LONG).show();

        TransportadorController transportadorController = new TransportadorController();
        transportadorController.updateGeoPoint(geoPointAtual);

    }

    public  void stopLocation(){
        if(locationManager != null){
            locationManager = null;
        }
    }
}
