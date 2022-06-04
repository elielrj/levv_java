package com.example.levv.controller.tela;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;;
import com.example.levv.model.DAO.ConfiguracaoFirebase;
import com.example.levv.model.DAO.endereco.EnderecoDAO;
import com.example.levv.support.AppUtil;
import com.example.levv.support.Localizar;
import com.example.levv.support.Tag;
import com.example.levv.model.tela.TelaCadastrarEnderecoModel;
import com.example.levv.model.bo.endereco.Bairro;
import com.example.levv.model.bo.endereco.Cidade;
import com.example.levv.model.bo.endereco.Endereco;
import com.example.levv.model.bo.endereco.Estado;
import com.example.levv.view.cadastro.endereco.TelaCadastrarEnderecoActivity;
import com.example.levv.view.funcional.TelaMainActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Locale;

public class TelaCadastrarEnderecoController {

    private TelaCadastrarEnderecoActivity activity;
    private TelaCadastrarEnderecoModel model;


    public TelaCadastrarEnderecoController(TelaCadastrarEnderecoActivity activity) {
        this.activity = activity;
        this.model = new TelaCadastrarEnderecoModel(this);
    }

    private boolean validarLogradouro() {
        return model.validarLogradouro(activity.editLogradouro.getText().toString());
    }

    private boolean validarNumero() {
        return model.validarNumeroLogradouro(activity.editNumero.getText().toString());
    }

    private boolean validarTipoDeMoradia() {

        if (activity.rbTipoDeMoradiaApt.isChecked() || activity.rbTipoDeMoradiaCasa.isChecked() || activity.rbTipoDeMoradiaLoja.isChecked())
            return true;
        return false;
    }

    private boolean validarCep() {
        return model.validarCep(activity.editCep.getText().toString());
    }

    private boolean validarBairro() {
        return model.validarBairro(activity.editBairro.getText().toString());
    }

    private boolean validarCidade() {
        return model.validarCidade(activity.editCidade.getText().toString());
    }

    private boolean validarEstado() {
        return model.validarEstado(activity.editEstado.getText().toString());
    }

    public void liparComponentes() {
        activity.editLogradouro.getText().clear();
        activity.editNumero.getText().clear();
        activity.editComplemento.getText().clear();
        activity.editBairro.getText().clear();
        activity.editCidade.getText().clear();
        activity.editEstado.getText().clear();
        activity.editCep.getText().clear();
        activity.rbTipoDeMoradiaLoja.setChecked(false);
        activity.rbTipoDeMoradiaCasa.setChecked(false);
        activity.rbTipoDeMoradiaApt.setChecked(false);
    }


