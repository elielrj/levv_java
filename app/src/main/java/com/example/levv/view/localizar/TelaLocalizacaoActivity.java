package com.example.levv.view.localizar;

import static com.example.levv.support.AppUtil.ERRO_AO_BUSCAR_NUMERO_DO_PEDIDO;
import static com.example.levv.support.AppUtil.LATITUDE_TUBARAO;
import static com.example.levv.support.AppUtil.LONGITUDE_TUBARAO;

import static com.example.levv.support.AppUtil.NUMERO_DO_PEDIDO;
import static com.example.levv.view.pedido.enviar.TelaProdutoEnviarActivity.TIPO_DE_USUARIO;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.levv.R;

import com.example.levv.support.AppUtil;
import com.example.levv.support.Localizar;
import com.example.levv.support.Rota;
import com.example.levv.view.funcional.TelaMainActivity;
import com.example.levv.view.pedido.acompanhar.TelaProdutoAcompanharActivity;
import com.example.levv.view.pedido.entregar.TelaProdutoEntregarActivity;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class TelaLocalizacaoActivity
        extends AppCompatActivity
        implements OnMapReadyCallback {

    String editOrigem;
    String editDestino;
    public GoogleMap mMap;
    private Rota rotaDeEntrega;
    private Rota rotaAteLocalDeColeta;
    private Localizar localizar;
    private Bundle bundle;
    private TextView txtNumeroDoPedido;
    private TextView txtValorDoPedido;
    private TextView txtEnderecoDeColeta;
    private TextView txtEnderecoDeEntrega;
    public LatLng latLngColeta;
    public LatLng latLngEntrega;
    public LatLng coordenadasAtual;
    private Button btnTrocarDeMapa;
    private boolean isMapTypeHybrid = false;
    private MarkerOptions markerOptionsColeta;
    private MarkerOptions markerOptionsEntrega;
    private String tipoDeUsuarioLogado;
    private static final String TRANSPORTADOR = "Transportador";


    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor dados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_localizacao);

        this.bundle = getIntent().getExtras();
        this.localizar = new Localizar(this);
        suporteMapFragment();
        inicializarComponentes();
        editOrigem = getIntent().getStringExtra(AppUtil.ENDERECO_COLETA_DO_PEDIDO);
        editDestino = getIntent().getStringExtra(AppUtil.ENDERECO_ENTREGA_PEDIDO);

        sharedPreferences = getSharedPreferences(AppUtil.SHARED_PREFERENCES_NAME_APP, Context.MODE_PRIVATE);
        dados = sharedPreferences.edit();

       startLocalizacaoDoTransportador();
    }

    private void startLocalizacaoDoTransportador() {
        tipoDeUsuarioLogado = sharedPreferences.getString(TIPO_DE_USUARIO, "");


        if(tipoDeUsuarioLogado != null) {

            if (tipoDeUsuarioLogado.equalsIgnoreCase(TRANSPORTADOR)) {

                Toast.makeText(this, "Start updates tELAlOCALIZACAOaCTIVITY", Toast.LENGTH_SHORT).show();

                localizar.startLocationUpdates();

            }

        }
    }

    @SuppressLint("SetTextI18n")
    private void inicializarComponentes() {

        txtNumeroDoPedido = findViewById(R.id.txtNumeroDoPedido);
        txtValorDoPedido = findViewById(R.id.txtValorDoPedido);
        txtEnderecoDeColeta = findViewById(R.id.txtEnderecoDeColeta);
        txtEnderecoDeEntrega = findViewById(R.id.txtEnderecoDeEntrega);
        btnTrocarDeMapa = findViewById(R.id.btnTrocarDeMapa);
        btnTrocarDeMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isMapTypeHybrid) {
                    mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                    isMapTypeHybrid = true;
                } else {
                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    isMapTypeHybrid = false;
                }
            }
        });

        txtNumeroDoPedido
                .setText("Número\n" +
                        bundle.getString(NUMERO_DO_PEDIDO, ERRO_AO_BUSCAR_NUMERO_DO_PEDIDO)
                );

        String valor =
                AppUtil
                        .formatarValorEmReais(
                                bundle.getDouble(AppUtil.VALOR_DO_PEDIDO, 0.00f)
                        );

        valor.replace(".", ",");
        txtValorDoPedido.setText("Valor\nR$ " + valor);

        txtEnderecoDeColeta
                .setText("Endereço de Coleta\n" +
                        bundle.getString(AppUtil.ENDERECO_COLETA_DO_PEDIDO, AppUtil.ERRO_AO_BUSCAR_ENDERECO_COLETA)
                );

        txtEnderecoDeEntrega
                .setText("Endereço de Entrega\n" +
                        bundle.getString(AppUtil.ENDERECO_ENTREGA_PEDIDO, AppUtil.ERRO_AO_BUSCAR_ENDERECO_ENTREGA)
                );

        latLngEntrega =
                new LatLng(
                        bundle.getDouble(AppUtil.LATITUDE_ENTREGA, LATITUDE_TUBARAO),
                        bundle.getDouble(AppUtil.LONGITUDE_ENTREGA, LONGITUDE_TUBARAO)
                );

        latLngColeta =
                new LatLng(
                        bundle.getDouble(AppUtil.LATITUDE_COLETA, LATITUDE_TUBARAO),
                        bundle.getDouble(AppUtil.LONGITUDE_COLETA, LONGITUDE_TUBARAO)
                );
    }

    private void suporteMapFragment() {
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        coordenadasAtual = localizar.localizarLatLng();

        markerOptionsColeta =
                new MarkerOptions()
                        .position(latLngColeta)
                        .title(AppUtil.MAKER_COLETA)
                        .alpha(1.0f)
                        .icon(BitmapDescriptorFactory.defaultMarker(200.0f));

        markerOptionsEntrega =
                new MarkerOptions()
                        .position(latLngEntrega)
                        .title(AppUtil.MAKER_ENTREGA)
                        .alpha(1.0f)
                        .icon(BitmapDescriptorFactory.defaultMarker(300.0f));

        mMap.addMarker(markerOptionsColeta);
        mMap.addMarker(markerOptionsEntrega);


        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordenadasAtual, 12));

        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);

        mMap.setMyLocationEnabled(true);

        rotaDeEntrega = new Rota(this, latLngColeta, latLngEntrega, Color.RED);

        if (!bundle.getString(AppUtil.TELA_ORIGEM, AppUtil.TELA_ACOMPANHAR_PEDIDO).equalsIgnoreCase(AppUtil.TELA_ACOMPANHAR_PEDIDO)) {

            moverMarcadorLocalizacaoAtualDoEntregador();

            moverCameraParaEntregador();

            this.rotaAteLocalDeColeta =
                    new Rota(this,
                            coordenadasAtual,
                            latLngColeta,
                            Color.GRAY
                    );
        }
    }

    @Override
    public void onBackPressed() {

        String telaDeOrigem =
                bundle.getString(AppUtil.TELA_ORIGEM, AppUtil.ERRO_AO_BUSCAR_TELA_ORIGEM);

        if (telaDeOrigem.equalsIgnoreCase(AppUtil.TELA_ACOMPANHAR_PEDIDO)) {

            startActivity(
                    new Intent(
                            TelaLocalizacaoActivity.this,
                            TelaProdutoAcompanharActivity.class
                    )
            );

        } else if (telaDeOrigem.equalsIgnoreCase(AppUtil.TELA_ENTREGAR_PEDIDO)) {

            startActivity(
                    new Intent(
                            TelaLocalizacaoActivity.this,
                            TelaProdutoEntregarActivity.class
                    )
            );

        } else {

            startActivity(
                    new Intent(TelaLocalizacaoActivity.this,
                            TelaMainActivity.class
                    )
            );

        }
    }

    private void moverMarcadorLocalizacaoAtualDoEntregador() {
        mMap.clear();

        mMap.addMarker(markerOptionsColeta);
        mMap.addMarker(markerOptionsEntrega);

        MarkerOptions markerOptionsAtual =
                new MarkerOptions()
                        .position(coordenadasAtual)
                        .title(AppUtil.MAKER_MINHA_LOCALIZACAO)
                        .alpha(1.0f)
                        .icon(
                                BitmapDescriptorFactory
                                        .defaultMarker(100.0f)
                        );

        mMap.addMarker(markerOptionsAtual);

        mMap.animateCamera(CameraUpdateFactory.newLatLng(markerOptionsAtual.getPosition()));
        // mMap.moveCamera(CameraUpdateFactory.newLatLng(markerOptions.getPosition()));
    }

    private void moverCameraParaEntregador(){
        CameraPosition cameraPosition = new CameraPosition.Builder().target(localizar.localizarLatLng()).zoom(12).build();
        CameraUpdate update = CameraUpdateFactory.newCameraPosition(cameraPosition);
        mMap.moveCamera(update);
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(tipoDeUsuarioLogado != null) {

            if (tipoDeUsuarioLogado.equalsIgnoreCase(TRANSPORTADOR)) {

                Toast.makeText(this, "Stop updates", Toast.LENGTH_SHORT).show();

                localizar.stopLocationUpdates();
            }

        }
    }

}