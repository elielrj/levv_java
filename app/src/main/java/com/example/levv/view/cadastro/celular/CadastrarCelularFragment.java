package com.example.levv.view.cadastro.celular;

import static android.widget.Toast.LENGTH_SHORT;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.example.levv.R;
import com.example.levv.model.tela.TelaCadastrarCelularModel;
import com.example.levv.support.CriarUsuario;
import com.example.levv.support.Tag;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;


public class CadastrarCelularFragment extends Fragment {

    BootstrapEditText nomeSobrenone;
    BootstrapEditText celularComDdd;
    BootstrapEditText email;
    BootstrapEditText senha;
    BootstrapEditText senhaDeConfirmacao;

    OnFragmentInteractionListenerCadastrar mListener;

    public CadastrarCelularFragment() {
    }

    interface OnFragmentInteractionListenerCadastrar {
        void onCadastrar();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof CadastrarCelularFragment.OnFragmentInteractionListenerCadastrar) {
            mListener = (OnFragmentInteractionListenerCadastrar) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cadastrar_celular, container, false);

        nomeSobrenone = view.findViewById(R.id.editNomeCadastrarFragment);
        celularComDdd = view.findViewById(R.id.editCelularCadastrarFragment);
        email = view.findViewById(R.id.editEmailCadastrarFragment);
        senha = view.findViewById(R.id.editSenha1CadastrarFragment);
        senhaDeConfirmacao = view.findViewById(R.id.editSenha2CadastrarFragment);

        SimpleMaskFormatter maskFormatterCelular = new SimpleMaskFormatter("(NN) N NNNN-NNNN");
        MaskTextWatcher maskTextWatcherCelular = new MaskTextWatcher(celularComDdd, maskFormatterCelular);
        celularComDdd.addTextChangedListener(maskTextWatcherCelular);

        return view;
    }

    public static CadastrarCelularFragment newInstance() {
        CadastrarCelularFragment cadastrarCelularFragment = new CadastrarCelularFragment();
        Bundle arguments = new Bundle();
        //DECLARAR ARQGUMENTOS P/ REPASSAR A ACTIVITY
        cadastrarCelularFragment.setArguments(arguments);
        return cadastrarCelularFragment;
    }




    public void limparComponentes() {
        nomeSobrenone.getText().clear();
        celularComDdd.getText().clear();
        email.getText().clear();
        senha.getText().clear();
        senhaDeConfirmacao.getText().clear();
    }

    public void cadastrar(TelaCadastrarCelularModel model){

        if(model.validarNomeCompleto(nomeSobrenone.toString())){
            if(model.validarCelular(celularComDdd.toString())){
                if(model.validarEmail(email.toString())){
                    if(model.validarSenhas(senha.toString(),senhaDeConfirmacao.toString())){

                        model.nomeComSobrenome(nomeSobrenone.toString());
                        model.setCelularComDdd(celularComDdd.toString());
                        model.setEmail(email.toString());
                        model.setSenha(senha.toString());
                        model.setSenhaDeConfirmacao(senhaDeConfirmacao.toString());

                        CriarUsuario criarUsuario = new CriarUsuario(getContext());
                        criarUsuario.cadastrarUsuarioComEmailSenha(model);

                    }else{
                        Toast.makeText(getContext(), Tag.SENHA_DIFERENTES, LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getContext(), Tag.EMAIL_INVALIDO, LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(getContext(), Tag.CELULAR_INVALIDO, LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(getContext(), Tag.NOME_INVALIDO, LENGTH_SHORT).show();
        }


    }




}