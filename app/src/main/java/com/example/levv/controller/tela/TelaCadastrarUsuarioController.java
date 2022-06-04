package com.example.levv.controller.tela;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.levv.model.DAO.ConfiguracaoFirebase;
import com.example.levv.support.Validar;
import com.example.levv.view.cadastro.endereco.TelaCadastrarEnderecoActivity;
import com.example.levv.view.cadastro.usuario.transportador.CadastrarUsuarioEntregadorDocumentoFragment;
import com.example.levv.R;
import com.example.levv.support.AppUtil;
import com.example.levv.support.Tag;
import com.example.levv.model.bo.meioDeTransporte.Veiculo;
import com.example.levv.model.bo.usuario.Arquivo;
import com.example.levv.model.bo.usuario.Cliente;
import com.example.levv.model.bo.usuario.Lojista;
import com.example.levv.model.bo.usuario.Transportador;
import com.example.levv.model.tela.TelaCadastrarUsuarioModel;
import com.example.levv.view.cadastro.usuario.TelaCadastrarUsuarioActivity;
import com.example.levv.view.cadastro.usuario.transportador.CadastrarUsuarioEntregadorMeioTransporteFragment;
import com.example.levv.view.cadastro.usuario.lojista.CadastrarUsuarioLojistaFragment;
import com.google.firebase.firestore.DocumentReference;

public class TelaCadastrarUsuarioController {


    private static final String DOCUMENTO_IDENTIFICAO = "identificacao";
    private static final String DOCUMENTO_CRLV = "crlv";

    private TelaCadastrarUsuarioActivity activity;
    private TelaCadastrarUsuarioModel model;

    private String cnpj;
    private String nomeDaEmpresa;
    private String nomeFantasia;

    private String cnhOuIdentidade;
    private String marca;
    private String modelo;
    private String renavan;
    private String placa;

    private Cliente cliente;

    public TelaCadastrarUsuarioController(TelaCadastrarUsuarioActivity activity) {
        this.activity = activity;
        this.model = new TelaCadastrarUsuarioModel(this);
    }

    public void limparComponentes() {

        int checkeId = activity.radioGroupUsuario.getCheckedRadioButtonId();
        View radioButton = activity.radioGroupUsuario.findViewById(checkeId);
        int index = activity.radioGroupUsuario.indexOfChild(radioButton);

        switch (index) {
            case 0: {
                clearDadosComuns();
                clearDadosLojista();
                break;
            }
            case 1: {
                clearDadosComuns();
                break;
            }
            case 2: {
                clearDadosComuns();
                clearDadosEntregador();
                clearDadosEntregadorMotorizado();
                break;
            }
            default: clearDadosComuns();

        }

    }

