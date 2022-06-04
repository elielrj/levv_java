package com.example.levv.controller.tela;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.levv.R;
import com.example.levv.support.Localizar;
import com.example.levv.support.LockableScrollView;
import com.example.levv.view.localizar.SelecionarPontoFragment;
import com.example.levv.controller.pedido.PedidoController;
import com.example.levv.model.DAO.ConfiguracaoFirebase;
import com.example.levv.model.bo.pedido.Item;
import com.example.levv.model.bo.pedido.Pedido;
import com.example.levv.model.tela.TelaProdutoEnviarModel;
import com.example.levv.support.AppUtil;
import com.example.levv.support.Tag;
import com.example.levv.support.Validar;
import com.example.levv.view.funcional.TelaMainActivity;
import com.example.levv.view.pedido.enviar.TelaProdutoEnviarActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.GeoPoint;

import java.util.Locale;
import java.util.Random;

public class TelaProdutoEnviarController {

    public static final String TIPO_DE_USUARIO = "tipoDeUsuario";
    public static final String CLIENTE = "Cliente";
    public static final String LOJISTA = "Lojista";
    public static final String TRANSPORTADOR = "transportador";
    public static final String ADMINISTRADOR = "Administrador";

    private TelaProdutoEnviarActivity activity;
    private TelaProdutoEnviarModel model;

    public boolean isDadosValidos = false;
    public boolean fragmentoMapaColeta = false;
    public boolean fragmentoMapaEntrega = false;

    public static final String NOME_DO_TIPO_DO_FRAGMENTO = "nomeDoFragmento";
    public static final String FRAGMENTO_COLETA = "fragmentoColeta";
    public static final String FRAGMENTO_ENTREGA = "fragmentoEntrega";


    public TelaProdutoEnviarController(TelaProdutoEnviarActivity activity) {
        this.activity = activity;
        this.model = new TelaProdutoEnviarModel(this);


        inicializarComponentes();
    }


