package com.example.levv.view.cadastro.celular;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.levv.R;
import com.example.levv.controller.tela.TelaCadastrarCelularController;
import com.example.levv.model.DAO.usuario.LoginDAO;
import com.example.levv.model.bo.usuario.Login;
import com.example.levv.support.AppUtil;
import com.example.levv.support.Tag;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class TelaCadastrarCelularActivity extends AppCompatActivity
        implements View.OnClickListener,
        CadastrarCelularLoginFragment.OnFragmentInteractionListenerLogin,
        CadastrarCelularFragment.OnFragmentInteractionListenerCadastrar {

    //TELA ACTIVITY
    public Button btnCadastrarLogar;
    public Button btnLimparCelNome;
    public Bundle bundle;
    public TextView txtCadastrarLogar;

    //Fragment ativo
    public boolean isLoginFragment = false;
    public boolean isCadastrarFragment = false;

    //CONTROLLER
    private TelaCadastrarCelularController controller;

    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor dados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cadastrar_celular);
        this.controller = new TelaCadastrarCelularController(this);
        inicializarComponentes();

        sharedPreferences = getSharedPreferences(AppUtil.SHARED_PREFERENCES_NAME_APP, Context.MODE_PRIVATE);
        dados = sharedPreferences.edit();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    private void inicializarComponentes() {
        //ID
        txtCadastrarLogar = findViewById(R.id.txtCadastrarLogar);
        btnCadastrarLogar = findViewById(R.id.btnCadastrarLogar);
        btnLimparCelNome = findViewById(R.id.btnLimpar);
        //OUVIR
        txtCadastrarLogar.setOnClickListener(this);
        btnCadastrarLogar.setOnClickListener(this);
        btnLimparCelNome.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (btnCadastrarLogar.getId() == v.getId()) {
            controller.enviar();
        } else if (btnLimparCelNome.getId() == v.getId()) {
            controller.liparComponentes();
        } else if (txtCadastrarLogar.getId() == v.getId()) {
            controller.displayFragment();
        }
    }

    @Override
    public void onCadastrar() {
    }

    //todo remover
    public void buscarTipoDeUsuario() {

        FirebaseFirestore.getInstance()
                .document(LoginDAO.NAME_COLLECTION_DATABASE + "/" + FirebaseAuth.getInstance().getCurrentUser().getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {

                            DocumentSnapshot document = task.getResult();

                            if (document.exists()) {

                                Log.d(Tag.TELALOGIN, "Busca com sucesso!");

                                Login login = document.toObject(Login.class);

                                login.getReferenciaDaPessoa()
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if (task.isSuccessful()) {

                                                    DocumentSnapshot document = task.getResult();

                                                    if (document.exists()) {


                                                        String tipo = (String) document.getData().get(AppUtil.TIPO_DE_USUARIO);
                                                        dados.putString(AppUtil.TIPO_DE_USUARIO, tipo);
                                                        dados.apply();

                                                        Log.d(Tag.TELALOGIN, "Busca de referencia da pessoa no login com sucesso, tipo de usuário: " + sharedPreferences.getString(AppUtil.TIPO_DE_USUARIO, "erro ao buscar tipo de user"));

                                                    } else {
                                                        Log.w(Tag.TELAENVIARPRODUTO, "Busca de referencia da pessoa no login falhou!");
                                                    }

                                                } else {
                                                    Log.w(Tag.LOGINDAO, "Não foi possível buscar referencia da pessoa no login!", task.getException());
                                                }
                                            }
                                        });


                            } else {
                                Log.w(Tag.TELAENVIARPRODUTO, "Busca falhou!");
                            }

                        } else {
                            Log.w(Tag.LOGINDAO, "Não foi possível buscar!", task.getException());
                        }
                    }
                });


    }
}