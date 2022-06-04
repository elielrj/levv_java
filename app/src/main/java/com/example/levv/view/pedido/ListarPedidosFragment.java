package com.example.levv.view.pedido;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.levv.R;
import com.example.levv.controller.pedido.PedidoController;
import com.example.levv.model.bo.pedido.Pedido;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class ListarPedidosFragment extends Fragment {

    private static final String OPTION_WISH_LIST = "option_wish_list";
    private static final int PENDENTES = 2;
    private static final int NONE = 3;

    private static final int ATIVOS = 0;
    private static final int FINALIZADOS = 1;


    List<Pedido> pedidos;
    PedidoAdpter adapter;

    private PedidoController pedidoController;

    RecyclerView recyclerView;

    OnFragmentInterctionListenerOptionListaDePedidos mListenerOptionListaDePedidos;

    private int mChosenWishList = PENDENTES;

    public ListarPedidosFragment() {
    }

    public interface OnFragmentInterctionListenerOptionListaDePedidos {
        void onOptionListaDePedidos(int optionListaDePedidos);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        //RESGATA CONTEXT REFERENTE A ESTE FRAGMENT
        if (context instanceof OnFragmentInterctionListenerOptionListaDePedidos) {
            mListenerOptionListaDePedidos = (OnFragmentInterctionListenerOptionListaDePedidos) context;
        } else {
            throw new ClassCastException(context.toString() + "Fragmento Lista de pedidos não implementado!");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_listar_pedidos, container, false);

        //BUSCA RECYCLER VIEW
        recyclerView = view.findViewById(R.id.rvPedidos);

        pedidoController = new PedidoController();


        pedidos = pedidoController.list();





        //pedidos = pedidoController.list();

        //VERIFICA SE BUNDLE JÁ POSSUI BOTÃO ESCOLHIDO
        if (getArguments().containsKey(OPTION_WISH_LIST)) {

            mChosenWishList = getArguments().getInt(OPTION_WISH_LIST);

            if (mChosenWishList != NONE) {

                listarPedidos(mChosenWishList);

                Log.i("FRAG","teste Fragment pedidos");
                //LISTAR PEDIDOS PENDESTES DEFAULT
            } else {
                mListenerOptionListaDePedidos.onOptionListaDePedidos(mChosenWishList);

            }
        }


        return view;
    }


    private void listarPedidos(int chosen) {

        adapter = new PedidoAdpter(pedidos, getContext(), chosen);

        //SETA O ADAPTER NO RECYCLER VIEW
        recyclerView.setAdapter(adapter);

        //CRIA UM LAYOUT P/ O RECYCLER VIEW
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    }

    public void listarPedidosDoFragmente(int opcao){

        listarPedidos(opcao);

    }

    public static ListarPedidosFragment newInstance(int optionWishList) {

        //CRIA CLARE
        ListarPedidosFragment fragment = new ListarPedidosFragment();

        //CRIA INSTANCIA DO BUNDLE
        Bundle arguments = new Bundle();

        //NA INSTANCIAÇÃO DEFINE UM VALOR PADRAO NO BUNDLE
        arguments.putInt(OPTION_WISH_LIST, optionWishList);

        //ATRIBUI O BUNDLE P/ INSTANCIA DE FRAGMENT CORRENTE
        fragment.setArguments(arguments);


        //DEVOLVE INSTANCIA + BUNDLE
        return fragment;
    }
}