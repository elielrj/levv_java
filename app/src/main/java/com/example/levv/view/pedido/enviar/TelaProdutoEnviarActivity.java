package com.example.levv.view.pedido.enviar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.example.levv.R;
import com.example.levv.support.AppUtil;
import com.example.levv.support.AutoCompletar;
import com.example.levv.support.Permissao;
import com.example.levv.view.funcional.TelaMainActivity;
import com.example.levv.view.localizar.SelecionarPontoFragment;
import com.example.levv.controller.tela.TelaProdutoEnviarController;
import com.example.levv.model.DAO.ConfiguracaoFirebase;
import com.example.levv.support.Tag;
import com.example.levv.model.bo.endereco.Bairro;
import com.example.levv.model.bo.endereco.Cidade;
import com.example.levv.model.bo.endereco.Endereco;
import com.example.levv.model.bo.endereco.Estado;
import com.example.levv.support.Localizar;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.GeoPoint;

import java.text.DecimalFormat;


public class TelaProdutoEnviarActivity extends AppCompatActivity implements View.OnClickListener,
        SelecionarPontoFragment.OnFragmentInteractionListenerMapa {

    public static final String TIPO_DE_USUARIO = "tipoDeUsuario";
    public static final String CLIENTE = "Cliente";
    public static final String LOJISTA = "Lojista";
    public static final String TRANSPORTADOR = "transportador";
    public static final String ADMINISTRADOR = "Administrador";

    public Spinner spQuantidade;
    public Spinner spPeso;
    public Spinner spVolume;
    public Spinner spMeioDeTransporte;

    //public AutoCompleteTextView editDescricaoDoItem;

    // public BootstrapEditText editEnderecoDeColeta;
    //public BootstrapEditText editEnderecoDeEntrega;
    public BootstrapEditText editValorEstimado;

    public AutoCompleteTextView autoCompleteTextViewColeta;
    public AutoCompleteTextView autoCompleteTextViewEntrega;

    public Button btnEnviarPedido;
    public Button btnLimpar;
    public Button btnCalcular;

    //IMG LOC NO MAPA
    public ImageView imgLocalizarColetaNoMapa;
    public ImageView imgLocalizarEntregaNoMapa;
    //img loc atual
    public ImageView imgLocalizarColeta;
    public ImageView imgLocalizarEntrega;

    private boolean isEnderecoEntrega = false;
    private boolean isEnderecoColeta = false;
    private static final int COLETA = 0;
    private static final int ENTREGA = 1;
    private int escolha = 2;

    public RadioButton rbVolumePequeno;
    public RadioButton rbVolumeMedio;
    public RadioButton rbVolumeGrande;
    public RadioGroup rgVolume;


    public GeoPoint latLngColeta;
    public GeoPoint latLngEntrega;

    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor dados;

    private TelaProdutoEnviarController controller;

    public ScrollView scrollView;
    private Permissao permissao;


    private AutoCompletar autoCompletarColeta;
    private AutoCompletar autoCompletarEntrega;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_produto_enviar);

        sharedPreferences = getSharedPreferences(AppUtil.SHARED_PREFERENCES_NAME_APP, Context.MODE_PRIVATE);
        dados = sharedPreferences.edit();

        controller = new TelaProdutoEnviarController(this);

        this.autoCompletarColeta = new AutoCompletar(this, R.id.autoCompletarColeta);
        this.autoCompletarEntrega = new AutoCompletar(this, R.id.autoCompletarEntrega);
        this.permissao = new Permissao(this);
        this.permissao.isPermissaoParaLocalizar();
        //this.permissao.isPermissaoParaLerEEscrever();

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(TelaProdutoEnviarActivity.this, TelaMainActivity.class));
    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View v) {

        if (v.getId() == btnEnviarPedido.getId()) {

            controller.enviarPedido();

        } else if (v.getId() == imgLocalizarColeta.getId()) {

            Localizar localizar = new Localizar(this);

            autoCompleteTextViewColeta.setText(localizar.buscarEnderecoCompleto());

            latLngColeta = localizar.localizarGeoPoint();

            editValorEstimado.setText(String.valueOf(atualizarValorDoPedido()));


        } else if (v.getId() == imgLocalizarEntrega.getId()) {

            Localizar localizar = new Localizar(this);

            autoCompleteTextViewEntrega.setText(localizar.buscarEnderecoCompleto());

            latLngEntrega = localizar.localizarGeoPoint();

            editValorEstimado.setText(String.valueOf(atualizarValorDoPedido()));


        } else if (v.getId() == btnLimpar.getId()) {


            //editEnderecoDeColeta.getText().clear();
            autoCompleteTextViewColeta.getText().clear();
            autoCompleteTextViewEntrega.getText().clear();
            editValorEstimado.getText().clear();
            rbVolumeGrande.setChecked(false);
            rbVolumeMedio.setChecked(false);
            rbVolumePequeno.setChecked(false);

        } else if (v.getId() == imgLocalizarColetaNoMapa.getId()) {

            controller.fragmentoParaLocalizarColetaNoMapa();

        } else if (v.getId() == imgLocalizarEntregaNoMapa.getId()) {

            controller.fragmentoParaLocalizarEntregaNoMapa();

        } else if(v.getId() == btnCalcular.getId()){
            editValorEstimado.setText(String.valueOf(atualizarValorDoPedido()));
        }
    }


    private DocumentReference usuarioDoPedido(String tipoDeUsuario) {


        String email = sharedPreferences.getString("email", "email erro");
        DocumentReference referenciaUser;

        switch (tipoDeUsuario) {
            case CLIENTE: {
                referenciaUser = ConfiguracaoFirebase.getFirebaseFirestore().document("usuarios/pessoas/cliente/" + email);
            }
            break;
            case LOJISTA: {
                referenciaUser = ConfiguracaoFirebase.getFirebaseFirestore().document("usuarios/pessoas/lojista/" + email);
            }
            break;
            case TRANSPORTADOR: {
                referenciaUser = ConfiguracaoFirebase.getFirebaseFirestore().document("usuarios/pessoas/transportador/" + email);
            }
            break;
            case ADMINISTRADOR: {
                referenciaUser = ConfiguracaoFirebase.getFirebaseFirestore().document("usuarios/pessoas/administrador/" + email);
            }
            break;

            default:
                throw new IllegalStateException("Unexpected value: " + tipoDeUsuario);
        }
        return referenciaUser;
    }



    public void trocarTela(Class<?> classeJava) {
        Intent intent = new Intent(TelaProdutoEnviarActivity.this, classeJava);
        Bundle bundle = new Bundle();

        bundle.putString("tela", "EnviarPedido");

        startActivity(intent);
        finish();
    }


    public void mascaraValorDecimal() {


        SimpleMaskFormatter maskFormatterValor = new SimpleMaskFormatter("NNN,NN");
        MaskTextWatcher maskTextWatcherValor = new MaskTextWatcher(editValorEstimado, maskFormatterValor);
        editValorEstimado.addTextChangedListener(maskTextWatcherValor);

    }


    @Override
    public void selecionarGeoLocalizacao(GeoPoint geoPoint, String nomeDoTipoDeFragmento) {

        Localizar localizar = new Localizar(this);


        String enderecoCompleto = localizar.converterLatLngEmEnderecoCompleto(geoPoint);

        if (nomeDoTipoDeFragmento.equalsIgnoreCase(TelaProdutoEnviarController.FRAGMENTO_COLETA)) {

            latLngColeta = geoPoint;

            Toast.makeText(this, Tag.LOCALIZAO_COLETA_SUCESS, Toast.LENGTH_LONG).show();

            Log.d("COLETA ENVIAR", "LAT " + geoPoint.getLatitude() + "LOG " + geoPoint.getLongitude());

            controller.fragmentoParaLocalizarColetaNoMapa();

            //editEnderecoDeColeta.setText(enderecoCompleto);
            autoCompleteTextViewColeta.setText(enderecoCompleto);
            editValorEstimado.setText(String.valueOf(atualizarValorDoPedido()));

        } else if (nomeDoTipoDeFragmento.equalsIgnoreCase(TelaProdutoEnviarController.FRAGMENTO_ENTREGA)) {

            latLngEntrega = geoPoint;

            Toast.makeText(this, Tag.LOCALIZAO_ENTREGA_SUCESS, Toast.LENGTH_LONG).show();

            Log.d("ENTREGA ENVIAR", "LAT " + geoPoint.getLatitude() + "LOG " + geoPoint.getLongitude());

            controller.fragmentoParaLocalizarEntregaNoMapa();

            autoCompleteTextViewEntrega.setText(enderecoCompleto);
            editValorEstimado.setText(String.valueOf(atualizarValorDoPedido()));
        }
    }

    public float atualizarValorDoPedido() {
        if (!autoCompleteTextViewColeta.getText().toString().isEmpty()) {
            if(!autoCompleteTextViewEntrega.getText().toString().isEmpty()) {
                if (!autoCompleteTextViewColeta.getText().toString().equalsIgnoreCase(autoCompleteTextViewEntrega.getText().toString())) {

                    if(rbVolumePequeno.isChecked() || rbVolumeMedio.isChecked() || rbVolumeGrande.isChecked()) {

                        int checkeId = rgVolume.getCheckedRadioButtonId();
                        View radioButton = rgVolume.findViewById(checkeId);
                        int index = rgVolume.indexOfChild(radioButton);


                        int quantidade = AppUtil.quantidadeDeItensSelecionado(spQuantidade.getSelectedItemPosition());
                        int peso = AppUtil.pesoEstimado(spPeso.getSelectedItemPosition());
                        int volume = AppUtil.volumeDoPedido(index);

                        return calcularValorDoPedido(quantidade, peso, volume);

                    }else{
                        Toast.makeText(getBaseContext(), "Selecione um tipo de volume!", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(getBaseContext(), "O endereço de coleta e entrega não podem ser iguais!", Toast.LENGTH_LONG).show();
                }

            } else {
                Toast.makeText(getBaseContext(), "Endereço de entrega vazio!", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getBaseContext(), "Endereço de coleta vazio!", Toast.LENGTH_LONG).show();
        }
        return 0.00f;
    }

    private float calcularValorDoPedido(int qtd, int peso, int vol) {

        //latLngColeta = localizar.converterEnderecoEmLatitudeLongitude(editEnderecoDeColeta.getText().toString());
        Localizar localizar = new Localizar(this);

        latLngColeta = localizar.converterEnderecoEmGeoPoint(autoCompleteTextViewColeta.getText().toString());

        latLngEntrega = localizar.converterEnderecoEmGeoPoint(autoCompleteTextViewEntrega.getText().toString());

        double distancia =
                calcularDistancia(
                        latLngColeta.getLatitude(),
                        latLngColeta.getLongitude(),
                        latLngEntrega.getLatitude(),
                        latLngEntrega.getLongitude()
                );

        double y = ((1.6 * distancia) + 5);


        double valorFinal = y * ((Float.parseFloat(String.valueOf(vol)) / 10) + qtd + (Float.parseFloat(String.valueOf(peso)) / 100));

        return formatarValor(valorFinal);
    }

    private double calcularDistancia(double lat1, double lng1, double lat2, double lng2) {
        //double earthRadius = 3958.75;//miles
        double earthRadius = 6371;//kilometers
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);
        double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                * Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2));
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double dist = earthRadius * c;

        return dist * 1; //em Km
    }

    private float formatarValor(Double valor) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String valorFormatado = decimalFormat.format(valor);
        valorFormatado = valorFormatado.replace(",", ".");
        float formatado = Float.parseFloat(valorFormatado);
        return formatado;
    }

    public boolean rotaEstaPreenchida(){
        if(!autoCompleteTextViewColeta.getText().toString().isEmpty() && !autoCompleteTextViewEntrega.getText().toString().isEmpty()){
            return true;
        }
        return false;
    }

}