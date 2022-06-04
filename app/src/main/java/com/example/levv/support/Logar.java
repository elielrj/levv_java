package com.example.levv.support;

import android.content.Context;

import com.example.levv.model.DAO.ConfiguracaoFirebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Logar {

    private Context context;
    private FirebaseAuth autenticacao;

    public Logar(Context context) {
        this.context = context;
        this.autenticacao = ConfiguracaoFirebase.getFirebaseAuth();
    }

    public static boolean usuarioLogado() {

        FirebaseUser currentUser = ConfiguracaoFirebase.getCurrentUser();

        if (currentUser != null) {
            return true;
        }
        return false;
    }

    public static void deslogarUsuario() {
        ConfiguracaoFirebase.getFirebaseAuth().signOut();
    }

    public void cadastrarUsuario(String email, String senha, Context context) {
 /*
        autenticacao.createUserWithEmailAndPassword(email, senha)

                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {


                        } else {
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
                            Toast.makeText(context, "Erro " + erroExcecao, Toast.LENGTH_LONG).show();

                        }


                });}*/


    }



/*
    private void cadastrarUsuario(Usuario usuario, Context context) {


        autenticacao = ConfiguracaoFirebase.getFirebaseAuth();
        autenticacao.createUserWithEmailAndPassword("elielrj@gmail.com", "1234567")
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // insereUsuario(usuario);
                        } else {
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
                            //Toast.makeText(context.getApplicationContext(), Tag.ERRO + erroExcecao, Toast.LENGTH_LONG).show();
                            Tag.ERRO + erroExcecao;
                        }
                    }
                });


    }

    */
}