    @SuppressLint("ServiceCast")
    public void localizar() {/*
        try {


            Toast.makeText(activity, "Por favor, aguarde enquanto localizamos seu endereço!", Toast.LENGTH_LONG).show();


            Thread t = new Thread(myCurrentLocation);
            t.start();

            //myCurrentLocation.onLocationChanged((Location) activity.getApplicationContext().getSystemService(LOCATION_SERVICE));

            //Thread.sleep(10000);


            activity.editLogradouro.setText(myCurrentLocation.getRua());
            activity.editNumero.setText(myCurrentLocation.getNumero());
            activity.editBairro.setText(myCurrentLocation.getBairro());
            activity.editCidade.setText(myCurrentLocation.getCidade());
            activity.editEstado.setText(myCurrentLocation.getEstado());
            activity.editCep.setText(myCurrentLocation.getCep());
            activity.editComplemento.setText(myCurrentLocation.getComplemento());

        } catch (Exception e) {
            Toast.makeText(activity, "Erro ao gerar localização!", Toast.LENGTH_LONG).show();
        }*/
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void cadastrar() {
        if (validarLogradouro()) {
            if (validarNumero()) {
                if (validarTipoDeMoradia()) {
                    if (validarCep()) {
                        if (validarBairro()) {
                            if (validarCidade()) {
                                if (validarEstado()) {
                                    try {


                                        Endereco endereco = criarEndereco();

                                        activity.dados.putString("estado", endereco.getBairro().getCidade().getEstado().getNome());
                                        activity.dados.putString("cidade", endereco.getBairro().getCidade().getNome());
                                        activity.dados.putString("bairro", endereco.getBairro().getNome());
                                        activity.dados.putString("logradouro", endereco.getLogradouro());
                                        activity.dados.putString("numeroEndereco", endereco.getNumero());
                                        activity.dados.putString("complemento", endereco.getComplemento());
                                        activity.dados.putString("tipoDeMoradia", endereco.getTipoDeMoradia());
                                        activity.dados.putString("cep", endereco.getCep());
                                        activity.dados.apply();

                                        model.inserirEndereco(endereco);


                                        DocumentReference referenciaEndereco = FirebaseFirestore.getInstance().document(EnderecoDAO.NAME_COLLECTION_DATABASE + "/" + FirebaseAuth.getInstance().getCurrentUser().getEmail());

                                        String tipoDeUsuario = activity.sharedPreferences.getString("tipoDeUsuario", "tipoDeUsuario erro");

                                        tipoDeUsuario = tipoDeUsuario.toLowerCase(Locale.ROOT);

                                        DocumentReference referenciaPessoa = FirebaseFirestore.getInstance().document("usuarios/pessoas/" + tipoDeUsuario + "/" + FirebaseAuth.getInstance().getCurrentUser().getEmail());

                                        referenciaPessoa
                                                .update("referenciaEndereco", referenciaEndereco)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        Log.d("Referencia ", " atualizada com sucesso!");
                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.d("Referencia ", "Falha ao atualizar!");
                                            }
                                        });

                                        enderecoCadastrado();

                                        trocarTela();

                                    } catch (Exception e) {
                                        Toast.makeText(activity, Tag.FALHA_AO_CADASTRAR_ENDERECO, Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(activity, Tag.ESTADO_INVALIDO, Toast.LENGTH_SHORT).show();
                                    activity.editEstado.requestFocus();
                                }
                            } else {
                                Toast.makeText(activity, Tag.CIDADE_INVALIDA, Toast.LENGTH_SHORT).show();
                                activity.editCidade.requestFocus();
                            }
                        } else {
                            Toast.makeText(activity, Tag.BAIRRO_INVALIDO, Toast.LENGTH_SHORT).show();
                            activity.editBairro.requestFocus();
                        }
                    } else {
                        Toast.makeText(activity, Tag.CEP_INVALIDO, Toast.LENGTH_SHORT).show();
                        activity.editCep.requestFocus();
                    }
                } else {
                    Toast.makeText(activity, Tag.TIPO_DE_MORADIA_INVALIDO, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(activity, Tag.NUMERO_INVALIDO, Toast.LENGTH_SHORT).show();
                activity.editNumero.requestFocus();
            }
        } else {
            Toast.makeText(activity, Tag.LOGRADOURO_INVALIDO, Toast.LENGTH_SHORT).show();
            activity.editLogradouro.requestFocus();
        }
    }

    private String tipoDeMoradia() {
        String tipoDeMoradia = "";
        if (activity.rbTipoDeMoradiaApt.isChecked()) {
            tipoDeMoradia = "Apartamento";
        } else if (activity.rbTipoDeMoradiaCasa.isChecked()) {
            tipoDeMoradia = "Casa";
        } else if (activity.rbTipoDeMoradiaLoja.isChecked()) {
            tipoDeMoradia = "Loja";
        }
        return tipoDeMoradia;
    }

    private Endereco criarEndereco() {
        Estado estado = criarEstado();
        Cidade cidade = criarCidade(estado);
        Bairro bairro = criarBairro(cidade);

        Localizar localizar = new Localizar(activity);

        Endereco endereco = new Endereco.EnderecoBuilder()
                .setLogradouro(activity.editLogradouro.getText().toString())
                .setNumero(activity.editNumero.getText().toString())
                .setComplemento(activity.editComplemento.getText().toString())
                .setTipoDeMoradia(tipoDeMoradia())
                .setCep(activity.editCep.getText().toString())
                .setBairro(bairro)
                .setLatLng(localizar.localizarGeoPoint())
                .createEndereco();
        return endereco;
    }

    private Estado criarEstado() {
        return new Estado(activity.editEstado.getText().toString());
    }

    private Cidade criarCidade(Estado estado) {
        return new Cidade(activity.editCidade.getText().toString(), estado);
    }

    private Bairro criarBairro(Cidade cidade) {
        return new Bairro(activity.editBairro.getText().toString(), cidade);
    }

    public void trocarTela() {
        Intent intent = new Intent(activity, TelaMainActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }

    public void enderecoCadastrado(){

        activity.dados.putBoolean(TelaCadastrarEnderecoModel.ENDERECO_CADASTRADO, true);
        activity.dados.apply();

    }



}
