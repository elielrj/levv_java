package com.example.levv.support;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.levv.model.DAO.ConfiguracaoFirebase;
import com.example.levv.model.bo.usuario.Login;
import com.example.levv.model.tela.TelaCadastrarCelularModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class CriarUsuario {

    private final ConfiguracaoFirebase firebase;
    private final Context context;
    private final static String NAME_COLLECTION_DATABASE = "login";
    private final String TAG = "CRIAR_USUARIO: ";
    private ConfiguracoesSharedPreferences preferences;

    public CriarUsuario(Context context) {
        this.context = context;
        this.firebase = new ConfiguracaoFirebase();
        this.preferences = new ConfiguracoesSharedPreferences(context);
    }

    public void cadastrarUsuarioComEmailSenha(TelaCadastrarCelularModel model) {

        firebase.getAuth().createUserWithEmailAndPassword(
                model.getEmail(), model.getSenha())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){

                            preferences.inserirDados().putString(Textos.NOME, model.getNome());
                            preferences.inserirDados().putString(Textos.SOBRENOME, model.getSobrenome());
                            preferences.inserirDados().putString(Textos.EMAIL, model.getEmail());
                            preferences.inserirDados().putString(Textos.DDD, Validar.celularBuscarDdd(model.getCelularComDdd()));
                            preferences.inserirDados().putString(Textos.CELULAR, Validar.celularBuscarNumero(model.getCelularComDdd()));
                            preferences.inserirDados().putString(Textos.SENHA, model.getEmail());
                            preferences.inserirDados().putBoolean(ConfiguracoesSharedPreferences.CELULAR_CADASTRADO, true);
                            preferences.gravarDados();

                            firebase.getBancoDeDadosFirestore()
                                    .collection(NAME_COLLECTION_DATABASE)
                                    .document(model.getEmail())
                                    .set(new Login.LoginBuilder()
                                            .email(model.getEmail())
                                            .senha(model.getSenha())
                                            .celularComDdd(model.getCelularComDdd())
                                            .create()
                                    )
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Log.d(TAG, "inclusão de usuário com e-mail e senha com sucesso!");
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d(TAG, "inclusão de usuário com e-mail e senha falhou!");
                                        }
                                    });
                        }else {
                            String erroExcecao = "";
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthWeakPasswordException e) {
                                erroExcecao = Tag.SENHA_FRACA;
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                erroExcecao = Tag.EMAIL_INVALIDO;
                            } catch (FirebaseAuthUserCollisionException e) {
                                erroExcecao = Tag.EMAIL_JA_REGISTRADO;
                            } catch (Exception e) {
                                erroExcecao = Tag.ERRO_AO_CADASTRAR;
                                e.printStackTrace();
                            }
                            Toast.makeText(context, Tag.ERRO + erroExcecao, Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }



}
