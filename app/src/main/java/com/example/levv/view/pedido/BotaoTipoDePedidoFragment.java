package com.example.levv.view.pedido;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.levv.R;

public class BotaoTipoDePedidoFragment extends Fragment implements View.OnClickListener {

    private Button buttonAtivos;
    private Button buttonFinalizados;
    private Button buttonPendentes;

    private static final int ATIVOS = 0;
    private static final int FINALIZADOS = 1;
    private static final int PENDENTES = 2;
    private static final int NONE = 3;

    private static final String OPTION_BUTTON = "option_button";


    OnFragmentInterctionListenerOptionButton mListenerButonOption;

    private int mChosenButon = PENDENTES;


    public BotaoTipoDePedidoFragment() {
    }

    public interface OnFragmentInterctionListenerOptionButton {
        void onButtonOption(int option);
        void onToUpdateWishList(int list);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        //RESGATA CONTEXT REFERENTE A ESTE FRAGMENT
        if (context instanceof OnFragmentInterctionListenerOptionButton) {
            mListenerButonOption = (OnFragmentInterctionListenerOptionButton) context;


        } else {
            throw new ClassCastException(context.toString() + "Fragmento Botão tipo de pedido não implementado!");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {

        if (buttonAtivos.getId() == v.getId()) {

            selecionarCorDoBotao(ATIVOS);
            mListenerButonOption.onButtonOption(ATIVOS);
            mListenerButonOption.onToUpdateWishList(ATIVOS);

        } else if (buttonFinalizados.getId() == v.getId()) {

            selecionarCorDoBotao(FINALIZADOS);
            mListenerButonOption.onButtonOption(FINALIZADOS);
            mListenerButonOption.onToUpdateWishList(FINALIZADOS);

        } else if (buttonPendentes.getId() == v.getId()) {

            selecionarCorDoBotao(PENDENTES);
            mListenerButonOption.onButtonOption(PENDENTES);
            mListenerButonOption.onToUpdateWishList(PENDENTES);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void selecionarCorDoBotao(int botaoSelecionado) {

        buttonAtivos.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.botao_inativo)));
        buttonFinalizados.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.botao_inativo)));
        buttonPendentes.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.botao_inativo)));

        switch (botaoSelecionado){

            case ATIVOS:
                buttonAtivos.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.botao_selecionado)));
                break;

            case FINALIZADOS:
                buttonFinalizados.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.botao_selecionado)));
                break;

            case PENDENTES:
                buttonPendentes.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.botao_selecionado)));
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_botao_tipo_de_pedido, container, false);

        //BUSCAR BOTÕES
        buttonAtivos = view.findViewById(R.id.btnAtivos);
        buttonFinalizados = view.findViewById(R.id.btnFinalizados);
        buttonPendentes = view.findViewById(R.id.btnPendentes);

        //OUVE BOTÕES
        buttonAtivos.setOnClickListener(this);
        buttonFinalizados.setOnClickListener(this);
        buttonPendentes.setOnClickListener(this);

        //VERIFICA SE BUNDLE JÁ POSSUI BOTÃO ESCOLHIDO
        if (getArguments().containsKey(OPTION_BUTTON)) {
            mChosenButon = getArguments().getInt(OPTION_BUTTON);

            if(mChosenButon == NONE){
                buttonPendentes.callOnClick();
            }
        }

        return view;
    }

    public static BotaoTipoDePedidoFragment newInstance(int optionButton) {

        //CRIA CLARE
        BotaoTipoDePedidoFragment fragment = new BotaoTipoDePedidoFragment();

        //CRIA INSTANCIA DO BUNDLE
        Bundle arguments = new Bundle();

        //NA INSTANCIAÇÃO DEFINE UM VALOR PADRAO NO BUNDLE
        arguments.putInt(OPTION_BUTTON, optionButton);

        //ATRIBUI O BUNDLE P/ INSTANCIA DE FRAGMENT CORRENTE
        fragment.setArguments(arguments);

        //DEVOLVE INSTANCIA + BUNDLE
        return fragment;
    }

}