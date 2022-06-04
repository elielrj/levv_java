package com.example.levv.view.cadastro.usuario.lojista;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.example.levv.R;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;


public class CadastrarUsuarioLojistaFragment extends Fragment {


    BootstrapEditText editLojistaCnpj;
    BootstrapEditText editLojistaNomeEmpresa;
    BootstrapEditText editLojistaNomeFantasia;

    public CadastrarUsuarioLojistaFragment() {
    }

    public interface OnFragmentInteractionListenerLojista{

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cadastrar_usuario_lojista, container, false);

        editLojistaCnpj = view.findViewById(R.id.editLojistaCnpj);
        editLojistaNomeEmpresa = view.findViewById(R.id.editLojistaNomeEmpresa);
        editLojistaNomeFantasia = view.findViewById(R.id.editLojistaNomeFantasia);

        SimpleMaskFormatter maskFormatterCnpj = new SimpleMaskFormatter("NN.NNN.NNN/NNNN-NN");
        MaskTextWatcher maskTextWatcherCnpj = new MaskTextWatcher(editLojistaCnpj, maskFormatterCnpj);
        editLojistaCnpj.addTextChangedListener(maskTextWatcherCnpj);




        return view;
    }


    public static CadastrarUsuarioLojistaFragment newInstance(){
        CadastrarUsuarioLojistaFragment cadastrarUsuarioLojistaFragment = new CadastrarUsuarioLojistaFragment();
        return cadastrarUsuarioLojistaFragment;
    }

    public String getEditLojistaCnpj() {
        return editLojistaCnpj.getText().toString();
    }

    public String getEditLojistaNomeEmpresa() {
        return editLojistaNomeEmpresa.getText().toString();
    }

    public String getEditLojistaNomeFantasia() {
        return editLojistaNomeFantasia.getText().toString();
    }

    public void clearDados(){
        editLojistaCnpj.getText().clear();
        editLojistaNomeEmpresa.getText().clear();
        editLojistaNomeFantasia.getText().clear();
    }
}