    private void clearDadosComuns() {
        activity.editNome.getText().clear();
        activity.editSobrenome.getText().clear();
        activity.editDataNascimento.getText().clear();
        activity.editCpf.getText().clear();
    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    public void cadastrarUsuario() {


        int checkeId = activity.radioGroupUsuario.getCheckedRadioButtonId();
        View radioButton = activity.radioGroupUsuario.findViewById(checkeId);
        int index = activity.radioGroupUsuario.indexOfChild(radioButton);
        switch (index) {
            case 0:
                cadastrarLojista();
                break;
            case 1:
                cadastrarCliente();
                break;
            case 2:
                cadastrarEntregador();
                break;
            default:
                break;
        }
    }

    public boolean radioButtonEstaMarcado(){

        if(activity.rbCliente.isChecked() || activity.rbEntregador.isChecked() || activity.rbLojista.isChecked()){
            return true;
        }
        return false;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void cadastrarCliente() {
        if (validarNome()) {
            if (validarSobrenome()) {
                if (validarDtNasc()) {
                    if (validarCpf()) {

                        cliente = new Cliente();

                        String nome = activity.sharedPreferences.getString("nome", null);
                        String sobrenome = activity.sharedPreferences.getString("sobrenome", null);
                        String cpf = AppUtil.removerMascaraCpf(activity.editCpf.getText().toString());
                        String dataDeNascimento = activity.editDataNascimento.getText().toString();
                        String tipoDeUsuario = "Cliente";

                        cliente.setNome(nome);
                        cliente.setSobrenome(sobrenome);
                        cliente.setCpf(cpf);
                        cliente.setDataDeNascimento(dataDeNascimento);
                        cliente.setStatus(false);
                        cliente.setEndereco(null);
                        cliente.setTipoDeUsuario(tipoDeUsuario);

                        //SHAREDPREFERENCES
                        activity.dados.putString("nome", nome);
                        activity.dados.putString("sobrenome", sobrenome);
                        activity.dados.putString("cpf", cpf);
                        activity.dados.putString("dataDeNascimento", dataDeNascimento);
                        activity.dados.putString("tipoDeUsuario", tipoDeUsuario);
                        activity.dados.apply();

                        try {

                            model.incluirCliente(cliente);

                            Toast.makeText(activity.getApplicationContext(), Tag.CLIENTE_CADASTRADO_SUCESSO, Toast.LENGTH_LONG).show();

                            trocarTela();

                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(activity.getApplicationContext(), Tag.ERRO_AO_CADASTRAR_CLIENTE, Toast.LENGTH_LONG).show();
                        }

                    } else {
                        Toast.makeText(activity, Tag.CPF_INVALIDO, Toast.LENGTH_SHORT).show();
                        activity.editCpf.requestFocus();
                    }
                } else {
                    Toast.makeText(activity, Tag.DATA_NASCIMENTO_INVALIDA, Toast.LENGTH_SHORT).show();
                    activity.editDataNascimento.requestFocus();
                }
            } else {
                Toast.makeText(activity, Tag.SOBRENOME_INVALIDO, Toast.LENGTH_SHORT).show();
                activity.editNome.requestFocus();
            }
        } else {
            Toast.makeText(activity, Tag.NOME_INVALIDO, Toast.LENGTH_SHORT).show();
            activity.editNome.requestFocus();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void cadastrarLojista() {

        buscarDadosDeLojistaNoFragment();

        if (validarNome()) {
            if (validarSobrenome()) {
                if (validarDtNasc()) {
                    if (validarCpf()) {

                        if (validarCnpj()) {
                            if (validarNomeEmpresa()) {
                                if (validarNomeFantasia()) {

                                    Lojista lojista = new Lojista();

                                    String nome = activity.sharedPreferences.getString("nome", null);

                                    String sobrenome = activity.sharedPreferences.getString("sobrenome", null);
                                    String cpf = AppUtil.removerMascaraCpf(activity.editCpf.getText().toString());
                                    String dataDeNascimento = activity.editDataNascimento.getText().toString();
                                    String tipoDeUsuario = "Lojista";
                                    cnpj = AppUtil.removerMascaraCnpj(cnpj);

                                    lojista.setNome(nome);
                                    lojista.setSobrenome(sobrenome);
                                    lojista.setCpf(cpf);
                                    lojista.setDataDeNascimento(dataDeNascimento);
                                    lojista.setStatus(false);
                                    lojista.setEndereco(null);
                                    lojista.setTipoDeUsuario(tipoDeUsuario);
                                    lojista.setCnpj(cnpj);
                                    lojista.setNomeFantasia(nomeDaEmpresa);
                                    lojista.setNomeDaEmpresa(nomeFantasia);

                                    //SHAREDPREFERENCES
                                    activity.dados.putString("nome", nome);
                                    activity.dados.putString("sobrenome", sobrenome);
                                    activity.dados.putString("cpf", cpf);
                                    activity.dados.putString("dataDeNascimento", dataDeNascimento);
                                    activity.dados.putString("tipoDeUsuario", tipoDeUsuario);
                                    activity.dados.putString("cnpj", cnpj);
                                    activity.dados.putString("nomeDaEmpresa", nomeDaEmpresa);
                                    activity.dados.putString("nomeFantasia", nomeFantasia);
                                    activity.dados.apply();

                                    try {

                                        model.incluirLojista(lojista);

                                        Toast.makeText(activity, Tag.LOJISTA_CADASTRADO_SUCESSO, Toast.LENGTH_LONG).show();

                                        trocarTela();


                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        Toast.makeText(activity, Tag.ERRO_AO_CADASTRAR_LOJISTA, Toast.LENGTH_LONG).show();
                                    }

                                } else {
                                    Toast.makeText(activity, Tag.NOME_INVALIDO, Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(activity, Tag.NOME_INVALIDO, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(activity, Tag.CNPJ_INVALIDO, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(activity, Tag.CPF_INVALIDO, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(activity, Tag.DATA_NASCIMENTO_INVALIDA, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(activity, Tag.SOBRENOME_INVALIDO, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(activity, Tag.NOME_INVALIDO, Toast.LENGTH_SHORT).show();
        }
    }


    private boolean validarNome() {
        return model.validarNome(activity.editNome.getText().toString());
    }

    private boolean validarSobrenome() {
        return model.validarSobrenome(activity.editSobrenome.getText().toString());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean validarDtNasc() {
        return model.validarDtNasc(activity.editDataNascimento.getText().toString());
    }

    private boolean validarCpf() {
        return model.validarCpf(activity.editCpf.getText().toString());
    }

    private boolean validarCnpj() {
        return model.validarCnpj(cnpj);
    }

    private boolean validarNomeEmpresa() {
        return model.validarNomeEmpresa(nomeDaEmpresa);
    }

    private boolean validarNomeFantasia() {
        return model.validarNomeFantasia(nomeFantasia);
    }

    public void cadastrarEntregador() {


        if (activity.mRadioButtonChoice == 0) {

            cadastrarEntregadorAPe();

        } else if (activity.mRadioButtonChoice == 1 || activity.mRadioButtonChoice == 2) {


            cadastrarEntregadorMotorizado();


        } else {
            Toast.makeText(activity, "Dados inválidos!", Toast.LENGTH_LONG).show();
        }


    }

    private void cadastrarEntregadorAPe() {

        //Busca dados de Frgmento Entregador a pé
        buscarDadosDeEntregador();

        if (validarNumeroCnhOuIdentidade()) {


            Transportador transportador = new Transportador();
            //Transportador A pé
            String nome = activity.sharedPreferences.getString("nome", "Nome");
            String sobrenome = activity.sharedPreferences.getString("sobrenome", "Sobrenome");
            String cpf = AppUtil.removerMascaraCpf(activity.editCpf.getText().toString());
            String dataDeNascimento = activity.editDataNascimento.getText().toString();
            String tipoDeUsuario = "Transportador";

            transportador.setNome(nome);
            transportador.setSobrenome(sobrenome);
            transportador.setCpf(cpf);
            transportador.setDataDeNascimento(dataDeNascimento);
            transportador.setStatus(false);
            transportador.setTipoDeUsuario(tipoDeUsuario);
            transportador.setTransportadorAprovado(false);
            transportador.setNumeroRegistroDeDocumento(cnhOuIdentidade);

            Arquivo documentoDeIdentificacao = new Arquivo(DOCUMENTO_IDENTIFICAO);
            documentoDeIdentificacao.setImageView(activity.imgIdentidade);
            transportador.setDocumentoDeIdentificacao(documentoDeIdentificacao);

            //SHAREDPREFERENCES
            activity.dados.putString("nome", nome);
            activity.dados.putString("sobrenome", sobrenome);
            activity.dados.putString("cpf", cpf);
            activity.dados.putString("dataDeNascimento", dataDeNascimento);
            activity.dados.putString("tipoDeUsuario", tipoDeUsuario);
            activity.dados.putString("meioDeTransporte", "ape");
            activity.dados.apply();

            try {

                this.model.inserirTransportador(transportador);

                Toast.makeText(activity.getApplicationContext(), Tag.CADASTRO_ENVIADO, Toast.LENGTH_LONG).show();

                trocarTela();

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(activity, Tag.DOCUMENTO_IDENTIFICACAO_INVALIDO, Toast.LENGTH_SHORT).show();
        }
    }

    private void cadastrarEntregadorMotorizado() {

        //Busca dados de Frgmento Entregador a pé
        buscarDadosDeEntregador();

        //Busca dados de Frgmento Entregador motorizado
        if (activity.mRadioButtonChoice == 1 || activity.mRadioButtonChoice == 2) {
            buscarDadosDeEntregadorMotorizado();
        }

        if (validarNumeroCnhOuIdentidade()) {
            if (validarDocumentoIdentidadeOuCnh()) {
                if (validarMarca()) {
                    if (validarModelo()) {
                        if (validarRenavam()) {
                            if (validarPlaca()) {
                                if (validarCrlvDocumento()) {
                                    if (validarMeioDeTransporte()) {


                                        Transportador transportador = new Transportador();
                                        //Transportador Motorizado

                                        String nome = activity.sharedPreferences.getString("nome", "Nome");
                                        String sobrenome = activity.sharedPreferences.getString("sobrenome", "Sobrenome");
                                        String cpf = AppUtil.removerMascaraCpf(activity.editCpf.getText().toString());
                                        String dataDeNascimento = activity.editDataNascimento.getText().toString();
                                        String tipoDeUsuario = "Transportador";


                                        transportador.setNome(nome);
                                        transportador.setSobrenome(sobrenome);
                                        transportador.setCpf(cpf);
                                        transportador.setStatus(false);
                                        transportador.setDataDeNascimento(dataDeNascimento);


                                        Arquivo documentoDeIdentificacao = new Arquivo(DOCUMENTO_IDENTIFICAO);
                                        documentoDeIdentificacao.setImageView(activity.imgIdentidade);
                                        transportador.setDocumentoDeIdentificacao(documentoDeIdentificacao);

                                        transportador.setTipoDeUsuario(tipoDeUsuario);
                                        transportador.setTransportadorAprovado(false);
                                        transportador.setNumeroRegistroDeDocumento(cnhOuIdentidade);

                                        Veiculo veiculo = new Veiculo();
                                        veiculo.setModelo(modelo);
                                        veiculo.setMarca(marca);
                                        veiculo.setPlaca(placa);
                                        veiculo.setRenavam(renavan);

                                        Arquivo documentoDeCrlv = new Arquivo(DOCUMENTO_CRLV);
                                        documentoDeCrlv.setImageView(activity.imgCrlv);
                                        veiculo.setDocumentoDeCrlv(documentoDeCrlv);

                                        veiculo.setStatus(false);


                                        String email = activity.sharedPreferences.getString("email", "erro email SharedPreferences");

                                        DocumentReference referenciaVeiculo = ConfiguracaoFirebase.getFirebaseFirestore().document("veiculos/" + email);

                                        transportador.setReferenciaVeiculo(referenciaVeiculo);

                                        String meioSelecionado = "";

                                        if (activity.mRadioButtonChoice == 1) {
                                            meioSelecionado = "Moto";
                                        } else if (activity.mRadioButtonChoice == 2) {
                                            meioSelecionado = "Carro";
                                        } else if (activity.mRadioButtonChoice == 0) {
                                            meioSelecionado = "A pe";
                                        }

                                        DocumentReference referenciaMeioDeTransporte = ConfiguracaoFirebase.getFirebaseFirestore().document("meioDeTransporte/" + meioSelecionado);

                                        veiculo.setReferenciaMeioDeTransporte(referenciaMeioDeTransporte);

                                        transportador.setVeiculo(veiculo);


                                        //SHAREDPREFERENCES
                                        activity.dados.putString("nome", nome);
                                        activity.dados.putString("sobrenome", sobrenome);
                                        activity.dados.putString("cpf", cpf);
                                        activity.dados.putString("dataDeNascimento", dataDeNascimento);
                                        activity.dados.putString("tipoDeUsuario", tipoDeUsuario);
                                        activity.dados.putString("meioDeTransporte", meioSelecionado);
                                        activity.dados.apply();


                                        try {

                                            this.model.inserirTransportador(transportador);

                                            Toast.makeText(activity.getApplicationContext(), Tag.TRANSPORTADOR_CADASTRADO_SUCESSO, Toast.LENGTH_LONG).show();

                                            trocarTela();

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    } else {
                                        Toast.makeText(activity, "Selecione um meio de transporte!", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(activity, Tag.CRLV_FALTANDO, Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(activity, Tag.PLACA_INVALIDA, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(activity, Tag.RENAVAM_INVALIDO, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(activity, Tag.MODELO_INVALIDO, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(activity, Tag.MARCA_INVALIDA, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(activity, Tag.CNH_INSERIR, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(activity, Tag.DOCUMENTO_IDENTIFICACAO_INVALIDO, Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validarMeioDeTransporte() {
        return true;//todo criar
    }


    public void displayFragment(int tipoUsuario) {
        //0 - LOJISTA | 1 - CLIENTE | 2 - ENTREGADOR


        if (tipoUsuario == 0) {

            if (activity.isFragmentDisplayedEntregador) {

                if (activity.mRadioButtonChoice == 1 || activity.mRadioButtonChoice == 2)
                    activity.closeFragment();

                removeFragmentEntregador();
                activity.isFragmentDisplayedEntregador = false;
            }

            if (!activity.isFragmentDisplayedLojista) {

                criaFragmentLojista();
                activity.isFragmentDisplayedLojista = true;
            }

        } else if (tipoUsuario == 1) {

            if (activity.isFragmentDisplayedLojista) {


                removeFragmentLojista();
                activity.isFragmentDisplayedLojista = false;

            } else if (activity.isFragmentDisplayedEntregador) {

                if (activity.mRadioButtonChoice == 1 || activity.mRadioButtonChoice == 2)
                    activity.closeFragment();

                removeFragmentEntregador();
                activity.isFragmentDisplayedEntregador = false;
            }

        } else if (tipoUsuario == 2) {

            if (activity.isFragmentDisplayedLojista) {
                removeFragmentLojista();
                activity.isFragmentDisplayedLojista = false;
            }

            if (!activity.isFragmentDisplayedEntregador) {
                criaFragmentEntregador();
                activity.isFragmentDisplayedEntregador = true;
            }

        }
    }

    private void criaFragmentEntregador() {
        //Suport fragment

        FragmentManager fragmentManager = activity.getSupportFragmentManager();

        //abre transação
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        //Cria uma nova instancia do fragmento
        CadastrarUsuarioEntregadorDocumentoFragment fragment = CadastrarUsuarioEntregadorDocumentoFragment.newInstance(activity.mRadioButtonChoice);

        //add novo fragmento
        fragmentTransaction.add(R.id.tela_cadastro_usuario_fragment, fragment).addToBackStack(null).commit();
    }

    private void criaFragmentLojista() {
        //Suport fragment
        FragmentManager fragmentManager = activity.getSupportFragmentManager();

        //abre transação
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        //Cria uma nova instancia do fragmento
        CadastrarUsuarioLojistaFragment fragment = CadastrarUsuarioLojistaFragment.newInstance();

        //add novo fragmento
        fragmentTransaction.add(R.id.tela_cadastro_usuario_fragment, fragment).addToBackStack(null).commit();
    }

    private void removeFragmentEntregador() {
        //Suport fragment
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        //pega fragmento
        CadastrarUsuarioEntregadorDocumentoFragment entregadorFragment = (CadastrarUsuarioEntregadorDocumentoFragment) fragmentManager.findFragmentById(R.id.tela_cadastro_usuario_fragment);
        //abre transação
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //remove Fragmento Entregador
        fragmentTransaction.remove(entregadorFragment).commit();
    }

    private void removeFragmentLojista() {
        //Suport fragment
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        //pega fragmento
        CadastrarUsuarioLojistaFragment cadastrarUsuarioLojistaFragment = (CadastrarUsuarioLojistaFragment) fragmentManager.findFragmentById(R.id.tela_cadastro_usuario_fragment);
        //abre transação
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //remove Fragmento Lojista
        fragmentTransaction.remove(cadastrarUsuarioLojistaFragment).commit();

    }


    private boolean validarCrlvDocumento() {

        if (activity.imgCrlv.getDrawable() != null) {
            return true;
        } else {
            return false;
        }

    }

    private boolean validarDocumentoIdentidadeOuCnh() {

        if (activity.imgIdentidade.getDrawable() != null) {
            return true;
        } else {
            return false;
        }

    }

    private boolean validarNumeroCnhOuIdentidade() {
        return Validar.documentoIdentificacao(cnhOuIdentidade);
    }

    private boolean validarMarca() {
        return model.validarMarca(marca);
    }

    private boolean validarModelo() {
        return model.validarModelo(modelo);
    }

    private boolean validarRenavam() {
        return model.validarRenavam(renavan);
    }

    private boolean validarPlaca() {
        return model.validarPlaca(placa);
    }


    private void trocarTela() {

        activity.dados.putBoolean(TelaCadastrarUsuarioModel.TIPO_DE_USUARIO_CADASTRADO, true);
        activity.dados.apply();

        Intent intent;
        intent = new Intent(activity, TelaCadastrarEnderecoActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }

    private void buscarDadosDeLojistaNoFragment() {
        //Suport fragment
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        //pega fragmento
        CadastrarUsuarioLojistaFragment cadastrarUsuarioLojistaFragment = (CadastrarUsuarioLojistaFragment) fragmentManager.findFragmentById(R.id.tela_cadastro_usuario_fragment);

        cnpj = cadastrarUsuarioLojistaFragment.getEditLojistaCnpj();
        nomeDaEmpresa = cadastrarUsuarioLojistaFragment.getEditLojistaNomeEmpresa();
        nomeFantasia = cadastrarUsuarioLojistaFragment.getEditLojistaNomeFantasia();

    }

    private void buscarDadosDeEntregador() {
        //Suport fragment
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        //pega fragmento
        CadastrarUsuarioEntregadorDocumentoFragment entregadorDocIdentFragment = (CadastrarUsuarioEntregadorDocumentoFragment) fragmentManager.findFragmentById(R.id.tela_cadastro_usuario_fragment);

        cnhOuIdentidade = entregadorDocIdentFragment.getCnhOuIdentidade();
        activity.imgIdentidade = entregadorDocIdentFragment.getImageViewIdt();
    }

    private void buscarDadosDeEntregadorMotorizado() {

        //Suport fragment
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        //pega fragmento
        CadastrarUsuarioEntregadorMeioTransporteFragment cadastrarUsuarioEntregadorMeioTransporteFragment = (CadastrarUsuarioEntregadorMeioTransporteFragment) fragmentManager.findFragmentById(R.id.tela_cadastro_usuario_meio_transp);

        marca = cadastrarUsuarioEntregadorMeioTransporteFragment.getMarca();
        modelo = cadastrarUsuarioEntregadorMeioTransporteFragment.getModelo();
        renavan = cadastrarUsuarioEntregadorMeioTransporteFragment.getRenavam();
        placa = cadastrarUsuarioEntregadorMeioTransporteFragment.getPlaca();

        activity.imgCrlv = cadastrarUsuarioEntregadorMeioTransporteFragment.getImageViewCrlv();
    }

    public void clearDadosEntregadorMotorizado() {
        //Suport fragment
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        //pega fragmento
        CadastrarUsuarioEntregadorMeioTransporteFragment cadastrarUsuarioEntregadorMeioTransporteFragment = (CadastrarUsuarioEntregadorMeioTransporteFragment) fragmentManager.findFragmentById(R.id.tela_cadastro_usuario_meio_transp);

        if (cadastrarUsuarioEntregadorMeioTransporteFragment != null) {
            cadastrarUsuarioEntregadorMeioTransporteFragment.clearCampos();
        }
    }

    public void clearDadosEntregador() {
        //Suport fragment
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        //pega fragmento
        CadastrarUsuarioEntregadorDocumentoFragment entregadorDocIdentFragment = (CadastrarUsuarioEntregadorDocumentoFragment) fragmentManager.findFragmentById(R.id.tela_cadastro_usuario_fragment);

        if (entregadorDocIdentFragment != null) {
            entregadorDocIdentFragment.clearCampos();
        }
    }

    public void clearDadosLojista() {
        //Suport fragment
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        //pega fragmento
        CadastrarUsuarioLojistaFragment cadastrarUsuarioLojistaFragment = (CadastrarUsuarioLojistaFragment) fragmentManager.findFragmentById(R.id.tela_cadastro_usuario_fragment);

        if (cadastrarUsuarioLojistaFragment != null) {
            cadastrarUsuarioLojistaFragment.clearDados();
        }
    }
}
