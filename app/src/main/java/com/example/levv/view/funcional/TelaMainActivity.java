package com.example.levv.view.funcional;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.levv.R;
import com.example.levv.controller.tela.TelaMainActivityController;
import com.example.levv.controller.tela.TelaProdutoAcompanharController;
import com.example.levv.controller.usuario.LoginController;
import com.example.levv.model.DAO.ConfiguracaoFirebase;
import com.example.levv.model.DAO.usuario.LoginDAO;
import com.example.levv.model.bo.usuario.Login;
import com.example.levv.support.AppUtil;
import com.example.levv.support.Localizar;
import com.example.levv.support.Tag;
import com.example.levv.view.cadastro.endereco.TelaCadastrarEnderecoActivity;
import com.example.levv.view.pedido.acompanhar.TelaProdutoAcompanharActivity;
import com.example.levv.view.pedido.entregar.TelaProdutoEntregarActivity;
import com.example.levv.view.pedido.enviar.TelaProdutoEnviarActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.AppBarLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class TelaMainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TIPO_DE_USUARIO = "tipoDeUsuario";
    private static final String CLIENTE = "Cliente";
    private static final String LOJISTA = "Lojista";
    private static final String TRANSPORTADOR = "Transportador";
    private static final String ADMINISTRADOR = "Administrador";

    public Button btnEntregarProduto;
    public Button btnEnviarProduto;
    public Button btnAcompanharProduto;

    private TelaMainActivityController controller;
    private String tipoDeUsuarioLogado;

    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor dados;

    // private Localizar localizar;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_main);

        this.controller = new TelaMainActivityController(this);

        sharedPreferences = getSharedPreferences(AppUtil.SHARED_PREFERENCES_NAME_APP, Context.MODE_PRIVATE);
        dados = sharedPreferences.edit();


        // localizar = new Localizar(this);

        if (sharedPreferences.getString(TIPO_DE_USUARIO, "") == "") {

            buscarTipoDeUsuario();
            //Toast.makeText(this, "TelaMainActivity: Tipo User: " + tipoDeUsuarioLogado + " Shared: " + sharedPreferences.getString(TIPO_DE_USUARIO, "null"), Toast.LENGTH_LONG).show();

        }


    }

    @Override
    public void onBackPressed() {
        finish();
    }


    @Override
    public void onClick(View v) {

        if (v.getId() == btnEntregarProduto.getId()) {

            if (tipoDeUsuarioLogado.equalsIgnoreCase(TRANSPORTADOR) || tipoDeUsuarioLogado.equalsIgnoreCase(ADMINISTRADOR)) {
                controller.entregarProduto();
            } else {
                Toast.makeText(this, "Você não está cadastrado como um usuário transportador!\n Altere seu cadastro e seja um entregador", Toast.LENGTH_LONG).show();
            }

        } else if (v.getId() == btnEnviarProduto.getId()) {

            controller.entregarEnviarProduto();

        } else if (v.getId() == btnAcompanharProduto.getId()) {

            controller.entregarAcompanhar();
        }
    }


    //CRIA MENU
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        tipoDeUsuarioLogado = sharedPreferences.getString(TIPO_DE_USUARIO, "");


        if (tipoDeUsuarioLogado == "") {

            buscarTipoDeUsuario();

            tipoDeUsuarioLogado = sharedPreferences.getString(TIPO_DE_USUARIO, "");
        }


        switch (tipoDeUsuarioLogado) {

            case CLIENTE:
                getMenuInflater().inflate(R.menu.menu_cliente, menu);
                break;
            case TRANSPORTADOR:
                getMenuInflater().inflate(R.menu.menu_transportador, menu);
                break;
            case LOJISTA:
                getMenuInflater().inflate(R.menu.menu_lojista, menu);
                break;
            case ADMINISTRADOR:
                getMenuInflater().inflate(R.menu.menu_administrador, menu);
                break;
        }


        return true;

    }


    //RECEBE O ID DO ITEM SELECIONADO
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        switch (tipoDeUsuarioLogado) {

            case CLIENTE: {

                switch (id) {

                    case R.id.action_sair_cliente:
                        controller.deslogarUsuario();
                        break;
                    case R.id.action_endereco_cliente: {

                        Intent intent = new Intent(TelaMainActivity.this, TelaCadastrarEnderecoActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    break;

                    case R.id.action_acompanhar_entrega_cliente: {

                        Intent intent = new Intent(TelaMainActivity.this, TelaProdutoAcompanharActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    break;
                    case R.id.action_enviar_cliente: {
                        Intent intent = new Intent(TelaMainActivity.this, TelaProdutoEnviarActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    break;
                }

            }
            break;
            case LOJISTA: {

                switch (id) {
                    case R.id.action_sair_lojista: {
                        controller.deslogarUsuario();
                    }
                    break;
                    case R.id.action_endereco_lojista: {
                        Intent intent = new Intent(TelaMainActivity.this, TelaCadastrarEnderecoActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    break;
                    case R.id.action_acompanhar_entrega_lojista: {
                        Intent intent = new Intent(TelaMainActivity.this, TelaProdutoAcompanharActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    break;
                    case R.id.action_enviar_lojista: {
                        Intent intent = new Intent(TelaMainActivity.this, TelaProdutoEnviarActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    break;
                }
            }
            break;
            case TRANSPORTADOR: {

                switch (id) {
                    case R.id.action_sair_entregador: {
                        controller.deslogarUsuario();
                    }
                    break;
                    case R.id.action_endereco_entregador: {
                        Intent intent = new Intent(TelaMainActivity.this, TelaCadastrarEnderecoActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    break;
                    case R.id.action_acompanhar_entrega_entregador: {
                        Intent intent = new Intent(TelaMainActivity.this, TelaProdutoAcompanharActivity.class);
                        startActivity(intent);
                        finish();

                    }
                    break;
                    case R.id.action_enviar_entregador: {
                        Intent intent = new Intent(TelaMainActivity.this, TelaProdutoEnviarActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    break;
                    case R.id.action_entregar_entregador: {
                        Intent intent = new Intent(TelaMainActivity.this, TelaProdutoEntregarActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    break;
                }
            }
            break;

            case ADMINISTRADOR: {


                switch (id) {
                    case R.id.action_sair_administrador: {
                        controller.deslogarUsuario();
                    }
                    break;
                    case R.id.action_endereco_administrador: {
                        Intent intent = new Intent(TelaMainActivity.this, TelaCadastrarEnderecoActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    break;
                    case R.id.action_acompanhar_entrega_administrador: {
                        Intent intent = new Intent(TelaMainActivity.this, TelaProdutoAcompanharActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    break;
                    case R.id.action_entregar_administrador: {
                        Intent intent = new Intent(TelaMainActivity.this, TelaProdutoEntregarActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    break;
                    case R.id.action_enviar_administrador: {
                        Intent intent = new Intent(TelaMainActivity.this, TelaProdutoEnviarActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    break;
                }


                break;
            }

        }

        return super.onOptionsItemSelected(item);

    }

    private void buscarTipoDeUsuario() {


        FirebaseFirestore.getInstance()
                .document(LoginDAO.NAME_COLLECTION_DATABASE + "/" + FirebaseAuth.getInstance().getCurrentUser().getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {

                            DocumentSnapshot document = task.getResult();

                            if (document.exists()) {

                                Log.d(Tag.TELAENVIARPRODUTO, "Busca com sucesso!");

                                Login login = document.toObject(Login.class);

                                login.getReferenciaDaPessoa()
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if (task.isSuccessful()) {

                                                    DocumentSnapshot document = task.getResult();

                                                    if (document.exists()) {

                                                        Log.d(Tag.TELAENVIARPRODUTO, "Busca de referencia da pessoa no login com sucesso!");

                                                        String tipo = (String) document.getData().get(TIPO_DE_USUARIO);
                                                        dados.putString(TIPO_DE_USUARIO, tipo);
                                                        dados.apply();


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