package com.example.levv.view.cadastro.usuario;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.example.levv.R;
import com.example.levv.controller.tela.TelaCadastrarUsuarioController;
import com.example.levv.model.bo.usuario.Arquivo;
import com.example.levv.view.cadastro.usuario.transportador.CadastrarUsuarioEntregadorDocumentoFragment;
import com.example.levv.view.cadastro.usuario.transportador.CadastrarUsuarioEntregadorMeioTransporteFragment;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

public class TelaCadastrarUsuarioActivity extends AppCompatActivity implements View.OnClickListener, CadastrarUsuarioEntregadorDocumentoFragment.OnFragmentInteractionListenerMeioDeTransporte {

    public Button btnUserCad, btnUserLimpar;
    public BootstrapEditText editNome, editSobrenome, editCpf, editDataNascimento;
    public RadioButton rbCliente, rbEntregador, rbLojista;
    public RadioGroup radioGroupUsuario;
    //public Bundle bundle;
    public Fragment fragment;
    //Controller
    public TelaCadastrarUsuarioController controller;
    //Fragment Lojista
    public boolean isFragmentDisplayedLojista = false;
    public boolean isFragmentDisplayedEntregador = false;


    public ImageView imgIdentidade;
    public ImageView imgCrlv;

    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor dados;

    // Saved instance state keys.
    static final String STATE_FRAGMENT = "state_of_fragment";
    static final String STATE_CHOICE = "user_choice";

    // Initialize the radio button choice to the default.
    public int mRadioButtonChoice = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cadastrar_usuario);
        //this.bundle = getIntent().getExtras();


        sharedPreferences = getSharedPreferences("datalevv", Context.MODE_PRIVATE);
        dados = sharedPreferences.edit();

        buscarComponentes();
        this.controller = new TelaCadastrarUsuarioController(this);



        if (savedInstanceState != null) {
            isFragmentDisplayedEntregador = savedInstanceState.getBoolean(STATE_FRAGMENT);
            mRadioButtonChoice = savedInstanceState.getInt(STATE_CHOICE);

        }
    }


    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    private void buscarComponentes() {
        //BUTTUNS
        this.btnUserCad = findViewById(R.id.BtnUserCad);
        this.btnUserLimpar = findViewById(R.id.btnUserLimpar);
        //OUVIR
        this.btnUserLimpar.setOnClickListener(this);
        this.btnUserCad.setOnClickListener(this);
        //TextViews
        this.editNome = findViewById(R.id.editNomeCadUser);
        this.editSobrenome = findViewById(R.id.editSobrenomeCadUser);
        this.editDataNascimento = findViewById(R.id.editDtNascCadUser);
        formatarDataDeNascimento(editDataNascimento);

        this.editCpf = findViewById(R.id.editCpfCadUser);
        formatarCpf(editCpf);

        //Set's
        this.editNome.setText(this.sharedPreferences.getString("nome", "Nome"));
        this.editSobrenome.setText(this.sharedPreferences.getString("sobrenome", "Sobrenome"));
        this.editDataNascimento.setText("");
        this.editCpf.setText("");

        //Radio Buttons
        this.rbCliente = findViewById(R.id.rbCliente);
        this.rbEntregador = findViewById(R.id.rbEntregador);
        this.rbLojista = findViewById(R.id.rbLojista);

        radioGroupUsuario = findViewById(R.id.radio_group_usuario);


        //ouvir
        this.rbEntregador.setOnClickListener(this);
        this.rbCliente.setOnClickListener(this);
        this.rbLojista.setOnClickListener(this);


    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View v) {
        if (this.btnUserCad.getId() == v.getId()) {

            if (controller.radioButtonEstaMarcado())
                this.controller.cadastrarUsuario();
            else
                Toast.makeText(this, "Selecione um tipo de usuário!", Toast.LENGTH_SHORT).show();

        } else if (this.btnUserLimpar.getId() == v.getId()) {
            this.controller.limparComponentes();
        }
        if (rbLojista.getId() == v.getId()) {
            //0 - LOJISTA | 1 - CLIENTE | 2 - ENTREGADOR
            controller.displayFragment(0);
        }
        if (rbCliente.getId() == v.getId()) {

            controller.displayFragment(1);
        }
        if (rbEntregador.getId() == v.getId()) {

            controller.displayFragment(2);
        }
    }

    @Override
    public void onRadioButtonChoice(int choice) {

        if (choice == 0) {

            if (mRadioButtonChoice == 1 || mRadioButtonChoice == 2) {
                closeFragment();
            }

        } else if (choice == 1 || choice == 2) {

            if (mRadioButtonChoice != 1 && mRadioButtonChoice != 2) {
                displayFragment();
            }

        } else if (choice == 3) {
            //nada faz
        }

        mRadioButtonChoice = choice;
    }

    private void buscaVeiculo() {
        FragmentManager fragmentManager = getSupportFragmentManager();

        CadastrarUsuarioEntregadorMeioTransporteFragment fragment = (CadastrarUsuarioEntregadorMeioTransporteFragment) fragmentManager
                .findFragmentById(R.id.tela_cadastro_usuario_meio_transp);

        fragment.getMarca();


    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the state of the fragment (true=open, false=closed).
        savedInstanceState.putBoolean(STATE_FRAGMENT, isFragmentDisplayedEntregador);
        savedInstanceState.putInt(STATE_CHOICE, mRadioButtonChoice);
        super.onSaveInstanceState(savedInstanceState);
    }

    public void displayFragment() {
        // Instantiate the fragment.
        CadastrarUsuarioEntregadorMeioTransporteFragment fragment =
                CadastrarUsuarioEntregadorMeioTransporteFragment.newInstance();
        // Get the FragmentManager and start a transaction.
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();

        // Add the SimpleFragment.
        fragmentTransaction.add(R.id.tela_cadastro_usuario_meio_transp,
                fragment).addToBackStack(null).commit();


        // Set boolean flag to indicate fragment is open.
        isFragmentDisplayedEntregador = true;
    }

    public void closeFragment() {
        // Get the FragmentManager.
        FragmentManager fragmentManager = getSupportFragmentManager();
        // Check to see if the fragment is already showing.
        CadastrarUsuarioEntregadorMeioTransporteFragment fragment = (CadastrarUsuarioEntregadorMeioTransporteFragment) fragmentManager
                .findFragmentById(R.id.tela_cadastro_usuario_meio_transp);
        if (fragment != null) {
            // Create and commit the transaction to remove the fragment.
            FragmentTransaction fragmentTransaction =
                    fragmentManager.beginTransaction();
            fragmentTransaction.remove(fragment).commit();
        }

        // Set boolean flag to indicate fragment is closed.
        isFragmentDisplayedEntregador = false;
        mRadioButtonChoice = 3;
    }


    private void formatarDataDeNascimento(BootstrapEditText editDataNascimento) {
        SimpleMaskFormatter maskFormatterDtNasc = new SimpleMaskFormatter("NN/NN/NNNN");
        MaskTextWatcher maskTextWatcherDtNasc = new MaskTextWatcher(editDataNascimento, maskFormatterDtNasc);
        editDataNascimento.addTextChangedListener(maskTextWatcherDtNasc);
    }

    private void formatarCpf(EditText editCpf) {
        SimpleMaskFormatter maskFormatterDtNasc = new SimpleMaskFormatter("NNN.NNN.NNN-NN");
        MaskTextWatcher maskTextWatcherDtNasc = new MaskTextWatcher(editCpf, maskFormatterDtNasc);
        editCpf.addTextChangedListener(maskTextWatcherDtNasc);
    }
}