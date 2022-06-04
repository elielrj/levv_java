package com.example.levv.view.cadastro.celular;

import static android.widget.Toast.LENGTH_SHORT;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.example.levv.R;
import com.example.levv.model.tela.TelaCadastrarCelularModel;
import com.example.levv.support.LogarUsuario;
import com.example.levv.support.Tag;


public class CadastrarCelularLoginFragment extends Fragment {

    public BootstrapEditText email;
    public BootstrapEditText senha;

    OnFragmentInteractionListenerLogin mListener;

    public CadastrarCelularLoginFragment() {
    }

    public interface OnFragmentInteractionListenerLogin {

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof OnFragmentInteractionListenerLogin) {
            mListener = (OnFragmentInteractionListenerLogin) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cadastrar_celular_login, container, false);

        email = view.findViewById(R.id.editEmailCelularLoginFragment);
        senha = view.findViewById(R.id.editSenhaCelularLoginFragment);

        return view;
    }

    public static CadastrarCelularLoginFragment newInstance() {
        CadastrarCelularLoginFragment cadastrarCelularLoginFragment = new CadastrarCelularLoginFragment();
        return cadastrarCelularLoginFragment;
    }

    public String getEmail() {
        return email.getText().toString();
    }

    public String getSenha() {
        return senha.getText().toString();
    }

    public void limparComponentes() {
        email.getText().clear();
        senha.getText().clear();
    }

    synchronized public void cadastrar(TelaCadastrarCelularModel model){

        Log.d("cadastro", email.getText().toString());
        Log.d("cadastro", senha.getText().toString());

        if (model.validarEmail(email.getText().toString())) {
            if(model.validarSenha(senha.getText().toString())){

                model.setEmail(email.getText().toString());
                model.setSenha(senha.getText().toString());

                Log.d("cadastro", model.getEmail());
                Log.d("cadastro", model.getSenha());

                LogarUsuario logarUsuario = new LogarUsuario(getContext());
                logarUsuario.logarComEmailSenha(model);


            }else{
                Toast.makeText(getContext(), "Senha inválida", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getContext(), Tag.EMAIL_INVALIDO, Toast.LENGTH_LONG).show();
        }
    }

}