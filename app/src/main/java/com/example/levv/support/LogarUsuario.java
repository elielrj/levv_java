package com.example.levv.support;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.levv.model.DAO.ConfiguracaoFirebase;
import com.example.levv.model.bo.usuario.Login;
import com.example.levv.model.tela.TelaCadastrarCelularModel;
import com.example.levv.view.cadastro.usuario.TelaCadastrarUsuarioActivity;
import com.example.levv.view.funcional.TelaMainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.firestore.QuerySnapshot;

public class LogarUsuario implements Runnable {

    private final ConfiguracaoFirebase firebase;
    private final Context context;
    private final static String NAME_COLLECTION_DATABASE = "login";
    private ConfiguracoesSharedPreferences preferences;
    private TelaCadastrarCelularModel model;

    public LogarUsuario(Context context) {
        this.context = context;
        this.firebase = new ConfiguracaoFirebase();
        this.preferences = new ConfiguracoesSharedPreferences(context);
        Thread thread = new Thread();
        thread.start();
    }

    public synchronized void logarComEmailSenha(TelaCadastrarCelularModel model) {

        firebase.getAuth().signInWithEmailAndPassword(
                model.getEmail(), model.getSenha()
        )
                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("senha: ", model.getSenha());

                            buscarDadosNoBanco();

                            Log.d("logado", "teste");
                            //todo buscar ainda dados de cadastro do user e endereço
                            //buscarTipoDeUsuario();
                            // preferences.inserirDados().putString(Textos.NOME, lo);
                            //preferences.inserirDados().putString(Textos.SOBRENOME, model.getSobrenome());


                            trocarDeTela();

                            Toast.makeText(context, "Login efetuado com sucesso!", Toast.LENGTH_SHORT).show();
                        } else {
                            String erroExcessao = "";
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthInvalidUserException e) {
                                erroExcessao = "Email não cadastrado";
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                erroExcessao = "Senha inválida" + model.getSenha().toString();
                            } catch (Exception e) {
                                erroExcessao = Tag.ERRO_AO_CADASTRAR;
                                e.printStackTrace();
                            }
                            Toast.makeText(context, erroExcessao, Toast.LENGTH_SHORT).show();
                        }
                    }
                });



    }

    private synchronized void buscarDadosNoBanco(){

        preferences.inserirDados().putString(Textos.EMAIL, model.getEmail());
        preferences.inserirDados().putString(Textos.SENHA, model.getSenha());

        firebase.getBancoDeDadosFirestore()
                .collection(NAME_COLLECTION_DATABASE)
                .whereEqualTo(model.getEmail(), true)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Login login = new Login();
                            login = (Login) task.getResult().toObjects(TelaCadastrarCelularModel.class);
                            preferences.inserirDados().putString(Textos.DDD, login.getDdd());
                            preferences.inserirDados().putString(Textos.CELULAR, login.getNumeroCelular());
                            preferences.inserirDados().putBoolean(ConfiguracoesSharedPreferences.CELULAR_CADASTRADO, true);
                        }
                    }
                });


        preferences.gravarDados();
    }

    @Override
    public void run() {



    }

    public void trocarDeTela() {
        Intent intent = new Intent(context, TelaMainActivity.class);
        //intent.putExtras();
        context.startActivity(intent);
        //context.finish();
    }
}
