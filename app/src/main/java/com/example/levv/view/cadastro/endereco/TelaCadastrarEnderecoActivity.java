package com.example.levv.view.cadastro.endereco;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.example.levv.controller.tela.TelaCadastrarEnderecoController;
import com.example.levv.R;
import com.example.levv.model.bo.endereco.Endereco;
import com.example.levv.support.Localizar;
import com.example.levv.support.Permissao;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;


public class TelaCadastrarEnderecoActivity extends AppCompatActivity implements View.OnClickListener {

    public Button buttonCadastrar;
    public Button buttonLimpar;
    public Button btncadLocalizar;
    public BootstrapEditText editLogradouro;
    public BootstrapEditText editNumero;
    public BootstrapEditText editComplemento;
    public BootstrapEditText editCep;
    public BootstrapEditText editBairro;
    public BootstrapEditText editCidade;
    public BootstrapEditText editEstado;
    public Bundle bundle;
    public RadioButton rbTipoDeMoradiaApt;
    public RadioButton rbTipoDeMoradiaCasa;
    public RadioButton rbTipoDeMoradiaLoja;

    private Localizar localizar;

    private Permissao permissao;
    private TelaCadastrarEnderecoController controller;

    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor dados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cadastrar_endereco);
        bundle = getIntent().getExtras();
        inicializarComponentes();
        this.controller = new TelaCadastrarEnderecoController(this);

        sharedPreferences = getSharedPreferences("datalevv", Context.MODE_PRIVATE);
        dados = sharedPreferences.edit();

        this.permissao = new Permissao(this);
        this.permissao.isPermissaoParaLocalizar();

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    private void inicializarComponentes() {
        //ID
        btncadLocalizar = findViewById(R.id.btnCadLocalizar);
        buttonCadastrar = findViewById(R.id.btnCadEnd);
        buttonLimpar = findViewById(R.id.btnCadLimpar);
        rbTipoDeMoradiaApt = findViewById(R.id.rbTipoDeMoradiaApt);
        rbTipoDeMoradiaCasa = findViewById(R.id.rbTipoDeMoradiaCasa);
        rbTipoDeMoradiaLoja = findViewById(R.id.rbTipoDeMoradiaLoja);
        editLogradouro = findViewById(R.id.editLogradouro);
        editNumero = findViewById(R.id.editCadEndNumero);
        editComplemento = findViewById(R.id.editComplemento);
        editCep = findViewById(R.id.editCep);
        formatarCep(editCep);

        editBairro = findViewById(R.id.editBairro);
        editCidade = findViewById(R.id.editCidade);
        editEstado = findViewById(R.id.editEstado);

        //OUVIR
        btncadLocalizar.setOnClickListener(this);
        buttonCadastrar.setOnClickListener(this);
        buttonLimpar.setOnClickListener(this);

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View v) {
        if (btncadLocalizar.getId() == v.getId()) {

            this. localizar = new Localizar(this);

            Endereco endereco = localizar.buscarEndereco();

            editLogradouro.setText(endereco.getLogradouro());
            editNumero.setText(endereco.getNumero());
            editComplemento.setText(endereco.getComplemento());
            editBairro.setText(endereco.getBairro().getNome());
            editCidade.setText(endereco.getBairro().getCidade().getNome());
            editEstado.setText(endereco.getBairro().getCidade().getEstado().getNome());
            editCep.setText(endereco.getCep());

        } else if (buttonCadastrar.getId() == v.getId()) {
            controller.cadastrar();
        } else if (buttonLimpar.getId() == v.getId()) {
            controller.liparComponentes();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        localizar.stopLocation();
    }

    private void formatarCep(EditText editTextCep) {
        SimpleMaskFormatter maskFormatterCep = new SimpleMaskFormatter("NNNNN-NNN");
        MaskTextWatcher maskTextWatcherCep = new MaskTextWatcher(editTextCep, maskFormatterCep);
        editTextCep.addTextChangedListener(maskTextWatcherCep);
    }
}