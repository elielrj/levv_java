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
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.example.levv.R;
import com.example.levv.model.bo.usuario.Arquivo;


public class CadastrarUsuarioEntregadorDocumentoFragment extends Fragment {


    private BootstrapEditText cnhOuIdentidade;
    private ActivityResultLauncher<Intent> launcher;

    private Button buttonCamera;
    private ImageView imageViewIdt;


    private RadioButton rbAPe;
    private RadioButton rbCarro;

    Arquivo arquivo = new Arquivo("identidade");

    private static final int A_PE = 0;
    private static final int MOTO = 1;
    private static final int CARRO = 2;
    private static final int NONE = 3;

    // Initialize the choice to the default (no choice)
    private int mRadioButtonChoice = NONE;

    // The "choice" key for the bundle.
    private static final String CHOICE = "choice";

    // The listener interface.
    OnFragmentInteractionListenerMeioDeTransporte mListener;

    public CadastrarUsuarioEntregadorDocumentoFragment() {
    }

    public interface OnFragmentInteractionListenerMeioDeTransporte {
        void onRadioButtonChoice(int choice);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        inicializarCapturaDeImagem();
        if (context instanceof OnFragmentInteractionListenerMeioDeTransporte) {
            mListener = (OnFragmentInteractionListenerMeioDeTransporte) context;
        } else {
            throw new ClassCastException(context.toString() + "fragment não implementado OnFragmentInteractionListenerMeioDeTransporte!");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cadastrar_usuario_entregador_documento, container, false);


        rbAPe = view.findViewById(R.id.tela_fragment_cadastro_entregador_meioTransp_pe_rb);
        rbAPe.setEnabled(false);

        rbCarro = view.findViewById(R.id.tela_fragment_cadastro_entregador_meioTransp_carro_rb);
        rbCarro.setEnabled(false);

        final RadioGroup radioGroup = view.findViewById(R.id.radio_group_meio_transporte);

        buttonCamera = view.findViewById(R.id.tela_fragment_cadastro_entregador_doc_btn);
        imageViewIdt = view.findViewById(R.id.imageViewIdt);



        cnhOuIdentidade = view.findViewById(R.id.tela_fragment_cadastro_entregador_doc_nr_edit);

        //SimpleMaskFormatter maskFormatterIdt = new SimpleMaskFormatter("NNN.NNN.NNN-NN");
        //MaskTextWatcher maskTextWatcherIdt = new MaskTextWatcher(cnhOuIdentidade, maskFormatterIdt);
        //cnhOuIdentidade.addTextChangedListener(maskTextWatcherIdt);

        buttonCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                abrirCamera();
            }
        });
        if (getArguments().containsKey(CHOICE)) {
            if (mRadioButtonChoice != NONE) {
                radioGroup.check(radioGroup.getChildAt(mRadioButtonChoice).getId());
            }
        }


        radioGroup.setOnCheckedChangeListener(
                new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        View radioButton = radioGroup.findViewById(checkedId);
                        int index = radioGroup.indexOfChild(radioButton);

                        switch (index) {
                            case A_PE:
                                mRadioButtonChoice = A_PE;
                                mListener.onRadioButtonChoice(A_PE);
                                break;
                            case MOTO:
                                mRadioButtonChoice = MOTO;
                                mListener.onRadioButtonChoice(MOTO);
                                break;
                            case CARRO:
                                mRadioButtonChoice = CARRO;
                                mListener.onRadioButtonChoice(CARRO);
                                break;
                            default:
                                mRadioButtonChoice = NONE;
                                mListener.onRadioButtonChoice(NONE);
                                break;
                        }
                    }
                });

        return view;
    }


    public static CadastrarUsuarioEntregadorDocumentoFragment newInstance(int choice) {

        CadastrarUsuarioEntregadorDocumentoFragment fragment = new CadastrarUsuarioEntregadorDocumentoFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(CHOICE, choice);
        fragment.setArguments(arguments);
        return fragment;
    }

    public Arquivo getArquivo() {
        return arquivo;
    }

    public String getCnhOuIdentidade() {
        return cnhOuIdentidade.getText().toString();
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
                            imageViewIdt.setImageBitmap(imageBitmap);
                            arquivo.setImageView(imageViewIdt);
                        }
                    }
                }
        );
    }

    public void abrirCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        launcher.launch(intent);
    }

    public ImageView getImageViewIdt() {
        return imageViewIdt;
    }

    public void setImageViewIdt(ImageView imageViewIdt) {
        this.imageViewIdt = imageViewIdt;
    }

    public void clearCampos(){
        cnhOuIdentidade.getText().clear();
        imageViewIdt.setImageDrawable(null);
    }
}