    private void inicializarComponentes() {
        //SPINNER
        activity.spQuantidade = activity.findViewById(R.id.spQuantidade);
        inicializarSpinnerQuantidadeDeItens();


        activity.spMeioDeTransporte = activity.findViewById(R.id.spMeioDeTransporte);
        inicializarSpinnerMeioDeTransporte();

        activity.spPeso = activity.findViewById(R.id.spPeso);
        inicializarSpinnerPeso();


        //EDIT
        //activity.editEnderecoDeColeta = activity.findViewById(R.id.editColeta);
        //activity.editEnderecoDeEntrega = activity.findViewById(R.id.autoCompletarEntrega);

        activity.editValorEstimado = activity.findViewById(R.id.edtValorEstimado);

        //BUTONS
        activity.imgLocalizarColeta = activity.findViewById(R.id.imgLocalizarColeta);
        activity.imgLocalizarColeta.setOnClickListener(activity);

        activity.imgLocalizarEntrega = activity.findViewById(R.id.imgLocalizarEntrega);
        activity.imgLocalizarEntrega.setOnClickListener(activity);

        activity.btnEnviarPedido = activity.findViewById(R.id.btnEnviarPedido);
        activity.btnEnviarPedido.setOnClickListener(activity);

        activity.btnLimpar = activity.findViewById(R.id.btnLimpar);
        activity.btnLimpar.setOnClickListener(activity);

        activity.imgLocalizarColetaNoMapa = activity.findViewById(R.id.imgLocalizarColetaNoMapa);
        activity.imgLocalizarColetaNoMapa.setOnClickListener(activity);

        activity.imgLocalizarEntregaNoMapa = activity.findViewById(R.id.imgLocalizarEntregaNoMapa);
        activity.imgLocalizarEntregaNoMapa.setOnClickListener(activity);

        activity.scrollView = activity.findViewById(R.id.svEnviar);

        //RadioGroup
        activity.rbVolumePequeno = activity.findViewById(R.id.rbVolumePequeno);
        activity.rbVolumePequeno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activity.rotaEstaPreenchida()) {
                    activity.editValorEstimado.setText(String.valueOf(activity.atualizarValorDoPedido()));
                }
            }
        });

        activity.rbVolumeMedio = activity.findViewById(R.id.rbVolumeMedio);
        activity.rbVolumeMedio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activity.rotaEstaPreenchida()) {
                    activity.editValorEstimado.setText(String.valueOf(activity.atualizarValorDoPedido()));
                }
            }
        });

        activity.rbVolumeGrande = activity.findViewById(R.id.rbVolumeGrande);
        activity.rbVolumeGrande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activity.rotaEstaPreenchida()) {
                    activity.editValorEstimado.setText(String.valueOf(activity.atualizarValorDoPedido()));
                }
            }
        });

        activity.rgVolume = activity.findViewById(R.id.rgVolume);

        //AutoCompletar
        activity.autoCompleteTextViewColeta = activity.findViewById(R.id.autoCompletarColeta);


        activity.autoCompleteTextViewEntrega = activity.findViewById(R.id.autoCompletarEntrega);

        activity.btnCalcular = activity.findViewById(R.id.btnCalcular);
        activity.btnCalcular.setOnClickListener(activity);

    }


    public void inicializarSpinnerQuantidadeDeItens() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(activity, R.array.quantidade_itens, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        activity.spQuantidade.setAdapter(adapter);
    }

    public void inicializarSpinnerPeso() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(activity, R.array.peso, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        activity.spPeso.setAdapter(adapter);
    }

    public void inicializarSpinnerMeioDeTransporte() {


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(activity, R.array.meiosDeTransportes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        activity.spMeioDeTransporte.setAdapter(adapter);


    }

    public void enviarPedido() {


        validarDados();


        if (isDadosValidos) {

            exibirrConfirmacao();

        }
    }

    private void exibirrConfirmacao(){

        activity.atualizarValorDoPedido();

        AlertDialog.Builder msgBox = new AlertDialog.Builder(activity);
        msgBox.setTitle("Valor  da sua entrega");
        msgBox.setIcon(R.drawable.icon_drawable_total_amount);
        msgBox.setMessage("Deseja confirmar o valor R$ " + activity.editValorEstimado.getText().toString() + " atualizado?");

        msgBox.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                inserirPedido();
            }
        });

        msgBox.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                activity.editValorEstimado.findFocus();
            }
        });

        msgBox.show();

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void inserirPedido() {
        try {

            int checkeId = activity.rgVolume.getCheckedRadioButtonId();
            View radioButton = activity.rgVolume.findViewById(checkeId);
            int index = activity.rgVolume.indexOfChild(radioButton);

            Item item = new Item.ItemBuilder()
                    .setVolume(AppUtil.volumeDoPedido(index))
                    .setPeso(AppUtil.pesoEstimado(activity.spPeso.getSelectedItemPosition()))
                    .setQuantidade(AppUtil.quantidadeDeItensSelecionado(activity.spQuantidade.getSelectedItemPosition()))
                    .create();

            String ERRO_TIPO_USUARIO = "erro tipo usuario";

            String tipoDeUsuario = activity.sharedPreferences.getString(TIPO_DE_USUARIO, ERRO_TIPO_USUARIO);

            Pedido pedido = new Pedido();


            String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
            DocumentReference referenciaDoUsuario = ConfiguracaoFirebase.getFirebaseFirestore().document("/usuarios/pessoas/" + tipoDeUsuario.toLowerCase(Locale.ROOT) + "/" + email);
            DocumentReference referenciaDoMeioDeTransporte = ConfiguracaoFirebase.getFirebaseFirestore().document("/meioDeTransporte/" + activity.spMeioDeTransporte.getSelectedItem().toString());

            pedido.setReferenciaMeioDeTransporte(referenciaDoMeioDeTransporte);

            String numero = AppUtil.dataAtual().replace("/", "") + AppUtil.horaAtual().replace(":", "");


            Random random = new Random();
            int alet = random.nextInt(100);

            alet = alet + random.nextInt(100);

            numero = numero + alet;


            pedido.setNumero(numero);
            pedido.setData(AppUtil.dataAtual());
            pedido.setHora(AppUtil.horaAtual());
            // pedido.setColeta(activity.editEnderecoDeColeta.getText().toString());
            pedido.setColeta(activity.autoCompleteTextViewColeta.getText().toString());
            pedido.setEntrega(activity.autoCompleteTextViewEntrega.getText().toString());
            pedido.setLatLngColeta(activity.latLngColeta);
            pedido.setLatLngEntrega(activity.latLngEntrega);
            pedido.setValor(Float.parseFloat(activity.editValorEstimado.getText().toString()));


            pedido.setTransportador(null);

            pedido.setReferenciaUsuarioDoPedido(referenciaDoUsuario);

            pedido.setItem(item);

            pedido.setValor(Float.parseFloat(activity.editValorEstimado.getText().toString()));

            PedidoController pedidoController = new PedidoController();
            pedidoController.create(pedido);


            Toast.makeText(activity, "Pedido incluído com sucesso!", Toast.LENGTH_LONG).show();

            activity.trocarTela(TelaMainActivity.class);

        } catch (Exception exception) {

            Toast.makeText(activity, "Não foi possível enviar o pedido!", Toast.LENGTH_LONG).show();


            Log.i("erro", "Pedido", exception);
        }
    }


    public void validarDados() {



            if (activity.rbVolumePequeno.isChecked() || activity.rbVolumeMedio.isChecked() || activity.rbVolumeGrande.isChecked()) {

                if (!activity.autoCompleteTextViewColeta.getText().toString().isEmpty()) {

                    Localizar localizar = new Localizar(activity);

                    //garante q haja um ponto de coleta em Geopoint
                    if (activity.latLngColeta == null) {

                        activity.latLngColeta = localizar.converterEnderecoEmGeoPoint(activity.autoCompleteTextViewColeta.getText().toString());
                    }
                    if (activity.latLngColeta != null) {

                        if (!activity.autoCompleteTextViewEntrega.getText().toString().isEmpty()) {

                            //garante q haja um ponto de entrega em Geopoint
                            if (activity.latLngEntrega == null) {
                                activity.latLngEntrega = localizar.converterEnderecoEmGeoPoint(activity.autoCompleteTextViewEntrega.getText().toString());
                            }

                            if (activity.latLngEntrega != null) {

                                if (!(Float.parseFloat(activity.editValorEstimado.getText().toString()) == 0.0f)) {


                                    if (Validar.validarValorDecimal(activity.editValorEstimado.getText().toString())) {

                                        isDadosValidos = true;

                                    } else {
                                        Toast.makeText(activity.getApplicationContext(), Tag.VALOR_DECIMA_INVALIDO, Toast.LENGTH_LONG).show();

                                    }

                                } else {
                                    Toast.makeText(activity.getApplicationContext(), "Atualize o endereço, pois o valor está inválido!", Toast.LENGTH_LONG).show();
                                }

                            } else {
                                Toast.makeText(activity.getApplicationContext(), Tag.GEOLOCALIZACAO_ENTREGA_INVALIDO, Toast.LENGTH_LONG).show();
                            }

                        } else {
                            Toast.makeText(activity.getApplicationContext(), Tag.ENDERECO_ENTREGA_INVALIDO, Toast.LENGTH_LONG).show();
                        }

                    } else {
                        Toast.makeText(activity.getApplicationContext(), Tag.GEOLOCALIZACAO_COLETA_INVALIDO, Toast.LENGTH_LONG).show();
                    }


                } else {
                    Toast.makeText(activity.getApplicationContext(), Tag.ENDERECO_COLETA_INVALIDO, Toast.LENGTH_LONG).show();
                }

            } else {
                Toast.makeText(activity.getApplicationContext(), Tag.VOLUME_NAO_SELECIONADO, Toast.LENGTH_LONG).show();

            }

    }

    public void fragmentoParaLocalizarColetaNoMapa() {


        if (!fragmentoMapaColeta) {

            displayFragmentMapaColeta();


        } else {

            closeFragmentMapaColeta();


        }

    }

    private void closeFragmentMapaColeta() {

        FragmentManager fragmentManager = activity.getSupportFragmentManager();

        SelecionarPontoFragment fragment = (SelecionarPontoFragment) fragmentManager.findFragmentById(R.id.flLocalizarNoMapaColeta);

        if (fragment != null) {

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.remove(fragment).commit();

            fragmentoMapaColeta = false;
        }

        desbloquearScrollView();

    }

    private void displayFragmentMapaColeta() {

        double latitude;
        double longitude;

        if (activity.latLngColeta == null || activity.latLngColeta == null) {

            Localizar localizar = new Localizar(activity);

            activity.latLngColeta = localizar.localizarGeoPoint();

            latitude = activity.latLngColeta.getLatitude();
            longitude = activity.latLngColeta.getLongitude();

        } else {

            latitude = activity.latLngColeta.getLatitude();
            longitude = activity.latLngColeta.getLongitude();

        }

        SelecionarPontoFragment fragment = SelecionarPontoFragment.newInstance(latitude, longitude, FRAGMENTO_COLETA);

        FragmentManager fragmentManager = activity.getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.flLocalizarNoMapaColeta, fragment).addToBackStack(null).commit();

        fragmentoMapaColeta = true;

        bloquearScrollView();
    }

    private void displayFragmentMapaEntrega() {

        double latitude;
        double longitude;

        if (activity.latLngEntrega == null || activity.latLngEntrega == null) {

            Localizar localizar = new Localizar(activity);

            activity.latLngEntrega = localizar.localizarGeoPoint();

            latitude = activity.latLngEntrega.getLatitude();

            longitude = activity.latLngEntrega.getLongitude();

        } else {
            latitude = activity.latLngEntrega.getLatitude();
            longitude = activity.latLngEntrega.getLongitude();
        }

        SelecionarPontoFragment fragment = SelecionarPontoFragment.newInstance(latitude, longitude, FRAGMENTO_ENTREGA);

        FragmentManager fragmentManager = activity.getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.flLocalizarNoMapaEntrega, fragment).addToBackStack(null).commit();

        fragmentoMapaEntrega = true;

        bloquearScrollView();

    }

    public void fragmentoParaLocalizarEntregaNoMapa() {

        if (!fragmentoMapaEntrega) {

            displayFragmentMapaEntrega();


        } else {

            closeFragmentMapaEntrega();

        }

    }

    private void closeFragmentMapaEntrega() {

        FragmentManager fragmentManager = activity.getSupportFragmentManager();

        SelecionarPontoFragment fragment = (SelecionarPontoFragment) fragmentManager.findFragmentById(R.id.flLocalizarNoMapaEntrega);

        if (fragment != null) {

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.remove(fragment).commit();

            fragmentoMapaEntrega = false;
        }

        desbloquearScrollView();

    }


    private void bloquearScrollView() {


        ((LockableScrollView) activity.findViewById(R.id.svEnviar)).fullScroll(LockableScrollView.FOCUS_DOWN);

        ((LockableScrollView) activity.findViewById(R.id.svEnviar)).setScrollingEnabled(false);
    }

    private void desbloquearScrollView() {
        ((LockableScrollView) activity.findViewById(R.id.svEnviar)).setScrollingEnabled(true);
    }
}


