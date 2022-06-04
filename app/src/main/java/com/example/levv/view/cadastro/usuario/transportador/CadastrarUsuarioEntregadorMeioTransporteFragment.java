package com.example.levv.view.cadastro.usuario.transportador;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.example.levv.R;
import com.example.levv.model.bo.usuario.Arquivo;


public class CadastrarUsuarioEntregadorMeioTransporteFragment extends Fragment {

   private BootstrapEditText marca;
   private BootstrapEditText modelo;
   private BootstrapEditText renavam;
   private BootstrapEditText placa;
   private Arquivo arquivo = new Arquivo("crlv");

   private Button buttunCadastrarCrlv;
    private ImageView imageViewCrlv;
    private ActivityResultLauncher<Intent> launcher;


    public CadastrarUsuarioEntregadorMeioTransporteFragment() {
    }

    public interface OnFragmentInteractionListenerEntregadorDocMeioTransp{

    }

    public static CadastrarUsuarioEntregadorMeioTransporteFragment newInstance() {
        CadastrarUsuarioEntregadorMeioTransporteFragment fragment = new CadastrarUsuarioEntregadorMeioTransporteFragment();

        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cadastrar_usuario_entregador_meio_transporte, container, false);

        marca = view.findViewById(R.id.tela_fragment_cadastro_entregador_veiculo_marca_edit);
        modelo = view.findViewById(R.id.tela_fragment_cadastro_entregador_veiculo_modelo_edit);
        renavam = view.findViewById(R.id.tela_fragment_cadastro_entregador_veiculo_renavam_edit);
        placa = view.findViewById(R.id.tela_fragment_cadastro_entregador_veiculo_placa_edit);
        imageViewCrlv = view.findViewById(R.id.imageViewCrlv);

         buttunCadastrarCrlv = view.findViewById(R.id.tela_fragment_cadastro_entregador_veiculo_crlv_btn);
         buttunCadastrarCrlv.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 abrirCamera();
             }
         });




        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        inicializarCapturaDeImagem();
    }

    public String getMarca() {
        return marca.getText().toString();
    }

    public String getModelo() {
        return modelo.getText().toString();
    }

    public String getRenavam() {
        return renavam.getText().toString();
    }

    public Arquivo getArquivo() {
        return arquivo;
    }

    public void setArquivo(Arquivo arquivo) {
        this.arquivo = arquivo;
    }

    public String getPlaca() {
        return placa.getText().toString();
    }

    public void inicializarCapturaDeImagem() {

        launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
                            imageViewCrlv.setImageBitmap(imageBitmap);
                        }
                    }
                }
        );
    }

    public void abrirCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        launcher.launch(intent);
    }

    public ImageView getImageViewCrlv() {
        return imageViewCrlv;
    }

    public void setImageViewCrlv(ImageView imageViewCrlv) {
        this.imageViewCrlv = imageViewCrlv;
    }

    public void clearCampos(){
        marca.getText().clear();
        modelo.getText().clear();
        renavam.getText().clear();
        placa.getText().clear();
        imageViewCrlv.setImageDrawable(null);
    }
}